package te.g2exe.input.core.parameterValidators

import static org.apache.commons.io.FilenameUtils.isExtension

class IconFileValidator {

    static def iconDoesNotExistOrIsNotFile = { input ->
        if (!input.icon) return false

        File iconFile = input.icon as File
        return !iconFile.exists() || !iconFile.isFile()
    }

    static def iconInIncorrectFormat = { input ->
        if (!input.icon) return false

        File iconFile = input.icon as File
        return !isExtension(iconFile.name, "ico")
    }

}
