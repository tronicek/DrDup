set NICAD6="mockito-consistent-0.50-minsize-6.xml"
set NICAD6d="mockito-consistent-0.50-minsize-6-dir.xml"
set NICAD16="mockito-consistent-0.05-minsize-16.xml"
set NICAD16d="mockito-consistent-0.05-minsize-16-dir.xml"
set NICAD_SRC_DIR="/home/ubuntu/mockito/mockito-2.2.0/src/main/"
set SRC_DIR="/research/mockito/mockito-2.2.0/src/main"

set DRDUP_JAR="/research/projects/DrDup/dist/DrDup.jar"
set TOOL_JAR="/research/projects/EvalTool/dist/EvalTool.jar"
set OPTIONS=-ea -Xmx8G -Xss4M

java %OPTIONS% -cp %TOOL_JAR% nicad.ChangeDir %NICAD_SRC_DIR% %NICAD6%
java %OPTIONS% -cp %TOOL_JAR% nicad.ChangeDir %NICAD_SRC_DIR% %NICAD16%

java %OPTIONS% -jar %DRDUP_JAR% mockito8.properties
java %OPTIONS% -jar %DRDUP_JAR% mockito16.properties

java %OPTIONS% -cp %TOOL_JAR% nicad.Diff %NICAD16d% drdup-mockito-8.xml nicad-consistent-16-drdup-8.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-mockito-16.xml %NICAD6d% drdup-16-nicad-consistent-6.xml

java %OPTIONS% -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% nicad-consistent-16-drdup-8.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% drdup-16-nicad-consistent-6.xml
