package groovyToExeConverter.core.commandLine.core

import groovyToExeConverter.core.exception.InputValidationException


class OptionAccessorValidator {

    //TODO: Update the validation rules to accommodate new appConfig arguments
    void validate(OptionAccessor optionAccessor) {
        switch (optionAccessor) {
            case fileToConvertIsNotFoundOrInvalid       : throw new InputValidationException("The given 'fileToConvert' doesn't exist or isn't a file.")
            case fileToConvertIsIncorrectFileType       : throw new InputValidationException("Invalid file type - only .groovy and .jar files are allowed")
            case destDirIsPresentButNotFoundOrInvalid   : throw new InputValidationException("The given 'destDir' doesn't exist or isn't a directory.")
            case tempDirIsPresentButNotFoundOrInvalid   : throw new InputValidationException("The given 'tempDir' doesn't exist, isn't a directory, or contains spaces in its path.")
            case tempDirIsMissingAndCannotBeDetermined  : throw new InputValidationException("The 'tempDir' parameter wasn't set and could not be determined from the system. Please set the temporary directory path explicitly with --tempDir <directory>.")
        }
    }

    private def fileToConvertIsNotFoundOrInvalid = { OptionAccessor optionAccessor ->
        File fileToConvert = optionAccessor.fileToConvert as File
        return !fileToConvert.exists() || !fileToConvert.isFile()
    }

    private def fileToConvertIsIncorrectFileType = { OptionAccessor optionAccessor ->
        File fileToConvert = optionAccessor.fileToConvert as File
        return !fileToConvert.name.endsWith(".groovy") && !fileToConvert.name.endsWith(".jar")
    }

    private def destDirIsPresentButNotFoundOrInvalid = { OptionAccessor optionAccessor ->
        // 'destDir' is optional, so we return false if it was never set
        if (!optionAccessor.destDir) return false

        File destinationDirectory = optionAccessor.destDir as File
        return !destinationDirectory.exists() || !destinationDirectory.isDirectory()
    }

    private def tempDirIsPresentButNotFoundOrInvalid = { OptionAccessor optionAccessor ->
        // 'tempDir' is optional, so we return false if it was never set
        if (!optionAccessor.tempDir) return false

        File temporaryDirectory = optionAccessor.tempDir as File
        return !temporaryDirectory.exists() || !temporaryDirectory.isDirectory() || temporaryDirectory.absolutePath.contains(" ")
    }

    private def tempDirIsMissingAndCannotBeDetermined = { OptionAccessor optionAccessor ->
        if(optionAccessor.tempDir) return false

        String sysTempPath = System.getenv("TEMP")
        return !sysTempPath
    }
}
