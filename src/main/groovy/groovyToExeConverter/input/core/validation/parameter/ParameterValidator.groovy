package groovyToExeConverter.input.core.validation.parameter

import groovyToExeConverter.model.exception.InputValidationException

interface ParameterValidator {
    void validate(OptionAccessor commandLineInput) throws InputValidationException
}
