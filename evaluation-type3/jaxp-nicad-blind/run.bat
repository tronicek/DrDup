set NICAD5="jaxp-blind-0.50-minsize-5.xml"
set NICAD5d="jaxp-blind-0.50-minsize-5-dir.xml"
set NICAD12="jaxp-blind-0.03-minsize-12.xml"
set NICAD12d="jaxp-blind-0.03-minsize-12-dir.xml"
set NICAD_SRC_DIR="/home/ubuntu/jaxp/src/"
set SRC_DIR="/research/OpenJDK/jaxp/src"

set DRDUP_JAR="/research/projects/DrDup/dist/DrDup.jar"
set TOOL_JAR="/research/projects/EvalTool/dist/EvalTool.jar"
set OPTIONS=-ea -Xmx8G -Xss4M

java %OPTIONS% -cp %TOOL_JAR% nicad.ChangeDir %NICAD_SRC_DIR% %NICAD5%
java %OPTIONS% -cp %TOOL_JAR% nicad.ChangeDir %NICAD_SRC_DIR% %NICAD12%

java %OPTIONS% -jar %DRDUP_JAR% jaxp6.properties
java %OPTIONS% -jar %DRDUP_JAR% jaxp12.properties

java %OPTIONS% -cp %TOOL_JAR% nicad.Diff %NICAD12d% drdup-jaxp-6.xml nicad-blind-12-drdup-6.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-jaxp-12.xml %NICAD5d% drdup-12-nicad-blind-5.xml

java %OPTIONS% -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% nicad-blind-12-drdup-6.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% drdup-12-nicad-blind-5.xml
