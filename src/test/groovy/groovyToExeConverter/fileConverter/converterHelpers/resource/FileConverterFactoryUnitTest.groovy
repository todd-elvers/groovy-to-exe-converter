package groovyToExeConverter.fileConverter.converterHelpers.resource
import groovyToExeConverter.domain.AppConfig
import groovyToExeConverter.fileConverter.FileConverter
import groovyToExeConverter.fileConverter.FileConverterFactory
import groovyToExeConverter.fileConverter.converters.GroovyToExeFileConverter
import groovyToExeConverter.fileConverter.converters.JarToExeFileConverter
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
