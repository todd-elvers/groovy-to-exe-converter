public class A {
    def someClosure = {}
    def someMethod() {}
    def notMainMethod() {}
    private String main() {}
}

class B {
    def someClosure = {}
    def someMethod() {}
    def notMainMethod() {}
    static void main(args) {}
    public String someMethod(param1, param2) {}
}

abstract class C {
    def doStuff() {}
    def notMainMethod() {}
    public static String main(args) {}
}
