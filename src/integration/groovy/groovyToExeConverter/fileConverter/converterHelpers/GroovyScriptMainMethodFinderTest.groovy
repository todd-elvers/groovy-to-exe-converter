package groovyToExeConverter.fileConverter.converterHelpers

import testHelpers.TempDirectorySpockTest

class GroovyScriptMainMethodFinderTest extends TempDirectorySpockTest {
    def groovyScriptClassFinder = new GroovyScriptMainMethodFinder()

    File scriptFileWithClasses,
        scriptFileWithoutClasses


    def setup() {
        scriptFileWithClasses    = getFileFromResourcesDir("TestScriptWithClasses.groovy")
        scriptFileWithoutClasses = getFileFromResourcesDir("TestScriptWithoutClasses.groovy")
    }


    def "given a script with multiple classes, return name of class containing main method" () {
        expect:
            groovyScriptClassFinder.findNameOfClassWithMainMethod(scriptFileWithClasses, DEFAULT_TEMP_DIR) == "B"
    }

    def "given a script with no classes, return name of script"() {
        expect:
            groovyScriptClassFinder.findNameOfClassWithMainMethod(scriptFileWithoutClasses, DEFAULT_TEMP_DIR) == "TestScriptWithoutClasses"
    }
}
