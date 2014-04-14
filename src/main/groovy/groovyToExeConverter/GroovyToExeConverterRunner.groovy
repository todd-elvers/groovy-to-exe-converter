package groovyToExeConverter

import groovy.util.logging.Log4j
import groovyToExeConverter.core.FileConverterFactory
import groovyToExeConverter.input.InputProcessor
import groovyToExeConverter.model.AppConfig
import groovyToExeConverter.util.EnvironmentHandler
import groovyToExeConverter.util.ExceptionHandler

@Log4j
class GroovyToExeConverterRunner implements Runnable {

    static void main(String[] args) {
        new GroovyToExeConverterRunner(args: args).run()
    }


    def inputProcessor = new InputProcessor(),
        environmentHandler = new EnvironmentHandler()

    String[] args

    @Override
    void run() {
        AppConfig appConfig

        try {
            environmentHandler.validateGroovyHome()
            environmentHandler.addShutdownHookToKillG2exeProcess()

            appConfig = inputProcessor.processIntoAppConfig(args)

            if (appConfig) {
                log.info("Converting...")
                FileConverterFactory.makeFileConverter(appConfig).convert()
                log.info("Conversion successful!")
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex, appConfig, args)
        }
    }
}
