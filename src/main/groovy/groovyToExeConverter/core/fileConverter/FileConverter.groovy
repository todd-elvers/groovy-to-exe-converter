package groovyToExeConverter.core.fileConverter

import groovy.transform.TupleConstructor
import groovyToExeConverter.core.fileConverter.core.ResourceHandler
import groovyToExeConverter.domain.AppConfig

@TupleConstructor
abstract class FileConverter {
    ResourceHandler resourceHandler
    AppConfig appConfig

    abstract File convert();
}