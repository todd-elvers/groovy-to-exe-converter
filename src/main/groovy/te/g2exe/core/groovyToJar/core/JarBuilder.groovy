package te.g2exe.core.groovyToJar.core
import groovy.util.logging.Slf4j
import te.g2exe.model.exception.CompilationException

@Slf4j
class JarBuilder {
    private final GROOVY_HOME = new File(System.getenv('GROOVY_HOME'))
    private final AntBuilder ANT = new AntBuilder()

    File destinationFile
    String mainClass

    File build() {
        log.debug("Building '${destinationFile}'...")

        try {
            disableAntLoggingToConsole()
            buildJar()
        } catch (all) {
            throw new CompilationException("Ant was unable to build a .jar file from the given input.", all.cause)
        }

        log.debug("Build successful.")
        return destinationFile
    }

    private disableAntLoggingToConsole() {
        ANT.getProject().getBuildListeners()[0].setMessageOutputLevel(0)
    }

    private File buildJar() {
        log.info("Bundling compiled script & groovy libraries into JAR.")
        ANT.jar(destfile: destinationFile, compress: true, index: true) {
            fileset(dir: destinationFile.parent, includes: '*.class')

            zipgroupfileset(dir: GROOVY_HOME, includes: 'embeddable/groovy-all-*.jar')
            zipgroupfileset(dir: GROOVY_HOME, includes: 'lib/commons*.jar')

            manifest {
                attribute(name: 'Main-Class', value: mainClass)
            }
        }

        return destinationFile
    }

}
