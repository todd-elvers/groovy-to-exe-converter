package groovyToExeConverter.model

import groovy.transform.Immutable

@Immutable(knownImmutableClasses = [File])
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

    boolean containsSplashFile() { return splashFile }
    boolean appTypeIsGUI(){ return appType == AppConfigDefaults.GUI_APP_TYPE as String }
}
