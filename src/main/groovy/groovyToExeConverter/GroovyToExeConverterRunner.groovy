package groovyToExeConverter

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import groovyToExeConverter.core.FileConverter
import groovyToExeConverter.core.FileConverterFactory
import groovyToExeConverter.input.InputProcessor
import groovyToExeConverter.model.AppConfig
import groovyToExeConverter.util.EnvironmentHandler
import groovyToExeConverter.util.ExceptionHandler

@Log4j
@CompileStatic
class GroovyToExeConverterRunner {

    static void main(String[] args) {
        new GroovyToExeConverterRunner().run(args)
    }


    EnvironmentHandler environmentHandler = new EnvironmentHandler()
    InputProcessor inputProcessor = new InputProcessor()

    void run(String[] args) {
        AppConfig appConfig

        try {
            environmentHandler.validateGroovyHome()
            environmentHandler.addShutdownHookToKillG2exeProcess()

            appConfig = inputProcessor.processIntoAppConfig(args)

            if (appConfig) {
                log.info("Converting...")
                FileConverter fileConverter = FileConverterFactory.makeFileConverter(appConfig)
                fileConverter.convert()
                log.info("Conversion successful!")
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex, appConfig, args)
        }
    }
}
