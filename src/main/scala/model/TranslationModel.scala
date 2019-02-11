package model

import translation.TranslationProcessor

class TranslationModel(toTranslateRequest: ToTranslateRequest) {
  def translate() = {
    toTranslateRequest.segments.map(src => TranslationProcessor.translate(src, "fra-FR"))
  }

}
