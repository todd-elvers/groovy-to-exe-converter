package groovyToExeConverter.core.fileConverter.converters

import groovy.util.logging.Log4j
import groovyToExeConverter.core.fileConverter.FileConverter

@Log4j
class GroovyToExeFileConverter extends FileConverter {

    File convert() {
        log.info("Creating executable from ${appConfig.fileToConvert.name}")
        def g2jarConverter = new GroovyToJarFileConverter(
                appConfig       : this.appConfig,
                resourceHandler : this.resourceHandler
        )
        def jarFile = g2jarConverter.convert()



        def j2exeConverter = new JarToExeFileConverter(
                appConfig       : this.appConfig,
                resourceHandler : this.resourceHandler
        )
        def exeFile = j2exeConverter.convert()

        return exeFile
    }
}
