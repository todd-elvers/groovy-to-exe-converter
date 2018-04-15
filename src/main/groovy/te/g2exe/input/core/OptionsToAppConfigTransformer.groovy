package te.g2exe.input.core

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service
import te.g2exe.model.AppConfig
import te.g2exe.model.ResourceFileNames

import static te.g2exe.model.AppConfigDefaults.*
import static org.apache.commons.io.FilenameUtils.removeExtension

@Slf4j
@Service
@SuppressWarnings("GrMethodMayBeStatic")
class OptionsToAppConfigTransformer {

    AppConfig transform(OptionAccessor options) {
        log.debug("Transforming user input into AppConfig.")

        File fileToConvert = new File(options.fileToConvert as String)
        def appConfig = new AppConfig(
                fileToConvert       : fileToConvert,
                iconFile            : resolveIconFile(options),
                temporaryDirectory  : resolveTempDir(options),
                destinationDirectory: resolveDestDir(options),
                splashFile          : options.splash ? options.splash as File : null,
                appType             : (options.gui ? GUI_APP_TYPE : CONSOLE_APP_TYPE) as String,
                minJreVersion       : (options.minJre ?: MIN_JRE_VERSION) as String,
                initialHeapSize     : (options.initHeapSize ?: INITIAL_HEAP_SIZE) as int,
                maximumHeapSize     : (options.maxHeapSize ?: MAXIMUM_HEAP_SIZE) as int,
                showStackTrace      : (options.stacktrace ?: SHOW_STACKTRACE) as boolean,
                jarFileName         : removeExtension(fileToConvert?.name) + '.jar',
                exeFileName         : removeExtension(fileToConvert?.name) + '.exe'
        )

        log.debug("$appConfig")
        return appConfig
    }

    protected File resolveIconFile(OptionAccessor input) {
        File tempDir = resolveTempDir(input)
        String defaultIconFileName = ResourceFileNames.G2EXE_RESOURCES.fileNames.find { String resource ->
            resource.endsWith("ico")
        }

        return (input.icon) ? new File(input.icon as String) : new File(tempDir, defaultIconFileName)
    }

    private File resolveTempDir(OptionAccessor input) {
        def tempDirPath = (input.tempDir ?: TEMP_DIR_PATH) as String
        return new File(tempDirPath, G2EXE_TEMP_DIR_NAME as String)
    }

    private File resolveDestDir(OptionAccessor input) {
        File fileToConvert = new File(input.fileToConvert as String)
        return (input.destDir ?: fileToConvert?.absoluteFile?.parent) as File
    }
}
