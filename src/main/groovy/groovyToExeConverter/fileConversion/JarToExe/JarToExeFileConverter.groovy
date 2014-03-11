package groovyToExeConverter.fileConversion.JarToExe
import groovy.util.logging.Log4j
import groovyToExeConverter.domain.AppConfigDefaults
import groovyToExeConverter.exception.CompilationException
import groovyToExeConverter.fileConversion.FileConverter
import groovyToExeConverter.fileConversion.JarToExe.core.BitmapImageWriter
import groovyToExeConverter.fileConversion.JarToExe.core.Launch4jCommandRunner
import groovyToExeConverter.fileConversion.JarToExe.core.Launch4jXmlHandler

import static org.apache.commons.io.FilenameUtils.removeExtension

@Log4j
class JarToExeFileConverter extends FileConverter {

    @Override
    File convert() {
        log.info("${appConfig.jarFileName.padRight(appConfig.fileToConvert.name.length())} --> ${appConfig.exeFileName}")

        def xmlFile = resourceHandler.createFileInTempDir('launch4jc_config.xml')
        def jarFile = resourceHandler.findFileInTempDir(appConfig.jarFileName)
        def exeFile = new File(appConfig.destinationDirectory, appConfig.exeFileName)

        def bmpFile
        if(shouldRewriteSplashFileAsBitmap()){
            bmpFile = resourceHandler.createFileInTempDir(removeExtension(appConfig.splashFile.name) + '.bmp')
            BitmapImageWriter.writeImageAsBitmap(appConfig.splashFile, bmpFile)
        }


        new Launch4jXmlHandler().with {
            generateLaunch4jXml(appConfig, jarFile, exeFile, bmpFile)
            writeLaunch4jXmlToFile(xmlFile)
        }
        new Launch4jCommandRunner().with {
            buildCommand(resourceHandler.resolveLaunch4jcExeHandle(), xmlFile)
            runCommand()
        }


        validateExeCreation(exeFile)
        return exeFile
    }

    private boolean shouldRewriteSplashFileAsBitmap(){
        appConfig.appType == AppConfigDefaults.GUI_APP_TYPE.defaultValue && appConfig.splashFile
    }

    private static void validateExeCreation(File exeFile) {
        if (!exeFile.exists()) {
            throw new CompilationException("Failed during JAR --> EXE :: Launch4j command line operation was not successful.")
        }
    }

}
