package groovyToExeConverter.core.commandLine.core

import groovy.util.logging.Log4j
import groovyToExeConverter.core.exception.InputValidationException
import groovyToExeConverter.domain.AppConfigDefaults


@Log4j
class OptionAccessorValidator {

    private final DIGITS_ONLY_PATTERN = /^\d{1,}$/                  // Only digits, one or more
    private final JRE_VERSION_PATTERN = /^\d{1}.\d{1}.\d{1,2}$/     // Only digits and periods, in format x.x.x or x.x.xx

    void validate(OptionAccessor optionAccessor) {
        switch (optionAccessor) {
            case fileToConvertIsMissing                 : throw new InputValidationException("The parameter 'fileToConvert' is missing or points to a file that doesn't exist.")
            case fileToConvertIsIncorrectFileType       : throw new InputValidationException("Invalid file type for 'fileToConvert' - only .groovy and .jar files are allowed.")
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

    private def fileToConvertIsMissing = { OptionAccessor optionAccessor ->
        File fileToConvert
        if (optionAccessor.fileToConvert) {
            fileToConvert = optionAccessor.fileToConvert as File
        }
        return !fileToConvert || !fileToConvert.exists()
    }

    private def fileToConvertIsIncorrectFileType = { OptionAccessor optionAccessor ->
        File fileToConvert = optionAccessor.fileToConvert as File
        return !fileToConvert.isFile() || (!fileToConvert.name.endsWith(".groovy") && !fileToConvert.name.endsWith(".jar"))
    }

    private def destDirDoesNotExistOrIsNotDir = { OptionAccessor optionAccessor ->
        // 'destDir' is optional, so we return false if it was never set
        if (!optionAccessor.destDir) return false

        File destinationDirectory = optionAccessor.destDir as File
        return !destinationDirectory.exists() || !destinationDirectory.isDirectory()
    }

    private def tempDirDoesNotExistOrIsNotDir = { OptionAccessor optionAccessor ->
        // 'tempDir' is optional, so we return false if it was never set
        if (!optionAccessor.tempDir) return false

        File temporaryDirectory = optionAccessor.tempDir as File
        return !temporaryDirectory.exists() || !temporaryDirectory.isDirectory()
    }

    private def tempDirPathContainsSpaces = { OptionAccessor optionAccessor ->
        String tempDirPath = optionAccessor.tempDir ?: AppConfigDefaults.TEMP_DIR_PATH.defaultValue
        return tempDirPath && tempDirPath.contains(" ")
    }

    private def tempDirIsMissingAndCannotBeDetermined = { OptionAccessor optionAccessor ->
        if (optionAccessor.tempDir) return false
        String sysTempPath = System.getenv("TEMP")
        return !sysTempPath
    }

    private def minJreInInvalidFormat = { OptionAccessor optionAccessor ->
        if (!optionAccessor.minJre) return false
        String minJre = optionAccessor.minJre
        return !minJre.matches(JRE_VERSION_PATTERN)
    }

    private def iconDoesNotExistOrIsNotFile = { OptionAccessor optionAccessor ->
        if (!optionAccessor.icon) return false
        File iconFile = optionAccessor.icon as File
        return !iconFile.exists() || !iconFile.isFile()
    }

    private def iconInIncorrectFormat = { OptionAccessor optionAccessor ->
        if (!optionAccessor.icon) return false
        File iconFile = optionAccessor.icon as File
        return !iconFile.name.endsWith(".ico")
    }

    private def initHeapSizeIsNotNumberOrIsZeroOrLess = { OptionAccessor optionAccessor ->
        if (!optionAccessor.initHeapSize) return false
        if (!String.valueOf(optionAccessor.initHeapSize).matches(DIGITS_ONLY_PATTERN)) return true

        int initialHeapSize = optionAccessor.initHeapSize as int
        return initialHeapSize <= 0
    }

    private def maxHeapSizeIsNotNumberOrIsZeroOrLess = { OptionAccessor optionAccessor ->
        if (!optionAccessor.maxHeapSize) return false
        if (!String.valueOf(optionAccessor.maxHeapSize).matches(DIGITS_ONLY_PATTERN)) return true

        int maximumHeapSize = optionAccessor.maxHeapSize as int
        return maximumHeapSize <= 0
    }

}
