package te.g2exe.input.core.argValidators

import org.springframework.stereotype.Component
import te.g2exe.input.core.OptionsValidator
import te.g2exe.input.core.OptionsAssertion

import static org.apache.commons.io.FilenameUtils.isExtension

@Component
class IconFileValidator extends OptionsValidator {

    @Override
    List<OptionsAssertion> getAssertions() {
        return [
                newAssertion(
                        "The 'icon' parameter points to a file that doesn't exist or is a directory.",
                        { options ->
                            if (!options.icon) return false

                            File iconFile = options.icon as File
                            return !iconFile.exists() || !iconFile.isFile()
                        }
                ),
                newAssertion(
                        "The 'icon' parameter points to a file with an invalid file type - only .ico files are supported.",
                        { options ->
                            if (!options.icon) return false

                            File iconFile = options.icon as File
                            return !isExtension(iconFile.name, "ico")
                        }
                )
        ]
    }
}
