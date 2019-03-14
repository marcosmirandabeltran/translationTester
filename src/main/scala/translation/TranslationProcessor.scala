package translation

import utils.{INFO, JSONLoader, StashLogging}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import spray.json._
import DefaultJsonProtocol._
import model.ToTranslateData
import org.slf4j.LoggerFactory

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future

object TranslationProcessor {

  val log =
    new StashLogging(this.getClass).log("TranslationProcessor-Loading") _

  val jsonLanguageParser = new JSONLoader

  val URLPattern =
    "[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)".r
  val placeholderPattern = "\\{([0-9]+)\\}".r

  //Here we load the JSON files, this is a safe operation, as the jsonLanguageParser will log an exception and stop the service if the files are not being proccesed.
  val languageSamples: Map[String, Map[String, String]] =
    jsonLanguageParser
      .parseFile[Map[String, Map[String, String]]]("languagemapping.json")
      .left
      .get

  val languageCodeMapping =
    jsonLanguageParser
      .parseFile[Map[String, String]]("languagecodemappings.json")
      .left
      .get

  val displayLangMaping =
    jsonLanguageParser
      .parseFile[Map[String, String]]("displaylangmapping.json")
      .left
      .get
  val dotnetMapping = jsonLanguageParser
    .parseFile[Map[String, String]]("dotnetmapping.json")
    .left
    .get

  def translate(implicit toTranslateData: ToTranslateData): Future[String] = {
    Future(
      toTranslateData.src.toLowerCase match {
        case "enu"       => translateCode
        case "english"   => translateLanguage
        case "en-us"     => translateDotNetMapping
        case (x: String) => fakeTranslate(toTranslateData)
        case ""          => toTranslateData.src
      }
    )
  }

  def fakeTranslate(toTranslateData: ToTranslateData): String = {
    val lanData: Map[String, String] = languageSamples.getOrElse(
      toTranslateData.trgLng,
      languageSamples.getOrElse("de", Map.empty))

    //If the target language its not on the JSON file, we load german as default

    def processWord(str: String): String = {

      val matches =
        (URLPattern.findFirstIn(str), placeholderPattern.findFirstIn(str))

      //We detect if the word itself its a URL or a placeholder if it is , then we dont translate the word
      matches match {
        case (None, None) => translateChars(str, toTranslateData.expansion)
        case _ =>
          log(INFO,
              "Detected a match with at least one of the regex in " + str,
              "N/A"); str
      }
    }
    def translateChars(word: String, expansion: Int): String = {
      val expansionOne = 33
      val expansionTwo = 66

      expansion match {
        case 0 =>
          word.map(c => lanData.getOrElse(c.toString, c.toString)).mkString
        case 1 => applyExpansion(word, expansionOne)
        case 2 => applyExpansion(word, expansionTwo)
        case _ =>
          word.map(c => lanData.getOrElse(c.toString, c.toString)).mkString
      }

    }

    def applyExpansion(word: String, defaultExpansionPc: Int) = {
      val expansionShortString = 50
      val expansionLongStrings = 25
      //According to the doc in git.autodesk, words with less than 5 chars will add 1 every 2 chars
      // Words with between 5 and 7 chars, will add 1 of 4, and bigger than that, based on the value of the expansion
      // provided in the request
      val applicableExpansionPc = word.length match {
        case x if x < 5           => expansionShortString
        case x if x >= 5 && x < 7 => expansionLongStrings
        case x if x >= 7          => defaultExpansionPc
      }

      var strBuilder = StringBuilder.newBuilder
      for (char <- word) {
        val rnd = new scala.util.Random
        val randomNumber = rnd.nextInt(101)
        //We are generating a random number from 1 to 100
        // and applying the expansion pattern based on that (Ex: 1 char of 4 means a 25%)

        if (randomNumber < applicableExpansionPc) {
          strBuilder.append(
            lanData.getOrElse(char.toString, char.toString).toString)
        } else {
          strBuilder.append(
            lanData.getOrElse(char.toString, char.toString).mkString + lanData
              .getOrElse(char.toString, char.toString)
              .mkString)
        }
      }
      strBuilder.toString()
    }

    //We get the segment with placeholders to avoid translate rubbish, then we
    // split it in an array of words and translate all of the words to finally merge it together in just one string

    toTranslateData.src.split(" ").map(word => processWord(word)).mkString(" ")
  }

  def translateCode(implicit toTranslateData: ToTranslateData): String = {
    languageCodeMapping.get(toTranslateData.trgLng).get
  }

  def translateLanguage(implicit toTranslateData: ToTranslateData): String = {
    displayLangMaping.get(toTranslateData.trgLng).get
  }

  def translateDotNetMapping(
      implicit toTranslateData: ToTranslateData): String = {
    dotnetMapping.get(toTranslateData.trgLng).get
  }

}
