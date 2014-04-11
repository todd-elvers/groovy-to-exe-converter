package groovyToExeConverter

import groovy.util.logging.Log4j
import groovyToExeConverter.core.FileConverterFactory
import groovyToExeConverter.input.InputProcessor
import groovyToExeConverter.model.AppConfig
import groovyToExeConverter.util.EnvironmentHandler
import groovyToExeConverter.util.ExceptionHandler

@Log4j
class GroovyToExeConverterRunner implements Runnable {

    //TODO: More integration testing
    //TODO: Final refactoring
    //TODO: Update/complete the readme
    //TODO: Update with latest launch4j from: http://sourceforge.net/projects/launch4j/
    //TODO: Ensure I can reference Launch4j by explicitly messaging 'grzegok'
    static void main(String[] args) {
        new GroovyToExeConverterRunner(input: args).run()
    }


    def inputProcessor = new InputProcessor(),
        environmentHandler = new EnvironmentHandler()

    String[] input

    @Override
    void run() {
        AppConfig appConfig

        try {
            environmentHandler.validateGroovyHome()
            environmentHandler.addShutdownHookToKillG2exeProcess()

            appConfig = inputProcessor.processIntoAppConfig(input)

            if (appConfig) {
                log.info("Converting...")
                FileConverterFactory.makeFileConverter(appConfig).convert()
                log.info("Conversion successful!")
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex, appConfig, input)
        }
    }
}
