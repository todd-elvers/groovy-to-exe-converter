package groovyToExeConverter.domain

enum AppConfigDefaults {
    G2EXE_TEMP_DIR_NAME("g2exe"),
    MIN_JRE_VERSION("1.5.0"),
    INITIAL_HEAP_SIZE(128),
    MAXIMUM_HEAP_SIZE(512),
    SHOW_STACKTRACE(false),
    TEMP_DIR_PATH(new File(System.getenv("TEMP")).absolutePath),
    ICON_RESOURCE_FILE_NAME("g2exeIcon.ico"),
    LAUNCH4JC_RESOURCES([
            'launch4j/launch4jc.exe',
            'launch4j/launch4j.jar',
            'launch4j/lib/ant.jar',
            'launch4j/lib/commons-beanutils.jar',
            'launch4j/lib/commons-logging.jar',
            'launch4j/lib/forms.jar',
            'launch4j/lib/formsrt.jar',
            'launch4j/lib/foxtrot.jar',
            'launch4j/lib/looks.jar',
            'launch4j/lib/xstream.jar',
            'launch4j/w32api/crt2.o',
            'launch4j/w32api/libadvapi32.a',
            'launch4j/w32api/libgcc.a',
            'launch4j/w32api/libkernel32.a',
            'launch4j/w32api/libmingw32.a',
            'launch4j/w32api/libmsvcrt.a',
            'launch4j/w32api/libshell32.a',
            'launch4j/w32api/libuser32.a',
            'launch4j/head/consolehead.o',
            'launch4j/head/guihead.o',
            'launch4j/head/head.o',
            'launch4j/bin/ld.exe',
            'launch4j/bin/windres.exe'
    ])





    final defaultValue

    AppConfigDefaults(defaultValue) {
        this.defaultValue = defaultValue
    }

    String toString() {
        defaultValue.toString()
    }
}
