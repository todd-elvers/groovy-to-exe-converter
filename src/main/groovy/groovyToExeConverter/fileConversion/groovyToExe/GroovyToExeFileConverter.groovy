package groovyToExeConverter.fileConversion.groovyToExe

import groovy.util.logging.Log4j
import groovyToExeConverter.fileConversion.FileConverter
import groovyToExeConverter.fileConversion.JarToExe.JarToExeFileConverter
import groovyToExeConverter.fileConversion.groovyToJar.GroovyToJarFileConverter

@Log4j
class GroovyToExeFileConverter extends FileConverter {

    File convert() {
        new GroovyToJarFileConverter(
                appConfig: appConfig,
                resourceHandler: resourceHandler
        ).convert()

        new JarToExeFileConverter(
                appConfig: appConfig,
                resourceHandler: resourceHandler
        ).convert()
    }
}
