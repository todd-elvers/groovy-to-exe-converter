package groovyToExeConverter.fileConverter

import groovy.transform.TupleConstructor
import groovyToExeConverter.domain.AppConfig
import groovyToExeConverter.fileConverter.helpers.ResourceHandler

@TupleConstructor
abstract class FileConverter {
    ResourceHandler resourceHandler
    AppConfig appConfig

    abstract File convert();
}