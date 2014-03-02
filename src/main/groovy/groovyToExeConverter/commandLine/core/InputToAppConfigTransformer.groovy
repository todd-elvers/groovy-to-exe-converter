package groovyToExeConverter.commandLine.core

import groovy.util.OptionAccessor as CommandLineInput
import groovy.util.logging.Log4j
import groovyToExeConverter.domain.AppConfig
import groovyToExeConverter.domain.Resources
import org.apache.commons.io.FilenameUtils

import static groovyToExeConverter.domain.AppConfigDefaults.*

@Log4j
class InputToAppConfigTransformer {

    AppConfig transform(CommandLineInput input) {
        File fileToConvert = input.fileToConvert as File

        def tempDirPath = (input.tempDir ?: TEMP_DIR_PATH.defaultValue) as String
        File temporaryDirectory = new File(tempDirPath, G2EXE_TEMP_DIR_NAME as String)

        File destinationDirectory = (input.destDir ?: fileToConvert.absoluteFile.parent) as File

        def defaultIconFileName = Resources.G2EXE_RESOURCES.fileNames.find { String resource -> resource.endsWith("ico") } as String
        File iconFile = (input.icon) ? input.icon as File : new File(temporaryDirectory, defaultIconFileName)

        String minJreVersion = (input.minJre ?: MIN_JRE_VERSION.defaultValue) as String
        int initialHeapSize = (input.initHeapSize ?: INITIAL_HEAP_SIZE.defaultValue) as int
        int maximumHeapSize = (input.maxHeapSize ?: MAXIMUM_HEAP_SIZE.defaultValue) as int

        String fileNameNoExt = FilenameUtils.removeExtension(fileToConvert.name)
        String jarFileName = fileNameNoExt + '.jar'
        String exeFileName = fileNameNoExt + '.exe'

        boolean showStackTrace = (input.stacktrace ?: SHOW_STACKTRACE.defaultValue) as boolean

        def appConfig = new AppConfig(
                fileToConvert: fileToConvert,
                iconFile: iconFile,
                destinationDirectory: destinationDirectory,
                temporaryDirectory: temporaryDirectory,
                minJreVersion: minJreVersion,
                initialHeapSize: initialHeapSize,
                maximumHeapSize: maximumHeapSize,
                jarFileName: jarFileName,
                exeFileName: exeFileName,
                showStackTrace: showStackTrace
        )

        log.debug("Transformation result: ${appConfig.dump()}")
        return appConfig
    }

}
