Compile and run the Java_files using the lines below in Command Prompt/Terminal

For Windows:
javac -d src -cp .\jars\jfreechart-1.5.3.jar;.\jars\jdatepicker-1.3.4.jar templates/*.java *.java
java -cp .\src;.\jars\jfreechart-1.5.3.jar;.\jars\jdatepicker-1.3.4.jar Project

For Mac:
javac -d src -cp ./jars/jfreechart-1.5.3.jar:./jars/jdatepicker-1.3.4.jar templates/*.java *.java
java -cp ./src:./jars/jfreechart-1.5.3.jar:./jars/jdatepicker-1.3.4.jar Project