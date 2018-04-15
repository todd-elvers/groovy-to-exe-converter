package te.g2exe.input.core.argValidators

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component
import te.g2exe.input.core.OptionsValidator
import te.g2exe.input.core.OptionsAssertion

import static org.apache.commons.io.FilenameUtils.isExtension

@Slf4j
@Component
class SplashFileValidator extends OptionsValidator {

    static final SUPPORTED_IMAGE_FORMATS = ['bmp', 'jpg', 'jpeg', 'gif']

    @Override
    List<OptionsAssertion> getAssertions() {
        return [
                newAssertion(
                        """
                            The 'splash' parameter points to a file that doesn't exist or is a directory.
                        """,
                        { options ->
                            if (!options.splash) return false

                            File splashFile = options.splash as File
                            return !splashFile.exists() || !splashFile.isFile()
                        }
                ),
                newAssertion(
                        """
                            The 'splash' parameter points to a file with an invalid file type - only bmp, jpg, jpeg, or gif files are supported.
                        """,
                        { options ->
                            if (!options.splash) return false

                            File splashFile = options.splash as File
                            return !isExtension(splashFile.name, SUPPORTED_IMAGE_FORMATS)
                        }
                )
        ]
    }
}
