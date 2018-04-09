package te.g2exe.input

import te.g2exe.input.core.*
import te.g2exe.model.AppConfig

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
