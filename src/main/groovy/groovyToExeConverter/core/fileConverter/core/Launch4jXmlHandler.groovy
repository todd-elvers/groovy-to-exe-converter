package groovyToExeConverter.core.fileConverter.core

import groovy.xml.MarkupBuilder
import groovyToExeConverter.domain.AppConfig

class Launch4jXmlHandler {

    private def launch4jXml

    //TODO: Build XML config based on appConfig
    //TODO: Make sure not to populate <splash> elements if headerType('console')
    //TODO: Finish populating XML elements with appConfig attributes
    void generateXmlConfigContents(AppConfig appConfig, File jarFile, File exeFile){
        launch4jXml = {
            dontWrapJar(false)
            headerType('console')
            jar(jarFile.absolutePath)
            outfile(exeFile.absolutePath)
            errTitle('HelloWorld Error')
            cmdLine()
            chdir()
            priority('normal')
            downloadUrl('http://java.com/download')
            supportUrl()
            manifest()
            icon('C:/Users/Todd/Desktop/g2exe_temp/carfox_pilgrim.ico')
            jre() {
                path('C:/Program Files/Java/jdk1.7.0_45')
                bundledJre64Bit(true)
                minVersion()
                maxVersion()
                jdkPreference('preferJre')
                initialHeapSize(1024)
                maxHeapSize(2048)
            }
        }
    }

    void writeXmlConfigContentsTo(File xmlFile){
        def xmlBuilder = new MarkupBuilder(new FileWriter(xmlFile))
        xmlBuilder.expandEmptyElements = true
        xmlBuilder.launch4jConfig(launch4jXml)
    }

}
