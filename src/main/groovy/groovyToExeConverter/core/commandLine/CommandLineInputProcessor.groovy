package groovyToExeConverter.core.commandLine

import groovyToExeConverter.core.commandLine.core.OptionAccessorToAppConfigTransformer
import groovyToExeConverter.core.commandLine.core.OptionAccessorValidator
import groovyToExeConverter.domain.AppConfig

class CommandLineInputProcessor {

    private OptionAccessorValidator optionAccessorValidator = new OptionAccessorValidator()
    private OptionAccessorToAppConfigTransformer optionAccessorTransformer = new OptionAccessorToAppConfigTransformer()
    private CliBuilder cli = new CliBuilder()

    CommandLineInputProcessor() {
        setupCliOptions()
    }

    private void setupCliOptions() {
        cli.with {
            h(longOpt: 'help', required: false, 'show usage information')
            d(longOpt: 'destDir', argName: 'folder', required: false, args: 1, 'exe destination filename, defaults to parent directory of {fileToConvert}')
            f(longOpt: 'fileToConvert', argName: 'groovy-script', required: true, args: 1, 'fully qualified path to the groovy script you wish to convert to an exe')
            s(longOpt: 'stacktrace', required: false, 'prints the full stacktrace of any exception caught')
            t(longOpt: 'tempDir', required: false, 'overrides the directory where temporary files are written to (defaults to the systems temp')
        }
    }

    AppConfig processIntoAppConfig(String[] args) {
        def optionAccessor = cli.parse(args)

        if (optionAccessor && !optionAccessor.help) {
            optionAccessorValidator.validate(optionAccessor)
            return optionAccessorTransformer.transform(optionAccessor)
        }
    }

}
