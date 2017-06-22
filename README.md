DrDup is a research project on clone detection. It detects Type 2 and Type 3
clones in Java source code.

The project is coupled with Oracle Java Development Kit (JDK) 1.8 and requires 
tools.jar from this JDK on classpath:

java -cp $JAVA_HOME/lib/tools.jar -jar DrDup.jar project.properties

The project.properties file describes the project you want to analyze.
See examples in the config directory.
