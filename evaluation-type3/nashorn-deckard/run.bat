set DECKARD50="nashorn-0.7-mintokens-50.txt"
set DECKARD50xml="nashorn-0.7-mintokens-50.xml"
set DECKARD100="nashorn-0.98-mintokens-100.txt"
set DECKARD100xml="nashorn-0.98-mintokens-100.xml"
set DECKARD_SRC_DIR="/home/ubuntu/nashorn/src/"
set SRC_DIR="/research/OpenJDK/nashorn/src"
set METHOD_FILE="nashorn-methods.txt"

set DRDUP_JAR="/research/projects/DrDup/dist/DrDup.jar"
set TOOL_JAR="/research/projects/EvalTool/dist/EvalTool.jar"
set OPTIONS=-ea -Xmx8G -Xss4M

java %OPTIONS% -jar %DRDUP_JAR% nashorn6-type2.properties

java %OPTIONS% -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD50% 70 %DECKARD_SRC_DIR%
java %OPTIONS% -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD100% 98 %DECKARD_SRC_DIR%

java %OPTIONS% -jar %DRDUP_JAR% nashorn6.properties
java %OPTIONS% -jar %DRDUP_JAR% nashorn16.properties

java %OPTIONS% -cp %TOOL_JAR% nicad.Diff %DECKARD100xml% drdup-nashorn-6.xml deckard-100-drdup-6.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-nashorn-16.xml %DECKARD50xml% drdup-16-deckard-50.xml

java %OPTIONS% -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% deckard-100-drdup-6.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% drdup-16-deckard-50.xml
