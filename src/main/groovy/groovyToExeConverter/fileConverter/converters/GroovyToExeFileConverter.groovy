package groovyToExeConverter.fileConverter.converters

import groovy.util.logging.Log4j
import groovyToExeConverter.fileConverter.FileConverter

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
