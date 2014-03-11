package groovyToExeConverter.input.core

import groovy.util.OptionAccessor as Input
import groovy.util.logging.Log4j
import groovyToExeConverter.domain.AppConfig
import groovyToExeConverter.domain.ResourceFileNames

import static groovyToExeConverter.domain.AppConfigDefaults.*
import static org.apache.commons.io.FilenameUtils.removeExtension

@Log4j
class InputTransformer {

    AppConfig transformIntoAppConfig(Input input) {
        File fileToConvert = input.fileToConvert as File
        def appConfig = new AppConfig(
                fileToConvert       : fileToConvert,
                iconFile            : resolveIconFile(input),
                temporaryDirectory  : resolveTempDir(input),
                destinationDirectory: resolveDestDir(input),
                splashFile          : input.splash ? input.splash as File : null,
                appType             : input.gui ? GUI_APP_TYPE.defaultValue : CONSOLE_APP_TYPE.defaultValue,
                minJreVersion       : (input.minJre ?: MIN_JRE_VERSION.defaultValue) as String,
                initialHeapSize     : (input.initHeapSize ?: INITIAL_HEAP_SIZE.defaultValue) as int,
                maximumHeapSize     : (input.maxHeapSize ?: MAXIMUM_HEAP_SIZE.defaultValue) as int,
                showStackTrace      : (input.stacktrace ?: SHOW_STACKTRACE.defaultValue) as boolean,
                jarFileName         : removeExtension(fileToConvert.name) + '.jar',
                exeFileName         : removeExtension(fileToConvert.name) + '.exe'
        )

        log.debug("Transformation result: ${appConfig.dump()}")
        return appConfig
    }

    private def resolveIconFile = { Input input ->
        File tempDir = resolveTempDir(input)
        def defaultIconFileName = ResourceFileNames.G2EXE_RESOURCES.fileNames.find { String resource -> resource.endsWith("ico") } as String
        input.icon ? new File(input.icon as String) : new File(tempDir, defaultIconFileName)
    }

    private def resolveTempDir = { Input input ->
        def tempDirPath = (input.tempDir ?: TEMP_DIR_PATH) as String
        new File(tempDirPath, G2EXE_TEMP_DIR_NAME as String)
    }

    private def resolveDestDir = { Input input ->
        File fileToConvert = input.fileToConvert as File
        (input.destDir ?: fileToConvert.absoluteFile.parent) as File
    }
}
