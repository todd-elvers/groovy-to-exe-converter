package groovyToExeConverter.input.core

import groovy.util.logging.Log4j
import groovyToExeConverter.core.common.resource.ResourceHandlerFactory
import groovyToExeConverter.model.AppConfig
import groovyToExeConverter.util.PropertiesReader
import org.apache.commons.cli.CommandLine

@Log4j
class OneOffCommandHandler {

    static boolean isOneOffCommand(OptionAccessor input) {
        input.help || input.version || input.launch4j
    }

    static void executeCommand(OptionAccessor input) {
        if (input.help) {
            new InputParser().usage()
        } else if (input.version) {
            log.info("Version: ${PropertiesReader.readAppProperty("version")}")
        } else if (input.launch4j) {
            log.info("Performing '--launch4j' one-off command (skipping validation & ignoring user input).")
            AppConfig appConfigWithAllDefaultValues = new InputTransformer().transformIntoAppConfig(new OptionAccessor(new CommandLine()))
            File launch4jExe = ResourceHandlerFactory
                    .makeGroovyScriptResourceHandler(appConfigWithAllDefaultValues)
                    .findFileInLaunch4jDir("launch4j.exe")

            String launch4jGuiCommand = "cmd /c \"$launch4jExe.absolutePath\""
            log.debug("Executing the following to open Launch4j GUI: $launch4jGuiCommand")
            launch4jGuiCommand.execute()
        }
    }

}
