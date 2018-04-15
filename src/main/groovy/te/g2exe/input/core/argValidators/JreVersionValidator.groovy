package te.g2exe.input.core.argValidators

import org.springframework.stereotype.Component
import te.g2exe.input.core.OptionsValidator
import te.g2exe.input.core.OptionsAssertion

@Component
class JreVersionValidator extends OptionsValidator {

    // Only digits and periods in the form: 'x.x.x[_xx]'
    static final JRE_VERSION_PATTERN = ~/^\d.\d.\d(_\d\d){0,1}$/

    @Override
    List<OptionsAssertion> getAssertions() {
        return [
                newAssertion(
                        "The 'minJre' parameter only accepts strings of the format x.x.x[_xx].",
                        { options ->
                            if (!options.minJre) return false

                            String minJre = options.minJre
                            return !minJre.matches(JRE_VERSION_PATTERN)
                        }
                )
        ]
    }
}
