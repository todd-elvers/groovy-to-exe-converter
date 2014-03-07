package groovyToExeConverter.fileConversion.converterHelpers.groovyScript

import testHelpers.TempDirectorySpockIntegrationTest

class GroovyScriptMainMethodFinderIntegrationTest extends TempDirectorySpockIntegrationTest {
    def groovyScriptClassFinder = new GroovyScriptMainMethodFinder()

    File scriptFileWithClasses,
         scriptFileWithoutClasses


    def setup() {
        scriptFileWithClasses = getFileFromResourcesDir("TestScriptWithClasses.groovy")
        scriptFileWithoutClasses = getFileFromResourcesDir("TestScriptWithoutClasses.groovy")
    }


    def "given a script with multiple classes, return name of class containing main method"() {
        expect:
            groovyScriptClassFinder.findNameOfClassWithMainMethod(scriptFileWithClasses, TempDirectorySpockIntegrationTest.TEMP_DIR) == "B"
    }

    def "given a script with no classes, return name of script"() {
        expect:
            groovyScriptClassFinder.findNameOfClassWithMainMethod(scriptFileWithoutClasses, TempDirectorySpockIntegrationTest.TEMP_DIR) == "TestScriptWithoutClasses"
    }
}
