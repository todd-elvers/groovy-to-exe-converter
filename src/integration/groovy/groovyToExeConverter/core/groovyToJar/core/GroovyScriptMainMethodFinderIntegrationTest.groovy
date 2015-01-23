package groovyToExeConverter.core.groovyToJar.core

import org.apache.commons.io.FileUtils
import org.codehaus.groovy.control.CompilationUnit
import testHelpers.TempDirectorySpockIntegrationTest

class GroovyScriptMainMethodFinderIntegrationTest extends TempDirectorySpockIntegrationTest {
    File scriptFileWithClassesInTempDir,
         scriptFileWithoutClassesInTempDir

    def setup() {
        def scriptFileWithClasses = getFileFromResourcesDir("TestScriptWithClasses.groovy")
        scriptFileWithClassesInTempDir = new File(TEMP_DIR, scriptFileWithClasses.name)
        FileUtils.copyFile(scriptFileWithClasses, scriptFileWithClassesInTempDir)

        def scriptFileWithoutClasses = getFileFromResourcesDir("TestScriptWithoutClasses.groovy")
        scriptFileWithoutClassesInTempDir = new File(TEMP_DIR, scriptFileWithoutClasses.name)
        FileUtils.copyFile(scriptFileWithoutClasses, scriptFileWithoutClassesInTempDir)
    }


    def "given a script file with classes: compileScriptToClassFilesInSameDirAndLoadIntoMemory() compiles it to .class files in the same directory and returns CompilationUnit"() {
        given:
            List<String> expectedClassFileNames = ["A\$_closure1.class", "A.class", "B\$_closure1.class", "B.class", "C.class"]
            expectedClassFileNames.each { new File(TEMP_DIR, it).delete() }

        when:
            CompilationUnit compiledScript = GroovyScriptMainMethodFinder.compileGroovyScript(scriptFileWithClassesInTempDir)

        then:
            compiledScript != null
            expectedClassFileNames.every { new File(TEMP_DIR, it).exists() }
    }

    def "given a script file without classes: loadScriptIntoMemoryAndCompile() compiles it to .class files in the same directory and returns CompilationUnit"() {
        given:
            List<String> expectedClassFileNames = ["TestScriptWithoutClasses.class"]
            expectedClassFileNames.each { new File(TEMP_DIR, it).delete() }

        when:
            CompilationUnit compiledScript = GroovyScriptMainMethodFinder.compileGroovyScript(scriptFileWithoutClassesInTempDir)

        then:
            compiledScript != null
            expectedClassFileNames.every { new File(TEMP_DIR, it).exists() }
    }


    def "given a compiled script with multiple classes: findNameOfClassWithMainMethod() returns name of class containing main method"() {
        expect:
            GroovyScriptMainMethodFinder.findNameOfClassWithMainMethod(scriptFileWithClassesInTempDir) == "B"
    }


    def "given a compiled script with no classes: findNameOfClassWithMainMethod() return name of script"() {
        expect:
            GroovyScriptMainMethodFinder.findNameOfClassWithMainMethod(scriptFileWithoutClassesInTempDir) == "TestScriptWithoutClasses"
    }
}
