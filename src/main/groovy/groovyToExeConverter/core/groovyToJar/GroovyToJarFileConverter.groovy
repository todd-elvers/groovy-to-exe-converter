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
        log.info("${appConfig.fileToConvert.name} --> ${appConfig.jarFileName}")

        File script = resourceHandler.copyFileToTempDir(appConfig.fileToConvert)
        File jarFile = resourceHandler.createFileInTempDir(appConfig.jarFileName)

        new JarBuilder(
                mainClass: GroovyScriptMainMethodFinder.findNameOfClassWithMainMethod(script),
                destFile : jarFile
        ).build()
    }

}
