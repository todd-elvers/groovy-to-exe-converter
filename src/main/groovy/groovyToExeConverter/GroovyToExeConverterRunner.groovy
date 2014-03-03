package groovyToExeConverter
import groovy.util.logging.Log4j
import groovyToExeConverter.input.InputProcessor
import groovyToExeConverter.util.EnvironmentValidator

import static groovyToExeConverter.fileConverter.FileConverterFactory.getFileConverter
import static org.apache.commons.io.FileUtils.copyFileToDirectory

@Log4j
class GroovyToExeConverterRunner implements Runnable {


    //TODO: Add unit testing for important components
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
            if (exception.message) log.error(exception.message)
            if (appConfig?.showStackTrace) log.error("Stacktrace:", exception)
            if (appConfig) log.error("Conversion failed!")
        }
    }
}
