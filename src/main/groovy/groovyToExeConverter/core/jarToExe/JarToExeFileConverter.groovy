package groovyToExeConverter.core.jarToExe

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import groovyToExeConverter.core.FileConverter
import groovyToExeConverter.core.jarToExe.core.BitmapImageWriter
import groovyToExeConverter.core.jarToExe.core.Launch4jCommandRunner
import groovyToExeConverter.core.jarToExe.core.Launch4jXmlHandler

import static org.apache.commons.io.FilenameUtils.removeExtension

@Log4j
@CompileStatic
class JarToExeFileConverter extends FileConverter {

    @Override
    File convert() {
        File xmlFile = resourceHandler.createFileInTempDir('launch4jc_config.xml')
        File exeFile = new File(appConfig.destinationDirectory, appConfig.exeFileName)

        log.info("Writing Launch4j XML to disk.")
        new Launch4jXmlHandler().with {
            File jarFile = resourceHandler.findFileInTempDir(appConfig.jarFileName)
            File bmpFile = resolveSplashFileHandle()

            generateXmlFrom(appConfig, jarFile, exeFile, bmpFile)
            writeXmlTo(xmlFile)
        }

        log.info("Running Launch4j from the command line.")
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
            BitmapImageWriter.writeImageAsBitmap(appConfig.splashFile, bmpFile)
            return bmpFile
        }
        return null
    }

}
