package te.g2exe.core.groovyToJar

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import te.g2exe.core.FileConverter
import te.g2exe.core.groovyToJar.core.GroovyScriptMainMethodFinder
import te.g2exe.core.groovyToJar.core.JarBuilder

@Log4j
@CompileStatic
class GroovyToJarFileConverter extends FileConverter {

    @Override
    File convert() {
        File script = resourceHandler.copyFileToTempDir(appConfig.fileToConvert)
        File jarFile = resourceHandler.createFileInTempDir(appConfig.jarFileName)

        String mainClassName = GroovyScriptMainMethodFinder.findNameOfClassWithMainMethod(script)

        new JarBuilder().with {
            setMainClass(mainClassName)
            setDestinationFile(jarFile)
            build()
        }
    }

}
