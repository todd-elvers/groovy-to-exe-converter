package groovyToExeConverter.util

import groovyToExeConverter.model.exception.ConfigurationException

class EnvironmentValidator {

    void validate() {
        switch (System.getenv()) {
            case invalidOrMissingGroovyHome: throw new ConfigurationException("Missing environment variable: GROOVY_HOME")
        }
    }

    private def invalidOrMissingGroovyHome = { Map<String, String> envVariables ->
        File GROOVY_HOME = envVariables.keySet().contains('GROOVY_HOME') ? new File(envVariables['GROOVY_HOME']) : null
        !GROOVY_HOME || !GROOVY_HOME.canRead() || !GROOVY_HOME.listFiles()*.name.contains("bin")
    }
}
