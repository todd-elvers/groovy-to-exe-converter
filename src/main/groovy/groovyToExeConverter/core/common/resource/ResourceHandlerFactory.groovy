package groovyToExeConverter.core.common.resource

import groovyToExeConverter.model.AppConfig

class ResourceHandlerFactory {

    static ResourceHandler makeJarFileResourceHandler(AppConfig appConfig) {
        new ResourceHandler(appConfig.temporaryDirectory).with {
            copyFileToTempDir(appConfig.fileToConvert)
            return it
        }
    }

    static ResourceHandler makeGroovyScriptResourceHandler(AppConfig appConfig) {
        return new ResourceHandler(appConfig.temporaryDirectory)
    }

}
