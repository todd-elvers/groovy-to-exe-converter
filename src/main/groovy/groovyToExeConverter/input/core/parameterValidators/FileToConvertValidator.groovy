package groovyToExeConverter.input.core.parameterValidators

import static org.apache.commons.io.FilenameUtils.isExtension

class FileToConvertValidator {

    static def fileToConvertIsMissing = { input ->
        File fileToConvert
        if (input.fileToConvert) {
            fileToConvert = input.fileToConvert as File
        }
        return !fileToConvert || !fileToConvert.exists()
    }

    static def fileToConvertIsIncorrectFileType = { input ->
        File fileToConvert = input.fileToConvert as File
        return !fileToConvert.isFile() || !isExtension(fileToConvert.name, ["groovy", "jar"])
    }

}
