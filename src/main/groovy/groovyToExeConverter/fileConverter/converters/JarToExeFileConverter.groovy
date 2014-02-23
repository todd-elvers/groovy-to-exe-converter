package groovyToExeConverter.fileConverter.converters

import groovy.util.logging.Log4j
import groovyToExeConverter.exception.CompilationException
import groovyToExeConverter.fileConverter.FileConverter
import groovyToExeConverter.fileConverter.handlers.Launch4jXmlHandler

@Log4j
class JarToExeFileConverter extends FileConverter {

    @Override
    File convert() {
        log.info("${appConfig.jarFileName.padRight(appConfig.fileToConvert.name.length())} --> ${appConfig.exeFileName}")

        def jarFile = resourceHandler.findFileInTempDir(appConfig.jarFileName)
        def exeFile = resourceHandler.createFileInTempDir(appConfig.exeFileName)
        def xmlFile = resourceHandler.createFileInTempDir('launch4jc_config.xml')

        new Launch4jXmlHandler().with {
            generateXmlConfigContents(appConfig, jarFile, exeFile)
            writeXmlConfigContentsTo(xmlFile)
        }

        executeLaunch4jcWithXmlFile(xmlFile)
        validateExeCreation(exeFile)

        return exeFile
    }

    private void executeLaunch4jcWithXmlFile(File launch4jcXmlFile) {
        def launch4jcExeFile = resourceHandler.resolveLaunch4jcExecutableHandle()
        def launch4jcCommand = "\"${launch4jcExeFile}\" \"${launch4jcXmlFile}\""

        Process command = launch4jcCommand.execute()
        pipeCommandOutputToConsole(command)
    }

    private static void pipeCommandOutputToConsole(Process command){
        List commandOutputLines = command.text?.split("\r\n")
        boolean commandFailed = !commandOutputLines?.any { it.contains("Successfully") }
        commandOutputLines.each { String line ->
            if (commandFailed) log.info(line.replace('launch4j: ', ''))
        }
    }

    //TODO: Is a compilation exception the right kind of exception?
    private void validateExeCreation(File exeFile) {
        if (!exeFile.exists()) {
            throw new CompilationException("Failed to convert ${appConfig.jarFileName} to ${appConfig.exeFileName}.")
        }
    }

}
