package te.g2exe.util

import groovy.util.logging.Slf4j
import te.g2exe.model.AppConfig

@Slf4j
class ExceptionHandler {

    static void handleException(Exception exception, AppConfig appConfig, String[] appArgs) {
        if (exception.message) {
            log.error(exception.message)
        }

        if (shouldDisplayStacktrace(appConfig, appArgs)) {
            log.error("Stacktrace:", exception)
        }

        if (exceptionOccurredDuringConversion(appConfig)) {
            log.error("Conversion failed!")
        }
    }

    private static boolean shouldDisplayStacktrace(AppConfig appConfig, String[] appArgs) {
        appConfig?.showStackTrace || "--stacktrace" in appArgs*.toLowerCase()
    }

    private static boolean exceptionOccurredDuringConversion(AppConfig appConfig) {
        appConfig != null
    }
}
