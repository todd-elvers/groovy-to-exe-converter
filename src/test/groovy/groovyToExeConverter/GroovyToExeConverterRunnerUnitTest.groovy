package groovyToExeConverter

import groovyToExeConverter.domain.AppConfig
import groovyToExeConverter.fileConversion.FileConverter
import groovyToExeConverter.fileConversion.FileConverterFactory
import groovyToExeConverter.input.InputProcessor
import groovyToExeConverter.util.EnvironmentValidator
import spock.lang.Specification

class GroovyToExeConverterRunnerUnitTest extends Specification {

    def fileConverter = Mock(FileConverter)

    GroovyToExeConverterRunner runner = [
            input: null,
            inputProcessor: Mock(InputProcessor),
            environmentValidator: Mock(EnvironmentValidator)
    ]

    def setup() {
        GroovyMock(FileConverterFactory, global: true)
    }

    def "valid input + run() = validates environment, validates input, makes fileConverter, and calls convert()"() {
        when:
            runner.run()

        then:
            1 * runner.environmentValidator.validate()
            1 * runner.inputProcessor.processIntoAppConfig(runner.input) >> new AppConfig()
            1 * FileConverterFactory.makeFileConverter(_ as AppConfig) >> fileConverter
            1 * fileConverter.convert()
    }

    def "invalid input + run() = validates environment, validates input, then stops"() {
        when:
            runner.run()

        then:
            1 * runner.environmentValidator.validate()
            1 * runner.inputProcessor.processIntoAppConfig(runner.input) >> null
            0 * FileConverterFactory.makeFileConverter(_ as AppConfig) >> fileConverter
            0 * fileConverter.convert()
    }
}