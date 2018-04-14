package te.g2exe.input.core.argValidators

import org.springframework.stereotype.Component
import te.g2exe.input.core.CommandLineArgValidator
import te.g2exe.input.core.ValidationAssertion

@Component
class AppTypeValidator extends CommandLineArgValidator {

    @Override
    List<ValidationAssertion> getAssertions() {
        return [
                newAssertion(
                        "Both --console and --gui cannot be set at the same time.",
                        { args ->
                            return args.console && args.gui
                        }
                )
        ]
    }
}
