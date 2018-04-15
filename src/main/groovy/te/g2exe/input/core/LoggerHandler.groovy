package te.g2exe.input.core

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Slf4j
@Service
@SuppressWarnings("GrMethodMayBeStatic")
class LoggerHandler {

    void changeLogLevelIfNecessary(OptionAccessor options) {
        if(options.debug) {
            (LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger).with {
                setLevel(Level.DEBUG)
            }

            log.debug("Debug logger enabled.")
        }
    }


}
