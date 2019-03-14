package utils

import scala.util.parsing.json.{JSONFormat, JSONObject}
import org.slf4j.{Logger, LoggerFactory}
import net.logstash.logback.marker.Markers.appendRaw


  sealed trait LoggingLevel
  case class ERROR(throwable: Option[Throwable] = None) extends LoggingLevel
  case object WARN                                      extends LoggingLevel
  case object DEBUG                                     extends LoggingLevel
  case object INFO                                      extends LoggingLevel

  class StashLogging(val clazz: Class[_]) {

    val logger: Logger = LoggerFactory.getLogger(clazz)

    def log(step: String)(logLevel: LoggingLevel, message: String, uuid: String = "NA"): Unit = {
      var map = Map[String, String]("step" -> step)
      if (uuid != "NA") map = map + ("uuid" -> uuid)
      log(logLevel)(map)(message)
    }

    def log(loggingLevel: LoggingLevel)(fields: Map[String, String])(message: String): Unit = {
      val jsonStr: String = JSONObject(fields).toString(JSONFormat.defaultFormatter)
      // use marker to log additional fields as proper json for easy search and extraction
      val marker = appendRaw("fields", jsonStr)
      loggingLevel match {
        case ERROR(t) => if (t.nonEmpty) logger.error(marker, message, t.get) else logger.error(marker, message)
        case WARN     => logger.warn(marker, message)
        case INFO     => logger.info(marker, message)
        case DEBUG    => logger.debug(marker, message)
      }
    }
}
