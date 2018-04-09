package te.g2exe.model

import groovy.transform.TupleConstructor

//TODO: Change this to something simpler.  Maybe store Launch4j as ZIP inside JAR and extract it.
@TupleConstructor
enum ResourceFileNames {
    G2EXE_RESOURCES(["gradle.properties", "g2exeIcon.ico"]),
    LAUNCH4JC_RESOURCES([
            'launch4j/launch4j.exe',
            'launch4j/launch4jc.exe',
            'launch4j/launch4j.jar',

            'launch4j/lib/ant.jar',
            'launch4j/lib/commons-beanutils.jar',
            'launch4j/lib/commons-logging.jar',
            'launch4j/lib/formsrt.jar',
            'launch4j/lib/foxtrot.jar',
            'launch4j/lib/jgoodies-common.jar',
            'launch4j/lib/jgoodies-forms.jar',
            'launch4j/lib/jgoodies-looks.jar',
            'launch4j/lib/xstream.jar',

            'launch4j/w32api/crt2.o',
            'launch4j/w32api/libadvapi32.a',
            'launch4j/w32api/libgcc.a',
            'launch4j/w32api/libkernel32.a',
            'launch4j/w32api/libmingw32.a',
            'launch4j/w32api/libmsvcrt.a',
            'launch4j/w32api/libshell32.a',
            'launch4j/w32api/libuser32.a',

            'launch4j/w32api_jni/crt2.o',
            'launch4j/w32api_jni/libadvapi32.a',
            'launch4j/w32api_jni/libgcc.a',
            'launch4j/w32api_jni/libkernel32.a',
            'launch4j/w32api_jni/libmingw32.a',
            'launch4j/w32api_jni/libmingwex.a',
            'launch4j/w32api_jni/libmoldname.a',
            'launch4j/w32api_jni/libmsvcrt.a',
            'launch4j/w32api_jni/libshell32.a',
            'launch4j/w32api_jni/libuser32.a',

            // TODO: See where/how these are used in launch4j.  Maybe we don't need them till we add a feature to use them w/ launch4j?
            'launch4j/sign4j/sign4j.c',
            'launch4j/sign4j/sign4j.exe',

            'launch4j/head/consolehead.o',
            'launch4j/head/guihead.o',
            'launch4j/head/head.o',

            'launch4j/bin/ld.exe',
            'launch4j/bin/windres.exe'
    ])

    final List<String> fileNames

    static List<String> asFlattenedList() {
        return values().fileNames.flatten()
    }
}