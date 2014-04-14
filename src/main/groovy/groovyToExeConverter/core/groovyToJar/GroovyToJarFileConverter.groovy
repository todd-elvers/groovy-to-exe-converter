package groovyToExeConverter.core.groovyToJar

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import groovyToExeConverter.core.FileConverter
import groovyToExeConverter.core.groovyToJar.core.GroovyScriptHandler
import groovyToExeConverter.core.groovyToJar.core.JarBuilder

@Log4j
@CompileStatic
class GroovyToJarFileConverter extends FileConverter {

    @Override
    File convert() {
        log.info("${appConfig.fileToConvert.name} --> ${appConfig.jarFileName}")

        final script = resourceHandler.copyFileToTempDir(appConfig.fileToConvert)
        final loadedScript = GroovyScriptHandler.loadScriptIntoMemoryAndCompile(script)
        final mainClassName = GroovyScriptHandler.findNameOfClassWithMainMethod(loadedScript)
        final jarFile = resourceHandler.createFileInTempDir(appConfig.jarFileName)

        new JarBuilder(mainClass: mainClassName, destFile: jarFile).build()
    }

}
