package groovyToExeConverter.input.core.parameterValidators

import groovy.util.OptionAccessor as Input

import static org.apache.commons.io.FilenameUtils.isExtension

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
        return !fileToConvert.isFile() || !isExtension(fileToConvert.name, ["groovy", "jar"])
    }

}
