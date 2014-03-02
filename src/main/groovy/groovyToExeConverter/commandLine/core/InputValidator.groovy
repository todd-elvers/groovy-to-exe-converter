package groovyToExeConverter.commandLine.core

import groovy.util.OptionAccessor as CommandLineInput
import groovy.util.logging.Log4j
import groovyToExeConverter.domain.AppConfigDefaults
import groovyToExeConverter.exception.InputValidationException

@Log4j
class InputValidator {

    private final DIGITS_ONLY_PATTERN = /^\d{1,}$/                  // Only digits, one or more
    private final JRE_VERSION_PATTERN = /^\d{1}.\d{1}.\d{1,2}$/     // Only digits and periods, in format x.x.x or x.x.xx

    //TODO: Has this class reached critical mass? (Consider packages 'commandLine.transformer' and 'commandLine.validation')
    //TODO: Try and ensure order doesn't matter here
    void validate(CommandLineInput input) {
        switch (input) {
            case fileToConvertIsMissing                 : throw new InputValidationException("The parameter 'fileToConvert' is missing or points to a file that doesn't exist. Use 'g2exe --help' to display available commands.")
            case fileToConvertIsIncorrectFileType       : throw new InputValidationException("Invalid file type for 'fileToConvert' - only .groovy and .jar files are allowed.")
            case destDirDoesNotExistOrIsNotDir          : throw new InputValidationException("The given 'destDir' doesn't exist or isn't a directory.")
            case tempDirDoesNotExistOrIsNotDir          : throw new InputValidationException("The given 'tempDir' doesn't exist or isn't a directory.")
            case tempDirPathContainsSpaces              : throw new InputValidationException("The given 'tempDir' contains spaces in its file path which causes issues with a tool this application uses.  Please explicitly set the temporary directory path to a directory not containing spaces.")
            case tempDirIsMissingAndCannotBeDetermined  : throw new InputValidationException("The 'tempDir' parameter wasn't set and could not be determined from the system. Please explicitly set the temporary directory path.")
            case minJreInInvalidFormat                  : throw new InputValidationException("The 'minJre' parameter only accepts strings of the format x.x.x or x.x.xx")
            case iconDoesNotExistOrIsNotFile            : throw new InputValidationException("The given 'icon' doesn't exist or isn't a file.")
            case iconInIncorrectFormat                  : throw new InputValidationException("Invalid file type for 'icon' - only .ico files are allowed.")
            case initHeapSizeIsNotNumberOrIsZeroOrLess  : throw new InputValidationException("The value for 'initHeapSize' is not a number, or is less than or equal to zero. Please provide a number greater than zero.")
            case maxHeapSizeIsNotNumberOrIsZeroOrLess   : throw new InputValidationException("The value for 'maxHeapSize' is not a number, or is less than or equal to zero. Please provide a number greater than zero.")
            case maxHeapSizeLessThanInitHeapSize        : throw new InputValidationException("The value for 'maxHeapSize' cannot be less than the value for 'initHeapSize'.")
        }
    }

    private def fileToConvertIsMissing = { CommandLineInput input ->
        File fileToConvert
        if (input.fileToConvert) {
            fileToConvert = input.fileToConvert as File
        }
        return !fileToConvert || !fileToConvert.exists()
    }

    private def fileToConvertIsIncorrectFileType = { CommandLineInput input ->
        File fileToConvert = input.fileToConvert as File
        return !fileToConvert.isFile() || (!fileToConvert.name.endsWith(".groovy") && !fileToConvert.name.endsWith(".jar"))
    }

    private def destDirDoesNotExistOrIsNotDir = { CommandLineInput input ->
        if (optionalArgNotSet(input.destDir)) return false

        File destinationDirectory = input.destDir as File
        return !destinationDirectory.exists() || !destinationDirectory.isDirectory()
    }

    private def tempDirDoesNotExistOrIsNotDir = { CommandLineInput input ->
        if (optionalArgNotSet(input.tempDir)) return false

        File temporaryDirectory = input.tempDir as File
        return !temporaryDirectory.exists() || !temporaryDirectory.isDirectory()
    }

    private def tempDirPathContainsSpaces = { CommandLineInput input ->
        String tempDirPath = input.tempDir ?: AppConfigDefaults.TEMP_DIR_PATH.defaultValue
        return tempDirPath && tempDirPath.contains(" ")
    }

    private def tempDirIsMissingAndCannotBeDetermined = { CommandLineInput input ->
        if (optionalArgNotSet(input.tempDir)) return false

        String sysTempPath = System.getenv("TEMP")
        return !sysTempPath
    }

    private def minJreInInvalidFormat = { CommandLineInput input ->
        if (optionalArgNotSet(input.minJre)) return false

        String minJre = input.minJre
        return !minJre.matches(JRE_VERSION_PATTERN)
    }

    private def iconDoesNotExistOrIsNotFile = { CommandLineInput input ->
        if (optionalArgNotSet(input.icon)) return false

        File iconFile = input.icon as File
        return !iconFile.exists() || !iconFile.isFile()
    }

    private def iconInIncorrectFormat = { CommandLineInput input ->
        if (optionalArgNotSet(input.icon)) return false

        File iconFile = input.icon as File
        return !iconFile.name.endsWith(".ico")
    }

    private def initHeapSizeIsNotNumberOrIsZeroOrLess = { CommandLineInput input ->
        if (optionalArgNotSet(input.initHeapSize)) return false
        if (!String.valueOf(input.initHeapSize).matches(DIGITS_ONLY_PATTERN)) return true

        int initialHeapSize = input.initHeapSize as int
        initialHeapSize <= 0
    }

    private def maxHeapSizeIsNotNumberOrIsZeroOrLess = { CommandLineInput input ->
        if (optionalArgNotSet(input.maxHeapSize)) return false
        if (!String.valueOf(input.maxHeapSize).matches(DIGITS_ONLY_PATTERN)) return true

        int maximumHeapSize = input.maxHeapSize as int
        return maximumHeapSize <= 0
    }

    //temporal cuppling!
    private def maxHeapSizeLessThanInitHeapSize = { CommandLineInput input ->
        int initialHeapSize = (input.initHeapSize ?: AppConfigDefaults.INITIAL_HEAP_SIZE.defaultValue) as int
        int maximumHeapSize = (input.maxHeapSize ?: AppConfigDefaults.MAXIMUM_HEAP_SIZE.defaultValue) as int
        return maximumHeapSize < initialHeapSize
    }

    private def optionalArgNotSet = { return !it }
}
