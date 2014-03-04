package groovyToExeConverter
import groovy.util.logging.Log4j
import groovyToExeConverter.domain.AppConfig
import groovyToExeConverter.input.InputProcessor
import groovyToExeConverter.util.EnvironmentValidator

import static groovyToExeConverter.fileConverter.FileConverterFactory.makeFileConverter

@Log4j
class GroovyToExeConverterRunner implements Runnable {


    //TODO: Add unit testing for important components
    //TODO: Update readme
    static void main(String[] args) {
        new GroovyToExeConverterRunner(input: args).run()
    }



    def inputProcessor = new InputProcessor()
    def environmentValidator = new EnvironmentValidator()
    String[] input

    @Override
    void run() {
        def appConfig

        try {
            environmentValidator.validate()
            appConfig = inputProcessor.processIntoAppConfig(input)

            if (appConfig) {
                log.info("Converting...")

                makeFileConverter(appConfig).convert()

                log.info("Conversion successful!")
            }
        } catch (Exception exception) {
            handleException(exception, appConfig)
        }
    }

    private static void handleException(Exception exception, AppConfig appConfig){
        if (exception.message) log.error(exception.message)
        if (appConfig?.showStackTrace) log.error("Stacktrace:", exception)
        if (appConfig) log.error("Conversion failed!")
    }
}
