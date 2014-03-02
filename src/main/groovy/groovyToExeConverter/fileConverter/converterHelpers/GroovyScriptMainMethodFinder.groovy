package groovyToExeConverter.fileConverter.converterHelpers
import groovy.util.logging.Log4j
import groovyToExeConverter.exception.MainClassResolutionException
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.control.CompilationUnit
import org.codehaus.groovy.control.CompilerConfiguration

@Log4j
class GroovyScriptMainMethodFinder {

    static String findNameOfClassWithMainMethod(File script, File tempDir){
        List mainClassNames = []

        CompilationUnit compiledScript = readInGroovyScript(script, tempDir)
        List<ClassNode> classes = compiledScript.getAST().classes
        for (clazz in classes) {
            for (method in compiledScript.getClassNode(clazz.name).methods) {
                if(method.name.contains("main")){
                    mainClassNames << clazz.name
                }
            }
        }

        validateMainClassNames(mainClassNames)
        return mainClassNames[0]
    }

    //TODO: Possibly add more error information here?
    private static void validateMainClassNames(List<String> mainClassNames){
        if(!mainClassNames) {
            throw new MainClassResolutionException("Could not locate the Groovy script's main-class.")
        } else if (mainClassNames.size() > 1){
            throw new MainClassResolutionException("More than one main-class was detected in the Groovy script.  " +
                    "Please ensure only one class containing a main method exists in the script.")
        }
    }

    private static CompilationUnit readInGroovyScript(File script, File tempDir){
        def groovyScript = new CompilationUnit()
        groovyScript.addSource(script)
        groovyScript.setConfiguration(new CompilerConfiguration(targetDirectory: tempDir))
        groovyScript.compile()
        return groovyScript
    }
}
