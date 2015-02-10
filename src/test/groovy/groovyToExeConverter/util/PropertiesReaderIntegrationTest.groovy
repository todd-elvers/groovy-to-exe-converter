package groovyToExeConverter.util

import test_helpers.TempDirectorySpockIntegrationTest

import static groovyToExeConverter.util.PropertiesReader.readPropertyFromFile

class PropertiesReaderIntegrationTest extends TempDirectorySpockIntegrationTest {

    def "readPropertyFromFile() reads properties from files"() {
        given:
            String propName = "version"
            String propValue = "1.2.34"
            String propFileName = "someFile.properties"
            File propFileInTempDir = new File(TEMP_DIR, propFileName)
            propFileInTempDir << "${propName}=${propValue}\n"
        expect:
            readPropertyFromFile(propName, propFileInTempDir) == propValue
    }

}
