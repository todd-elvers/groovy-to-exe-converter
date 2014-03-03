package groovyToExeConverter.input.core.parameterValidators

import groovy.util.OptionAccessor as Input

class IconFileValidator {

    static def iconDoesNotExistOrIsNotFile = { Input input ->
        if (!input.icon) return false

        File iconFile = input.icon as File
        return !iconFile.exists() || !iconFile.isFile()
    }

    static def iconInIncorrectFormat = { Input input ->
        if (!input.icon) return false

        File iconFile = input.icon as File
        return !iconFile.name.endsWith(".ico")
    }

}
