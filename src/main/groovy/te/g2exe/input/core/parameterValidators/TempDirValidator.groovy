package te.g2exe.input.core.parameterValidators

import te.g2exe.model.AppConfigDefaults

class TempDirValidator {

    static def tempDirDoesNotExistOrIsNotDir = { input ->
        if (!input.tempDir) return false

        File temporaryDirectory = input.tempDir as File
        return !temporaryDirectory.exists() || !temporaryDirectory.isDirectory()
    }

    static def tempDirPathContainsSpaces = { input ->
        String tempDirPath = input.tempDir ?: AppConfigDefaults.TEMP_DIR_PATH.defaultValue
        return tempDirPath && tempDirPath.contains(" ")
    }

    static def tempDirCannotBeDetermined = { input ->
        if (!input.tempDir) return false

        String sysTempPath = System.getenv("TEMP")
        return !sysTempPath
    }

}
