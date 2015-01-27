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

        log.info("Compiling groovy script.")
        String mainClassName = GroovyScriptMainMethodFinder.findNameOfClassWithMainMethod(script)

        log.info("Bundling compiled script & groovy libraries into JAR.")
        new JarBuilder(
                mainClass: mainClassName,
                destFile : jarFile
        ).build()
    }

}
