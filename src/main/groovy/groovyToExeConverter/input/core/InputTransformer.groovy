package groovyToExeConverter.input.core
import groovy.util.OptionAccessor as CommandLineInput
import groovy.util.logging.Log4j
import groovyToExeConverter.domain.AppConfig
import groovyToExeConverter.domain.Resources

import static groovyToExeConverter.domain.AppConfigDefaults.*
import static org.apache.commons.io.FilenameUtils.removeExtension

@Log4j
class InputTransformer {

    AppConfig transformIntoAppConfig(CommandLineInput input) {
        File fileToConvert = input.fileToConvert as File

        def appConfig = new AppConfig(
                fileToConvert       : fileToConvert,
                iconFile            : resolveIconFile(input),
                destinationDirectory: resolveDestDir(input),
                temporaryDirectory  : resolveTempDir(input),
                minJreVersion       : (input.minJre         ?: MIN_JRE_VERSION.defaultValue) as String,
                initialHeapSize     : (input.initHeapSize   ?: INITIAL_HEAP_SIZE.defaultValue) as int,
                maximumHeapSize     : (input.maxHeapSize    ?: MAXIMUM_HEAP_SIZE.defaultValue) as int,
                showStackTrace      : (input.stacktrace     ?: SHOW_STACKTRACE.defaultValue) as boolean,
                jarFileName         : removeExtension(fileToConvert.name) + '.jar',
                exeFileName         : removeExtension(fileToConvert.name) + '.exe'
        )

        log.debug("Transformation result: ${appConfig.dump()}")
        return appConfig
    }

    private def resolveTempDir = { CommandLineInput input ->
        def tempDirPath = (input.tempDir ?: TEMP_DIR_PATH.defaultValue) as String
        new File(tempDirPath, G2EXE_TEMP_DIR_NAME as String)
    }

    private def resolveDestDir = { CommandLineInput input ->
        File fileToConvert = input.fileToConvert as File
        (input.destDir ?: fileToConvert.absoluteFile.parent) as File
    }

    private def resolveIconFile = { CommandLineInput input ->
        File tempDir = resolveTempDir(input)
        def defaultIconFileName = Resources.G2EXE_RESOURCES.fileNames.find { String resource -> resource.endsWith("ico") } as String
        input.icon ? new File(input.icon as String) : new File(tempDir, defaultIconFileName)
    }
}
