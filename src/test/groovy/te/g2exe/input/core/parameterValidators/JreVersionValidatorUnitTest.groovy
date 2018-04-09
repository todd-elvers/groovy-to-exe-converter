package te.g2exe.input.core.parameterValidators

import spock.lang.Specification
import spock.lang.Unroll

class JreVersionValidatorUnitTest extends Specification {

    @Unroll
    def "given invalid JRE version #jreVersion, minJreInInvalidFormat() returns true"() {
        expect:
            JreVersionValidator.minJreInInvalidFormat(new Expando([minJre: jreVersion]))

        where:
            jreVersion << ['1.4.00', '1.2', '1', '1.5.0_245']
    }

    @Unroll
    def "given JRE Version #jreVersion, minJreInInvalidFormat() returns false"() {
        expect:
            !JreVersionValidator.minJreInInvalidFormat(new Expando([minJre: jreVersion]))

        where:
            jreVersion << ['1.4.0', '1.5.1', '1.5.0_24']
    }

}
