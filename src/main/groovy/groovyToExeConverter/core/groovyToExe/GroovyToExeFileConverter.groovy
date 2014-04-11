package groovyToExeConverter.core.groovyToExe

import groovy.util.logging.Log4j
import groovyToExeConverter.core.FileConverter
import groovyToExeConverter.core.groovyToJar.GroovyToJarFileConverter
import groovyToExeConverter.core.jarToExee.JarToExeFileConverter

@Log4j
class GroovyToExeFileConverter extends FileConverter {

    File convert() {
        new GroovyToJarFileConverter(appConfig: super.appConfig, resourceHandler: super.resourceHandler).convert()
        new JarToExeFileConverter   (appConfig: super.appConfig, resourceHandler: super.resourceHandler).convert()
    }

}
