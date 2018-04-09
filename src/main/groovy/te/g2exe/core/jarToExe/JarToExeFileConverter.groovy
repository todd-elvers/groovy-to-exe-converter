package te.g2exe.core.jarToExe

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import te.g2exe.core.FileConverter
import te.g2exe.core.jarToExe.core.BitmapImageWriter
import te.g2exe.core.jarToExe.core.Launch4jCommandRunner
import te.g2exe.core.jarToExe.core.Launch4jXmlHandler

import static org.apache.commons.io.FilenameUtils.removeExtension

@Log4j
@CompileStatic
class JarToExeFileConverter extends FileConverter {

    @Override
    File convert() {
        File xmlFile = resourceHandler.createFileInTempDir('launch4jc_config.xml')
        File exeFile = new File(appConfig.destinationDirectory, appConfig.exeFileName)

        new Launch4jXmlHandler().with {
            File jarFile = resourceHandler.findFileInTempDir(appConfig.jarFileName)
            File bmpFile = resolveSplashFileHandle()
            generateXmlFrom(appConfig, jarFile, exeFile, bmpFile)
            writeXmlTo(xmlFile)
        }

        new Launch4jCommandRunner().with {
            File launch4jcExe = resourceHandler.findFileInLaunch4jDir("launch4jc.exe")
            buildCommand(launch4jcExe, xmlFile)
            runCommand()
        }

        log.info("Executable file created: $exeFile")
        return exeFile
    }

    private File resolveSplashFileHandle() {
        if (appConfig.containsSplashFile() && appConfig.appTypeIsGUI()) {
            File bmpFile = resourceHandler.createFileInTempDir(removeExtension(appConfig.splashFile.name) + '.bmp')
            return BitmapImageWriter.writeImageAsBitmap(appConfig.splashFile, bmpFile)
        }
        return null
    }

}
