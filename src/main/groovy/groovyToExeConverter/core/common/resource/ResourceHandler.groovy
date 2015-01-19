package groovyToExeConverter.core.common.resource

import groovy.util.logging.Log4j
import org.apache.commons.io.FileUtils

import static groovyToExeConverter.model.ResourceFileNames.LAUNCH4JC_RESOURCES

@Log4j
class ResourceHandler {
    final ResourceExtractor resourceExtractor

    protected ResourceHandler(File temporaryDirectory) {
        resourceExtractor = new ResourceExtractor(temporaryDirectory)
        resourceExtractor.extractResourcesIfNecessary()
    }

    File findFileInTempDir(String fileName) {
        File file = new File(resourceExtractor.TEMP_DIR, fileName)
        if (!file.exists()) {
            throw new FileNotFoundException("'$fileName' not found in temporary directory.")
        }
        return file
    }

    File resolveLaunch4jcExeHandle() {
        String launch4jcExeResourceFileName = LAUNCH4JC_RESOURCES.fileNames.find { it == 'launch4j/launch4jc.exe' }
        return findFileInTempDir(launch4jcExeResourceFileName)
    }

    File createFileInTempDir(String fileName) {
        new File(resourceExtractor.TEMP_DIR, fileName)
    }

    File copyFileToTempDir(File file) {
        File fileInTempDir = createFileInTempDir(file.name)
        FileUtils.copyFile(file, fileInTempDir)
        return fileInTempDir
    }
}

