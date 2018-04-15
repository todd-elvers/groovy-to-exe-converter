package te.g2exe.input.core

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service
import te.g2exe.core.common.resource.ResourceHandlerFactory
import te.g2exe.model.AppConfig
import te.g2exe.util.PropertiesReader
import org.apache.commons.cli.CommandLine

@Slf4j
@Service
class OneOffCommandBuilder {
    
    Optional<Closure> tryToBuild(OptionAccessor options) {
        Closure oneOffCommand = null

        if (options.help) {
            oneOffCommand = { ->
                new ArgsToOptionsParser().usage()
            }
        } else if (options.version) {
            oneOffCommand = { ->
                log.info("Version: ${PropertiesReader.readAppProperty("version")}")
            }
        } else if (options.launch4j) {
            oneOffCommand = { ->
                log.info("Opening Launch4j GUI.")

                AppConfig appConfigWithAllDefaultValues = new OptionsToAppConfigTransformer().transform(new OptionAccessor(new CommandLine()))
                File launch4jExe = ResourceHandlerFactory
                        .makeGroovyScriptResourceHandler(appConfigWithAllDefaultValues)
                        .findFileInLaunch4jDir("launch4j.exe")

                String launch4jGuiCommand = "cmd /c \"$launch4jExe.absolutePath\""
                log.debug("Opening Launch4j GUI with '$launch4jGuiCommand'")
                launch4jGuiCommand.execute()
            }
        } else if (options.clearIconCache) {
            oneOffCommand = { ->
                log.info("Clearing current user's icon cache.")

                String clearIconCacheCommand = "cmd /c del %userprofile%/AppData/Local/IconCache.db /a"
                log.debug("Clearing the current user's icon cache with '$clearIconCacheCommand'")
                clearIconCacheCommand.execute().waitForOrKill(2000)

                String restartWindowsExplorerCommand = "cmd /c taskkill /f /im explorer.exe && explorer.exe"
                log.debug("Restarting Windows Explorer via with '$restartWindowsExplorerCommand'")
                restartWindowsExplorerCommand.execute()
            }
        }

        return Optional.ofNullable(oneOffCommand)
    }

}
