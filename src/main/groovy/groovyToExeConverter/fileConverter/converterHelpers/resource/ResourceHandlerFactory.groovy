package groovyToExeConverter.fileConverter.converterHelpers.resource

import groovyToExeConverter.domain.AppConfig

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
