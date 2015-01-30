package groovyToExeConverter.core

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import groovyToExeConverter.core.common.resource.ResourceHandler
import groovyToExeConverter.model.AppConfig

@Log4j
@CompileStatic
abstract class FileConverter {
    ResourceHandler resourceHandler
    AppConfig appConfig

    protected FileConverter(){}

    abstract File convert()
}