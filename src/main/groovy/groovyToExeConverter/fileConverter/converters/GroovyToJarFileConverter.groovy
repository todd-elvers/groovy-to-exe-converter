package groovyToExeConverter.fileConverter.converters
import groovy.util.logging.Log4j
import groovyToExeConverter.exception.CompilationException
import groovyToExeConverter.exception.ConfigurationException
import groovyToExeConverter.fileConverter.FileConverter
import org.codehaus.groovy.tools.FileSystemCompiler

@Log4j
class GroovyToJarFileConverter extends FileConverter {

    private AntBuilder ant = new AntBuilder()

    @Override
    File convert() {
        log.info("Converting ${appConfig.fileToConvert.name} --> ${appConfig.jarFileName}")
        if (!isGroovyHomeSetProperly()) {
            throw new ConfigurationException("Missing environment variable: GROOVY_HOME")
        }

        def groovyScript = resourceHandler.copyFileToTempDir(appConfig.fileToConvert)
        compileGroovyScript(groovyScript)
        buildJarThenReturnHandle()
    }

    private File buildJarThenReturnHandle() {
        final GROOVY_HOME = new File(System.getenv('GROOVY_HOME'))
        def scriptNameNoFileExt = appConfig.fileToConvert.name.substring(0, appConfig.fileToConvert.name.lastIndexOf('.'))
        def jarFile = resourceHandler.createFileInTempDir(appConfig.jarFileName)

        tellAntNotToOutputAnything()

        try {
            log.debug("Building '${jarFile.name}'...")
            ant.jar(destfile: jarFile, compress: true, index: true) {
                fileset(dir: jarFile.parent, includes: scriptNameNoFileExt + '*.class')

                //TODO: Expose this as possible command line appConfig
                zipgroupfileset(dir: GROOVY_HOME, includes: 'embeddable/groovy-all-*.jar')
                zipgroupfileset(dir: GROOVY_HOME, includes: 'lib/commons*.jar')
                // add more jars here

                manifest {
                    attribute(name: 'Main-Class', value: scriptNameNoFileExt)
                }
            }
            log.debug("Build successful.")
        } catch (all) {
            throw new CompilationException("Build failed - unable to compile '${appConfig.fileToConvert.name}' into .jar file.", all.cause)
        }

        jarFile
    }

    private tellAntNotToOutputAnything(){
        ant.getProject().getBuildListeners()[0].setMessageOutputLevel(0)
    }

    private static boolean isGroovyHomeSetProperly() {
        File GROOVY_HOME = System.getenv('GROOVY_HOME') ? new File(System.getenv('GROOVY_HOME')) : null
        GROOVY_HOME && GROOVY_HOME.canRead() && GROOVY_HOME.listFiles()*.name.contains("bin")
    }

    private void compileGroovyScript(File scriptFile) {
        log.debug("Compiling '${scriptFile}'...")
        try {
            def args = createArgsToCompileGroovyScriptToTempDir(scriptFile)
            FileSystemCompiler.commandLineCompile(args);
            log.debug("Compilation successful.")
        } catch (all) {
            throw new CompilationException("Compilation failed - unable to compile '${scriptFile.name}' into .class files.", all.cause)
        }
    }

    private String[] createArgsToCompileGroovyScriptToTempDir(File scriptFile) {
        [scriptFile, "-d", resourceHandler.TEMP_DIR] as String[]
    }
}
