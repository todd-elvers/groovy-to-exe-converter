package groovyToExeConverter

import groovyToExeConverter.domain.AppConfigDefaults
import groovyToExeConverter.exception.ConfigurationException
import org.apache.commons.io.FileUtils
import spock.lang.Specification

import static org.apache.commons.io.FilenameUtils.removeExtension

/*
 *  Use gradle task 'integrationTest' to execute these tests.
 *
 *  Reason:
 *  To reduce resource duplication, these integration tests rely on some resources in /src/main/resources/.
 *  The above gradle task first deploys all application resources before executing the integration tests.
 */
class GroovyToExeConverterRunnerTest extends Specification {

    static final File DEFAULT_TEMP_DIR = new File(System.getenv("TEMP"), AppConfigDefaults.G2EXE_TEMP_DIR_NAME.toString())

    // Check to make sure we can resolve the system's temporary directory before running any tests
    def setupSpec() {
        if(!System.getenv("TEMP")) throw new ConfigurationException("Unable to resolve system's temporary directory.")
    }

    def cleanupSpec() { FileUtils.deleteQuietly(DEFAULT_TEMP_DIR) }

    void "can convert groovy script to an executable file"() {
        given:
            File groovyScriptFile = getFileFromResourcesDir("SimpleTestScript.groovy")
            println("GroovyScript: ${groovyScriptFile}")
            File jarFile = new File(DEFAULT_TEMP_DIR, removeExtension(groovyScriptFile.name) + '.jar')
            File exeFile = new File(DEFAULT_TEMP_DIR, removeExtension(groovyScriptFile.name) + '.exe')
            String[] args = ['-f', groovyScriptFile]

        when:
            GroovyToExeConverterRunner.main(args)

        then:
            groovyScriptFile.exists()
            jarFile.exists()
            exeFile.exists()
    }

    void "can convert jar file to an executable file"() {
        given:
            File jarFile = getFileFromResourcesDir("SimpleTestScript.jar")
            File exeFile = new File(DEFAULT_TEMP_DIR, removeExtension(jarFile.name) + '.exe')
            String[] args = ['-f', jarFile]

        when:
            GroovyToExeConverterRunner.main(args)

        then:
            jarFile.exists()
            exeFile.exists()
    }

    private File getFileFromResourcesDir(String fileName){
        File file = new File(getClass().getClassLoader().getResource(fileName).file)
        if(!file) throw new FileNotFoundException("Unable to locate ${fileName} in the src/integration/resources directory.")
        return file
    }
}
