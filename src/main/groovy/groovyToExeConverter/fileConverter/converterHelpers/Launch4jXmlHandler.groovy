package groovyToExeConverter.fileConverter.converterHelpers
import groovy.xml.MarkupBuilder
import groovyToExeConverter.domain.AppConfig

import static groovyToExeConverter.domain.AppConfigDefaults.GUI_APP_TYPE

class Launch4jXmlHandler {

    private def launch4jXml

    void generateLaunch4jXml(AppConfig appConfig, File jarFile, File exeFile) {
        launch4jXml = {
            dontWrapJar(false)
            headerType(appConfig.appType)
            jar(jarFile.absolutePath)
            outfile(exeFile.absolutePath)
            errTitle(exeFile.name)
            cmdLine()
            chdir('.')
            priority('normal')
            downloadUrl('http://java.com/download')
            supportUrl()
            manifest()
            icon(appConfig.iconFile.absolutePath)
            jre() {
                minVersion(appConfig.minJreVersion)
                maxVersion()
                jdkPreference('preferJre')
                initialHeapSize(appConfig.initialHeapSize)
                maxHeapSize(appConfig.maximumHeapSize)
            }
            messages(){
                startupErr("An error occurred while starting ${exeFile.name}.\n\nIf you specified '--splash' on the command line, make sure " +
                        "you passed in a .bmp file that has 24-bit color.  Any color depth besides 24-bit will cause an error.")
            }
            if(shouldWriteSplashFileXML(appConfig)){
              splash(){
                  file(appConfig.splashFile.absolutePath)
              }
            }
        }
    }

    private static boolean shouldWriteSplashFileXML(AppConfig appConfig){
        appConfig.appType == GUI_APP_TYPE.toString() && appConfig.splashFile
    }

    void writeLaunch4jXmlToFile(File xmlFile) {
        def xmlBuilder = new MarkupBuilder(new FileWriter(xmlFile))
        xmlBuilder.expandEmptyElements = true
        xmlBuilder.launch4jConfig(launch4jXml)
    }

}

