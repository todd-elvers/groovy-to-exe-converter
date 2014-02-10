package groovyToExeConverter.core.exception

class InputValidationException extends Exception {

    InputValidationException(String message){
        super(message)
    }

    InputValidationException(String message, Throwable cause){
        super(message, cause)
    }

}
