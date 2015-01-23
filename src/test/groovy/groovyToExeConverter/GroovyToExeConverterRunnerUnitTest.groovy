package groovyToExeConverter

import groovyToExeConverter.core.FileConverter
import groovyToExeConverter.core.FileConverterFactory
import groovyToExeConverter.input.InputProcessor
import groovyToExeConverter.model.AppConfig
import groovyToExeConverter.util.EnvironmentHandler
import spock.lang.Specification

class GroovyToExeConverterRunnerUnitTest extends Specification {

    def fileConverter = GroovyMock(FileConverter)

    GroovyToExeConverterRunner runner = [
            args              : null,
            inputProcessor    : GroovyMock(InputProcessor),
            environmentHandler: GroovyMock(EnvironmentHandler)
    ]

    def setup() {
        GroovyMock(FileConverterFactory, global: true)
    }

    def "valid input + run() = validates groovy home, adds shutdown hook, validates input, makes fileConverter and calls convert()"() {
        when:
            runner.run()

        then:
            1 * runner.environmentHandler.validateGroovyHome()
            1 * runner.environmentHandler.addShutdownHookToKillG2exeProcess()
            1 * runner.inputProcessor.processIntoAppConfig(runner.args) >> new AppConfig(null)
            1 * FileConverterFactory.makeFileConverter(_ as AppConfig) >> fileConverter
            1 * fileConverter.convert()
    }

    def "invalid input + run() = validates groovy home, adds shutdown hook, validates input, then stops"() {
        when:
            runner.run()

        then:
            1 * runner.environmentHandler.validateGroovyHome()
            1 * runner.environmentHandler.addShutdownHookToKillG2exeProcess()
            1 * runner.inputProcessor.processIntoAppConfig(runner.args) >> null
            0 * FileConverterFactory.makeFileConverter(_ as AppConfig) >> fileConverter
            0 * fileConverter.convert()
    }
}