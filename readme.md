#groovy-to-exe-converter (g2exe)

This application accepts **.groovy** scripts or **.jar** files and generates Windows executable files.  This application was written in Groovy and wraps [Launch4j](http://launch4j.sourceforge.net/), turning it into an easy-to-use command line application.

<br/>

##When could this be helpful?

1. If you have a **.groovy** script that you are planning to run on Windows machines that only have Java installed
2. If you have an executable **.jar** and need to run that jar with a specified heap size, but don't want to have to include a .bat file just to set it
3. If you are planning on running either a **.groovy** script or a **.jar** file on a Windows machine that has Java installed, but does not have the PATH configured correctly or is missing the JAVA_HOME system environment variable
4. If you have a **.groovy** script or a **.jar** file that executes as a GUI application and want to have a native Windows splash screen display until their application loaded, or have that GUI application treated like a native Windows GUI application
5. If you are looking for a tool to integrate into your build process that will generate Windows executable files from **.jar** or **.groovy** files

<br/>

##How it works
Although g2exe only exposes a subset of Launch4j's features, it also adds support for **.groovy** scripts and for a wider range of splash image formats.  Executable files created by g2exe will automatically resolve Java's installation directory of the PC it's running on (and handle the case when there isn't one).
  
####Groovy scripts

1. The script is loaded into memory by g2exe
2. The script's classes are loaded as an AST (abstract syntax tree) and scanned to determine the appropriate main-method
3. The script is compiled into a .jar file
4. The appropriate Launch4j XML is generated
5. Launch4j is called with the generated XML

####Jar files
1.  The appropriate Launch4j XML is generated
2.  Launch4j is called with the generated XML

__TIP:__ If your application is a gradle application, you can use the gradle task 'buildJar' in [build-executable.gradle](https://github.com/todd-elvers/g2exe/blob/master/build-executable.gradle) to generate an all-in-one jar for your application (be sure to omit the line containing `from('gradle.properties', 'LICENSE.txt', '/licenses')`).


####Java installation scanning
The resolution of the java installation directory is done via registry scanning, meaning even java installations to custom directories will be found.  When an executable generated by Launch4j runs, it scans the registry for an acceptable JRE version.  If one is found, the application utilizes it for execution.  If one is not found, an error message appears with a link to the [Java Download Page](http://java.com/download).

<br/>

## Installation
Simply download the [latest g2exe build](https://github.com/todd-elvers/g2exe/releases/download/1.2.0/g2exe.exe).

Alternatively, you could build g2exe yourself:

1. Clone the repository
2. Navigate to the repository's directory and execute: ```gradlew buildExe```

If the gradle task `buildExe` was successful, g2exe.exe will be created on your desktop.  You can override this behavior by manipulating the arguments passed into `buildExe` in [build-executable.gradle](https://github.com/todd-elvers/g2exe/blob/master/build-executable.gradle).

<br/>

##Usage
Add g2exe.exe to your system's path or navigate to its directory and run:

```g2exe -f file```

where `file` is the path (absolute or relative) to the .groovy script or .jar file you want to convert to an executable.  This is the minimum required input to run g2exe.


###Optional Parameters
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

<br/>

#####Application Type
- If the flag `--console` is provided to g2exe, the executable created will operate like a native Windows command line program: opening a command prompt window if one isn't open and printing output to the console.
- If the flag `--gui` is provided to g2exe, the executable created will operate like a native Windows GUI program: not opening a command prompt window, and not printing output to the console (this flag also allows the use of a splash screen via `--splash <image-file>`)

<br/>

#####Known Issues
This application cannot handle scripts which use `@Grab`. 

<br/>

##Launch4j
All credit for the [Launch4j source code](https://sourceforge.net/projects/launch4j) goes to [Grzegorz Kowal](http://sourceforge.net/u/grzegok/profile/).

<br/>

##License
This application has been released under the MIT License (MIT).
