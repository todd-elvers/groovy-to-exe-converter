package groovyToExeConverter.core.fileConverter.core

import groovy.xml.MarkupBuilder
import groovyToExeConverter.domain.AppConfig

class Launch4jXmlHandler {

    private def launch4jXml

    //TODO: Build XML config based on appConfig
    //TODO: Make sure not to populate <splash> elements if headerType('console')
    //TODO: Finish populating XML elements with appConfig attributes
    void generateXmlConfigContents(AppConfig appConfig, File jarFile, File exeFile) {
        def iconPath = appConfig.iconFile ? appConfig.iconFile.absolutePath : ''
        launch4jXml = {
            dontWrapJar(false)
            headerType('console')
            jar(jarFile.absolutePath)
            outfile(exeFile.absolutePath)
            errTitle('Error')
            cmdLine()
            chdir()
            priority('normal')
            downloadUrl('http://java.com/download')
            supportUrl()
            manifest()
            icon(iconPath)
            jre() {
//                path('C:/Program Files/Java/jdk1.7.0_45')
//                bundledJre64Bit(true)
                minVersion(appConfig.minJreVersion)
                maxVersion()
                jdkPreference('preferJre')
                initialHeapSize(appConfig.initialHeapSize)
                maxHeapSize(appConfig.maximumHeapSize)
            }
//            splash() {
//                file('')
//                waitForWindow(true)
//                timeout(60)
//                timeoutErr(true)
//            }
        }
    }

    void writeXmlConfigContentsTo(File xmlFile) {
        def xmlBuilder = new MarkupBuilder(new FileWriter(xmlFile))
        xmlBuilder.expandEmptyElements = true
        xmlBuilder.launch4jConfig(launch4jXml)
    }

}

