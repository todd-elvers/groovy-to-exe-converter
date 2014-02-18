package groovyToExeConverter.commandLine.core

import groovy.util.logging.Log4j
import groovyToExeConverter.domain.AppConfigDefaults
import groovyToExeConverter.exception.InputValidationException

@Log4j
class OptionAccessorValidator {

    private final DIGITS_ONLY_PATTERN = /^\d{1,}$/                  // Only digits, one or more
    private final JRE_VERSION_PATTERN = /^\d{1}.\d{1}.\d{1,2}$/     // Only digits and periods, in format x.x.x or x.x.xx

    void validate(OptionAccessor optionAccessor) {
        switch (optionAccessor) {
            case fileToConvertIsMissing                 : throw new InputValidationException("The parameter 'fileToConvert' is missing or points to a file that doesn't exist.")
            case fileToConvertIsIncorrectFileType       : throw new InputValidationException("Invalid file type for 'fileToConvert' - only .groovy and .jar files are allowed.")
            case fileToConvertPathIsNotAbsolute         : throw new InputValidationException("The parameter 'fileToConvert' requires a fully qualified file path (e.g. C:/.../.../file)")
            case destDirDoesNotExistOrIsNotDir          : throw new InputValidationException("The given 'destDir' doesn't exist or isn't a directory.")
            case tempDirDoesNotExistOrIsNotDir          : throw new InputValidationException("The given 'tempDir' doesn't exist or isn't a directory.")
            case tempDirPathContainsSpaces              : throw new InputValidationException("The given 'tempDir' contains spaces in its file path which causes issues with a tool this application uses.  Please explicitly set the temporary directory path to a directory not containing spaces.")
            case tempDirIsMissingAndCannotBeDetermined  : throw new InputValidationException("The 'tempDir' parameter wasn't set and could not be determined from the system. Please explicitly set the temporary directory path.")
            case minJreInInvalidFormat                  : throw new InputValidationException("The 'minJre' parameter only accepts strings of the format x.x.x or x.x.xx")
            case iconDoesNotExistOrIsNotFile            : throw new InputValidationException("The given 'icon' doesn't exist or isn't a file.")
            case iconInIncorrectFormat                  : throw new InputValidationException("Invalid file type for 'icon' - only .ico files are allowed.")
            case initHeapSizeIsNotNumberOrIsZeroOrLess  : throw new InputValidationException("The given 'initHeapSize' is not a number, or is less than or equal to zero. Please provide a number greater than zero, or provide nothing to use the default value.")
            case maxHeapSizeIsNotNumberOrIsZeroOrLess   : throw new InputValidationException("The given 'maxHeapSize' is not a number, or is less than or equal to zero. Please provide a number greater than zero, or provide nothing to use the default value.")        }
    }

    private def fileToConvertIsMissing = { OptionAccessor options ->
        File fileToConvert
        if (options.fileToConvert) {
            fileToConvert = options.fileToConvert as File
        }
        return !fileToConvert || !fileToConvert.exists()
    }

    private def fileToConvertIsIncorrectFileType = { OptionAccessor options ->
        File fileToConvert = options.fileToConvert as File
        return !fileToConvert.isFile() || (!fileToConvert.name.endsWith(".groovy") && !fileToConvert.name.endsWith(".jar"))
    }

    private def fileToConvertPathIsNotAbsolute = { OptionAccessor options ->
        File fileToConvert = options.fileToConvert as File
        return !fileToConvert.isAbsolute()
    }

    private def destDirDoesNotExistOrIsNotDir = { OptionAccessor options ->
        if(optionalArgNotSet(options.destDir)) return false

        File destinationDirectory = options.destDir as File
        return !destinationDirectory.exists() || !destinationDirectory.isDirectory()
    }

    private def tempDirDoesNotExistOrIsNotDir = { OptionAccessor options ->
        if(optionalArgNotSet(options.tempDir)) return false

        File temporaryDirectory = options.tempDir as File
        return !temporaryDirectory.exists() || !temporaryDirectory.isDirectory()
    }

    private def tempDirPathContainsSpaces = { OptionAccessor options ->
        String tempDirPath = options.tempDir ?: AppConfigDefaults.TEMP_DIR_PATH.defaultValue
        return tempDirPath && tempDirPath.contains(" ")
    }

    private def tempDirIsMissingAndCannotBeDetermined = { OptionAccessor options ->
        if(optionalArgNotSet(options.tempDir)) return false

        String sysTempPath = System.getenv("TEMP")
        return !sysTempPath
    }

    private def minJreInInvalidFormat = { OptionAccessor options ->
        if(optionalArgNotSet(options.minJre)) return false

        String minJre = options.minJre
        return !minJre.matches(JRE_VERSION_PATTERN)
    }

    private def iconDoesNotExistOrIsNotFile = { OptionAccessor options ->
        if(optionalArgNotSet(options.icon)) return false

        File iconFile = options.icon as File
        return !iconFile.exists() || !iconFile.isFile()
    }

    private def iconInIncorrectFormat = { OptionAccessor options ->
        if(optionalArgNotSet(options.icon)) return false

        File iconFile = options.icon as File
        return !iconFile.name.endsWith(".ico")
    }

    private def initHeapSizeIsNotNumberOrIsZeroOrLess = { OptionAccessor options ->
        if(optionalArgNotSet(options.initHeapSize)) return false
        if (!String.valueOf(options.initHeapSize).matches(DIGITS_ONLY_PATTERN)) return true

        int initialHeapSize = options.initHeapSize as int
        initialHeapSize <= 0
    }

    private def maxHeapSizeIsNotNumberOrIsZeroOrLess = { OptionAccessor options ->
        if(optionalArgNotSet(options.maxHeapSize)) return false
        if (!String.valueOf(options.maxHeapSize).matches(DIGITS_ONLY_PATTERN)) return true

        int maximumHeapSize = options.maxHeapSize as int
        return maximumHeapSize <= 0
    }

    private def optionalArgNotSet = { return !it }
}
