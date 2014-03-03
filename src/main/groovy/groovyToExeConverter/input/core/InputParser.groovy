package groovyToExeConverter.input.core

import groovy.util.OptionAccessor as CommandLineInput

class InputParser {

    private def cli = new CliBuilder()

    InputParser() {
        cli.usage = "\ng2exe -f [groovyScript | jarFile] [optional-args]"
        cli.header = "Arguments:"
        cli.with {
            h(longOpt: 'help', 'Shows usage information')
            _(longOpt: 'version', 'Shows version information')
            _(longOpt: 'stacktrace', 'Prints the full stacktrace of any exception caught')
            f(longOpt: 'fileToConvert', args: 1, argName: 'file', 'Required. The path to the groovy script or jar file to convert to an exe. Supports absolute & relative file paths. (Try placing the path in quotes if you encounter issues)')
            d(longOpt: 'destDir', args: 1, argName: 'directory', 'Optional. Executable destination directory, defaults to parent directory of {fileToConvert}')
            t(longOpt: 'tempDir', args: 1, argName: 'directory', "Optional. Overrides the directory where temporary files are written to. Files will be written to folder 'g2exe' in the temporary directory. \n(Default = <The-System's-Temp>/g2exe)")
            m(longOpt: 'minJre', args: 1, argName: 'jre_version', "Optional. The minimum JRE the application will accept when searching a user's system for a JRE. Accepts values like 'x.x.x' or 'x.x.xx'.\n(Default = '1.5.0')")
            i(longOpt: 'icon', args: 1, argName: 'file', "Optional.  Fully qualified path to an .ico file that will be used as the file's icon.\n(Default = g2exe's icon)")
            xms(longOpt: 'initHeapSize', args: 1, argName: 'size', "Optional. The JVM's initial heap size in MB.\n(Default = 128)")
            xmx(longOpt: 'maxHeapSize', args: 1, argName: 'size', "Optional. The JVM's maximum heap size in MB.\n(Default = 512)")
        }
    }

    CommandLineInput parse(String[] args){
        cli.parse(args)
    }

    void printUsage(){
        cli.usage()
    }
}
