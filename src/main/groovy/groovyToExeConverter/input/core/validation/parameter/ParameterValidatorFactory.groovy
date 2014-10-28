package groovyToExeConverter.input.core.validation.parameter

import groovyToExeConverter.input.core.validation.parameter.validators.*

class ParameterValidatorFactory {

    static ParameterValidator makeRequiredParameterValidator(){
        [].withTraits(
                EndOfValidationChain,
                FileToConvertValidator
        )
    }

    static ParameterValidator makeOptionalParameterValidator() {
        [].withTraits(
                EndOfValidationChain,
                AppTypeValidator,
                DestDirValidator,
                HeapSizeValidator,
                IconFileValidator,
                JreVersionValidator,
                SplashFileValidator,
                TempDirValidator
        )
    }
    
}
