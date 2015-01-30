package groovyToExeConverter.util

import groovy.transform.CompileStatic
import groovyToExeConverter.model.exception.ConfigurationException

@CompileStatic
class EnvironmentHandler {

    void validateGroovyHome() {
        File groovyHome = System.getenv('GROOVY_HOME') ? new File(System.getenv('GROOVY_HOME')) : null
        if(groovyHomeMissingOrInvalid(groovyHome)){
            throw new ConfigurationException("Environment variable GROOVY_HOME is either missing or invalid.")
        }
    }

    private static boolean groovyHomeMissingOrInvalid(File groovyHome){
        !groovyHome || !groovyHome.canRead() || !groovyHome.listFiles()*.name.contains("bin")
    }

    void addShutdownHookToKillG2exeProcess() {
        addShutdownHook {
            try {
                "taskkill /f /im g2exe.exe".execute()
            } catch (ignored) { }
        }
    }
}
