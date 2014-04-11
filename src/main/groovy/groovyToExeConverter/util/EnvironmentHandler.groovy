package groovyToExeConverter.util

class EnvironmentHandler {

    void validateGroovyHome() {
        File GROOVY_HOME = System.getenv('GROOVY_HOME') ? new File(System.getenv('GROOVY_HOME')) : null
        !GROOVY_HOME || !GROOVY_HOME.canRead() || !GROOVY_HOME.listFiles()*.name.contains("bin")
    }

    void addShutdownHookToKillG2exeProcess(){
        addShutdownHook {
            "taskkill /f /im g2exe.exe".execute()
        }
    }
}
