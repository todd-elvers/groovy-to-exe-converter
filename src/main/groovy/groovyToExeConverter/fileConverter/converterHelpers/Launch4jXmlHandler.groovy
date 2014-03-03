package groovyToExeConverter.fileConverter.converterHelpers

import groovy.xml.MarkupBuilder
import groovyToExeConverter.domain.AppConfig

class Launch4jXmlHandler {

    private def launch4jXml

    void generateLaunch4jXml(AppConfig appConfig, File jarFile, File exeFile) {
        launch4jXml = {
            dontWrapJar(false)
            headerType(appConfig.appType)
            jar(jarFile.absolutePath)
            outfile(exeFile.absolutePath)
            errTitle('Error')
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

            if(shouldWriteSplashFileXML(appConfig)){
              splash(){
                  file(appConfig.splashFile.absolutePath)
              }
            }

        }
    }

    private static boolean shouldWriteSplashFileXML(AppConfig appConfig){
        appConfig.appType == 'gui' && appConfig.splashFile
    }

    void writeLaunch4jXmlToFile(File xmlFile) {
        def xmlBuilder = new MarkupBuilder(new FileWriter(xmlFile))
        xmlBuilder.expandEmptyElements = true
        xmlBuilder.launch4jConfig(launch4jXml)
    }

}

