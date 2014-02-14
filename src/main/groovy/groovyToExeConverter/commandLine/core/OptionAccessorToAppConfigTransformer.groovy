package groovyToExeConverter.commandLine.core

import groovy.util.logging.Log4j
import groovyToExeConverter.domain.AppConfig

import static groovyToExeConverter.domain.AppConfigDefaults.*

@Log4j
class OptionAccessorToAppConfigTransformer {

    AppConfig transform(OptionAccessor parsedArgs) {
        File fileToConvert = parsedArgs.fileToConvert as File

        def tempDirPath = (parsedArgs.tempDir ?: TEMP_DIR_PATH.defaultValue) as String
        File temporaryDirectory = new File(tempDirPath, G2EXE_TEMP_DIR_NAME.toString())

        def iconFile = (parsedArgs.icon) ? parsedArgs.icon as File : new File(temporaryDirectory, ICON_FILE_NAME.defaultValue)

        File destinationDirectory = (parsedArgs.destDir ?: fileToConvert.parent) as File
        boolean showStackTrace = (parsedArgs.stacktrace ?: SHOW_STACKTRACE.defaultValue) as boolean
        String minJreVersion = (parsedArgs.minJre ?: MIN_JRE_VERSION.defaultValue) as String
        int initialHeapSize = (parsedArgs.initHeapSize ?: INITIAL_HEAP_SIZE.defaultValue) as int
        int maximumHeapSize = (parsedArgs.maxHeapSize ?: MAXIMUM_HEAP_SIZE.defaultValue) as int


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
