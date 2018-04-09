package te.g2exe.input.core

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import org.apache.log4j.ConsoleAppender
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.PatternLayout

@Log4j
@CompileStatic
class Log4jHandler {

    static void setAppenderToDebugAppender(){
        removeAllLoggers()
        addDebugLogger()
        log.debug("Debug logger enabled.")
    }

    static void removeAllLoggers(){
        Logger.rootLogger.loggerRepository.resetConfiguration()
    }

    static void addDebugLogger(){
        ConsoleAppender consoleAppender = new ConsoleAppender()
        consoleAppender.setLayout(new PatternLayout("[%-5p] %c{1} - %m%n"))
        consoleAppender.setThreshold(Level.DEBUG)
        consoleAppender.activateOptions()

        Logger.rootLogger.addAppender(consoleAppender)
    }


}
