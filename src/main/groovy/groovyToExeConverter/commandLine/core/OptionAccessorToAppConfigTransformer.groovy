package groovyToExeConverter.commandLine.core

import groovy.util.logging.Log4j
import groovyToExeConverter.domain.AppConfig

import static groovyToExeConverter.domain.AppConfigDefaults.*

@Log4j
class OptionAccessorToAppConfigTransformer {

    AppConfig transform(OptionAccessor options) {
        File fileToConvert = options.fileToConvert as File

        def tempDirPath = (options.tempDir ?: TEMP_DIR_PATH.defaultValue) as String
        File temporaryDirectory = new File(tempDirPath, G2EXE_TEMP_DIR_NAME.toString())

        def iconFile = (options.icon) ? options.icon as File : new File(temporaryDirectory, ICON_RESOURCE_FILE_NAME.defaultValue)

        File destinationDirectory = (options.destDir ?: fileToConvert.parent) as File
        boolean showStackTrace = (options.stacktrace ?: SHOW_STACKTRACE.defaultValue) as boolean
        String minJreVersion = (options.minJre ?: MIN_JRE_VERSION.defaultValue) as String
        int initialHeapSize = (options.initHeapSize ?: INITIAL_HEAP_SIZE.defaultValue) as int
        int maximumHeapSize = (options.maxHeapSize ?: MAXIMUM_HEAP_SIZE.defaultValue) as int


        String fileNameNoFileExt = fileToConvert.name.substring(0, fileToConvert.name.lastIndexOf("."))
        String jarFileName = fileNameNoFileExt+'.jar'
        String exeFileName = fileNameNoFileExt+'.exe'

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
