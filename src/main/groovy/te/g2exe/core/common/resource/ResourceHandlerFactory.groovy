package te.g2exe.core.common.resource

import groovy.transform.CompileStatic
import te.g2exe.model.AppConfig

@CompileStatic
class ResourceHandlerFactory {

    static ResourceHandler makeJarFileResourceHandler(AppConfig appConfig) {
        ResourceHandler resourceHandler = new ResourceHandler(appConfig.temporaryDirectory)
        resourceHandler.copyFileToTempDir(appConfig.fileToConvert)
        return resourceHandler
    }

    static ResourceHandler makeGroovyScriptResourceHandler(AppConfig appConfig) {
        return new ResourceHandler(appConfig.temporaryDirectory)
    }

}
