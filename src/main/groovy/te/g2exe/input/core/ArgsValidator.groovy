package te.g2exe.input.core

import groovy.transform.CompileStatic

@CompileStatic
abstract class ArgsValidator {

    abstract List<ValidationAssertion> getAssertions()

    static ValidationAssertion newAssertion(def assertionFailedMessage, Closure<Boolean> assertion) {
        if(assertionFailedMessage instanceof GString) {
            assertionFailedMessage = assertionFailedMessage.stripIndent()
        }

        return new ValidationAssertion(
                assertion             : assertion,
                failureMessage: assertionFailedMessage
        )
    }
}
