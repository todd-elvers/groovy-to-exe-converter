package te.g2exe

import te.test_helpers.TempDirectorySpockIntegrationTest

import static org.apache.commons.io.FilenameUtils.removeExtension


class EndToEndIntegrationTest extends TempDirectorySpockIntegrationTest {

    void "can convert groovy scripts to executable files that function properly"() {
        given:
            File groovyScriptFile = getFileFromResourcesDir(scriptFilename)
            File jarFile = new File(TEMP_DIR, removeExtension(groovyScriptFile.name) + '.jar')
            File exeFile = new File(TEMP_DIR, removeExtension(groovyScriptFile.name) + '.exe')

        and:
            String[] args = ['-f', groovyScriptFile, '--destDir', TEMP_DIR, '--stacktrace']

        when:
            Application.main(args)

        then:
            groovyScriptFile.exists()
            jarFile.exists()
            exeFile.exists()

        then: 'we execute the EXE file and time-box it to 2 seconds'
            Process exeProcess = "$exeFile".execute()
            exeProcess.waitForOrKill(2000)

        and: 'we get the expected text in standard-out w/ graceful termination'
            exeProcess.getText().contains("Hello World")
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
            Application.main(args)

        then:
            jarFile.exists()
            exeFile.exists()

        then: 'we execute the EXE file and time-box it to 2 seconds'
            Process exeProcess = "$exeFile".execute()
            exeProcess.waitForOrKill(2000)

        and: 'we get the expected text in standard-out w/ graceful termination'
            exeProcess.getText().contains("Hello World")
            assert exeProcess.exitValue() == 0 : "The exe generated ('$exeFile.name') did not return 0 after 2 seconds."
    }

}
