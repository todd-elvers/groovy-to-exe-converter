package te.g2exe

import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import te.g2exe.core.FileConverter
import te.g2exe.core.FileConverterFactory
import te.g2exe.input.InputProcessor
import te.g2exe.model.AppConfig
import te.g2exe.util.ExceptionHandler

//TODO: We need an overhaul on this project now that it is the flagship app on my Github
//TODO: Then create some e2e tests so we can add features & refactor we safety
//TODO: Then refactor for v2.0.0
@Log4j
@CompileStatic
class Startup {

    static void main(String[] args) {
        new Startup().run(args)
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
