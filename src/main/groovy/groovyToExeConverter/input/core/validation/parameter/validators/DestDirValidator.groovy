package groovyToExeConverter.input.core.validation.parameter.validators

import groovyToExeConverter.input.core.validation.parameter.ParameterValidator
import groovyToExeConverter.model.exception.InputValidationException

trait DestDirValidator implements ParameterValidator {

    void validate(OptionAccessor commandLineInput) {
        switch(commandLineInput){
            case destDirDoesNotExistOrIsNotDir: throw new InputValidationException("The given 'destDir' doesn't exist or isn't a directory.")
        }

        super.validate(commandLineInput)
    }

    private Closure<Boolean> destDirDoesNotExistOrIsNotDir = { input ->
        if (!input.destDir) return false

        File destinationDirectory = input.destDir as File
        return !destinationDirectory.exists() || !destinationDirectory.isDirectory()
    }
}
