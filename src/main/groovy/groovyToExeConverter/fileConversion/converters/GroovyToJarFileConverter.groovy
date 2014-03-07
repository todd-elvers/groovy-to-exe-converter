package groovyToExeConverter.fileConversion.converters
import groovy.util.logging.Log4j
import groovyToExeConverter.exception.CompilationException
import groovyToExeConverter.fileConversion.FileConverter

import static groovyToExeConverter.fileConversion.converterHelpers.groovyScript.GroovyScriptCompiler.compileGroovyScript
import static groovyToExeConverter.fileConversion.converterHelpers.groovyScript.GroovyScriptMainMethodFinder.findNameOfClassWithMainMethod

@Log4j
class GroovyToJarFileConverter extends FileConverter {

    final ANT = new AntBuilder()

    @Override
    File convert() {
        log.info("${appConfig.fileToConvert.name} --> ${appConfig.jarFileName}")

        def groovyScript = resourceHandler.copyFileToTempDir(appConfig.fileToConvert)
        compileGroovyScript(groovyScript, appConfig.temporaryDirectory)
        buildJarThenReturnHandle()
    }

    private File buildJarThenReturnHandle() {
        log.debug("Building '${appConfig.jarFileName}'...")

        def jarFile
        try {
            tellAntNotToOutputAnything()
            jarFile = buildJar()
        } catch (all) {
            throw new CompilationException("Build failed - unable to compile '${appConfig.fileToConvert.name}' into a .jar file.", all.cause)
        }

        log.debug("Build successful.")
        return jarFile
    }

    private tellAntNotToOutputAnything(){
        ANT.getProject().getBuildListeners()[0].setMessageOutputLevel(0)
    }

    private File buildJar(){
        final GROOVY_HOME = new File(System.getenv('GROOVY_HOME'))
        def jarFile = resourceHandler.createFileInTempDir(appConfig.jarFileName)
        def mainClassName = findNameOfClassWithMainMethod(appConfig.fileToConvert, appConfig.temporaryDirectory)

        log.debug("Dynamically detected '$mainClassName' as main-class.")

        ANT.jar(destfile: jarFile, compress: true, index: true) {
            fileset(dir: jarFile.parent, includes: mainClassName + '*.class')

            zipgroupfileset(dir: GROOVY_HOME, includes: 'embeddable/groovy-all-*.jar')
            zipgroupfileset(dir: GROOVY_HOME, includes: 'lib/commons*.jar')

            manifest {
                attribute(name: 'Main-Class', value: mainClassName)
            }
        }

        return jarFile
    }

}
