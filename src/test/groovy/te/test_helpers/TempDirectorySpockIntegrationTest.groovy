package te.test_helpers

import org.junit.BeforeClass
import te.g2exe.model.AppConfigDefaults
import org.apache.commons.io.FileUtils
import spock.lang.Ignore
import spock.lang.Specification

@Ignore("Test helper class.")
abstract class TempDirectorySpockIntegrationTest extends Specification {
    protected static final String TEMP_DIR_ENV_VAR = "TEMP"
    protected static final File TEMP_DIR = new File(
            System.getenv(TEMP_DIR_ENV_VAR) as String,
            AppConfigDefaults.G2EXE_TEMP_DIR_NAME as String
    )

    @BeforeClass
    static void setupTempDirectory() {
        FileUtils.deleteQuietly(TEMP_DIR)

        if(!System.getenv(TEMP_DIR_ENV_VAR)) {
            throw new IOException("Unable to resolve system's temporary directory - please set the $TEMP_DIR_ENV_VAR variable.")
        }

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