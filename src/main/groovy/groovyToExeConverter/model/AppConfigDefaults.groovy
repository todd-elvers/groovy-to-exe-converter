package groovyToExeConverter.model

import groovy.transform.TupleConstructor

@TupleConstructor
enum AppConfigDefaults {
    G2EXE_TEMP_DIR_NAME("g2exe"),
    MIN_JRE_VERSION("1.5.0"),
    INITIAL_HEAP_SIZE(128),
    MAXIMUM_HEAP_SIZE(512),
    SHOW_STACKTRACE(false),
    TEMP_DIR_PATH(new File(System.getenv("TEMP")).absolutePath),
    CONSOLE_APP_TYPE('console'),
    GUI_APP_TYPE('gui')

    final def defaultValue

    def asType(Class clazz) {
        switch(clazz){
            case String : return defaultValue as String
            case int    : return defaultValue as int
            case boolean: return defaultValue as boolean
        }
    }
}
