package groovyToExeConverter.core.groovyToJar

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import groovyToExeConverter.core.FileConverter
import groovyToExeConverter.core.groovyToJar.core.JarBuilder

import static groovyToExeConverter.core.groovyToJar.core.GroovyScriptHandler.compileScriptToClassFilesInSameDirAndLoadIntoMemory
import static groovyToExeConverter.core.groovyToJar.core.GroovyScriptHandler.findNameOfClassWithMainMethod

@Log4j
@CompileStatic
class GroovyToJarFileConverter extends FileConverter {

    @Override
    File convert() {
        log.info("${appConfig.fileToConvert.name} --> ${appConfig.jarFileName}")

        final script = resourceHandler.copyFileToTempDir(appConfig.fileToConvert)
        final loadedScript = compileScriptToClassFilesInSameDirAndLoadIntoMemory(script)
        final mainClassName = findNameOfClassWithMainMethod(loadedScript)
        final jarFile = resourceHandler.createFileInTempDir(appConfig.jarFileName)

        new JarBuilder(mainClass: mainClassName, destFile: jarFile).build()
    }

}
