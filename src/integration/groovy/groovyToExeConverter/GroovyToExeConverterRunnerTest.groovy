package groovyToExeConverter

import testHelpers.TempDirectorySpockTest

import static org.apache.commons.io.FilenameUtils.removeExtension


/*
 *  Use gradle task 'integrationTest' to execute these tests.
 *
 *  Reason:
 *  To reduce resource duplication, these integration tests rely on some resources in /src/main/resources/.
 *  The above gradle task first deploys all necessary resources before executing the integration tests.
 */

class GroovyToExeConverterRunnerTest extends TempDirectorySpockTest {

    void "can convert groovy script to an executable file"() {
        given:
            File groovyScriptFile = getFileFromResourcesDir("TestScriptWithoutClasses.groovy")
            File jarFile = new File(DEFAULT_TEMP_DIR, removeExtension(groovyScriptFile.name) + '.jar')
            File exeFile = new File(DEFAULT_TEMP_DIR, removeExtension(groovyScriptFile.name) + '.exe')

            File splashFile = new File("C:/users/todd/desktop/splash.bmp")

            String[] args = ['-f', groovyScriptFile, '--destDir', DEFAULT_TEMP_DIR, '--gui', "-s", splashFile]

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
            File exeFile = new File(DEFAULT_TEMP_DIR, removeExtension(jarFile.name) + '.exe')
            String[] args = ['-f', jarFile, '--destDir', DEFAULT_TEMP_DIR]

        when:
            GroovyToExeConverterRunner.main(args)

        then:
            jarFile.exists()
            exeFile.exists()
    }

}
