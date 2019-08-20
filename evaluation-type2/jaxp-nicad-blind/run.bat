set NICAD5="jaxp-blind-minsize-5.xml"
set NICAD5d="jaxp-blind-minsize-5-dir.xml"
set NICAD10="jaxp-blind-minsize-10.xml"
set NICAD10d="jaxp-blind-minsize-10-dir.xml"
set NICAD_SRC_DIR="/home/ubuntu/jaxp/src/"
set SRC_DIR="/research/OpenJDK/jaxp/src"

set DRDUP_JAR="/research/projects/DrDup/dist/DrDup.jar"
set TOOL_JAR="/research/projects/EvalTool/dist/EvalTool.jar"

java -cp %TOOL_JAR% nicad.ChangeDir %NICAD_SRC_DIR% %NICAD5%
java -cp %TOOL_JAR% nicad.ChangeDir %NICAD_SRC_DIR% %NICAD10%

java -ea -Xmx4G -Xss4M -jar %DRDUP_JAR% jaxp5.properties
java -cp %TOOL_JAR% drdup.Separator drdup-jaxp-5.xml

java -ea -Xmx4G -Xss4M -jar %DRDUP_JAR% jaxp10.properties
java -cp %TOOL_JAR% drdup.Separator drdup-jaxp-10.xml

java -cp %TOOL_JAR% nicad.Diff %NICAD10d% drdup-jaxp-5-separated.xml nicad-blind-10-drdup-5.xml
java -cp %TOOL_JAR% nicad.Diff drdup-jaxp-10-separated.xml %NICAD5d% drdup-10-nicad-blind-5.xml

java -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% nicad-blind-10-drdup-5.xml
java -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% drdup-10-nicad-blind-5.xml
