package te.g2exe.core.jarToExe.core

import groovy.util.logging.Slf4j
import groovy.xml.MarkupBuilder
import te.g2exe.model.AppConfig

@Slf4j
class Launch4jXmlHandler {

    private def launch4jXml

    void generateXmlFrom(AppConfig appConfig, File jarFile, File exeFile, File imgFile) {
        log.info("Generating Launch4j XML.")
        launch4jXml = {
            dontWrapJar(false)
            headerType(appConfig.appType)
            jar(jarFile.absolutePath)
            outfile(exeFile.absolutePath)
            errTitle(exeFile.name)
            cmdLine()
            priority('normal')
            downloadUrl('http://java.com/download')
            supportUrl()
            manifest()
            icon(appConfig.iconFile.absolutePath)
            jre() {
                // At the very least the minimum JRE version must be specified
                // in order for Launch4j to perform a registry scan
                minVersion(appConfig.minJreVersion)
                maxVersion()
                jdkPreference('preferJre')
                initialHeapSize(appConfig.initialHeapSize)
                maxHeapSize(appConfig.maximumHeapSize)
            }
            messages(){
                startupErr("An error occurred while starting ${exeFile.name}.")
                jreVersionErr("""\
                    An error occurred while trying to locate a valid Java Runtime Environment.
                    After pressing OK, you will be directed to the Java download page.

                    This application expects the following Java version (or greater): """.stripIndent())
            }
            if(appConfig.containsSplashFile() && appConfig.appTypeIsGUI()){
                splash(){
                    file(imgFile.absolutePath)
                }
            }
        }
    }

    void writeXmlTo(File xmlFile) {
        log.info("Writing Launch4j XML to file.")
        xmlFile.withWriter { fileWriter ->
            MarkupBuilder xmlBuilder = new MarkupBuilder(fileWriter)

            xmlBuilder.expandEmptyElements = true
            xmlBuilder.launch4jConfig(launch4jXml)
        }
    }

}

