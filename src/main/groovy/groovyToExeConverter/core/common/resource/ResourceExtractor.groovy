package groovyToExeConverter.core.common.resource

import groovy.io.FileType
import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import groovyToExeConverter.model.ResourceFileNames
import groovyToExeConverter.model.exception.ResourceExtractionException
import groovyToExeConverter.util.PropertiesReader
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils


@Log4j
@CompileStatic
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
        RESOURCE_FILE_NAMES.any { String filename ->
            !new File(TEMP_DIR, filename).exists()
        }
    }

    private boolean versionMismatch() {
        try {
            def prevG2exePropFile = new File(TEMP_DIR, "gradle.properties"),
                prevG2exeVersion = PropertiesReader.readPropertyFromFile("version", prevG2exePropFile),
                currentG2exeVersion = PropertiesReader.readAppProperty("version")

            return prevG2exeVersion != currentG2exeVersion
        } catch (ignored) {}
        return true
    }

    private void extractResourcesToTempDir() {
        log.debug("Temporary directory is either missing, incomplete, or from an earlier version.")
        log.debug("Creating temporary directory @ '${TEMP_DIR}'")

        RESOURCE_FILE_NAMES.each { String resourceFileName ->
            log.debug("Extracting resource '$resourceFileName' to temporary directory.")

            //TODO: Make more Groovy-like
            def inputStream, tempFile
            try {
                tempFile = new File(TEMP_DIR, resourceFileName)
                inputStream = resolveResourceInputStream(resourceFileName)
                FileUtils.copyInputStreamToFile(inputStream, tempFile)
            } catch (all) {
                throw new ResourceExtractionException("Unable to extract '${resourceFileName}' to '${tempFile}'.", all.cause)
            } finally {
                IOUtils.closeQuietly(inputStream)
            }
        }
    }

    /**
     * Try and resolve the give resource by reading it in from /main/resources/.
     * If that fails, try and just open the file locally.  Otherwise, return null.
     */
    private static InputStream resolveResourceInputStream(String resourceFileName) {
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
