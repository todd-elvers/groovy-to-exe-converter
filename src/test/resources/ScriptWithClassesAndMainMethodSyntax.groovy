class ClassOne {

    static final ClassThree CLASS_THREE = new ClassThree()

    public static void main(String[] args) {
        new ClassOne().print()
        new ClassTwo().print()
        CLASS_THREE.print()
    }

    void print() {
        println "Hello World - ClassOne successfully instantiated."
    }
}

class ClassTwo {
    void print() {
        println "Hello World - ClassTwo successfully instantiated."
    }
}

class ClassThree {
    void print() {
        println "Hello World - ClassThree successfully instantiated."
    }
}