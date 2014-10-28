package groovyToExeConverter.input.core.validation.parameter.validators
import groovyToExeConverter.input.core.validation.parameter.ParameterValidator
import groovyToExeConverter.model.exception.InputValidationException

import static org.apache.commons.io.FilenameUtils.isExtension

trait FileToConvertValidator implements ParameterValidator {

    void validate(OptionAccessor commandLineInput) {
        switch(commandLineInput){
            case fileToConvertIsMissing           : throw new InputValidationException("The parameter 'fileToConvert' is missing or points to a file that doesn't exist.\nUse 'g2exe --help' to display available commands.")
            case fileToConvertIsIncorrectFileType : throw new InputValidationException("Invalid file type for 'fileToConvert' - only .groovy and .jar files are allowed.")
        }

        super.validate(commandLineInput)
    }

    private Closure<Boolean> fileToConvertIsMissing = { input ->
        File fileToConvert
        if (input.fileToConvert) {
            fileToConvert = input.fileToConvert as File
        }
        return !fileToConvert || !fileToConvert.exists()
    }

    private Closure<Boolean> fileToConvertIsIncorrectFileType = { input ->
        File fileToConvert = input.fileToConvert as File
        return !fileToConvert.isFile() || !isExtension(fileToConvert.name, ["groovy", "jar"])
    }

}
