package groovyToExeConverter.domain

class AppConfig {
    File fileToConvert
    File destinationDirectory
    File temporaryDirectory
    File iconFile
    int initialHeapSize
    int maximumHeapSize
    String jarFileName
    String exeFileName
    String minJreVersion
    boolean showStackTrace
}
