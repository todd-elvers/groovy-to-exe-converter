package te.g2exe.core.groovyToExe

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import te.g2exe.core.FileConverter
import te.g2exe.core.groovyToJar.GroovyToJarFileConverter
import te.g2exe.core.jarToExe.JarToExeFileConverter

@Slf4j
@CompileStatic
class GroovyToExeFileConverter extends FileConverter {

    File convert() {
        new GroovyToJarFileConverter(appConfig: super.appConfig, resourceHandler: super.resourceHandler).convert()
        new JarToExeFileConverter   (appConfig: super.appConfig, resourceHandler: super.resourceHandler).convert()
    }

}
