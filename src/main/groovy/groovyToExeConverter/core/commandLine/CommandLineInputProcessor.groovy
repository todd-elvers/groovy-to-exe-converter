package groovyToExeConverter.core.commandLine

import groovy.util.logging.Log4j
import groovyToExeConverter.core.commandLine.core.OptionAccessorToAppConfigTransformer
import groovyToExeConverter.core.commandLine.core.OptionAccessorValidator
import groovyToExeConverter.core.exception.InputValidationException
import groovyToExeConverter.domain.AppConfig

@Log4j
class CommandLineInputProcessor {

    private OptionAccessorValidator optionAccessorValidator = new OptionAccessorValidator()
    private OptionAccessorToAppConfigTransformer optionAccessorTransformer = new OptionAccessorToAppConfigTransformer()
    private CliBuilder cli = new CliBuilder()

    CommandLineInputProcessor() {
        setupCliOptions()
    }

    private void setupCliOptions() {
        cli.with {
            h(longOpt: 'help', 'Shows usage information')
            v(longOpt: 'version', 'Shows version information')
            s(longOpt: 'stacktrace', 'Prints the full stacktrace of any exception caught')

            f(longOpt: 'fileToConvert', argName: 'file', args: 1, 'Required.  Fully qualified path to the groovy script or jar file you wish to convert to an exe')
            d(longOpt: 'destDir', argName: 'directory', args: 1, 'Executable destination directory, defaults to parent directory of {fileToConvert}')
            t(longOpt: 'tempDir', argName: 'directory', args: 1, "Overrides the directory where temporary files are written to. Default=the system's temp")
            m(longOpt: 'minJre', args: 1, argName: 'jre_version', "The minimum JRE the application will accept when searching a user's system for a JRE. Accepts values like 'x.x.x' or 'x.x.xx'. Default = '1.5.0'")
            i(longOpt: 'icon', args: 1, argName: 'file', "Fully qualified path to an .ico file that will be used as the file's icon.  Default = None")
            xms(longOpt: 'initHeapSize', args: 1, argName: 'size', "The executable's initial heap size in MB. Default = 128.")
            xmx(longOpt: 'maxHeapSize', args: 1, argName: 'size', "The executable's maximum heap size in MB. Default = 256.")

            //TODO: Possibly postpone the following features till next release
//            j(longOpt: 'javaPath', args: 1, argName: 'directory', "Overrides the directory where launch4j first searches for a JRE on a user's system. If a JRE is not found in this location, launch4j will search the registry. Default=None.")
//            hT(longOpt: 'headerType', "Value 'gui' allows for splash screen but no console output, and value 'console' allows for console output but no splash screen.  Default='console'.")
//            splash(args: 1, argName: 'bmp-file', "If headerType='gui' then one can point this file to a .bmp and...")
//            u64(longOpt: 'use64', "...")
        }
        cli.usage = "g2exe -f file.[groovy|jar]\n"
    }

    AppConfig processIntoAppConfig(String[] args) {
        def optionAccessor = cli.parse(args)

        if (optionAccessor.help) {
            cli.usage()
        } else if (optionAccessor.version) {
            //TODO: Figure out how to not hard-code the version here
            log.info("version 0.0.10-SNAPSHOT")
        } else {
            optionAccessorValidator.validate(optionAccessor)
            return optionAccessorTransformer.transform(optionAccessor)
        }

        throw new InputValidationException()
    }

}
