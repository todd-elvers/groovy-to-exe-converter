package groovyToExeConverter.core.groovyToJar.core
import groovy.util.logging.Log4j
import groovyToExeConverter.model.exception.CompilationException

@Log4j
class JarBuilder {
    private final GROOVY_HOME = new File(System.getenv('GROOVY_HOME'))
    private final ANT = new AntBuilder()

    File destFile
    String mainClass

    File build() {
        log.debug("Building '${destFile}'...")

        try {
            disableAntLoggingToConsole()
            buildJar()
        } catch (all) {
            throw new CompilationException("Ant was unable to build a .jar file from the given input.", all.cause)
        }

        log.debug("Build successful.")
        return destFile
    }

    private disableAntLoggingToConsole() {
        ANT.getProject().getBuildListeners()[0].setMessageOutputLevel(0)
    }

    private File buildJar() {
        ANT.jar(destfile: destFile, compress: true, index: true) {
            fileset(dir: destFile.parent, includes: mainClass + '*.class')

            zipgroupfileset(dir: GROOVY_HOME, includes: 'embeddable/groovy-all-*.jar')
            zipgroupfileset(dir: GROOVY_HOME, includes: 'lib/commons*.jar')

            manifest {
                attribute(name: 'Main-Class', value: mainClass)
            }
        }

        return destFile
    }

}
