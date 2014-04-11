package groovyToExeConverter.core.jarToExe.core

import groovy.xml.MarkupBuilder
import groovyToExeConverter.model.AppConfig

class Launch4jXmlHandler {

    private def launch4jXml

    void generateXmlFrom(AppConfig appConfig, File jarFile, File exeFile, File imgFile) {
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
            }
            if(appConfig.containsSplashFile() && appConfig.appTypeIsGUI()){
                splash(){
                    file(imgFile.absolutePath)
                }
            }
        }
    }

    void writeXmlTo(File xmlFile) {
        def xmlBuilder = new MarkupBuilder(new FileWriter(xmlFile))
        xmlBuilder.expandEmptyElements = true
        xmlBuilder.launch4jConfig(launch4jXml)
    }

}

