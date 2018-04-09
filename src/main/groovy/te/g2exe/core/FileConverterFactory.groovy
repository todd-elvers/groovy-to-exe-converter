package te.g2exe.core

import te.g2exe.core.common.resource.ResourceHandlerFactory
import te.g2exe.core.groovyToExe.GroovyToExeFileConverter
import te.g2exe.core.jarToExe.JarToExeFileConverter
import te.g2exe.model.AppConfig

import static org.apache.commons.io.FilenameUtils.isExtension

class FileConverterFactory {
    private static Closure<Boolean> isJarFile = { File f -> isExtension(f.name, "jar") }
    private static Closure<Boolean> isGroovyFile = { File f -> isExtension(f.name, "groovy") }

    static FileConverter makeFileConverter(AppConfig appConfig) {
        switch (appConfig.fileToConvert) {
            case isJarFile:
                return new JarToExeFileConverter(
                        appConfig      : appConfig,
                        resourceHandler: ResourceHandlerFactory.makeJarFileResourceHandler(appConfig)
                )
            case isGroovyFile:
                return new GroovyToExeFileConverter(
                        appConfig      : appConfig,
                        resourceHandler: ResourceHandlerFactory.makeGroovyScriptResourceHandler(appConfig)
                )
        }
    }

}
