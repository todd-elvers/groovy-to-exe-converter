package groovyToExeConverter

import org.junit.Test

//TODO: Move these to a /src/integration/groovy like directory
//TODO: Update gradle file to recognize integration files
//TODO: Figure out how to handle resources in these tests without duplicating the /launch4j/ folder
//TODO: Write tests for most important classes w/ Spock
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

        File expectedExeFile = new File(g2exeTemp, "Example.exe")
        expectedExeFile.delete()

        GroovyToExeConverterRunner.main(args)

        assert expectedExeFile.exists(): "Groovy -> Exe conversion failed."
    }

    @Test
    void "can convert groovy script to an executable file with heap size parameters"() {
        String[] args = [
                '-f', new File(g2exeTemp, 'Example.groovy').absolutePath,
                '-i', new File(g2exeTemp, 'carfox_pilgrim.ico').absolutePath,
                '-xms', 1024,
                '-xmx', 2048
        ]

        File expectedExeFile = new File(g2exeTemp, "Example.exe")
        expectedExeFile.delete()

        GroovyToExeConverterRunner.main(args)

        assert expectedExeFile.exists(): "Groovy -> Exe conversion failed."
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

        File expectedExeFile = new File(g2exeTemp, "Example2.exe")
        expectedExeFile.delete()

        GroovyToExeConverterRunner.main(args)

        assert expectedExeFile.exists(): "Jar -> Exe conversion failed."
    }

    @Test
    void "show help"() {
        String[] args = ['--help']

        GroovyToExeConverterRunner.main(args)

        assert true
    }
}
