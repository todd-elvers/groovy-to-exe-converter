package groovyToExeConverter.input.core.validation.parameter.validators

import groovyToExeConverter.input.core.validation.parameter.ParameterValidator
import groovyToExeConverter.model.exception.InputValidationException
//@Log4j
trait EndOfValidationChain implements ParameterValidator {

    void validate(OptionAccessor commandLineInput) throws InputValidationException {
//        log.debug("End of validation chain.")
    }

}
