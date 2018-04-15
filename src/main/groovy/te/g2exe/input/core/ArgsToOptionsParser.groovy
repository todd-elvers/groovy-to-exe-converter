package te.g2exe.input.core

import org.springframework.stereotype.Service

@Service
class ArgsToOptionsParser {

    CliBuilder commandLineInterpreter = new CliBuilder()

    ArgsToOptionsParser() {
        commandLineInterpreter.with {
            // Decoration
            header = "arguments:"
            usage = "\ng2exe -f [groovyScript | jarFile] [optional-args]"
            footer = "\n[source code @ http://github.com/todd-elvers/g2exe]"

            // Arguments
            h(longOpt: 'help', 'Shows usage information')
            _(longOpt: 'version', 'Shows version information')
            _(longOpt: 'stacktrace', 'Prints the full stacktrace of any exception caught')
            f(longOpt: 'fileToConvert', args: 1, argName: 'file', 'Required. The groovy script or jar file to convert to an exe. (If issues occur, use quotes around path)')
            d(longOpt: 'destDir', args: 1, argName: 'directory', "Optional. Executable destination directory.\n(Default = fileToConvert's parent dir)")
            t(longOpt: 'tempDir', args: 1, argName: 'directory', "Optional. Overrides the directory where temporary files are written to. Files will be written to a folder entitled 'g2exe' in the given directory. \n(Default = <The-System's-Temp>/g2exe)")
            m(longOpt: 'minJre', args: 1, argName: 'jre_version', "Optional. The minimum JRE the application will accept when searching a user's system for a JRE. Accepts values like x.x.x[_xx].\n(Default = 1.5.0)")
            i(longOpt: 'icon', args: 1, argName: 'file', "Optional.  Fully qualified path to an .ico file that will be used as the file's icon.\n(Default = g2exe's icon)")
            xms(longOpt: 'initHeapSize', args: 1, argName: 'size', "Optional. The JVM's initial heap size in MB.\n(Default = 128)")
            xmx(longOpt: 'maxHeapSize', args: 1, argName: 'size', "Optional. The JVM's maximum heap size in MB.\n(Default = 512)")
            _(longOpt: 'console', "Optional. This flag instructs g2exe to generate an exe that operates as a native command line program. This flag is incompatible with --gui.\n(Default = this flag is present)")
            _(longOpt: 'gui', "Optional. This flag instructs g2exe to generate an exe that operates as a native gui application.  This flag is incompatible with --console.\n(Default = this flag is not present)")
            s(longOpt: 'splash', args: 1, argName: 'image', "Optional. Accepts a .bmp, .jpg, .wbmp, .jpeg, .png, or .gif image and converts it to a 24-bit .bmp image which is used as a splash screen when the executable is launched. This flag is only respected when --gui is also present.\n(Default = none)")
            _(longOpt: 'debug', "Optional.  This flag instructs g2exe to set the logging level to 'debug'.")
            _(longOpt: 'launch4j', "This flag instructs g2exe to launch the Launch4j GUI directly. All other options are ignored if this flag is used.")
            _(longOpt: 'clearIconCache', "This flag instructs g2exe to clear Windows' IconCache for the current user & restart explorer.exe. All other options are ignored if this flag is used.")
        }
    }

    Optional<OptionAccessor> parse(String[] args) {
        Optional.ofNullable(commandLineInterpreter.parse(args))
    }

    void usage() {
        commandLineInterpreter.usage()
    }


}
