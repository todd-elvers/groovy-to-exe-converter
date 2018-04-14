package te.g2exe.input.core.argValidators

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component
import te.g2exe.input.core.CommandLineArgValidator
import te.g2exe.input.core.ValidationAssertion

import static org.apache.commons.io.FilenameUtils.isExtension

@Slf4j
@Component
class CommandLineArgValidator extends CommandLineArgValidator{

    static final SUPPORTED_IMAGE_FORMATS = ['bmp', 'jpg', 'jpeg', 'gif']

    @Override
    List<ValidationAssertion> getValidationAssertions() {
        return [
                newAssertion(
                        """
                            The 'splash' parameter points to a file that doesn't exist or is a directory.
                        """,
                        { args ->
                            if (!args.splash) return false

                            File splashFile = args.splash as File
                            return !splashFile.exists() || !splashFile.isFile()
                        }
                ),
                newAssertion(
                        """
                            The 'splash' parameter points to a file with an invalid file type - only bmp, jpg, jpeg, or gif files are supported.
                        """,
                        { args ->
                            if (!args.splash) return false

                            File splashFile = args.splash as File
                            return !isExtension(splashFile.name, SUPPORTED_IMAGE_FORMATS)
                        }
                )
        ]
    }
}
