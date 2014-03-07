#g2exe

This application attempts to make utilization of [Launch4j](https://github.com/mirror/launch4j) easier by abstracting away all of Launch4j's configuration to a simple, parameterizable executable file, namely **g2exe.exe**.


##How it works

This application wraps **.groovy** scripts and **.jar** files to generate an executable file that will automatically resolve java's installation directory of the PC it's running on (and handle the case of when java is not installed).


####Java Installation Scanning
Launch4j performs the resolution of the java install directory via registry scanning, meaning java installations to custom directories will also be found.  When an executable generated by Launch4j runs, it scans the registry for an acceptable JRE version.  If one is found, the application utilizes it for execution.  If one is not found, an error message appears with a link to the [Java Download Page](http://java.com/download).


## Installation
Build g2exe yourself:

1. Clone the repository
2. Navigate to the repository's directory and execute: ```gradlew buildExe```

If the gradle task `buildExe` was successful, g2exe.exe will be created on your desktop.  You can override this behavior by manipulating the arguments passed into `buildExe` in build-executable.gradle.
 
Alternatively, you could download the latest g2exe.exe file from: [latest g2exe build](https://github.com/todd-elvers/g2exe/releases/download/1.0.0/g2exe.exe).


##Usage
Add g2exe.exe to your system's path or navigate to its directory and run:

```g2exe -f file```

where  `file` is the path (absolute or relative) to the .groovy script or .jar file you want to convert to an executable.  


#####Groovy Scripts
If input is a Groovy script, the file is loaded by g2exe and scanned to locate a class containing a main method.  This practice of main method scanning allows g2exe to handle groovy scripts containing __0__, __1__, or __n__ classes - so long as __one and only one__ of those classes has a main method.

#####Jar Files
Executable jar files are also valid input to g2exe.  __TIP:__ If your application is a gradle application, you can use the gradle task 'buildJar' in [build-executable.gradle](https://github.com/todd-elvers/g2exe/blob/master/build-executable.gradle) to generate an all-in-one jar for your application (be sure to omit the line containing `from('gradle.properties')`).

##Parameters
g2exe supports setting:

- Minimum acceptable JRE version 
- Application type (console or GUI)
- Application splash screen
- Executable file icon
- The destination directory
- The temporary directory used for g2exe resources
- The JVM initial heap size
- The JVM maximum heap size

For more information on the available parameters, or their default values, run:

```g2exe --help```

#####Application Type
- If the flag `--console` is provided to g2exe, the executable created will operate like a native Windows command line program: opening a command prompt window if one isn't open and printing output to the console.
- If the flag `--gui` is provided to g2exe, the executable created will operate like a native Windows GUI program: not opening a command prompt window, and not printing output to the console (this flag also allows the use of a splash screen via `--splash <image-file>`)
