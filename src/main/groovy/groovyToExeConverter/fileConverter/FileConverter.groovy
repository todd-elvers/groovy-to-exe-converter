package groovyToExeConverter.fileConverter

import groovy.util.logging.Log4j
import groovyToExeConverter.domain.AppConfig
import groovyToExeConverter.fileConverter.converterHelpers.resource.ResourceHandler

@Log4j
abstract class FileConverter {
    ResourceHandler resourceHandler
    AppConfig appConfig

    protected FileConverter(){}

    abstract File convert()
}