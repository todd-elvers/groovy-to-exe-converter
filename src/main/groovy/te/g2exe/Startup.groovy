package te.g2exe

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@CompileStatic
@SpringBootApplication(scanBasePackages = ["te.g2exe"])
class Startup {

    static void main(String[] args) { SpringApplication.run(Application, args) }

}
