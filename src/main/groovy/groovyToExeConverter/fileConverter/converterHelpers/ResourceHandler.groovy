package groovyToExeConverter.fileConverter.converterHelpers

import groovy.io.FileType
import groovy.util.logging.Log4j
import groovyToExeConverter.domain.Resources
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils

import static groovyToExeConverter.domain.Resources.LAUNCH4JC_RESOURCES
import static groovyToExeConverter.util.PropertiesReader.readAppProperty
import static groovyToExeConverter.util.PropertiesReader.readPropertyFromFile

@Log4j
class ResourceHandler {
    final List<String> RESOURCES = Resources.asFlattenedList()
    final File TEMP_DIR

    ResourceHandler(File temporaryDirectory) {
        TEMP_DIR = temporaryDirectory
        needToExtractResources() ? extractToTempDir(RESOURCES) : deleteAllInTempDirExcept(RESOURCES)
    }

    private boolean needToExtractResources() {
        !TEMP_DIR.exists() || RESOURCES.any(missingFromTempDir) || versionMismatch()
    }

    private def missingFromTempDir = { String resource ->
        !new File(TEMP_DIR, resource).exists()
    }

    private boolean versionMismatch() {
        try {
            def tempDirPropFile = new File(TEMP_DIR, "gradle.properties")
            return readPropertyFromFile("version", tempDirPropFile) != readAppProperty("version")
        } catch (ignored) { }
        return true
    }

    private void extractToTempDir(List resourcesToExtract) {
        log.debug("Folder 'g2exe' not found in temporary directory - creating temp dir and copying resources there.")

        resourcesToExtract.each { String resourceFileName ->
            log.debug("Copying resource '$resourceFileName' to temporary directory.")

            def inputStream, tempFile
            try {
                tempFile = new File(TEMP_DIR, resourceFileName)
                inputStream = resolveResourceInputStream(resourceFileName)
                FileUtils.copyInputStreamToFile(inputStream, tempFile)
                inputStream.close()
            } catch (Exception ex) {
                log.error("Unable to copy g2exe resource '${resourceFileName}' to ${tempFile}")
                throw ex
            } finally {
                IOUtils.closeQuietly(inputStream)
            }
        }
    }

    private void deleteAllInTempDirExcept(List<String> fileNamesToIgnore) {
        log.debug("Folder 'g2exe' found in temporary directory - cleaning temp directory.")

        TEMP_DIR.eachFile(FileType.FILES) { File file ->
            fileNamesToIgnore.contains(file.name) ?: file.delete()
        }
    }

    private static InputStream resolveResourceInputStream(String resourceFileName){
        // First try and read the resource from /main/resources/, if that fails try opening the file locally
        def resourceInputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(resourceFileName)
        resourceInputStream ?: FileUtils.openInputStream(resourceFileName as File)
    }

    File findFileInTempDir(String fileName) {
        File file = new File(TEMP_DIR, fileName)
        if (!file.exists()) {
            throw new FileNotFoundException("'$fileName' not found in temporary directory.")
        }
        return file
    }

    File resolveLaunch4jcExecutableHandle() {
        String launch4jcExeResourceFileName = LAUNCH4JC_RESOURCES.fileNames.find { 'launch4jc.exe' }
        findFileInTempDir(launch4jcExeResourceFileName)
    }

    File createFileInTempDir(String fileName) {
        new File(TEMP_DIR, fileName)
    }

    File copyFileToTempDir(File file) {
        File fileInTempDir = createFileInTempDir(file.name)
        FileUtils.copyFile(file, fileInTempDir)
        return fileInTempDir
    }
}

