package groovyToExeConverter.fileConversion.converters

import groovy.util.logging.Log4j
import groovyToExeConverter.domain.AppConfigDefaults
import groovyToExeConverter.exception.CompilationException
import groovyToExeConverter.fileConversion.FileConverter
import groovyToExeConverter.fileConversion.converterHelpers.BitmapImageWriter
import groovyToExeConverter.fileConversion.converterHelpers.Launch4jXmlHandler
import org.apache.commons.io.FilenameUtils

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

        executeLaunch4jcWithXmlFile(xmlFile)
        validateExeCreation(exeFile)

        return exeFile
    }

    private boolean shouldRewriteSplashFileAsBitmap(){
        appConfig.appType == AppConfigDefaults.GUI_APP_TYPE.defaultValue && appConfig.splashFile
    }

    private void executeLaunch4jcWithXmlFile(File launch4jcXmlFile) {
        def launch4jcExeFile = resourceHandler.resolveLaunch4jcExecutableHandle()
        def launch4jcCommand = "\"${launch4jcExeFile}\" \"${launch4jcXmlFile}\""

        Process command = launch4jcCommand.execute()
        pipeCommandOutputToConsole(command)
    }

    private static void pipeCommandOutputToConsole(Process command){
        List commandOutputLines = command.text?.split("\r\n")
        boolean commandFailed = !commandOutputLines?.any { it.contains("Successfully") }
        commandOutputLines.each { String line ->
            if (commandFailed) log.info(line.replace('launch4j: ', ''))
        }
    }

    private static void validateExeCreation(File exeFile) {
        if (!exeFile.exists()) {
            throw new CompilationException("Launch4j command line operation was not successful.")
        }
    }

}
