package groovyToExeConverter.input.core.parameterValidators

import groovy.util.OptionAccessor as Input

class JreVersionValidator {

    // Only digits and periods in the form: 'x.x.x' or 'x.x.xx'
    static final JRE_VERSION_PATTERN = ~/^\d{1}.\d{1}.\d{1,2}$/

    static def minJreInInvalidFormat = { Input input ->
        if (!input.minJre) return false

        String minJre = input.minJre
        return !minJre.matches(JRE_VERSION_PATTERN)
    }

}
