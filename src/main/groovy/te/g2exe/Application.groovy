package te.g2exe

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import te.g2exe.core.FileConverterFactory
import te.g2exe.input.OptionsToAppConfigProcessor
import te.g2exe.input.core.ArgsToOptionsParser
import te.g2exe.input.core.LoggerHandler
import te.g2exe.input.core.OneOffCommandBuilder
import te.g2exe.model.AppConfig

@Slf4j
@Service
class Application {

    @Autowired LoggerHandler loggerHandler
    @Autowired OneOffCommandBuilder offCommandBuilder
    @Autowired OptionsToAppConfigProcessor optionsToAppConfigProcessor

    void run(OptionAccessor options) throws Exception {
        loggerHandler.changeLogLevelIfNecessary(options)

        Optional<Closure> oneOffCommand = offCommandBuilder.tryToBuild(options)
        if(oneOffCommand.isPresent()) {
            oneOffCommand.get().call()
        } else {
            runApplication(options)
        }
    }

    private void runApplication(OptionAccessor options) {
        AppConfig appConfig = optionsToAppConfigProcessor.process(options)

        log.info("Converting $appConfig.fileToConvert.name into $appConfig.exeFileName...")
        FileConverterFactory.makeFileConverter(appConfig).convert()
        log.info("Conversion successful!")
    }

}
