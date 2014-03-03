package testHelpers
import groovyToExeConverter.domain.AppConfigDefaults
import groovyToExeConverter.exception.ConfigurationException
import org.apache.commons.io.FileUtils
import spock.lang.Ignore
import spock.lang.Specification

@Ignore("Test helper class.")
class TempDirectorySpockTest extends Specification {
    static final File DEFAULT_TEMP_DIR = new File(System.getenv("TEMP"), AppConfigDefaults.G2EXE_TEMP_DIR_NAME.toString())

    def setupSpec() { ensureTemporaryDirectoryIsAccessible() }
    def cleanupSpec() { FileUtils.deleteQuietly(DEFAULT_TEMP_DIR) }

    private static void ensureTemporaryDirectoryIsAccessible(){
        if(!System.getenv("TEMP")) throw new ConfigurationException("Unable to resolve system's temporary directory.")
        DEFAULT_TEMP_DIR.mkdir()
    }

    File getFileFromResourcesDir(String fileName) {
        File file = new File(getClass().getClassLoader().getResource(fileName).file)
        if (!file) throw new FileNotFoundException("Unable to locate ${fileName} in the src/integration/resources directory.")
        return file
    }
}