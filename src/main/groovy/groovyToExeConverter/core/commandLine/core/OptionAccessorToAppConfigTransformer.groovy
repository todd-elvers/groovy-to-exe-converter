package groovyToExeConverter.core.commandLine.core
import groovyToExeConverter.domain.AppConfig

import static groovyToExeConverter.domain.AppConfigDefaults.SHOW_STACKTRACE
import static groovyToExeConverter.domain.AppConfigDefaults.TEMP_DIR_PATH


class OptionAccessorToAppConfigTransformer {

    AppConfig transform(OptionAccessor parsedArgs) {

        File fileToConvert = parsedArgs.fileToConvert as File
        File destinationDirectory = (parsedArgs.destDir) ? (parsedArgs.destDir as File) : (fileToConvert.parentFile)
        File temporaryDirectory = (parsedArgs.tempDir) ? new File(parsedArgs.tempDir as String) : new File(TEMP_DIR_PATH as String)
        boolean showStackTrace = (parsedArgs.stacktrace) ?: SHOW_STACKTRACE as boolean

        String fileNameNoFileExt = fileToConvert.name.substring(0, fileToConvert.name.lastIndexOf("."))
        String jarFileName = fileNameNoFileExt+'.jar'
        String exeFileName = fileNameNoFileExt+'.exe'

        return new AppConfig(
                fileToConvert: fileToConvert,
                destinationDirectory: destinationDirectory,
                temporaryDirectory: temporaryDirectory,
                jarFileName: jarFileName,
                exeFileName: exeFileName,
                showStackTrace: showStackTrace
        )
    }

}
