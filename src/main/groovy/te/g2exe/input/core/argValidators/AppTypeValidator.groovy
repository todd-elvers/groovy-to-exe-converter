package te.g2exe.input.core.argValidators

import org.springframework.stereotype.Component
import te.g2exe.input.core.OptionsValidator
import te.g2exe.input.core.OptionsAssertion

@Component
class AppTypeValidator extends OptionsValidator {

    @Override
    List<OptionsAssertion> getAssertions() {
        return [
                newAssertion(
                        "Both --console and --gui cannot be set at the same time.",
                        { options ->
                            return options.console && options.gui
                        }
                )
        ]
    }
}
