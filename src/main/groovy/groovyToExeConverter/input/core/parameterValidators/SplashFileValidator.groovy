package groovyToExeConverter.input.core.parameterValidators

import groovy.util.logging.Log4j

import static org.apache.commons.io.FilenameUtils.isExtension

@Log4j
class SplashFileValidator {

    static final SUPPORTED_IMAGE_FORMATS = ['bmp', 'jpg', 'jpeg', 'gif']

    static def splashDoesNotExistOrIsNotFile = { input ->
        if (!input.splash) return false

        File splashFile = input.splash as File
        return !splashFile.exists() || !splashFile.isFile()
    }

    static def splashInIncorrectFormat = { input ->
        if (!input.splash) return false

        File splashFile = input.splash as File
        return !isExtension(splashFile.name, SUPPORTED_IMAGE_FORMATS)
    }

}
