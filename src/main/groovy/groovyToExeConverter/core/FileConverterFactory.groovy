package groovyToExeConverter.core

import groovyToExeConverter.core.common.resource.ResourceHandlerFactory
import groovyToExeConverter.core.groovyToExe.GroovyToExeFileConverter
import groovyToExeConverter.core.jarToExe.JarToExeFileConverter
import groovyToExeConverter.model.AppConfig

import static org.apache.commons.io.FilenameUtils.isExtension

class FileConverterFactory {

    static FileConverter makeFileConverter(AppConfig appConfig) {
        switch (appConfig.fileToConvert) {
            case isJarFile:
                return new JarToExeFileConverter([
                        appConfig      : appConfig,
                        resourceHandler: ResourceHandlerFactory.makeJarFileResourceHandler(appConfig)
                ])
            case isGroovyFile:
                return new GroovyToExeFileConverter([
                        appConfig      : appConfig,
                        resourceHandler: ResourceHandlerFactory.makeGroovyScriptResourceHandler(appConfig)
                ])
        }
    }

    private static def isJarFile = { File file -> isExtension(file.name, "jar") }
    private static def isGroovyFile = { File file -> isExtension(file.name, "groovy") }
}
