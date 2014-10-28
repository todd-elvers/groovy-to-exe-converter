package groovyToExeConverter.input.core.validation
import groovy.util.OptionAccessor as Input
import groovy.util.logging.Log4j
import groovyToExeConverter.input.core.validation.parameter.ParameterValidator
import groovyToExeConverter.input.core.validation.parameter.ParameterValidatorFactory

@Log4j
class InputValidator {

    private ParameterValidator requiredParameterValidator = ParameterValidatorFactory.makeRequiredParameterValidator()
    private ParameterValidator optionalParameterValidator = ParameterValidatorFactory.makeOptionalParameterValidator()

    void validate(Input commandLineInput) {
        log.debug("Validating input...")

        requiredParameterValidator.validate(commandLineInput)
        optionalParameterValidator.validate(commandLineInput)

        log.debug("Input valid.")
    }


}
