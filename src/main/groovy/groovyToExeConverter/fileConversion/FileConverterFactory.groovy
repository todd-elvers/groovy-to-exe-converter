package groovyToExeConverter.fileConversion

import groovyToExeConverter.domain.AppConfig
import groovyToExeConverter.fileConversion.converterHelpers.resource.ResourceHandlerFactory
import groovyToExeConverter.fileConversion.converters.GroovyToExeFileConverter
import groovyToExeConverter.fileConversion.converters.JarToExeFileConverter

import static org.apache.commons.io.FilenameUtils.isExtension

class FileConverterFactory {

    static FileConverter makeFileConverter(AppConfig appConfig) {
        switch (appConfig.fileToConvert) {
            case isJarFile:
                def resourceHandler = ResourceHandlerFactory.makeJarFileResourceHandler(appConfig)
                return new JarToExeFileConverter(
                        appConfig: appConfig,
                        resourceHandler: resourceHandler
                )
            case isGroovyFile:
                def resourceHandler = ResourceHandlerFactory.makeGroovyScriptResourceHandler(appConfig)
                return new GroovyToExeFileConverter(
                        appConfig: appConfig,
                        resourceHandler: resourceHandler
                )
        }
    }

    private static def isJarFile = { File file -> isExtension(file.name, "jar") }
    private static def isGroovyFile = { File file -> isExtension(file.name, "groovy") }
}
