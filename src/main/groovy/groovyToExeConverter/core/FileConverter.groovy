package groovyToExeConverter.core

import groovy.util.logging.Log4j
import groovyToExeConverter.core.common.resource.ResourceHandler
import groovyToExeConverter.model.AppConfig

@Log4j
abstract class FileConverter {
    ResourceHandler resourceHandler
    AppConfig appConfig

    protected FileConverter(){}

    abstract File convert()
}