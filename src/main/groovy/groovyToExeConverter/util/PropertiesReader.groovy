package groovyToExeConverter.util

import org.apache.commons.io.FileUtils

class PropertiesReader {
    static final PROPERTIES_FILE_NAME = "gradle.properties"

    static String readAppProperty(String propName) {
        try {
            return readPropertyFromInputStream(propName, resolveAppPropFileInputStream())
        } catch (ignored) {
            throw new RuntimeException("Unable to read '${propName}' from g2exe's properties file.")
        }
    }

    private static InputStream resolveAppPropFileInputStream(){
        def propFileInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)
        if(!propFileInputStream){
            propFileInputStream = FileUtils.openInputStream(PROPERTIES_FILE_NAME as File)
        }
        return propFileInputStream
    }

    static String readPropertyFromFile(String propName, File propFile) {
        try {
            return readPropertyFromInputStream(propName, FileUtils.openInputStream(propFile))
        } catch (ignored) {
            throw new RuntimeException("Unable to read from properties file.")
        }
    }

    private static String readPropertyFromInputStream(String propName, InputStream inputStream) {
        def properties = new Properties()
        properties.load(inputStream)
        return properties[propName]
    }
}
