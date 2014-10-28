package groovyToExeConverter.input.core.validation.parameter.validators

import groovyToExeConverter.input.core.validation.parameter.ParameterValidator
import groovyToExeConverter.model.exception.InputValidationException

import static org.apache.commons.io.FilenameUtils.isExtension

trait SplashFileValidator implements ParameterValidator {

    private static final SUPPORTED_IMAGE_FORMATS = ['bmp', 'jpg', 'jpeg', 'gif']

    void validate(OptionAccessor commandLineInput){
        switch(commandLineInput){
            case splashImageDoesNotExistOrIsNotAFile: throw new InputValidationException("The 'splash' parameter points to a file that doesn't exist or is a directory.")
            case splashImageInIncorrectFormat       : throw new InputValidationException("The 'splash' parameter points to a file with an invalid file type - only bmp, jpg, jpeg, or gif files are supported.")
        }

        super.validate(commandLineInput)
    }

    private Closure<Boolean> splashImageDoesNotExistOrIsNotAFile = { input ->
        if (!input.splash) return false

        File splashFile = input.splash as File
        return !splashFile.exists() || !splashFile.isFile()
    }

    private Closure<Boolean> splashImageInIncorrectFormat = { input ->
        if (!input.splash) return false

        File splashFile = input.splash as File
        return !isExtension(splashFile.name, SUPPORTED_IMAGE_FORMATS)
    }

}
