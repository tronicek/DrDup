set NICAD5="nashorn-consistent-minsize-5.xml"
set NICAD5d="nashorn-consistent-minsize-5-dir.xml"
set NICAD10="nashorn-consistent-minsize-10.xml"
set NICAD10d="nashorn-consistent-minsize-10-dir.xml"
set NICAD_SRC_DIR="/home/ubuntu/nashorn/src/"
set SRC_DIR="/research/OpenJDK/nashorn/src"

set DRDUP_JAR="/research/projects/DrDup/dist/DrDup.jar"
set TOOL_JAR="/research/projects/EvalTool/dist/EvalTool.jar"

java -cp %TOOL_JAR% nicad.ChangeDir %NICAD_SRC_DIR% %NICAD5%
java -cp %TOOL_JAR% nicad.ChangeDir %NICAD_SRC_DIR% %NICAD10%

java -ea -Xmx4G -Xss4M -jar %DRDUP_JAR% nashorn5.properties
java -cp %TOOL_JAR% drdup.Separator drdup-nashorn-5.xml

java -ea -Xmx4G -Xss4M -jar %DRDUP_JAR% nashorn10.properties
java -cp %TOOL_JAR% drdup.Separator drdup-nashorn-10.xml

java -cp %TOOL_JAR% nicad.Diff %NICAD10d% drdup-nashorn-5-separated.xml nicad-consistent-10-drdup-5.xml
java -cp %TOOL_JAR% nicad.Diff drdup-nashorn-10-separated.xml %NICAD5d% drdup-10-nicad-consistent-5.xml

java -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% nicad-consistent-10-drdup-5.xml
java -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% drdup-10-nicad-consistent-5.xml
