package groovyToExeConverter.core.common.resource

import testHelpers.TempDirectorySpockIntegrationTest

import static org.apache.commons.io.FileUtils.deleteDirectory
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic

class ResourceHandlerIntegrationTest extends TempDirectorySpockIntegrationTest {

    ResourceHandler resourceHandler = new ResourceHandler(TEMP_DIR)

    def "when the given file exists in temp dir, findFileInTempDir() finds it"() {
        given:
            String resourceFileName = randomAlphabetic(10)
            File expectedResource = new File(TEMP_DIR, resourceFileName)
            expectedResource.createNewFile()
        when:
            File actualResource = resourceHandler.findFileInTempDir(resourceFileName)
        then:
            actualResource == expectedResource
    }

    def "when the given file doesn't exist in temp dir, findFileInTempDir() throws FileNotFoundException"() {
        when:
            resourceHandler.findFileInTempDir(randomAlphabetic(10))
        then:
            thrown(FileNotFoundException)
    }

    def "when launch4jc.exe exists in temp dir, resolveLaunch4jcExecutableHandle() finds it"() {
        given:
            File launch4jDir = new File(TEMP_DIR, "launch4j")
            launch4jDir.mkdir()
            File expectedLaunch4jcFile = new File(launch4jDir, "launch4jc.exe")
            expectedLaunch4jcFile.createNewFile()
        when:
            File actualLaunch4jcFile = resourceHandler.resolveLaunch4jcExeHandle()
        then:
            actualLaunch4jcFile == expectedLaunch4jcFile
    }

    def "when launch4jc.exe doesn't exist in temp dir, resolveLaunch4jcExecutableHandle() throws FileNotFoundException"() {
        when:
            deleteDirectory(new File(TEMP_DIR, "launch4j"))
            resourceHandler.resolveLaunch4jcExeHandle()
        then:
            thrown(FileNotFoundException)
    }

    def "createFileInTempDir() returns handle of new file in temp dir"() {
        expect:
            resourceHandler.createFileInTempDir("someFile") == new File(TEMP_DIR, "someFile")
    }

    def "when the file exists, copyFileToTempDir() copies it to temp dir"() {
        given:
            File log4jFileInResourceDir = getFileFromResourcesDir("log4j.properties")
            File log4jFileInTempDir = new File(TEMP_DIR, "log4j.properties")
            log4jFileInTempDir.delete()
        when:
            resourceHandler.copyFileToTempDir(log4jFileInResourceDir)
        then:
            log4jFileInTempDir.exists()
    }

    def "when the file doesn't exist, copyFileToTempDir() throws an IOException"() {
        given:
            File resourceFile = new File(randomAlphabetic(10))
            File resourceInTempDir = new File(TEMP_DIR, resourceFile.name)
        when:
            resourceHandler.copyFileToTempDir(resourceFile)
        then:
            !resourceInTempDir.exists()
            thrown(IOException)
    }
}
