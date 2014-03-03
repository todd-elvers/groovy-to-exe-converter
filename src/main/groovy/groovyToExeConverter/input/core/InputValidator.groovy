package groovyToExeConverter.input.core

import groovy.util.OptionAccessor as Input
import groovyToExeConverter.exception.InputValidationException

import static groovyToExeConverter.input.core.parameterValidators.AppTypeValidator.getConsoleAndGuiFlagsBothSet
import static groovyToExeConverter.input.core.parameterValidators.DestDirValidator.getDestDirDoesNotExistOrIsNotDir
import static groovyToExeConverter.input.core.parameterValidators.FileToConvertValidator.getFileToConvertIsIncorrectFileType
import static groovyToExeConverter.input.core.parameterValidators.FileToConvertValidator.getFileToConvertIsMissing
import static groovyToExeConverter.input.core.parameterValidators.HeapSizeValidator.*
import static groovyToExeConverter.input.core.parameterValidators.IconFileValidator.getIconDoesNotExistOrIsNotFile
import static groovyToExeConverter.input.core.parameterValidators.IconFileValidator.getIconInIncorrectFormat
import static groovyToExeConverter.input.core.parameterValidators.JreVersionValidator.getMinJreInInvalidFormat
import static groovyToExeConverter.input.core.parameterValidators.SplashFileValidator.getSplashDoesNotExistOrIsNotFile
import static groovyToExeConverter.input.core.parameterValidators.SplashFileValidator.getSplashInIncorrectFormat
import static groovyToExeConverter.input.core.parameterValidators.TempDirValidator.*

class InputValidator {

    void validate(Input input) {
        switch (input) {
            case fileToConvertIsMissing              : throw new InputValidationException("The parameter 'fileToConvert' is missing or points to a file that doesn't exist. Use 'g2exe --help' to display available commands.")
            case fileToConvertIsIncorrectFileType    : throw new InputValidationException("Invalid file type for 'fileToConvert' - only .groovy and .jar files are allowed.")
            case destDirDoesNotExistOrIsNotDir       : throw new InputValidationException("The given 'destDir' doesn't exist or isn't a directory.")
            case tempDirDoesNotExistOrIsNotDir       : throw new InputValidationException("The given 'tempDir' doesn't exist or isn't a directory.")
            case tempDirPathContainsSpaces           : throw new InputValidationException("The given 'tempDir' contains spaces in its file path which causes issues with a tool this application uses.  Please explicitly set the temporary directory path to a directory not containing spaces.")
            case tempDirCannotBeDetermined           : throw new InputValidationException("The 'tempDir' parameter wasn't set and could not be determined from the system. Please explicitly set the temporary directory path.")
            case minJreInInvalidFormat               : throw new InputValidationException("The 'minJre' parameter only accepts strings of the format x.x.x or x.x.xx")
            case iconDoesNotExistOrIsNotFile         : throw new InputValidationException("The 'icon' parameter points to a file that doesn't exist or is a directory.")
            case iconInIncorrectFormat               : throw new InputValidationException("The 'icon' parameter points to a file with an invalid file type - only .ico files are supported.")
            case initHeapSizeIsNotNumOrIsZeroOrLess  : throw new InputValidationException("The value for 'initHeapSize' is not a number, or is less than or equal to zero. Please provide a number greater than zero.")
            case maxHeapSizeIsNotNumOrIsZeroOrLess   : throw new InputValidationException("The value for 'maxHeapSize' is not a number, or is less than or equal to zero. Please provide a number greater than zero.")
            case maxHeapSizeLessThanInitHeapSize     : throw new InputValidationException("The value for 'maxHeapSize' cannot be less than the value for 'initHeapSize'.")
            case consoleAndGuiFlagsBothSet           : throw new InputValidationException("Both --console and --gui cannot be set at the same time.")
            case splashDoesNotExistOrIsNotFile       : throw new InputValidationException("The 'splash' parameter points to a file that doesn't exist or is a directory.")
            case splashInIncorrectFormat             : throw new InputValidationException("The 'splash' parameter points to a file with an invalid file type - only .bmp files are supported.")
        }
    }

}
