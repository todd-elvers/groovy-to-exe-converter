package groovyToExeConverter.core.fileConverter.core

import groovy.io.FileType
import groovy.transform.ThreadInterrupt
import groovy.util.logging.Log4j
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils

import static groovyToExeConverter.domain.AppConfigDefaults.DEFAULT_LAUNCH4JC_RESOURCES

@ThreadInterrupt
@Log4j
class ResourceHandler {
    final File TEMP_DIR

    //TODO: Improve the exceptions in this class
    ResourceHandler(File temporaryDirectory) {
        TEMP_DIR = temporaryDirectory

        if (tempDirIsMissingLaunch4jResources()) {
            copyLaunch4jcResourcesToTempDir()
        } else {
            cleanTempDir()
        }
    }

    private boolean tempDirIsMissingLaunch4jResources() {
        !TEMP_DIR.exists() || !TEMP_DIR.listFiles()*.name.contains("launch4j")
    }

    private void copyLaunch4jcResourcesToTempDir() {
        log.debug("Folder 'g2exe' not found in temporary directory - creating temp dir and copying launch4j resources there.")
        def resources = DEFAULT_LAUNCH4JC_RESOURCES.defaultValue
        resources.each(extractToTempDir)
    }

    //TODO: If the system hold a file for too long again, consider using a timeout here
    private void cleanTempDir() {
        log.debug("Folder 'g2exe' found in temporary directory - removing all non-launch4j files.")
        TEMP_DIR.eachFile(FileType.FILES) { it.delete() }
    }

    File findFileInTempDir(String fileName) {
        File file = new File(TEMP_DIR, fileName)
        if (!file.exists()) {
            throw new FileNotFoundException("'$fileName' not found in temporary directory.")
        }
        return file
    }

    File resolveLaunch4jcExecutableHandle() {
        String launch4jcExeResourceFileName = DEFAULT_LAUNCH4JC_RESOURCES.defaultValue.find { 'launch4jc.exe' }
        return findFileInTempDir(launch4jcExeResourceFileName)
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
            log.error("Unable to copy g2exe files to temporary directory:\n${TEMP_DIR}")
            throw ex
        } finally {
            outputStream?.close()
            inputStream?.close()
        }
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

