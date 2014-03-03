package groovyToExeConverter.input.core.parameterValidators

import groovy.util.OptionAccessor as Input

class DestDirValidator {

    static def destDirDoesNotExistOrIsNotDir = { Input input ->
        if (!input.destDir) return false

        File destinationDirectory = input.destDir as File
        return !destinationDirectory.exists() || !destinationDirectory.isDirectory()
    }

}
