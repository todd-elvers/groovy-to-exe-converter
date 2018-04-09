package te.g2exe.core

import te.g2exe.core.common.resource.ResourceHandlerFactory
import te.g2exe.core.groovyToExe.GroovyToExeFileConverter
import te.g2exe.core.jarToExe.JarToExeFileConverter
import te.g2exe.model.AppConfig
import spock.lang.Specification

class FileConverterFactoryUnitTest extends Specification {

    FileConverterFactory fileConverterFactory = new FileConverterFactory()

    def "when appConfig contains a groovy script, then GroovyToExeFileConverter is returned"(){
        given:
            def appConfig = new AppConfig(fileToConvert: new File("file.groovy"))
            ResourceHandlerFactory.metaClass.static.makeGroovyScriptResourceHandler = {AppConfig ac -> null}

        when:
            def fileConverter = fileConverterFactory.makeFileConverter(appConfig)

        then:
            fileConverter instanceof FileConverter
            fileConverter instanceof GroovyToExeFileConverter
    }

    def "when appConfig contains a jar file, then JarToExeFileConverter is returned"(){
        given:
            def appConfig = new AppConfig(fileToConvert: new File("file.jar"))
            ResourceHandlerFactory.metaClass.static.makeJarFileResourceHandler = {AppConfig ac -> null}

        when:
            def fileConverter = fileConverterFactory.makeFileConverter(appConfig)

        then:
            fileConverter instanceof FileConverter
            fileConverter instanceof JarToExeFileConverter
    }

}
