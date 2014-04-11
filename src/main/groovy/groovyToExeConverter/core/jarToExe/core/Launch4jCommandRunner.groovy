package groovyToExeConverter.core.jarToExe.core

import groovy.util.logging.Log4j

@Log4j
class Launch4jCommandRunner {
    String commandToExecute

    void buildCommand(File launch4jcFile, File launch4jcXmlFile){
        commandToExecute = "\"${launch4jcFile}\" \"${launch4jcXmlFile}\""
    }

    void runCommand() {
        Process command = commandToExecute.execute()
        pipeCommandOutputToConsole(command)
    }

    private static void pipeCommandOutputToConsole(Process command){
        List commandOutputLines = command.text?.split("\r\n")
        boolean commandFailed = !commandOutputLines?.any { String line -> line.contains("Successfully") }
        commandOutputLines.each { String line ->
            if (commandFailed) log.error(line.replace('launch4j: ', ''))
        }
    }
}
