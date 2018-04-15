package te.g2exe.input

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import te.g2exe.input.core.*
import te.g2exe.model.AppConfig
import te.g2exe.model.exception.InputValidationException

@Slf4j
@Component
class OptionsToAppConfigProcessor {

    @Autowired List<OptionsValidator> optionsValidator
    @Autowired OptionsToAppConfigTransformer optionsToAppConfigTransformer

    AppConfig process(OptionAccessor options) {
        log.debug("Validating user input...")
        optionsValidator.each { validator ->
            validator.assertions.each { assertion ->
                if(assertion.isNotValid(options)) {
                    throw new InputValidationException(assertion.failureMessage)
                }
            }
        }
        log.debug("Input valid.")

        return optionsToAppConfigTransformer.transform(options)
    }


}
