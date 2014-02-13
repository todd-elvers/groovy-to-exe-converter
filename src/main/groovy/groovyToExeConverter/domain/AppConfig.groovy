package groovyToExeConverter.domain

class AppConfig {
    File fileToConvert
    File destinationDirectory
    File temporaryDirectory
    File iconFile
    File javaHomeDirectory

    int initialHeapSize
    int maximumHeapSize

    String jarFileName
    String exeFileName
    String minJreVersion
    String maxJreVersion

    boolean showStackTrace
}
