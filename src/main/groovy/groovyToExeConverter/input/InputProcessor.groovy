package groovyToExeConverter.input
import groovy.util.OptionAccessor as Input
import groovy.util.logging.Log4j
import groovyToExeConverter.input.core.InputParser
import groovyToExeConverter.input.core.InputTransformer
import groovyToExeConverter.input.core.Log4jHandler
import groovyToExeConverter.input.core.validation.InputValidator
import groovyToExeConverter.model.AppConfig
import groovyToExeConverter.util.PropertiesReader

@Log4j
class InputProcessor {

    final inputParser = new InputParser(),
          inputValidator = new InputValidator(),
          inputTransformer = new InputTransformer()

    AppConfig processIntoAppConfig(String[] args) {
        Input input = inputParser.parse(args)

        if (!input || isOneOffCommand(input)) {
            return null
        } else if (input.debug) {
            Log4jHandler.setAppenderToDebugAppender()
        }

        inputValidator.validate(input)
        inputTransformer.transformIntoAppConfig(input)
    }

    private boolean isOneOffCommand(Input input) {
        if (input.help) {
            inputParser.usage()
            return true
        } else if (input.version) {
            log.info("Version: ${PropertiesReader.readAppProperty("version")}")
            return true
        }
    }

}
