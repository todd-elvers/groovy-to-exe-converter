package te.g2exe.input.core


class OptionsAssertion {
    Closure<Boolean> assertion
    String failureMessage

    boolean isNotValid(OptionAccessor options) {
        return !assertion.call(options)
    }
}
