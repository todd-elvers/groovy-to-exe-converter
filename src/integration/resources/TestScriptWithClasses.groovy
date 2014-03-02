public class A {
    def someClosure = {}
    def someMethod() {}
    def notMainMethod() {}
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
}
