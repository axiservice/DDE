JDDE is JNI based FREE open source Java library which allows Java applications 
to communicate with native applications on Windows platform via Dynamic Data Exchange (DDE) protocol. 

Internet site:
http://jdde.pretty-tools.com/

----------------------------------------------------------------------------------------------------

Library is distributed under Apache License, Version 2.0.


Download binaries:
pretty-tools-JDDE-2.1.0.zip (77 kbyte)

Download javadoc:
pretty-tools-JDDE-2.1.0-javadoc.jar (37 kbyte)

Download examples:
pretty-tools-JDDE-2.1.0-examples.zip (5 kbyte)

Download source code:
pretty-tools-JDDE-2.1.0-src.zip (40 kbyte)

The latest source code is available on SourceForge.net
svn checkout http://svn.code.sf.net/p/jdde/code/ jdde-code

Related 3rd party projects:
A Clojure library to interoperate with DDE sources created by tuddman at https://github.com/tuddman/clj-dde. 


----------------------------------------------------------------------------------------------------

Examples:
Requesting data from Excel, change Excel cells, and executing commands.
Subscribe to Excel cells modification
Java DDE Server
DDE Request
DDE Poke
DDE Execute
DDE Advice


----------------------------------------------------------------------------------------------------
See also:
How to avoid java.lang.UnsatisfiedLinkError

Exception in thread "main" java.lang.UnsatisfiedLinkError: no JavaDDE in java.library.path
 at java.lang.ClassLoader.loadLibrary(Unknown Source)
 at java.lang.Runtime.loadLibrary0(Unknown Source)
 at java.lang.System.loadLibrary(Unknown Source)
 at com.pretty_tools.dde.client.DDEClientConversation.(DDEClientConversation.java:384)
 at ExcelExample.main(ExcelExample.java:21)

JDDE is JNI based library and it requires native code library (DLL for Windows). JavaDDE.dll should be placed to current directory from which you run your example, or you should specify JVM java.library.path parameter that points for folder with JavaDDE.dll.
Example:

 java -Djava.library.path="C:\jdde" ExcelExample


