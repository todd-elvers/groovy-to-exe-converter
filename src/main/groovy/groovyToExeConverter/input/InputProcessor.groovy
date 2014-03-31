package groovyToExeConverter.input

import groovy.util.OptionAccessor as Input
import groovy.util.logging.Log4j
import groovyToExeConverter.input.core.InputParser
import groovyToExeConverter.input.core.InputTransformer
import groovyToExeConverter.input.core.InputValidator
import groovyToExeConverter.model.AppConfig
import groovyToExeConverter.util.PropertiesReader

@Log4j
class InputProcessor {

    final inputParser = new InputParser()
    final inputValidator = new InputValidator()
    final inputTransformer = new InputTransformer()

    AppConfig processIntoAppConfig(String[] args) {
        Input input = inputParser.parse(args)

        if (!input) {
            return null
        } else if (input.help) {
            inputParser.printUsage()
            return null
        } else if (input.version) {
            log.info("Version: ${PropertiesReader.readAppProperty("version")}")
            return null
        }

        inputValidator.validate(input)
        inputTransformer.transformIntoAppConfig(input)
    }

}
