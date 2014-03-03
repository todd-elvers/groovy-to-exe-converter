package groovyToExeConverter.input.core

import groovy.util.OptionAccessor as Input
import groovyToExeConverter.exception.InputValidationException

import static groovyToExeConverter.input.core.parameterValidators.DestDirValidator.getDestDirDoesNotExistOrIsNotDir
import static groovyToExeConverter.input.core.parameterValidators.FileToConvertValidator.getFileToConvertIsIncorrectFileType
import static groovyToExeConverter.input.core.parameterValidators.FileToConvertValidator.getFileToConvertIsMissing
import static groovyToExeConverter.input.core.parameterValidators.HeapSizeValidator.*
import static groovyToExeConverter.input.core.parameterValidators.IconFileValidator.getIconDoesNotExistOrIsNotFile
import static groovyToExeConverter.input.core.parameterValidators.IconFileValidator.getIconInIncorrectFormat
import static groovyToExeConverter.input.core.parameterValidators.JreVersionValidator.getMinJreInInvalidFormat
import static groovyToExeConverter.input.core.parameterValidators.TempDirValidator.*

class InputValidator {

    void validate(Input input) {
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

}
