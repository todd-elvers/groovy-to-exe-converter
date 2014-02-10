package groovyToExeConverter.core.exception

class ConfigurationException extends Exception {
    ConfigurationException(String message) {
        super(message)
    }

    ConfigurationException(String message, Throwable cause){
        super(message,cause)
    }
}
