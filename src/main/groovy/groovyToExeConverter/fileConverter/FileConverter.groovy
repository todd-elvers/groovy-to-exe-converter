package groovyToExeConverter.fileConverter

import groovyToExeConverter.domain.AppConfig
import groovyToExeConverter.fileConverter.converterHelpers.ResourceHandler

abstract class FileConverter {
    ResourceHandler resourceHandler
    AppConfig appConfig

    protected FileConverter(){}

    abstract File convert();
}