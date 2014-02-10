package groovyToExeConverter.core.fileConverter

import groovyToExeConverter.core.fileConverter.converters.GroovyToExeFileConverter
import groovyToExeConverter.core.fileConverter.converters.JarToExeFileConverter
import groovyToExeConverter.core.fileConverter.core.ResourceHandler
import groovyToExeConverter.domain.AppConfig

class FileConverterFactory {

    static FileConverter getFileConverter(AppConfig appConfig) {
        ResourceHandler resourceHandler = new ResourceHandler(appConfig.temporaryDirectory)

        switch (appConfig.fileToConvert) {
            case isJarFile:
                resourceHandler.copyFileToTempDir(appConfig.fileToConvert)
                return new JarToExeFileConverter(
                        appConfig: appConfig,
                        resourceHandler: resourceHandler
                )
            case isGroovyFile:
                return new GroovyToExeFileConverter(
                        appConfig: appConfig,
                        resourceHandler: resourceHandler
                )
        }
    }

    private static def isJarFile = { File f -> f.name.endsWith(".jar") }
    private static def isGroovyFile = { File f -> f.name.endsWith(".groovy") }
}
