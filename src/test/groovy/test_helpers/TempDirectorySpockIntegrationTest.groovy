package test_helpers
import te.g2exe.model.AppConfigDefaults
import org.apache.commons.io.FileUtils
import spock.lang.Ignore
import spock.lang.Specification

@Ignore("Test helper class.")
class TempDirectorySpockIntegrationTest extends Specification {
    public static final File TEMP_DIR = new File(System.getenv("TEMP"), AppConfigDefaults.G2EXE_TEMP_DIR_NAME as String)

    def setupSpec() {
        FileUtils.deleteQuietly(TEMP_DIR)
        ensureTemporaryDirectoryIsAccessible()
    }

    private static void ensureTemporaryDirectoryIsAccessible(){
        if(!System.getenv("TEMP")) throw new IOException("Unable to resolve system's temporary directory.")
        TEMP_DIR.mkdir()
    }

    File getFileFromResourcesDir(String fileName) {
        File file
        try {
            file = new File(Thread.currentThread().contextClassLoader?.getResource(fileName)?.file)
        } catch(ignored) {
            throw new FileNotFoundException("Unable to locate ${fileName} in the src/integration/resources directory.")
        }
        return file
    }
}