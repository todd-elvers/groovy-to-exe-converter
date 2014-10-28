package groovyToExeConverter.input.core.validation.parameter

import groovyToExeConverter.input.core.validation.parameter.validators.EndOfValidationChain
import groovyToExeConverter.input.core.validation.parameter.validators.JreVersionValidator
import groovyToExeConverter.model.exception.InputValidationException
import spock.lang.Specification
import spock.lang.Unroll

class JreVersionValidatorTest extends Specification {

    class Validator implements EndOfValidationChain, JreVersionValidator {}

    Validator validator = new Validator()

    @Unroll
    def "given invalid JRE version #jreVersion, validate() throws InputValidationException"() {
        given:
            def commandLineInput = Mock(OptionAccessor)

        and:
            commandLineInput.getProperty('minJre') >> jreVersion

        when:
            validator.validate(commandLineInput)

        then:
            thrown(InputValidationException)

        where:
            jreVersion << ['1.4.00', '1.2', '1', '1.5.0_245']
    }

    @Unroll
    def "given JRE Version #jreVersion, validate() throws no exception"() {
        given:
            def commandLineInput = Mock(OptionAccessor)

        and:
            commandLineInput.getProperty('minJre') >> jreVersion

        when:
            validator.validate(commandLineInput)

        then:
            noExceptionThrown()

        where:
            jreVersion << ['1.4.0', '1.5.1', '1.5.0_24']
    }

}
