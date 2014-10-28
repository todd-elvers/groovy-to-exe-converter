package groovyToExeConverter.input.core.validation.parameter.validators
import groovyToExeConverter.input.core.validation.parameter.ParameterValidator
import groovyToExeConverter.model.AppConfigDefaults
import groovyToExeConverter.model.exception.InputValidationException

trait HeapSizeValidator implements ParameterValidator {

    static final String ONE_OR_MORE_DIGITS_ONLY_PATTERN = /^\d+$/

    void validate(OptionAccessor commandLineInput) {
        switch(commandLineInput) {
            case initHeapSizeIsNotNumOrIsZeroOrLess: throw new InputValidationException("The value for 'initHeapSize' is not a number, or is less than or equal to zero. Please provide a number greater than zero.")
            case maxHeapSizeIsNotNumOrIsZeroOrLess : throw new InputValidationException("The value for 'maxHeapSize' is not a number, or is less than or equal to zero. Please provide a number greater than zero.")
            case maxHeapSizeLessThanInitHeapSize   : throw new InputValidationException("The value for 'maxHeapSize' cannot be less than the value for 'initHeapSize'.")
        }

        super.validate(commandLineInput)
    }

    private Closure<Boolean> initHeapSizeIsNotNumOrIsZeroOrLess = { input ->
        String initHeapSize = input.initHeapSize
        if (initHeapSize && initHeapSize.matches(ONE_OR_MORE_DIGITS_ONLY_PATTERN)) {
            return initHeapSize.toInteger() <= 0
        }
        return false
    }

    private Closure<Boolean> maxHeapSizeIsNotNumOrIsZeroOrLess = { input ->
        String maxHeapSize = input.maxHeapSize
        if (maxHeapSize && maxHeapSize.matches(ONE_OR_MORE_DIGITS_ONLY_PATTERN)) {
            return maxHeapSize.toInteger() <= 0
        }
        return false
    }

    private Closure<Boolean> maxHeapSizeLessThanInitHeapSize = { input ->
        int initialHeapSize = (input.initHeapSize ?: AppConfigDefaults.INITIAL_HEAP_SIZE.defaultValue) as int
        int maximumHeapSize = (input.maxHeapSize ?: AppConfigDefaults.MAXIMUM_HEAP_SIZE.defaultValue) as int
        return maximumHeapSize < initialHeapSize
    }


}
