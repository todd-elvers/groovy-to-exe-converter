package groovyToExeConverter.input

import groovy.util.OptionAccessor as Input
import groovy.util.logging.Log4j
import groovyToExeConverter.input.core.InputParser
import groovyToExeConverter.input.core.InputTransformer
import groovyToExeConverter.input.core.InputValidator
import groovyToExeConverter.input.core.Log4jHandler
import groovyToExeConverter.model.AppConfig
import groovyToExeConverter.util.PropertiesReader

@Log4j
class InputProcessor {

    final inputParser = new InputParser(),
          inputValidator = new InputValidator(),
          inputTransformer = new InputTransformer()

    AppConfig processIntoAppConfig(String[] args) {
        Input input = inputParser.parse(args)

        if (!input) {
            return null
        } else if (isOneOffCommand(input)) {
            performOneOffCommand(input)
            return null
        } else if (input.debug) {
            Log4jHandler.setAppenderToDebugAppender()
        }

        inputValidator.validate(input)
        inputTransformer.transformIntoAppConfig(input)
    }

    private static boolean isOneOffCommand(Input input) {
        input.help || input.version
    }

    private void performOneOffCommand(Input input) {
        if (input.help) {
            inputParser.usage()
        } else if (input.version) {
            log.info("Version: ${PropertiesReader.readAppProperty("version")}")
        }
    }

}
