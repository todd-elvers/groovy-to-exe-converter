package te.g2exe.input.core.argValidators

import org.springframework.stereotype.Component
import te.g2exe.input.core.OptionsValidator
import te.g2exe.input.core.OptionsAssertion
import te.g2exe.model.AppConfigDefaults

@Component
class TempDirValidator extends OptionsValidator {

    @Override
    List<OptionsAssertion> getAssertions() {
        return [
                newAssertion(
                        """
                            The given 'tempDir' doesn't exist or isn't a directory.
                        """,
                        { options ->
                            if (!options.tempDir) return false

                            File temporaryDirectory = options.tempDir as File
                            return !temporaryDirectory.exists() || !temporaryDirectory.isDirectory()
                        }
                ),
                newAssertion(
                        """
                            The given 'tempDir' contains spaces in its file path which causes issues with a tool this application uses.
                            Please explicitly set the temporary directory path to a directory not containing spaces.
                        """,
                        { options ->
                            String tempDirPath = options.tempDir ?: AppConfigDefaults.TEMP_DIR_PATH.defaultValue
                            return tempDirPath && tempDirPath.contains(" ")
                        }
                ),

                newAssertion(
                        """
                            The 'tempDir' parameter wasn't set and could not be determined from the system.
                            Please explicitly set the temporary directory path.
                        """,
                        { options ->
                            if (!options.tempDir) return false

                            String sysTempPath = System.getenv("TEMP")
                            return !sysTempPath
                        }
                )

        ]
    }
}
