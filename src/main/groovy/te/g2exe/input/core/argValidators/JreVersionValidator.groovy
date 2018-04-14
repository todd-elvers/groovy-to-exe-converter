package te.g2exe.input.core.argValidators

import org.springframework.stereotype.Component
import te.g2exe.input.core.CommandLineArgValidator
import te.g2exe.input.core.ValidationAssertion

@Component
class JreVersionValidator extends CommandLineArgValidator {

    // Only digits and periods in the form: 'x.x.x[_xx]'
    static final JRE_VERSION_PATTERN = ~/^\d.\d.\d(_\d\d){0,1}$/

    @Override
    List<ValidationAssertion> getAssertions() {
        return [
                newAssertion(
                        "The 'minJre' parameter only accepts strings of the format x.x.x[_xx].",
                        { args ->
                            if (!args.minJre) return false

                            String minJre = args.minJre
                            return !minJre.matches(JRE_VERSION_PATTERN)
                        }
                )
        ]
    }
}
