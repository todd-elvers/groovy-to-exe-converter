package te.g2exe.input.core.parameterValidators

class JreVersionValidator {

    // Only digits and periods in the form: 'x.x.x[_xx]'
    static final JRE_VERSION_PATTERN = ~/^\d.\d.\d(_\d\d){0,1}$/

    static def minJreInInvalidFormat = { input ->
        if (!input.minJre) return false

        String minJre = input.minJre
        return !minJre.matches(JRE_VERSION_PATTERN)
    }

}
