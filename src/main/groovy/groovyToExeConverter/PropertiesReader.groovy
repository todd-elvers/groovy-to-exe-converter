package groovyToExeConverter

class PropertiesReader {
    static String readProperty(String propName){
        try {
            def properties = new Properties()
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("gradle.properties"))
            return properties[propName]
        } catch(ignored){
            throw new RuntimeException("Unable to read from properties file.")
        }
    }
}
