package groovyToExeConverter.core.commandLine

import org.junit.Test

/**
 * Created by Todd on 1/20/14.
 */
class AppConfigProcessorTest {

    private CommandLineInputProcessor inputProcessor = new CommandLineInputProcessor()

    public void setup() {

    }

    @Test
    public void "parser returns null when --help is specified"() {
        assert inputProcessor.processIntoAppConfig(['--help'] as String[]) == null
    }

}
