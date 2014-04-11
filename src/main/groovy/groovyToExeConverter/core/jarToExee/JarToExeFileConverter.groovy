package groovyToExeConverter.core.jarToExee

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import groovyToExeConverter.core.FileConverter
import groovyToExeConverter.core.jarToExee.core.BitmapImageWriter
import groovyToExeConverter.core.jarToExee.core.Launch4jCommandRunner
import groovyToExeConverter.core.jarToExee.core.Launch4jXmlHandler
import groovyToExeConverter.model.AppConfigDefaults

import static org.apache.commons.io.FilenameUtils.removeExtension

@Log4j
@CompileStatic
class JarToExeFileConverter extends FileConverter {

    @Override
    File convert() {
        log.info("${appConfig.jarFileName.padRight(appConfig.fileToConvert.name.length())} --> ${appConfig.exeFileName}")

        def xmlFile = resourceHandler.createFileInTempDir('launch4jc_config.xml'),
            jarFile = resourceHandler.findFileInTempDir(appConfig.jarFileName),
            exeFile = new File(appConfig.destinationDirectory, appConfig.exeFileName),
            bmpFile = resolveSplashFileHandle()

        new Launch4jXmlHandler().with {
            generateXmlFrom(appConfig, jarFile, exeFile, bmpFile)
            writeXmlTo(xmlFile)
        }

        new Launch4jCommandRunner().with {
            buildCommand(resourceHandler.resolveLaunch4jcExeHandle(), xmlFile)
            runCommand()
        }

        return exeFile
    }

    private File resolveSplashFileHandle() {
        if (shouldRewriteSplashFileAsBitmap()) {
            File bmpFile = resourceHandler.createFileInTempDir(removeExtension(appConfig.splashFile.name) + '.bmp')
            BitmapImageWriter.writeImageAsBitmap(appConfig.splashFile, bmpFile)
            return bmpFile
        }
        return null
    }

    private boolean shouldRewriteSplashFileAsBitmap(){
        appConfig.appType == AppConfigDefaults.GUI_APP_TYPE.defaultValue && appConfig.splashFile
    }

}
