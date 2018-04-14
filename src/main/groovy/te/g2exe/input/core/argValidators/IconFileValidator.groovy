package te.g2exe.input.core.argValidators

import org.springframework.stereotype.Component
import te.g2exe.input.core.CommandLineArgValidator
import te.g2exe.input.core.ValidationAssertion

import static org.apache.commons.io.FilenameUtils.isExtension

@Component
class IconFileValidator extends CommandLineArgValidator {

    @Override
    List<ValidationAssertion> getAssertions() {
        return [
                newAssertion(
                        "The 'icon' parameter points to a file that doesn't exist or is a directory.",
                        { args ->
                            if (!args.icon) return false

                            File iconFile = args.icon as File
                            return !iconFile.exists() || !iconFile.isFile()
                        }
                ),
                newAssertion(
                        "The 'icon' parameter points to a file with an invalid file type - only .ico files are supported.",
                        { args ->
                            if (!args.icon) return false

                            File iconFile = args.icon as File
                            return !isExtension(iconFile.name, "ico")
                        }
                )
        ]
    }
}
