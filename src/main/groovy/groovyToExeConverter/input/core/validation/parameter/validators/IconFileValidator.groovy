package groovyToExeConverter.input.core.validation.parameter.validators

import groovyToExeConverter.input.core.validation.parameter.ParameterValidator
import groovyToExeConverter.model.exception.InputValidationException

import static org.apache.commons.io.FilenameUtils.isExtension

trait IconFileValidator implements ParameterValidator {

    void validate(OptionAccessor commandLineInput) {
        switch(commandLineInput) {
            case iconDoesNotExistOrIsNotFile: throw new InputValidationException("The 'icon' parameter points to a file that doesn't exist or is a directory.")
            case iconInIncorrectFormat      : throw new InputValidationException("The 'icon' parameter points to a file with an invalid file type - only .ico files are supported.")
        }

        super.validate(commandLineInput)
    }

    private Closure<Boolean> iconDoesNotExistOrIsNotFile = { input ->
        if (!input.icon) return false

        File iconFile = input.icon as File
        return !iconFile.exists() || !iconFile.isFile()
    }

    private Closure<Boolean> iconInIncorrectFormat = { input ->
        if (!input.icon) return false

        File iconFile = input.icon as File
        return !isExtension(iconFile.name, "ico")
    }

}
