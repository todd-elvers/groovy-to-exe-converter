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

        String[] args = ['-f', groovyScriptFile.absolutePath, '--stacktrace']
        GroovyToExeConverterRunner.main(args)
        assert new File(g2exeTemp, "Example.exe").exists() : "Groovy -> Exe conversion failed."
    }

    @Test
    void "can convert jar file to an executable file"() {
        def jarFile = new File(g2exeTemp, "Example2.jar")

        String[] args = ['-f', jarFile.absolutePath]
        GroovyToExeConverterRunner.main(args)
        assert new File(g2exeTemp, "Example2.exe").exists() : "Jar -> Exe conversion failed."
    }

//    @Test
//    void "Does show stacktrack when user inputs --stacktrace"() {
//        def script = new File(g2exeTemp, "Example2.jar")
//
//        String[] args = ['-f', script.absolutePath, '--stacktrace']
//        GroovyToExeConverterRunner.main(args)
//        assert new File(g2exeTemp, "Example2.exe").exists() : "Jar -> Exe conversion failed."
//    }

}
