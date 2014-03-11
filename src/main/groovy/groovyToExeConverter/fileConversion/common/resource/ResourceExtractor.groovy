package groovyToExeConverter.fileConversion.common.resource

import groovy.io.FileType
import groovy.util.logging.Log4j
import groovyToExeConverter.domain.ResourceFileNames
import groovyToExeConverter.exception.ResourceExtractionException
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils

import static groovyToExeConverter.util.PropertiesReader.readAppProperty
import static groovyToExeConverter.util.PropertiesReader.readPropertyFromFile
import static org.apache.commons.io.FileUtils.copyInputStreamToFile

@Log4j
class ResourceExtractor {

    final List<String> RESOURCE_FILE_NAMES = ResourceFileNames.asFlattenedList()
    final File TEMP_DIR

    protected ResourceExtractor(File file) {
        TEMP_DIR = file
    }

    void extractResourcesIfNecessary() {
        if (!TEMP_DIR.exists() || anyResourcesAreMissingFromTempDir() || versionMismatch()) {
            extractResourcesToTempDir()
        } else {
            deleteAllInTempDirExceptResources()
        }
    }

    private boolean anyResourcesAreMissingFromTempDir() {
        RESOURCE_FILE_NAMES.any { !new File(TEMP_DIR, it).exists() }
    }

    private boolean versionMismatch() {
        try {
            def prevG2exePropFile = new File(TEMP_DIR, "gradle.properties")
            def prevG2exeVersion = readPropertyFromFile("version", prevG2exePropFile)
            def currentG2exeVersion = readAppProperty("version")

            return prevG2exeVersion != currentG2exeVersion
        } catch (ignored) {}
        return true
    }

    private void extractResourcesToTempDir() {
        log.debug("Temporary directory NOT found.")
        log.debug("Creating temporary directory @ '${TEMP_DIR}'")

        RESOURCE_FILE_NAMES.each { String resourceFileName ->
            log.debug("Extracting resource '$resourceFileName' to temporary directory.")

            def inputStream, tempFile
            try {
                tempFile = new File(TEMP_DIR, resourceFileName)
                inputStream = resolveResourceInputStream(resourceFileName)
                copyInputStreamToFile(inputStream, tempFile)
            } catch (all) {
                throw new ResourceExtractionException("Unable to extract '${resourceFileName}' to '${tempFile}'.", all.cause)
            } finally {
                IOUtils.closeQuietly(inputStream)
            }
        }
    }

    private static InputStream resolveResourceInputStream(String resourceFileName) {
        // First try and read the resource from /main/resources/, if that fails try opening the file locally
        def resourceInputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(resourceFileName)
        resourceInputStream ?: FileUtils.openInputStream(resourceFileName as File)
    }

    private void deleteAllInTempDirExceptResources() {
        log.debug("Temporary directory found.")
        log.debug("Removing all files from temporary directory that aren't g2exe resources.")

        TEMP_DIR.eachFile(FileType.FILES) { File file ->
            RESOURCE_FILE_NAMES.contains(file.name) ?: file.delete()
        }
    }

}
