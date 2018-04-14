package te.g2exe.input.core.argValidators

import te.g2exe.input.core.CommandLineArgValidator
import te.g2exe.input.core.ValidationAssertion
import te.g2exe.model.AppConfigDefaults

class HeapSizeValidator extends CommandLineArgValidator {

    static final ONE_OR_MORE_DIGITS_ONLY_PATTERN = /^\d{1,}$/

    @Override
    List<ValidationAssertion> getAssertions() {
        return [
                newAssertion(
                        """
                            The value for 'initHeapSize' is not a number, or is less than or equal to zero. Please provide a number greater than zero.
                        """,
                        { args ->
                            if (!args.initHeapSize) return false
                            if (!String.valueOf(args.initHeapSize).matches(ONE_OR_MORE_DIGITS_ONLY_PATTERN)) return true

                            int initialHeapSize = args.initHeapSize as int
                            return initialHeapSize <= 0
                        }
                ),

                newAssertion(
                        """
                            The value for 'maxHeapSize' is not a number, or is less than or equal to zero. Please provide a number greater than zero.
                        """,
                        { args ->
                            if (!args.maxHeapSize) return false
                            if (!String.valueOf(args.maxHeapSize).matches(ONE_OR_MORE_DIGITS_ONLY_PATTERN)) return true

                            int maximumHeapSize = args.maxHeapSize as int
                            return maximumHeapSize <= 0
                        }
                ),

                newAssertion(
                        """
                            The value for 'maxHeapSize' cannot be less than the value for 'initHeapSize'.
                        """,
                        { args ->
                            int initialHeapSize = (args.initHeapSize ?: AppConfigDefaults.INITIAL_HEAP_SIZE.defaultValue) as int
                            int maximumHeapSize = (args.maxHeapSize ?: AppConfigDefaults.MAXIMUM_HEAP_SIZE.defaultValue) as int
                            return maximumHeapSize < initialHeapSize
                        }
                )
        ]
    }
}
