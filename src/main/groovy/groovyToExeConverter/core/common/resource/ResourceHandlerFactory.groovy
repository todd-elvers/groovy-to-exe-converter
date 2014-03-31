package groovyToExeConverter.core.common.resource

import groovyToExeConverter.model.AppConfig

class ResourceHandlerFactory {

    static ResourceHandler makeJarFileResourceHandler(AppConfig appConfig) {
        def resourceHandler = new ResourceHandler(appConfig.temporaryDirectory)
        resourceHandler.copyFileToTempDir(appConfig.fileToConvert)
        return resourceHandler
    }

    static ResourceHandler makeGroovyScriptResourceHandler(AppConfig appConfig) {
        return new ResourceHandler(appConfig.temporaryDirectory)
    }

}
