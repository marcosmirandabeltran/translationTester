package utils

  class JSONLoader {
    import java.io.InputStream
    import java.nio.charset.{Charset, CodingErrorAction}

    import spray.json.{JsonReader, _}

    import scala.io.{Codec, Source}


    def parseFile[T: JsonReader](fileName: String): Either[T, None.type] = {

      try {
        val stream: InputStream = getClass.getClassLoader.getResourceAsStream(fileName)

        val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)

        val fileString = Source.fromInputStream(stream)(decoder).getLines().mkString

        val json = fileString.parseJson
        Left(json.convertTo[T])
      } catch {
        case ex: Throwable =>
          System.exit(1)
          Right(None)
      }
    }



}
