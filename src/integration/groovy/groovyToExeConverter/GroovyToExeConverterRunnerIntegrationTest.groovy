package groovyToExeConverter

import testHelpers.TempDirectorySpockIntegrationTest

import static org.apache.commons.io.FilenameUtils.removeExtension


class GroovyToExeConverterRunnerIntegrationTest extends TempDirectorySpockIntegrationTest {

    void "can convert groovy script to an executable file"() {
        given:
            File groovyScriptFile = getFileFromResourcesDir("TestScriptWithClasses.groovy")
            File jarFile = new File(TEMP_DIR, removeExtension(groovyScriptFile.name) + '.jar')
            File exeFile = new File(TEMP_DIR, removeExtension(groovyScriptFile.name) + '.exe')

            String[] args = ['-f', groovyScriptFile, '--destDir', TEMP_DIR, '--gui', '--minJre', '1.4.2', '--stacktrace']

        when:
            GroovyToExeConverterRunner.main(args)

        then:
            groovyScriptFile.exists()
            jarFile.exists()
            exeFile.exists()
    }

    void "can convert jar file to an executable file"() {
        given:
            File jarFile = getFileFromResourcesDir("TestScriptWithoutClasses.jar")
            File exeFile = new File(TEMP_DIR, removeExtension(jarFile.name) + '.exe')
            String[] args = ['-f', jarFile, '--destDir', TEMP_DIR]

        when:
            GroovyToExeConverterRunner.main(args)

        then:
            jarFile.exists()
            exeFile.exists()
    }

}
