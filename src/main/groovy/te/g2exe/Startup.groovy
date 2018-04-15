package te.g2exe

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import te.g2exe.input.core.ArgsToOptionsParser

@CompileStatic
@SpringBootApplication(scanBasePackages = ["te.g2exe"])
class Startup implements CommandLineRunner {

    static void main(String[] args) { SpringApplication.run(Application, args) }

    @Autowired Application application
    @Autowired ArgsToOptionsParser argsToOptionsParser

    @Override
    void run(String... args) throws Exception {
        argsToOptionsParser
                .parse(args)
                .ifPresent({ options -> application.run(options) })
    }
}
