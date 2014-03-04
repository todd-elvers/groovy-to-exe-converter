package groovyToExeConverter.input.core.parameterValidators

import groovy.util.OptionAccessor as Input
import groovyToExeConverter.domain.AppConfigDefaults

class HeapSizeValidator {

    static final ONE_OR_MORE_DIGITS_ONLY_PATTERN = /^\d{1,}$/

    static def initHeapSizeIsNotNumOrIsZeroOrLess = { Input input ->
        if (!input.initHeapSize) return false
        if (!String.valueOf(input.initHeapSize).matches(ONE_OR_MORE_DIGITS_ONLY_PATTERN)) return true

        int initialHeapSize = input.initHeapSize as int
        initialHeapSize <= 0
    }

    static def maxHeapSizeIsNotNumOrIsZeroOrLess = { Input input ->
        if (!input.maxHeapSize) return false
        if (!String.valueOf(input.maxHeapSize).matches(ONE_OR_MORE_DIGITS_ONLY_PATTERN)) return true

        int maximumHeapSize = input.maxHeapSize as int
        return maximumHeapSize <= 0
    }

    static def maxHeapSizeLessThanInitHeapSize = { Input input ->
        int initialHeapSize = (input.initHeapSize ?: AppConfigDefaults.INITIAL_HEAP_SIZE.defaultValue) as int
        int maximumHeapSize = (input.maxHeapSize ?: AppConfigDefaults.MAXIMUM_HEAP_SIZE.defaultValue) as int
        return maximumHeapSize < initialHeapSize
    }

}
