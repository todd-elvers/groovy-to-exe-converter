package groovyToExeConverter.core.groovyToJar

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import groovyToExeConverter.core.FileConverter
import groovyToExeConverter.core.groovyToJar.core.GroovyScriptMainMethodFinder
import groovyToExeConverter.core.groovyToJar.core.JarBuilder

@Log4j
@CompileStatic
class GroovyToJarFileConverter extends FileConverter {

    @Override
    File convert() {
        File script = resourceHandler.copyFileToTempDir(appConfig.fileToConvert)
        File jarFile = resourceHandler.createFileInTempDir(appConfig.jarFileName)

        String mainClassName = GroovyScriptMainMethodFinder.findNameOfClassWithMainMethod(script)

        new JarBuilder().with {
            mainClass = mainClassName
            destinationFile = jarFile
            build()
        }
    }

}
