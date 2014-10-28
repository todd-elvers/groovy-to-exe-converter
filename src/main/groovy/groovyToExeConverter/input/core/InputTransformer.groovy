package groovyToExeConverter.input.core

import groovy.util.logging.Log4j
import groovyToExeConverter.model.AppConfig
import groovyToExeConverter.model.ResourceFileNames

import static groovyToExeConverter.model.AppConfigDefaults.*
import static org.apache.commons.io.FilenameUtils.removeExtension

@Log4j
class InputTransformer {

    //TODO: Maybe refactor 'AppConfig' to 'Settings' ?
    AppConfig transformIntoAppConfig(OptionAccessor input) {
        log.debug("Transforming user input into AppConfig...")

        File fileToConvert = input.fileToConvert as File
        def appConfig = new AppConfig(
                fileToConvert       : fileToConvert,
                iconFile            : resolveIconFile(input),
                temporaryDirectory  : resolveTempDir(input),
                destinationDirectory: resolveDestDir(input),
                splashFile          : input.splash ? input.splash as File : null,
                appType             : (input.gui ? GUI_APP_TYPE : CONSOLE_APP_TYPE) as String,
                minJreVersion       : (input.minJre ?: MIN_JRE_VERSION) as String,
                initialHeapSize     : (input.initHeapSize ?: INITIAL_HEAP_SIZE) as int,
                maximumHeapSize     : (input.maxHeapSize ?: MAXIMUM_HEAP_SIZE) as int,
                showStackTrace      : (input.stacktrace ?: SHOW_STACKTRACE) as boolean,
                jarFileName         : removeExtension(fileToConvert.name) + '.jar',
                exeFileName         : removeExtension(fileToConvert.name) + '.exe'
        )

        log.debug("$appConfig")
        return appConfig
    }

    private Closure<File> resolveIconFile = { OptionAccessor input ->
        File tempDir = resolveTempDir(input)
        def defaultIconFileName = ResourceFileNames.G2EXE_RESOURCES.fileNames.find { String resource -> resource.endsWith("ico") } as String
        input.icon ? new File(input.icon as String) : new File(tempDir, defaultIconFileName)
    }

    private Closure<File> resolveTempDir = { OptionAccessor input ->
        def tempDirPath = (input.tempDir ?: TEMP_DIR_PATH) as String
        new File(tempDirPath, G2EXE_TEMP_DIR_NAME as String)
    }

    private Closure<File> resolveDestDir = { OptionAccessor input ->
        File fileToConvert = input.fileToConvert as File
        (input.destDir ?: fileToConvert.absoluteFile.parent) as File
    }
}
