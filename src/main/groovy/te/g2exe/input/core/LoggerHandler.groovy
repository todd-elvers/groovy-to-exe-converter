package te.g2exe.input.core

import ch.qos.logback.classic.Level
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Slf4j
@CompileStatic
class LoggerHandler {

    static void setAppenderToDebugAppender(){
        (LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger).with {
            setLevel(Level.DEBUG)
        }

        log.debug("Debug logger enabled.")
    }


}
