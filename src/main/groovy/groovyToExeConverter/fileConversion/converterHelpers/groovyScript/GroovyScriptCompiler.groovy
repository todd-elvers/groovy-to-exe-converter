package groovyToExeConverter.fileConversion.converterHelpers.groovyScript
import groovy.util.logging.Log4j
import groovyToExeConverter.exception.CompilationException
import org.codehaus.groovy.tools.FileSystemCompiler

@Log4j
class GroovyScriptCompiler {

    static void compileGroovyScript(File scriptFile, File tempDir) {
        log.debug("Compiling '${scriptFile}'...")
        def args = generateArgsToCompileScriptIntoClassFilesInTargetDir(scriptFile, tempDir)

        try {
            FileSystemCompiler.commandLineCompile(args)
        } catch (all) {
            throw new CompilationException("Compilation failed - unable to compile '${scriptFile.name}' into .class files.", all.cause)
        }

        log.debug("Compilation successful.")
    }

    private static String[] generateArgsToCompileScriptIntoClassFilesInTargetDir(File scriptFile, File targetDir) {
        [scriptFile, "-d", targetDir] as String[]
    }
}
