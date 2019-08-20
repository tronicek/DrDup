set DECKARD30="jaxp-mintokens-30.txt"
set DECKARD30xml="jaxp-mintokens-30.xml"
set DECKARD60="jaxp-mintokens-60.txt"
set DECKARD60xml="jaxp-mintokens-60.xml"
set DECKARD_SRC_DIR="/home/tronicek/jaxp/src/"
set METHOD_FILE="jaxp-methods.txt"
set SRC_DIR="/research/OpenJDK/jaxp/src"

set DRDUP_JAR="/research/projects/DrDup/dist/DrDup.jar"
set TOOL_JAR="/research/projects/EvalTool/dist/EvalTool.jar"
set SIMILARITY=100

java -ea -Xmx4G -Xss4M -jar %DRDUP_JAR% jaxp5.properties
java -cp %TOOL_JAR% drdup.Separator drdup-jaxp-5.xml

java -ea -Xmx4G -Xss4M -jar %DRDUP_JAR% jaxp15.properties
java -cp %TOOL_JAR% drdup.Separator drdup-jaxp-15.xml

java -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD30% %SIMILARITY% %DECKARD_SRC_DIR%
java -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD60% %SIMILARITY% %DECKARD_SRC_DIR%

java -cp %TOOL_JAR% nicad.Diff %DECKARD60xml% drdup-jaxp-5-separated.xml deckard-60-drdup-5.xml
java -cp %TOOL_JAR% nicad.Diff drdup-jaxp-15-separated.xml %DECKARD30xml% drdup-15-deckard-30.xml

java -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% deckard-60-drdup-5.xml
java -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% drdup-15-deckard-30.xml
