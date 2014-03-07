package groovyToExeConverter.fileConversion.converterHelpers.groovyScript
import groovy.util.logging.Log4j
import groovyToExeConverter.exception.MainClassResolutionException
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.control.CompilationUnit
import org.codehaus.groovy.control.CompilerConfiguration

@Log4j
class GroovyScriptMainMethodFinder {

    static String findNameOfClassWithMainMethod(File scriptFile, File tempDir){
        CompilationUnit loadedScript = dynamicallyLoadScript(scriptFile, tempDir)
        List mainClassNames = scanForMainMethods(loadedScript)
        validateMainClassNames(mainClassNames)
        return mainClassNames[0]
    }

    private static CompilationUnit dynamicallyLoadScript(File scriptFile, File tempDir){
        def groovyScript = new CompilationUnit()
        groovyScript.addSource(scriptFile)
        groovyScript.setConfiguration(new CompilerConfiguration(targetDirectory: tempDir))
        groovyScript.compile()
        return groovyScript
    }

    private static List scanForMainMethods(CompilationUnit loadedScript){
        List mainClassesFound = []
        List<ClassNode> classes = loadedScript.getAST().classes
        for (clazz in classes) {
            for (method in loadedScript.getClassNode(clazz.name).methods) {
                if(method.name == "main"){
                    mainClassesFound << clazz.name
                }
            }
        }
        return mainClassesFound
    }

    private static void validateMainClassNames(List mainClassNames){
        if(!mainClassNames) {
            throw new MainClassResolutionException("Could dynamically resolve the Groovy script's main-class.")
        } else if (mainClassNames.size() > 1){
            throw new MainClassResolutionException("More than one main-class was detected in the Groovy script.  " +
                    "Please ensure only one class containing a main method exists in the script.")
        }
    }
}
