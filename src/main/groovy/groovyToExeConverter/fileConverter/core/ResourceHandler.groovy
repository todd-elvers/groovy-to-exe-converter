package groovyToExeConverter.fileConverter.core

import groovy.io.FileType
import groovy.util.logging.Log4j
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils

import static groovyToExeConverter.domain.AppConfigDefaults.ICON_RESOURCE_FILE_NAME
import static groovyToExeConverter.domain.AppConfigDefaults.LAUNCH4JC_RESOURCES

@Log4j
class ResourceHandler {
    final File TEMP_DIR

    //TODO: Improve the exceptions in this class
    ResourceHandler(File temporaryDirectory) {
        TEMP_DIR = temporaryDirectory

        if (tempDirIsMissingResources()) {
            copyResourcesToTempDir()
        } else {
            deleteAllButResourcesFromTempDir()
        }
    }

    //TODO: Actually check if all files are present?
    //TODO: Possibly check version of app too?  Clean if the versions differ?
    private boolean tempDirIsMissingResources() {
        !TEMP_DIR.exists() || !TEMP_DIR.listFiles()*.name.contains("launch4j")
    }

    private void copyResourcesToTempDir() {
        log.debug("Folder 'g2exe' not found in temporary directory - creating temp dir and copying launch4j resources there.")
        List resources = [
                LAUNCH4JC_RESOURCES.defaultValue,
                ICON_RESOURCE_FILE_NAME.defaultValue
        ].flatten()
        resources.each(extractToTempDir)
    }

    private extractToTempDir = { String resourceFileName ->
        log.debug("Copying resource '$resourceFileName' to temporary directory.")

        def inputStream, outputStream
        try {
            def tempFile = new File(TEMP_DIR, resourceFileName)
            inputStream = getClass()?.getClassLoader()?.getResourceAsStream(resourceFileName)
            outputStream = FileUtils.openOutputStream(tempFile)
            IOUtils.copy(inputStream, outputStream)
        } catch (Exception ex) {
            log.error("Unable to copy g2exe resource '${resourceFileName}' to temporary directory: ${TEMP_DIR}")
            throw ex
        } finally {
            outputStream?.close()
            inputStream?.close()
        }
    }

    //TODO: If the system holds a file for too long again, consider using a timeout here. MicrosoftSecurityEssentials issue?
    private void deleteAllButResourcesFromTempDir() {
        log.debug("Folder 'g2exe' found in temporary directory - removing all non-launch4j/g2exe files.")
        def fileTypesToRemove = ['.exe', '.groovy', '.jar', '.class', '.xml']
        TEMP_DIR.eachFile(FileType.FILES) { File file ->
            if (fileTypesToRemove.any { file.name.endsWith(it) }) {
                file.delete()
            }
        }
    }

    File findFileInTempDir(String fileName) {
        File file = new File(TEMP_DIR, fileName)
        if (!file.exists()) {
            throw new FileNotFoundException("'$fileName' not found in temporary directory.")
        }
        return file
    }

    File resolveLaunch4jcExecutableHandle() {
        String launch4jcExeResourceFileName = LAUNCH4JC_RESOURCES.defaultValue.find { 'launch4jc.exe' }
        return findFileInTempDir(launch4jcExeResourceFileName)
    }

    File createFileInTempDir(String fileName) {
        return new File(TEMP_DIR, fileName)
    }

    File copyFileToTempDir(File file) {
        File fileInTempDir = createFileInTempDir(file.name)
        FileUtils.copyFile(file, fileInTempDir)
        return fileInTempDir
    }
}

