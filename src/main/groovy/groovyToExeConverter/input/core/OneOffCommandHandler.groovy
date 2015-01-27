package groovyToExeConverter.input.core

import groovy.util.logging.Log4j
import groovyToExeConverter.core.common.resource.ResourceHandlerFactory
import groovyToExeConverter.model.AppConfig
import groovyToExeConverter.util.PropertiesReader
import org.apache.commons.cli.CommandLine

@Log4j
class OneOffCommandHandler {

    static boolean isOneOffCommand(OptionAccessor input) {
        input.help || input.version || input.launch4j || input.clearIconCache
    }

    static void executeCommand(OptionAccessor input) {
        if (input.help) {
            new InputParser().usage()
        } else if (input.version) {
            log.info("Version: ${PropertiesReader.readAppProperty("version")}")
        } else if (input.launch4j) {
            log.info("Opening Launch4j GUI.")

            AppConfig appConfigWithAllDefaultValues = new InputTransformer().transformIntoAppConfig(new OptionAccessor(new CommandLine()))
            File launch4jExe = ResourceHandlerFactory
                    .makeGroovyScriptResourceHandler(appConfigWithAllDefaultValues)
                    .findFileInLaunch4jDir("launch4j.exe")

            String launch4jGuiCommand = "cmd /c \"$launch4jExe.absolutePath\""
            log.debug("Opening Launch4j GUI with '$launch4jGuiCommand'")
            launch4jGuiCommand.execute()
        } else if (input.clearIconCache){
            log.info("Clearing current user's icon cache.")

            String clearIconCacheCommand = "cmd /c del %userprofile%/AppData/Local/IconCache.db /a"
            log.debug("Clearing the current user's icon cache with '$clearIconCacheCommand'")
            clearIconCacheCommand.execute().waitForOrKill(2000)

            String restartWindowsExplorerCommand = "cmd /c taskkill /f /im explorer.exe && explorer.exe"
            log.debug("Restarting Windows Explorer via with '$restartWindowsExplorerCommand'")
            restartWindowsExplorerCommand.execute()
        }
    }

}
