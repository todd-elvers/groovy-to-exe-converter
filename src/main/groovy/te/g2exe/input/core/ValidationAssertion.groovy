package te.g2exe.input.core

import groovy.util.OptionAccessor as CommandLineInput

class ValidationAssertion {
    Closure<Boolean> assertion
    String failureMessage

    boolean isNotValid(CommandLineInput input) {
        return !assertion.call(input)
    }
}
