package groovyToExeConverter.input

import groovy.util.logging.Log4j
import groovyToExeConverter.input.core.*
import groovyToExeConverter.model.AppConfig

@Log4j
class InputProcessor {

    InputParser inputParser = new InputParser()
    InputValidator inputValidator = new InputValidator()
    InputTransformer inputTransformer = new InputTransformer()

    AppConfig processIntoAppConfig(String[] args) {
        OptionAccessor input = inputParser.parse(args)

        // If there's no input then CliBuilder's logic determined the command
        // wasn't well-formed and already printed a message to the console
        if (!input) {
            return null
        }

        // Turn on debugging if requested
        if (input.debug) {
            Log4jHandler.setAppenderToDebugAppender()
        }

        // In the case of a one-off command, return null to terminate g2exe
        if (OneOffCommandHandler.isOneOffCommand(input)) {
            OneOffCommandHandler.executeCommand(input)
            return null
        }

        inputValidator.validate(input)

        return inputTransformer.transformIntoAppConfig(input)
    }


}
