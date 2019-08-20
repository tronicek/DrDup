set NICAD3="mockito-consistent-minsize-3.xml"
set NICAD3d="mockito-consistent-minsize-3-dir.xml"
set NICAD8="mockito-consistent-minsize-8.xml"
set NICAD8d="mockito-consistent-minsize-8-dir.xml"
set NICAD_SRC_DIR="/home/ubuntu/mockito/mockito-2.2.0/src/main/"
set SRC_DIR="/research/mockito/mockito-2.2.0/src/main"

set DRDUP_JAR="/research/projects/DrDup/dist/DrDup.jar"
set TOOL_JAR="/research/projects/EvalTool/dist/EvalTool.jar"

java -cp %TOOL_JAR% nicad.ChangeDir %NICAD_SRC_DIR% %NICAD3%
java -cp %TOOL_JAR% nicad.ChangeDir %NICAD_SRC_DIR% %NICAD8%

java -ea -Xmx4G -Xss4M -jar %DRDUP_JAR% mockito3.properties
java -cp %TOOL_JAR% drdup.Separator drdup-mockito-3.xml

java -ea -Xmx4G -Xss4M -jar %DRDUP_JAR% mockito8.properties
java -cp %TOOL_JAR% drdup.Separator drdup-mockito-8.xml

java -cp %TOOL_JAR% nicad.Diff %NICAD8d% drdup-mockito-3-separated.xml nicad-consistent-8-drdup-3.xml
java -cp %TOOL_JAR% nicad.Diff drdup-mockito-8-separated.xml %NICAD3d% drdup-8-nicad-consistent-3.xml

java -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% nicad-consistent-8-drdup-3.xml
java -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% drdup-8-nicad-consistent-3.xml
