package groovyToExeConverter.input.core.parameterValidators
import groovy.util.OptionAccessor as Input
import groovy.util.logging.Log4j

import static org.apache.commons.io.FilenameUtils.isExtension

@Log4j
class SplashFileValidator {

    static def splashDoesNotExistOrIsNotFile = { Input input ->
        if (!input.splash) return false

        File splashFile = input.splash as File
        return !splashFile.exists() || !splashFile.isFile()
    }

    static def splashInIncorrectFormat = { Input input ->
        if(!input.splash) return false

        File splashFile = input.splash as File
        return !isExtension(splashFile.name, ['bmp', 'jpg', 'wbmp', 'jpeg', 'png', 'gif'])
    }

}
