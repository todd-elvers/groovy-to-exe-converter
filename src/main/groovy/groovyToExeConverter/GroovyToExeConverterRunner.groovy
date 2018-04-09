package groovyToExeConverter

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import groovyToExeConverter.core.FileConverter
import groovyToExeConverter.core.FileConverterFactory
import groovyToExeConverter.input.InputProcessor
import groovyToExeConverter.model.AppConfig
import groovyToExeConverter.util.ExceptionHandler

//TODO: We need an overhaul on this project now that it is the flagship app on my Github
//TODO: We can do the quick thing and get 1.3.2 out he door w/ bugfixes
    // Or if we're impatient we can skip to the next step and then the refactor
//TODO: Then create some e2e tests so we can add features & refactor we safety
//TODO: Then refactor for v2.0.0
@Log4j
@CompileStatic
class GroovyToExeConverterRunner {

    static void main(String[] args) {
        new GroovyToExeConverterRunner().run(args)
    }


    InputProcessor inputProcessor = new InputProcessor()

    void run(String[] args) {
        AppConfig appConfig

        try {
            addShutdownHookToKillG2exeProcess()

            appConfig = inputProcessor.processIntoAppConfig(args)

            if (appConfig) {
                log.info("Converting $appConfig.fileToConvert.name into $appConfig.exeFileName...")
                FileConverter fileConverter = FileConverterFactory.makeFileConverter(appConfig)
                fileConverter.convert()
                log.info("Conversion successful!")
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex, appConfig, args)
        }
    }

    private void addShutdownHookToKillG2exeProcess() {
        addShutdownHook {
            try {
                "taskkill /f /im g2exe.exe".execute()
            } catch (ignored) { }
        }
    }
}
