package groovyToExeConverter.input

import groovy.util.OptionAccessor as Input
import groovy.util.logging.Log4j
import groovyToExeConverter.domain.AppConfig
import groovyToExeConverter.fileConversion.converterHelpers.PropertiesReader
import groovyToExeConverter.input.core.InputParser
import groovyToExeConverter.input.core.InputTransformer
import groovyToExeConverter.input.core.InputValidator

@Log4j
class InputProcessor {

    private def inputParser = new InputParser()
    private def inputValidator = new InputValidator()
    private def inputTransformer = new InputTransformer()

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
