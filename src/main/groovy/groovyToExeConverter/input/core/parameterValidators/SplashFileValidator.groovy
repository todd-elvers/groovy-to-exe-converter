package groovyToExeConverter.input.core.parameterValidators
import groovy.util.OptionAccessor as Input

import static org.apache.commons.io.FilenameUtils.isExtension

class SplashFileValidator {

    static def splashDoesNotExistOrIsNotFile = { Input input ->
        if (!input.splash) return false

        File splashFile = input.splash as File
        return !splashFile.exists() || !splashFile.isFile()
    }

    static def splashInIncorrectFormat = { Input input ->
        if(!input.splash) return false

        File splashFile = input.splash as File
        return !isExtension(splashFile.name, "bmp")
    }

}
