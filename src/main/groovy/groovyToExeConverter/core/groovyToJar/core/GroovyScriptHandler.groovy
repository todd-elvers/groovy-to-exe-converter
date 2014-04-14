package groovyToExeConverter.core.groovyToJar.core
import groovy.util.logging.Log4j
import groovyToExeConverter.model.exception.MainClassResolutionException
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.control.CompilationUnit
import org.codehaus.groovy.control.CompilerConfiguration

@Log4j
class GroovyScriptHandler {

    static CompilationUnit loadScriptIntoMemoryAndCompile(File groovyScriptFile) {
        def loadedGroovyScript = new CompilationUnit()

        loadedGroovyScript.addSource(groovyScriptFile)
        loadedGroovyScript.setConfiguration(new CompilerConfiguration(targetDirectory: groovyScriptFile.parentFile))
        loadedGroovyScript.compile()

        return loadedGroovyScript
    }

    static String findNameOfClassWithMainMethod(CompilationUnit compiledGroovyScript) {
        List mainClassNames = collectClassNamesWithMainMethods(compiledGroovyScript)
        validateMainMethodCollectionResults(mainClassNames)

        log.debug("Dynamically detected '${mainClassNames[0]}' as main-class.")

        return mainClassNames[0]
    }

    private static List collectClassNamesWithMainMethods(CompilationUnit compiledGroovyScript) {
        List classesWithMainMethods = []
        List<ClassNode> classes = compiledGroovyScript.getAST().classes
        for (clazz in classes) {
            for (method in compiledGroovyScript.getClassNode(clazz.name).methods) {
                if (method.name == "main") {
                    classesWithMainMethods << clazz.name
                }
            }
        }

        return classesWithMainMethods
    }

    private static void validateMainMethodCollectionResults(List mainClassNames) {
        if (!mainClassNames) {
            throw new MainClassResolutionException("No main methods were found when scanning through the classes of the given Groovy Script.")
        } else if (mainClassNames.size() > 1) {
            throw new MainClassResolutionException("More than one main method was detected in the Groovy script. " +
                    "Please ensure only one class containing a main method exists in the script.")
        }
    }
}
