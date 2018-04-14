package te.g2exe.core.groovyToJar.core

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import groovy.util.logging.Slf4j
import te.g2exe.model.exception.MainClassResolutionException
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.control.CompilationUnit
import org.codehaus.groovy.control.CompilerConfiguration

@Slf4j
@CompileStatic
class GroovyScriptMainMethodFinder {

    static String findNameOfClassWithMainMethod(File groovyScript) {
        CompilationUnit compiledGroovyScript = compileGroovyScript(groovyScript)
        List<String> mainClassNames = collectClassNamesWithMainMethods(compiledGroovyScript)
        validateMainMethodCollectionResults(mainClassNames)

        log.debug("Dynamically detected '${mainClassNames[0]}' as main-class.")

        return mainClassNames[0]
    }

    protected static CompilationUnit compileGroovyScript(File groovyScript) {
        String directoryToPlaceClassFiles = groovyScript.parentFile.absolutePath

        log.info("Compiling groovy script.")
        CompilationUnit compilationUnit = new CompilationUnit(new CompilerConfiguration(targetDirectory: directoryToPlaceClassFiles))
        compilationUnit.addSource(groovyScript)
        compilationUnit.compile()
        return compilationUnit
    }

    private static List<String> collectClassNamesWithMainMethods(CompilationUnit compiledGroovyScript) {
        List classesWithMainMethods = []
        List<ClassNode> classes = compiledGroovyScript.getAST().classes
        classes.each { ClassNode clazz ->
            clazz.methods.each { MethodNode method ->
                if (method.name == "main" && method.isPublic() && method.isStatic() && method.isVoidMethod()) {
                    classesWithMainMethods << clazz.name
                }
            }
        }

        return classesWithMainMethods
    }

    private static void validateMainMethodCollectionResults(List<String> mainClassNames) {
        if (!mainClassNames) {
            throw new MainClassResolutionException("No main methods were found when scanning through the classes of the given Groovy Script.")
        } else if (mainClassNames.size() > 1) {
            throw new MainClassResolutionException("More than one main method was detected in the Groovy script. " +
                    "Please ensure only one class containing a main method exists in the script.")
        }
    }
}
