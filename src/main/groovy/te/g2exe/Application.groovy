package te.g2exe

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import te.g2exe.core.FileConverterFactory
import te.g2exe.input.ArgsToAppConfigProcessor
import te.g2exe.input.core.CommandLineArgsParser
import te.g2exe.input.core.OneOffCommandBuilder
import te.g2exe.model.AppConfig

//TODO: We need an overhaul on this project now that it is the flagship app on my Github
//TODO: Then create some e2e tests so we can add features & refactor we safety
//TODO: Then refactor for v2.0.0
@Slf4j
@CompileStatic
class Application implements CommandLineRunner {

    @Autowired
    ArgsToAppConfigProcessor argsToAppConfigProcessor

    @Autowired
    CommandLineArgsParser commandLineArgsParser

    @Autowired
    OneOffCommandBuilder offCommandBuilder

    @Override
    void run(String... args) {
        try {
            runApplication()
        } catch (Exception ex) {
//            ExceptionHandler.handleException(ex, appConfig, args)
        }
    }

    protected void runApplication(String... args) throws Exception {
        OptionAccessor commandLineArgs = commandLineArgsParser.parse(args)
        if(!commandLineArgs) return

        Optional<Closure> oneOffCommand = offCommandBuilder.build(commandLineArgs)
        if(oneOffCommand.isPresent()) {
            oneOffCommand.get().call()
        } else {
            AppConfig appConfig = argsToAppConfigProcessor.process(args)

            if (appConfig) {
                log.info("Converting $appConfig.fileToConvert.name into $appConfig.exeFileName...")
                FileConverterFactory.makeFileConverter(appConfig).convert()
                log.info("Conversion successful!")
            }
        }
    }

}
