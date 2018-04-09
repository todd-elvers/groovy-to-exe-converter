package te.g2exe.input.core.parameterValidators

class DestDirValidator {

    static def destDirDoesNotExistOrIsNotDir = { input ->
        if (!input.destDir) return false

        File destinationDirectory = input.destDir as File
        return !destinationDirectory.exists() || !destinationDirectory.isDirectory()
    }

}
