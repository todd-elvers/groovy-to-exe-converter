package groovyToExeConverter.input.core.validation.parameter.validators

import groovyToExeConverter.input.core.validation.parameter.ParameterValidator
import groovyToExeConverter.model.exception.InputValidationException

trait AppTypeValidator implements ParameterValidator {

    void validate(OptionAccessor commandLineInput) {
        println("App type validator")
        switch(commandLineInput){
            case consoleAndGuiFlagsBothSet: throw new InputValidationException("Both --console and --gui cannot be set at the same time.")
        }

        super.validate(commandLineInput)
    }

    private Closure<Boolean> consoleAndGuiFlagsBothSet = { OptionAccessor input ->
        input.getProperty('console') && input.getProperty('gui')
    }
}
