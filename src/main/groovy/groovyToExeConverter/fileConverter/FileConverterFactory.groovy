package groovyToExeConverter.fileConverter

import groovyToExeConverter.domain.AppConfig
import groovyToExeConverter.fileConverter.converterHelpers.ResourceHandler
import groovyToExeConverter.fileConverter.converters.GroovyToExeFileConverter
import groovyToExeConverter.fileConverter.converters.JarToExeFileConverter

import static org.apache.commons.io.FilenameUtils.isExtension

class FileConverterFactory {

    static FileConverter getFileConverter(AppConfig appConfig) {
        def resourceHandler = new ResourceHandler(appConfig.temporaryDirectory)

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

    private static def isJarFile = { File file -> isExtension(file.name, "jar") }
    private static def isGroovyFile = { File file -> isExtension(file.name, "groovy") }
}
