package groovyToExeConverter
import groovy.util.logging.Log4j
import groovyToExeConverter.core.FileConverterFactory
import groovyToExeConverter.input.InputProcessor
import groovyToExeConverter.util.EnvironmentValidator
import groovyToExeConverter.util.ExceptionHandler

@Log4j
class GroovyToExeConverterRunner implements Runnable {

    //TODO: More integration testing
    //TODO: Final refactoring
    //TODO: Update/complete the readme
    //TODO: Determine which software license to use
    static void main(String[] args) {
        new GroovyToExeConverterRunner(input: args).run()
    }



    final inputProcessor = new InputProcessor()
    final environmentValidator = new EnvironmentValidator()
    String[] input

    @Override
    void run() {
        def appConfig

        try {
            environmentValidator.validate()
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
