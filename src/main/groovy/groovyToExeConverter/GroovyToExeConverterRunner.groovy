package groovyToExeConverter
import groovy.util.logging.Log4j
import groovyToExeConverter.core.FileConverterFactory
import groovyToExeConverter.input.InputProcessor
import groovyToExeConverter.model.AppConfig
import groovyToExeConverter.util.EnvironmentValidator

@Log4j
class GroovyToExeConverterRunner implements Runnable {

    //TODO: More integration testing
    //TODO: Final refactoring
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
        } catch (Exception exception) {
            //TODO: Possibly push this off to some other class
            handleException(exception, appConfig)
        }
    }

    private static void handleException(Exception exception, AppConfig appConfig){
        if (exception.message) log.error(exception.message)
        if (appConfig?.showStackTrace) log.error("Stacktrace:", exception)
        if (appConfig) log.error("Conversion failed!")
    }
}
