package te.g2exe.input.core.argValidators

import org.springframework.stereotype.Component
import te.g2exe.input.core.CommandLineArgValidator
import te.g2exe.input.core.ValidationAssertion
import te.g2exe.model.AppConfigDefaults

@Component
class TempDirValidator extends CommandLineArgValidator {

    @Override
    List<ValidationAssertion> getAssertions() {
        return [
                newAssertion(
                        """
                            The given 'tempDir' doesn't exist or isn't a directory.
                        """,
                        { args ->
                            if (!args.tempDir) return false

                            File temporaryDirectory = args.tempDir as File
                            return !temporaryDirectory.exists() || !temporaryDirectory.isDirectory()
                        }
                ),
                newAssertion(
                        """
                            The given 'tempDir' contains spaces in its file path which causes issues with a tool this application uses.
                            Please explicitly set the temporary directory path to a directory not containing spaces.
                        """,
                        { args ->
                            String tempDirPath = args.tempDir ?: AppConfigDefaults.TEMP_DIR_PATH.defaultValue
                            return tempDirPath && tempDirPath.contains(" ")
                        }
                ),

                newAssertion(
                        """
                            The 'tempDir' parameter wasn't set and could not be determined from the system.
                            Please explicitly set the temporary directory path.
                        """,
                        { args ->
                            if (!args.tempDir) return false

                            String sysTempPath = System.getenv("TEMP")
                            return !sysTempPath
                        }
                )

        ]
    }
}
