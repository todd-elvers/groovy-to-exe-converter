package groovyToExeConverter.input.core

import org.apache.log4j.ConsoleAppender
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.PatternLayout

class Log4jHandler {

    static void setAppenderToDebugAppender(){
        removeAllLoggers()
        addDebugLogger()
    }

    static void removeAllLoggers(){
        Logger.getRootLogger().getLoggerRepository().resetConfiguration()
    }

    static void addDebugLogger(){
        ConsoleAppender consoleAppender = new ConsoleAppender()
        consoleAppender.setLayout(new PatternLayout("[%-5p] %c{1} - %m%n"))
        consoleAppender.setThreshold(Level.DEBUG)
        consoleAppender.activateOptions()

        Logger.getRootLogger().addAppender(consoleAppender)
    }


}
