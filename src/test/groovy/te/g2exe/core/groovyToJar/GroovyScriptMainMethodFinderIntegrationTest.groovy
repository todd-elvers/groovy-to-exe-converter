package te.g2exe.core.groovyToJar

import te.g2exe.core.groovyToJar.core.GroovyScriptMainMethodFinder
import org.apache.commons.io.FileUtils
import org.codehaus.groovy.control.CompilationUnit
import test_helpers.TempDirectorySpockIntegrationTest

class GroovyScriptMainMethodFinderIntegrationTest extends TempDirectorySpockIntegrationTest {
    File scriptWithClassesAndMainMethodSyntaxInTempDir,
         scriptWithClassesAndScriptSyntaxInTempDir,
         scriptWithNoClassesInTempDir

    def setup() {
        File scriptFileWithClassesAndMainMethodSyntax = getFileFromResourcesDir("ScriptWithClassesAndMainMethodSyntax.groovy")
        scriptWithClassesAndMainMethodSyntaxInTempDir = new File(TEMP_DIR, scriptFileWithClassesAndMainMethodSyntax.name)
        FileUtils.copyFile(scriptFileWithClassesAndMainMethodSyntax, scriptWithClassesAndMainMethodSyntaxInTempDir)

        File scriptFileWithClassesAndScriptSyntax = getFileFromResourcesDir("ScriptWithClassesAndScriptSyntax.groovy")
        scriptWithClassesAndScriptSyntaxInTempDir = new File(TEMP_DIR, scriptFileWithClassesAndScriptSyntax.name)
        FileUtils.copyFile(scriptFileWithClassesAndScriptSyntax, scriptWithClassesAndScriptSyntaxInTempDir)

        File scriptFileNoClasses = getFileFromResourcesDir("ScriptWithNoClasses.groovy")
        scriptWithNoClassesInTempDir = new File(TEMP_DIR, scriptFileNoClasses.name)
        FileUtils.copyFile(scriptFileNoClasses, scriptWithNoClassesInTempDir)
    }


    def "given a script with classes and main method syntax: compileGroovyScript() compiles it to .class files in the same directory and returns CompilationUnit"() {
        given:
            List<String> expectedClassFileNames = ["ClassOne.class", "ClassTwo.class", "ClassThree.class"]
            expectedClassFileNames.each { new File(TEMP_DIR, it).delete() }

        when:
            CompilationUnit compiledScript = GroovyScriptMainMethodFinder.compileGroovyScript(scriptWithClassesAndMainMethodSyntaxInTempDir)

        then:
            compiledScript != null
            expectedClassFileNames.every { new File(TEMP_DIR, it).exists() }
    }

    def "given a script with classes and a script syntax: compileGroovyScript() compiles it to .class files in the same directory and returns CompilationUnit"() {
        given:
            List<String> expectedClassFileNames = ["SomeClass.class"]
            expectedClassFileNames.each { new File(TEMP_DIR, it).delete() }

        when:
            CompilationUnit compiledScript = GroovyScriptMainMethodFinder.compileGroovyScript(scriptWithClassesAndScriptSyntaxInTempDir)

        then:
            compiledScript != null
            expectedClassFileNames.every { new File(TEMP_DIR, it).exists() }
    }

    def "given a script file without classes: compileGroovyScript() compiles it to .class files in the same directory and returns CompilationUnit"() {
        given:
            List<String> expectedClassFileNames = ["ScriptWithNoClasses.class"]
            expectedClassFileNames.each { new File(TEMP_DIR, it).delete() }

        when:
            CompilationUnit compiledScript = GroovyScriptMainMethodFinder.compileGroovyScript(scriptWithNoClassesInTempDir)

        then:
            compiledScript != null
            expectedClassFileNames.every { new File(TEMP_DIR, it).exists() }
    }


    def "given a compiled script with classes, one of which has a main method: findNameOfClassWithMainMethod() returns name of class containing the main method"() {
        expect:
            GroovyScriptMainMethodFinder.findNameOfClassWithMainMethod(scriptWithClassesAndMainMethodSyntaxInTempDir) == "ClassOne"
    }

    def "given a compiled script with classes, and a script-style instantiation: findNameOfClassWithMainMethod() returns name of script"() {
        expect:
            GroovyScriptMainMethodFinder.findNameOfClassWithMainMethod(scriptWithClassesAndScriptSyntaxInTempDir) == "ScriptWithClassesAndScriptSyntax"
    }

    def "given a compiled script with no classes: findNameOfClassWithMainMethod() return name of script"() {
        expect:
            GroovyScriptMainMethodFinder.findNameOfClassWithMainMethod(scriptWithNoClassesInTempDir) == "ScriptWithNoClasses"
    }
}
