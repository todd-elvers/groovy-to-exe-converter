package groovyToExeConverter.fileConversion.converterHelpers.groovyScript

import org.apache.commons.io.FilenameUtils
import testHelpers.TempDirectorySpockIntegrationTest

class GroovyScriptCompilerIntegrationTest extends TempDirectorySpockIntegrationTest {
    def groovyScriptCompiler = new GroovyScriptCompiler()

    def "given a script, compileGroovyScript() compiles it into class files in a given directory"() {
        when:
            File scriptFileWithClasses = getFileFromResourcesDir("TestScriptWithClasses.groovy")
            groovyScriptCompiler.compileGroovyScript(scriptFileWithClasses, TEMP_DIR)

        then:
            TEMP_DIR.listFiles().any { File f -> FilenameUtils.isExtension(f.name, "class") }
    }

}
