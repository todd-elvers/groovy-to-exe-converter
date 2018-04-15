package te.g2exe.input.core.argValidators

import org.springframework.stereotype.Component
import te.g2exe.input.core.OptionsValidator
import te.g2exe.input.core.OptionsAssertion

import static org.apache.commons.io.FilenameUtils.isExtension

@Component
class FileToConvertValidator extends OptionsValidator {

    @Override
    List<OptionsAssertion> getAssertions() {
        return [
                newAssertion(
                        """
                            The parameter 'fileToConvert' is missing or points to a file that doesn't exist.
                            Use 'g2exe --help' to display available commands.
                        """,
                        { options ->
                            File fileToConvert
                            if (options.fileToConvert) {
                                fileToConvert = new File(options.fileToConvert as String)
                            }
                            return !fileToConvert || !fileToConvert.exists()
                        }
                ),

                newAssertion(
                        """
                            Invalid file type for 'fileToConvert' - only .groovy and .jar files are allowed.
                        """,
                        { options ->
                            File fileToConvert = options.fileToConvert as File
                            return !fileToConvert.isFile() || !isExtension(fileToConvert.name, ["groovy", "jar"])
                        }
                )
        ]
    }
}
