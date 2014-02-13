package groovyToExeConverter
import groovy.util.logging.Log4j
import groovyToExeConverter.core.commandLine.CommandLineInputProcessor
import groovyToExeConverter.core.fileConverter.FileConverterFactory
import org.apache.commons.io.FileUtils

@Log4j
class GroovyToExeConverterRunner implements Runnable {

    static void main(String[] args) {
        new GroovyToExeConverterRunner(commandLineInput: args).run()
    }

    private static CommandLineInputProcessor inputProcessor = new CommandLineInputProcessor()
    String[] commandLineInput

    @Override
    void run() {
        def appConfig, fileConverter, exeFile

        try {
            appConfig = inputProcessor.processIntoAppConfig(commandLineInput)

            fileConverter = FileConverterFactory.getFileConverter(appConfig)
            exeFile = fileConverter.convert()

            FileUtils.copyFileToDirectory(exeFile, appConfig.destinationDirectory)
            log.info("Result: ${new File(appConfig.destinationDirectory, exeFile.name)}")
        } catch (Exception exception) {
            log.error(exception.message)
            if (appConfig?.showStackTrace) {
                log.error("Stacktrace:", exception)
            }
        }
    }


}
