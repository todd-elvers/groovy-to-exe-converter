package te.g2exe.input.core

import groovy.transform.CompileStatic

@CompileStatic
abstract class OptionsValidator {

    abstract List<OptionsAssertion> getAssertions()

    static OptionsAssertion newAssertion(def failureMessage, Closure<Boolean> assertion) {
        if(failureMessage instanceof GString) {
            failureMessage = failureMessage.stripIndent()
        }

        return new OptionsAssertion(
                assertion     : assertion,
                failureMessage: failureMessage
        )
    }
}
