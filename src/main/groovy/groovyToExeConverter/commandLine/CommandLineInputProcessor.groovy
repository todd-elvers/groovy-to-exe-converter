package groovyToExeConverter.commandLine
import groovy.util.logging.Log4j
import groovyToExeConverter.PropertiesReader
import groovyToExeConverter.commandLine.core.OptionAccessorToAppConfigTransformer
import groovyToExeConverter.commandLine.core.OptionAccessorValidator
import groovyToExeConverter.domain.AppConfig

@Log4j
class CommandLineInputProcessor {
    private OptionAccessorValidator optionAccessorValidator = new OptionAccessorValidator()
    private OptionAccessorToAppConfigTransformer optionAccessorToAppConfigTransformer = new OptionAccessorToAppConfigTransformer()
    private CliBuilder cli = new CliBuilder()

    CommandLineInputProcessor() {
        setupCliOptions()
    }

    private void setupCliOptions() {
        cli.usage = "g2exe -f file.[groovy|jar] (optional-args)"
        cli.header = "\nArguments:"
        cli.with {
            h(longOpt: 'help', 'Shows usage information')
            v(longOpt: 'version', 'Shows version information')
            s(longOpt: 'stacktrace', 'Prints the full stacktrace of any exception caught')
            f(longOpt: 'fileToConvert', args: 1, argName: 'file', 'Required.  Fully qualified path to the groovy script or jar file to convert to an exe.  Place the path in quotes.')
            d(longOpt: 'destDir', args: 1, argName: 'directory', 'Executable destination directory, defaults to parent directory of {fileToConvert}')
            t(longOpt: 'tempDir', args: 1, argName: 'directory', "Overrides the directory where temporary files are written to. When using this parameter, it's best to specify an empty directory.\n(Default = the system's temp)")
            m(longOpt: 'minJre', args: 1, argName: 'jre_version', "The minimum JRE the application will accept when searching a user's system for a JRE. Accepts values like 'x.x.x' or 'x.x.xx'. (Default = '1.5.0')")
            i(longOpt: 'icon', args: 1, argName: 'file', "Fully qualified path to an .ico file that will be used as the file's icon.\n(Default = None)")
            xms(longOpt: 'initHeapSize', args: 1, argName: 'size', "The JVM's initial heap size in MB.\n(Default = 128)")
            xmx(longOpt: 'maxHeapSize', args: 1, argName: 'size', "The JVM's maximum heap size in MB.\n(Default = 512)")
        }
    }

    AppConfig processIntoAppConfig(String[] args) {
        def optionAccessor = cli.parse(args)

        if (optionAccessor.help) {
            cli.usage()
            return
        } else if (optionAccessor.version) {
            log.info("Version: ${PropertiesReader.readAppProperty("version")}")
            return
        }

        optionAccessorValidator.validate(optionAccessor)
        optionAccessorToAppConfigTransformer.transform(optionAccessor)
    }

}
