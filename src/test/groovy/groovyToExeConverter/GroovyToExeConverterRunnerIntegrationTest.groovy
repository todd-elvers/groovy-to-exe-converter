package groovyToExeConverter

import test_helpers.TempDirectorySpockIntegrationTest

import static org.apache.commons.io.FilenameUtils.removeExtension


class GroovyToExeConverterRunnerIntegrationTest extends TempDirectorySpockIntegrationTest {

    void "can convert groovy scripts to executable files that function properly"() {
        given:
            File groovyScriptFile = getFileFromResourcesDir(scriptFilename)
            File jarFile = new File(TEMP_DIR, removeExtension(groovyScriptFile.name) + '.jar')
            File exeFile = new File(TEMP_DIR, removeExtension(groovyScriptFile.name) + '.exe')

        and:
            String[] args = ['-f', groovyScriptFile, '--destDir', TEMP_DIR, '--stacktrace']

        when:
            new GroovyToExeConverterRunner().run(args)

        then:
            groovyScriptFile.exists()
            jarFile.exists()
            exeFile.exists()

        then:
            Process exeProcess = "$exeFile".execute()
            exeProcess.waitForOrKill(2000)
            assert exeProcess.exitValue() == 0 : "The exe generated ('$exeFile.name') did not return 0 after 2 seconds."

        where:
            scriptFilename << [
                    "ScriptWithClassesAndMainMethodSyntax.groovy",
                    "ScriptWithClassesAndScriptSyntax.groovy",
                    "ScriptWithNoClasses.groovy",
            ]
    }

    void "can convert jar file to an executable file"() {
        given:
            File jarFile = getFileFromResourcesDir("ScriptWithNoClasses.jar")
            File exeFile = new File(TEMP_DIR, removeExtension(jarFile.name) + '.exe')
            String[] args = ['-f', jarFile, '--destDir', TEMP_DIR, '--stacktrace']

        when:
            GroovyToExeConverterRunner.main(args)

        then:
            jarFile.exists()
            exeFile.exists()

        then:
            Process exeProcess = "$exeFile".execute()
            exeProcess.waitForOrKill(2000)
            assert exeProcess.exitValue() == 0 : "The exe generated ('$exeFile.name') did not return 0 after 2 seconds."
    }

}
