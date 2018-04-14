package te.g2exe.input.core.argValidators

import org.springframework.stereotype.Component
import te.g2exe.input.core.CommandLineArgValidator
import te.g2exe.input.core.ValidationAssertion

@Component
class DestDirValidator extends CommandLineArgValidator {

    @Override
    List<ValidationAssertion> getAssertions() {
        return [
                newAssertion(
                        """
                        The given 'destDir' doesn't exist or isn't a directory.
                    """,
                        { args ->
                            if (!args.destDir) return false

                            File destinationDirectory = args.destDir as File
                            return !destinationDirectory.exists() || !destinationDirectory.isDirectory()
                        }
                )
        ]
    }
}
