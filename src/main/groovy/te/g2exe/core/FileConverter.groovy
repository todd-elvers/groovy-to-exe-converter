package te.g2exe.core

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import te.g2exe.core.common.resource.ResourceHandler
import te.g2exe.model.AppConfig

@Log4j
@CompileStatic
abstract class FileConverter {
    ResourceHandler resourceHandler
    AppConfig appConfig

    protected FileConverter(){}

    abstract File convert()
}