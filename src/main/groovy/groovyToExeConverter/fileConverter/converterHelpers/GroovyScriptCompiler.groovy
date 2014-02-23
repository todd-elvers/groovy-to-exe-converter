package groovyToExeConverter.fileConverter.converterHelpers
import groovy.util.logging.Log4j
import groovyToExeConverter.exception.CompilationException
import org.codehaus.groovy.tools.FileSystemCompiler

@Log4j
class GroovyScriptCompiler {

    static void compileGroovyScript(File scriptFile, File tempDir) {
        log.debug("Compiling '${scriptFile}'...")
        try {
            def args = generateArgsToCompileScriptAndPutClassFilesInDir(scriptFile, tempDir)
            FileSystemCompiler.commandLineCompile(args)

            log.debug("Compilation successful.")
        } catch (all) {
            throw new CompilationException("Compilation failed - unable to compile '${scriptFile.name}' into .class files.", all.cause)
        }
    }

    private static String[] generateArgsToCompileScriptAndPutClassFilesInDir(File scriptFile, File tempDir) {
        [scriptFile, "-d", tempDir] as String[]
    }
}
