package groovyToExeConverter.input.core

import groovyToExeConverter.input.core.validation.InputValidator
import groovyToExeConverter.input.core.validation.parameter.ParameterValidator
import spock.lang.Specification

class InputValidatorTest extends Specification {

    InputValidator inputValidator = [
            requiredParameterValidator: Mock(ParameterValidator),
            optionalParameterValidator: Mock(ParameterValidator)
    ]

    void "first validates required parameters, then validates optional parameters"(){
        given:
            OptionAccessor commandLineInput = Mock(OptionAccessor)

        when:
            inputValidator.validate(commandLineInput)

        then:
            1 * inputValidator.requiredParameterValidator.validate(commandLineInput)

        then:
            1 * inputValidator.optionalParameterValidator.validate(commandLineInput)
    }

}
