package groovyToExeConverter
import groovy.util.logging.Log4j
import groovyToExeConverter.commandLine.InputProcessor
import groovyToExeConverter.util.EnvironmentValidator

import static groovyToExeConverter.fileConverter.FileConverterFactory.getFileConverter
import static org.apache.commons.io.FileUtils.copyFileToDirectory

@Log4j
class GroovyToExeConverterRunner implements Runnable {

    //TODO: Add ability to set header to 'gui' OR 'console'
    //TODO: Add ability to set splash screen
    //TODO: Add ability to inject 'normal look-and-feel' code into main method?
    static void main(String[] args) {
        new GroovyToExeConverterRunner(input: args).run()
    }



    private def inputProcessor = new InputProcessor()
    private def environmentValidator = new EnvironmentValidator()
    String[] input

    @Override
    void run() {
        def appConfig, exeFile

        try {
            environmentValidator.validate()
            appConfig = inputProcessor.processIntoAppConfig(input)

            if (appConfig) {
                log.info("Converting...")

                exeFile = getFileConverter(appConfig).convert()
                copyFileToDirectory(exeFile, appConfig.destinationDirectory)

                log.info("Conversion successful!")
            }
        } catch (Exception exception) {
            log.error("Conversion failed!")
            if (exception.message) log.error("Reason: ${exception.message}")
            if (appConfig?.showStackTrace) log.error("Stacktrace:", exception)
        }
    }
}
