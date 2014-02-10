package groovyToExeConverter.domain

enum AppConfigDefaults {
    DEFAULT_LAUNCH4JC_RESOURCES([
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
    ]),
    SHOW_STACKTRACE(false),
    DEFAULT_PERMISSIONS_ERROR_MESSAGE("You may have insufficient privileges to write to this system's temporary directory.  " +
            "Use --tempDir <directory> to override the directory used to store temporary files."),
    DEFAULT_TEMP_DIR_NAME("g2exe"),
    TEMP_DIR_PATH(new File(System.getenv("TEMP"), DEFAULT_TEMP_DIR_NAME.defaultValue as String))

    final defaultValue

    AppConfigDefaults(defaultValue){
        this.defaultValue = defaultValue
    }

    String toString(){
        defaultValue.toString()
    }

//    DEFAULT_

//    xmlBuilder.launch4jConfig {
//        dontWrapJar(false)
//        /*  Use <headerType> 'console' if your application prints values to a terminal  */
//        headerType('console')
//        jar(jarFile.absolutePath)
//        outfile(exeFile.absolutePath)
//        /*  Title for java-not-found errors popups  */
//        errTitle('HelloWorld Error')
//        /*  Optional. Constant command line arguments   */
//        cmdLine()
//        /*  Optional. Change current directory to an arbitrary path relative to the executable  */
//        chdir()
//        priority('normal')
//        downloadUrl('http://java.com/download')
//        supportUrl()
//
//        /*  Optional. Defaults to false in GUI header, always true in CONSOLE header.
//            Makes launcher wait for app to close and returns exit code. */
////            stayAlive(false)
//
//        /*  Unsure about this argument  */
//        manifest()
//
//        /*  Required.  Application icon in ICO format. May contain multiple color depths/resolutions.  */
//        icon('C:/Users/Todd/Desktop/g2exe_temp/cfx_icon.ico')
//
//        /*  Optional.  Used for custom header files.  Ordered list of header object files.    */
////            obj()
////            obj()
//
//        /*  Optional.  Used for custom header files.  Ordered list of libraries used by header.    */
////            lib()
////            lib()
//
//        /*  Optional.  Allows only one instance of application to run on the system.    */
////            singleInstance(){
////                mutexName()       /*  Unique mutex name that will identify the application */
////            }
//
//        /*  <classPath> parent element cannot exist if child element <mainClass> is not populated    */
////            classPath(){
////                mainClass()
////                cp()
////                cp()
////            }
//        jre() {
//            path('C:/Program Files/Java/jre7')
//            bundledJre64Bit(true)
//            minVersion()
//            maxVersion()
//            jdkPreference('preferJre')
//            /* These values are in MB */
//            initialHeapSize(2048)
//            maxHeapSize(2048)
//        }
//        /* Optional. Splash screen settings. Allowed only in GUI header. */
////            splash() {
////                file('C:/Users/Todd/Desktop/ScriptJarToExeConverter/application/gbl_logo_carfaxBIG09.bmp')
////                waitForWindow(true)
////                timeout(60)
////                timeoutErr(true)
////            }
//    }
}