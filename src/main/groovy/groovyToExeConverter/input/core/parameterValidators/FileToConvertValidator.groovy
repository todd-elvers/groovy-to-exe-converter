package groovyToExeConverter.input.core.parameterValidators

import groovy.util.OptionAccessor as Input

class FileToConvertValidator {

    static def fileToConvertIsMissing = { Input input ->
        File fileToConvert
        if (input.fileToConvert) {
            fileToConvert = input.fileToConvert as File
        }
        return !fileToConvert || !fileToConvert.exists()
    }

    static def fileToConvertIsIncorrectFileType = { Input input ->
        File fileToConvert = input.fileToConvert as File
        return !fileToConvert.isFile() || (!fileToConvert.name.endsWith(".groovy") && !fileToConvert.name.endsWith(".jar"))
    }

}
