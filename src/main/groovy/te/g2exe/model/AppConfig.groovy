package te.g2exe.model

import groovy.transform.CompileStatic
import groovy.transform.Immutable

@CompileStatic
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

    String toString(){
        def stringBuilder = new StringBuilder('AppConfig = {\n')
        this.properties.each { key, value ->
            if(key != 'class') {
                stringBuilder << "\t$key: $value\n"
            }
        }
        stringBuilder << "}"
        return stringBuilder.toString()
    }
}
