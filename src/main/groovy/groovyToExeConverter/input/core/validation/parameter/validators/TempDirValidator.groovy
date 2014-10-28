package groovyToExeConverter.input.core.validation.parameter.validators

import groovyToExeConverter.input.core.validation.parameter.ParameterValidator
import groovyToExeConverter.model.AppConfigDefaults
import groovyToExeConverter.model.exception.InputValidationException

trait TempDirValidator implements ParameterValidator {

    void validate(OptionAccessor commandLineInput){
        switch(commandLineInput){
            case tempDirDoesNotExistOrIsNotDir: throw new InputValidationException("The given 'tempDir' doesn't exist or isn't a directory.")
            case tempDirPathContainsSpaces    : throw new InputValidationException("The given 'tempDir' contains spaces in its file path which causes issues with a tool this application uses.  Please explicitly set the temporary directory path to a directory not containing spaces.")
            case tempDirCannotBeDetermined    : throw new InputValidationException("The 'tempDir' parameter wasn't set and could not be determined from the system. Please explicitly set the temporary directory path.")
        }

        super.validate(commandLineInput)
    }

    private Closure<Boolean> tempDirDoesNotExistOrIsNotDir = { input ->
        if (!input.tempDir) return false

        File temporaryDirectory = input.tempDir as File
        return !temporaryDirectory.exists() || !temporaryDirectory.isDirectory()
    }

    private Closure<Boolean> tempDirPathContainsSpaces = { input ->
        String tempDirPath = input.tempDir ?: AppConfigDefaults.TEMP_DIR_PATH.defaultValue
        return tempDirPath && tempDirPath.contains(" ")
    }

    private Closure<Boolean> tempDirCannotBeDetermined = { input ->
        if (!input.tempDir) return false

        String sysTempPath = System.getenv("TEMP")
        return !sysTempPath
    }

}
