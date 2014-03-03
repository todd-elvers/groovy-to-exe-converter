package groovyToExeConverter.domain

class AppConfig {
    File fileToConvert
    File destinationDirectory
    File temporaryDirectory
    File iconFile
    File splashFile

    int initialHeapSize
    int maximumHeapSize

    String jarFileName
    String exeFileName
    String minJreVersion
    String appType

    boolean showStackTrace
}
