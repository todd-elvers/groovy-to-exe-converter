package groovyToExeConverter.input.core.parameterValidators

import groovy.util.OptionAccessor as Input
import groovyToExeConverter.domain.AppConfigDefaults

class TempDirValidator {

    static def tempDirDoesNotExistOrIsNotDir = { Input input ->
        if (!input.tempDir) return false

        File temporaryDirectory = input.tempDir as File
        return !temporaryDirectory.exists() || !temporaryDirectory.isDirectory()
    }

    static def tempDirPathContainsSpaces = { Input input ->
        String tempDirPath = input.tempDir ?: AppConfigDefaults.TEMP_DIR_PATH.defaultValue
        return tempDirPath && tempDirPath.contains(" ")
    }

    static def tempDirIsMissingAndCannotBeDetermined = { Input input ->
        if (!input.tempDir) return false

        String sysTempPath = System.getenv("TEMP")
        return !sysTempPath
    }


}
