package te.g2exe.input.core.argValidators

import org.springframework.stereotype.Component
import te.g2exe.input.core.OptionsValidator
import te.g2exe.input.core.OptionsAssertion
import te.g2exe.model.AppConfigDefaults

@Component
class HeapSizeValidator extends OptionsValidator {

    static final ONE_OR_MORE_DIGITS_ONLY_PATTERN = /^\d{1,}$/

    @Override
    List<OptionsAssertion> getAssertions() {
        return [
                newAssertion(
                        """
                            The value for 'initHeapSize' is not a number, or is less than or equal to zero. Please provide a number greater than zero.
                        """,
                        { options ->
                            if (!options.initHeapSize) return false
                            if (!String.valueOf(options.initHeapSize).matches(ONE_OR_MORE_DIGITS_ONLY_PATTERN)) return true

                            int initialHeapSize = options.initHeapSize as int
                            return initialHeapSize <= 0
                        }
                ),

                newAssertion(
                        """
                            The value for 'maxHeapSize' is not a number, or is less than or equal to zero. Please provide a number greater than zero.
                        """,
                        { options ->
                            if (!options.maxHeapSize) return false
                            if (!String.valueOf(options.maxHeapSize).matches(ONE_OR_MORE_DIGITS_ONLY_PATTERN)) return true

                            int maximumHeapSize = options.maxHeapSize as int
                            return maximumHeapSize <= 0
                        }
                ),

                newAssertion(
                        """
                            The value for 'maxHeapSize' cannot be less than the value for 'initHeapSize'.
                        """,
                        { options ->
                            int initialHeapSize = (options.initHeapSize ?: AppConfigDefaults.INITIAL_HEAP_SIZE.defaultValue) as int
                            int maximumHeapSize = (options.maxHeapSize ?: AppConfigDefaults.MAXIMUM_HEAP_SIZE.defaultValue) as int
                            return maximumHeapSize < initialHeapSize
                        }
                )
        ]
    }
}
