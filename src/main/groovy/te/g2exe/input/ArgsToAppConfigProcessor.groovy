package te.g2exe.input

import groovy.util.OptionAccessor as CommandLineArgs
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import te.g2exe.input.core.*
import te.g2exe.model.AppConfig
import te.g2exe.model.exception.InputValidationException

@Slf4j
@Component
class ArgsToAppConfigProcessor {

    @Autowired
    CommandLineArgsParser commandLineArgsParser

    @Autowired
    List<CommandLineArgsValidator> commandLineArgsValidators

    @Autowired
    ArgsToAppConfigTransformer argsToAppConfigTransformer

    AppConfig process(String[] commandLineArgs) {
        CommandLineArgs args = commandLineArgsParser.parse(commandLineArgs)

        // If there's no input then CliBuilder's logic determined the command
        // wasn't well-formed and already printed a message to the console
        if (!args) {
            return null
        }

//        // Turn on debugging if requested
//        if (input.debug) {
//            LoggerHandler.setAppenderToDebugAppender()
//        }
//
//        //TODO: Refactor this - breaks SRP
//        // In the case of a one-off command, return null to terminate g2exe
//        if (OneOffCommandBuilder.isOneOffCommand(input)) {
//            OneOffCommandBuilder.executeCommand(input)
//            return null
//        }

        log.debug("Validating user input...")
        commandLineArgsValidators.each { validator ->
            validator.assertions.each { assertion ->
                if(assertion.isNotValid(args)) {
                    throw new InputValidationException(assertion.failureMessage)
                }
            }
        }
        log.debug("Input valid.")

        return argsToAppConfigTransformer.transform(args)
    }


}
