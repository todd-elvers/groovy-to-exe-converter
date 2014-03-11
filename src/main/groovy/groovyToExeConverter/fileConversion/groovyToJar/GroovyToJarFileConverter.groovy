package groovyToExeConverter.fileConversion.groovyToJar
import groovy.util.logging.Log4j
import groovyToExeConverter.fileConversion.FileConverter
import groovyToExeConverter.fileConversion.groovyToJar.core.JarBuilder

import static groovyToExeConverter.fileConversion.groovyToJar.core.GroovyScriptHandler.compileScriptToClassFilesInSameDirAndLoadIntoMemory
import static groovyToExeConverter.fileConversion.groovyToJar.core.GroovyScriptHandler.findNameOfClassWithMainMethod

@Log4j
class GroovyToJarFileConverter extends FileConverter {

    @Override
    File convert() {
        log.info("${appConfig.fileToConvert.name} --> ${appConfig.jarFileName}")

        def script = resourceHandler.copyFileToTempDir(appConfig.fileToConvert)
        def loadedScript = compileScriptToClassFilesInSameDirAndLoadIntoMemory(script)
        def mainClassName = findNameOfClassWithMainMethod(loadedScript)
        def jarFile = resourceHandler.createFileInTempDir(appConfig.jarFileName)

        new JarBuilder(mainClass: mainClassName, destFile: jarFile).build()
    }

}
