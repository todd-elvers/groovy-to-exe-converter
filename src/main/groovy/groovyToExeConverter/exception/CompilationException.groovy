package groovyToExeConverter.exception

class CompilationException extends Exception{
    CompilationException(String message) {
        super(message)
    }

    CompilationException(String message, Throwable cause){
        super(message,cause)
    }
}
