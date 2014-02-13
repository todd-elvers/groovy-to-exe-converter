package groovyToExeConverter

import org.junit.Test

//TODO: Move these to a /src/integration/groovy like directory
//TODO: Update gradle file to recognize integration files
class GroovyToExeConverterRunnerTest {

    private final USER_DESKTOP = new File(System.getenv("USERPROFILE"), "Desktop")
    private final g2exeTemp = new File(USER_DESKTOP, "g2exe_temp")


    @Test
    void "can convert groovy script to an executable file"() {
        def groovyScriptFile = new File(g2exeTemp, "Example.groovy")
        String[] args = [
                '-f', groovyScriptFile.absolutePath,
                '--stacktrace'
        ]

        GroovyToExeConverterRunner.main(args)

        assert new File(g2exeTemp, "Example.exe").exists(): "Groovy -> Exe conversion failed."
    }

    @Test
    void "can convert groovy script to an executable file with heap size parameters"() {
        def groovyScriptFile = new File(g2exeTemp, "Example.groovy")
        String[] args = [
                '-f', 'C:/users/todd/desktop/g2exe_temp/Example.groovy',
                '-i', 'C:/users/todd/desktop/g2exe_temp/carfox_pilgrim.ico',
                '-xms', 1024,
                '-xmx', 2048
        ]

        GroovyToExeConverterRunner.main(args)

        assert new File(g2exeTemp, "Example.exe").exists(): "Groovy -> Exe conversion failed."
    }

    @Test
    void "can convert jar file to an executable file"() {
        def jarFile = new File(g2exeTemp, "Example2.jar")
        String[] args = [
                '--fileToConvert', jarFile.absolutePath,
                '-xmx', 1200,
                '--minJre', '1.6.0',
                '--icon', 'C:/Users/Todd/Desktop/g2exe_temp_old/cfx_icon.ico'
        ]

        GroovyToExeConverterRunner.main(args)

        assert new File(g2exeTemp, "Example2.exe").exists(): "Jar -> Exe conversion failed."
    }
}
