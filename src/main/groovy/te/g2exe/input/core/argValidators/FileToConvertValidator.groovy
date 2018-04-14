package te.g2exe.input.core.argValidators

import org.springframework.stereotype.Component
import te.g2exe.input.core.CommandLineArgValidator
import te.g2exe.input.core.ValidationAssertion

import static org.apache.commons.io.FilenameUtils.isExtension

@Component
class FileToConvertValidator extends CommandLineArgValidator {

    @Override
    List<ValidationAssertion> getAssertions() {
        return [
                newAssertion(
                        """
                            The parameter 'fileToConvert' is missing or points to a file that doesn't exist.
                            Use 'g2exe --help' to display available commands.
                        """,
                        { args ->
                            File fileToConvert
                            if (args.fileToConvert) {
                                fileToConvert = new File(args.fileToConvert as String)
                            }
                            return !fileToConvert || !fileToConvert.exists()
                        }
                ),

                newAssertion(
                        """
                            Invalid file type for 'fileToConvert' - only .groovy and .jar files are allowed.
                        """,
                        { args ->
                            File fileToConvert = args.fileToConvert as File
                            return !fileToConvert.isFile() || !isExtension(fileToConvert.name, ["groovy", "jar"])
                        }
                )
        ]
    }
}
