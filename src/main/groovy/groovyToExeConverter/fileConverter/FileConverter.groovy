package groovyToExeConverter.fileConverter

import groovy.transform.TupleConstructor
import groovyToExeConverter.domain.AppConfig
import groovyToExeConverter.fileConverter.core.ResourceHandler

@TupleConstructor
abstract class FileConverter {
    ResourceHandler resourceHandler
    AppConfig appConfig

    abstract File convert();
}