package groovyToExeConverter.core.fileConverter.converters

import groovy.util.logging.Log4j
import groovyToExeConverter.core.exception.CompilationException
import groovyToExeConverter.core.fileConverter.FileConverter
import groovyToExeConverter.core.fileConverter.core.Launch4jXmlHandler

@Log4j
class JarToExeFileConverter extends FileConverter {

    @Override
    File convert() {
        log.info("Converting ${appConfig.jarFileName} --> ${appConfig.exeFileName}")

        def jarFile = resourceHandler.findFileInTempDir(appConfig.jarFileName)
        def exeFile = resourceHandler.createFileInTempDir(appConfig.exeFileName)
        def launch4jcXmlFile = resourceHandler.createFileInTempDir('launch4jc_config.xml')

        new Launch4jXmlHandler().with {
            generateXmlConfigContents(appConfig, jarFile, exeFile)
            writeXmlConfigContentsTo(launch4jcXmlFile)
        }

        executeLaunch4jcWithXmlFile(launch4jcXmlFile)
        validateExeCreation(exeFile)

        return exeFile
    }

    private void executeLaunch4jcWithXmlFile(File launch4jcXmlFile) {
        def launch4jcExeFile = resourceHandler.resolveLaunch4jcExecutableHandle()
        def launch4jcCommand = "\"${launch4jcExeFile}\" \"${launch4jcXmlFile}\""

        List commandOutputLines = launch4jcCommand.execute().text?.split("\r\n")
        boolean commandFailed = !commandOutputLines?.any { it.contains("Successfully") }
        commandOutputLines.each { String line ->
            if (commandFailed) log.info(line.replace('launch4j: ', ''))
        }
    }

//TODO: Compilation exception right kind of exception?
    private void validateExeCreation(File exeFile) {
        if (!exeFile.exists()) {
            throw new CompilationException("Failed to convert ${appConfig.jarFileName} to ${appConfig.exeFileName}.")
        }
    }

}
