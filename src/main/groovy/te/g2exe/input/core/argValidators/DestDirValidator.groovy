package te.g2exe.input.core.argValidators

import org.springframework.stereotype.Component
import te.g2exe.input.core.OptionsValidator
import te.g2exe.input.core.OptionsAssertion

@Component
class DestDirValidator extends OptionsValidator {

    @Override
    List<OptionsAssertion> getAssertions() {
        return [
                newAssertion(
                        """
                        The given 'destDir' doesn't exist or isn't a directory.
                    """,
                        { options ->
                            if (!options.destDir) return false

                            File destinationDirectory = options.destDir as File
                            return !destinationDirectory.exists() || !destinationDirectory.isDirectory()
                        }
                )
        ]
    }
}
