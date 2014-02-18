package groovyToExeConverter

import groovy.util.logging.Log4j
import groovyToExeConverter.commandLine.CommandLineInputProcessor

import static groovyToExeConverter.fileConverter.FileConverterFactory.getFileConverter
import static org.apache.commons.io.FileUtils.copyFileToDirectory

@Log4j
class GroovyToExeConverterRunner implements Runnable {

    static void main(String[] args) {
        new GroovyToExeConverterRunner(commandLineInput: args).run()
    }

    private CommandLineInputProcessor commandLineInputProcessor = new CommandLineInputProcessor()
    private String[] commandLineInput

    @Override
    void run() {
        def appConfig, exeFile

        try {
            appConfig = commandLineInputProcessor.processIntoAppConfig(commandLineInput)
            log.info("Converting '${appConfig.fileToConvert.name}' to an executable...")

            exeFile = getFileConverter(appConfig).convert()
            copyFileToDirectory(exeFile, appConfig.destinationDirectory)

            log.info("Result: ${new File(appConfig.destinationDirectory, exeFile.name)}")
        } catch (Exception exception) {
            if (exception.message) log.error(exception.message)
            if (appConfig?.showStackTrace) log.error("Stacktrace:", exception)
        }
    }
}
