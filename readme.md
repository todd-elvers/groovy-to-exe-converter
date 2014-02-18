#g2exe

This application attempts to make utilization of [Launch4j](https://github.com/mirror/launch4j) easier by abstracting away all of Launch4j's configuration to a simple, parameterizable executable file, namely **g2exe.exe**.

##How it works

This application wraps **.groovy** scripts and **.jar** files to generate an executable file that will automatically resolve java's installation directory of the PC it's running on (and handle the case of when java is not installed).

####Java Installation Scanning
Launch4j performs the resolution of the java install directory via registry scanning, meaning java installations to custom directories will also be found.  When an executable generated by Launch4j runs, it scans the registry for an acceptable JRE version.  If one is found, the application utilizes it for execution.  If one is not found, an error message appears with a link to the [Java Download Page](http://java.com/download).

## Installation
Build g2exe your self:

1. Clone the repository
2. Navigate to the repository's directory and execute:
```
gradlew buildExe
```
If the gradle task `buildExe` was successful, g2exe.exe will be created on your desktop

Alternatively, you could download the latest g2exe.exe file from: [insert-download-link-here].

##Usage
Add g2exe.exe to your system's path or navigate to its directory and run:

```
g2exe -f file
```

where  `file` is the fully qualified path to the .groovy script or .jar file you want to convert to an executable.


####Parameters
g2exe supports setting:

- Minimum acceptable JRE version 
- Icon to embed into the executable file
- The destination directory
- The temporary directory used for g2exe resources
- The JVM initial heap size
- The JVM maximum heap size

For more information on the available parameters, or their default values, run:

```g2exe --help```.
