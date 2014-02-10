package groovyToExeConverter.core.fileConverter.converters
import groovy.util.logging.Log4j
import groovyToExeConverter.core.fileConverter.core.Launch4jXmlHandler
import groovyToExeConverter.core.exception.CompilationException
import groovyToExeConverter.core.fileConverter.FileConverter

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

        log.info("Success! Result: $exeFile")
        return exeFile
    }

    private void executeLaunch4jcWithXmlFile(File launch4jcXmlFile) {
        def launch4jcExeFile = resourceHandler.resolveLaunch4jcExecutableHandle()
        def launch4jcCommand = "\"${launch4jcExeFile}\" \"${launch4jcXmlFile}\""

        Process process = launch4jcCommand.execute()
        process.text.split("\n").each { log.debug(it) }
    }

    private void validateExeCreation(File exeFile){
        if(!exeFile.exists()) {
            throw new CompilationException("An unknown error occurred while calling launch4jc.exe " +
                    "to convert ${appConfig.jarFileName} to ${appConfig.exeFileName}.")
        }
    }
}
