package groovyToExeConverter.core.groovyToJar.core
import groovy.util.logging.Log4j
import groovyToExeConverter.model.exception.MainClassResolutionException
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.control.CompilationUnit
import org.codehaus.groovy.control.CompilerConfiguration

@Log4j
class GroovyScriptMainMethodFinder {

    static String findNameOfClassWithMainMethod(File groovyScript) {
        CompilationUnit compiledGroovyScript = compileGroovyScript(groovyScript)
        List<String> mainClassNames = collectClassNamesWithMainMethods(compiledGroovyScript)
        validateMainMethodCollectionResults(mainClassNames)

        log.debug("Dynamically detected '${mainClassNames[0]}' as main-class.")

        return mainClassNames[0]
    }

    protected static CompilationUnit compileGroovyScript(File groovyScript) {
        File directoryToPlaceClassFiles = groovyScript.parentFile

        new CompilationUnit().with {
            addSource(groovyScript)
            setConfiguration(new CompilerConfiguration(targetDirectory: directoryToPlaceClassFiles))
            compile()

            return it
        }
    }

    private static List<String> collectClassNamesWithMainMethods(CompilationUnit compiledGroovyScript) {
        List classesWithMainMethods = []
        List<ClassNode> classes = compiledGroovyScript.getAST().classes
        classes.each { clazz ->
            compiledGroovyScript.getClassNode(clazz.name).methods.each { method ->
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
