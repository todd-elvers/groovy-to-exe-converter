package groovyToExeConverter.input.core.validation.parameter.validators

import groovyToExeConverter.input.core.validation.parameter.ParameterValidator
import groovyToExeConverter.model.exception.InputValidationException

trait JreVersionValidator implements ParameterValidator{

    // Only digits and periods in the form: 'x.x.x[_xx]'
    static final String JRE_VERSION_PATTERN = /^\d.\d.\d(_\d\d){0,1}$/

    void validate(OptionAccessor commandLineInput){
        switch(commandLineInput){
            case minJreInInvalidFormat: throw new InputValidationException("The 'minJre' parameter only accepts strings of the format x.x.x[_xx].")
        }

        super.validate(commandLineInput)
    }

    private Closure<Boolean> minJreInInvalidFormat = { input ->
        if (!input.minJre) return false

        String minJre = input.minJre
        return !minJre.matches(JRE_VERSION_PATTERN)
    }

}
