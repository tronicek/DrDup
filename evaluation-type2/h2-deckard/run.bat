set DECKARD30="h2-mintokens-30.txt"
set DECKARD30xml="h2-mintokens-30.xml"
set DECKARD60="h2-mintokens-60.txt"
set DECKARD60xml="h2-mintokens-60.xml"
set DECKARD_SRC_DIR="/home/ubuntu/h2/src/main/"
set METHOD_FILE="h2-methods.txt"
set SRC_DIR="/research/h2/h2database-version-1.4.196/h2/src/main"

set DRDUP_JAR="/research/projects/DrDup/dist/DrDup.jar"
set TOOL_JAR="/research/projects/EvalTool/dist/EvalTool.jar"
set SIMILARITY=100

java -ea -Xmx4G -Xss4M -jar %DRDUP_JAR% h2-5.properties
java -cp %TOOL_JAR% drdup.Separator drdup-h2-5.xml

java -ea -Xmx4G -Xss4M -jar %DRDUP_JAR% h2-15.properties
java -cp %TOOL_JAR% drdup.Separator drdup-h2-15.xml

java -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD30% %SIMILARITY% %DECKARD_SRC_DIR%
java -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD60% %SIMILARITY% %DECKARD_SRC_DIR%

java -cp %TOOL_JAR% nicad.Diff %DECKARD60xml% drdup-h2-5-separated.xml deckard-60-drdup-5.xml
java -cp %TOOL_JAR% nicad.Diff drdup-h2-15-separated.xml %DECKARD30xml% drdup-15-deckard-30.xml

java -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% deckard-60-drdup-5.xml
java -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% drdup-15-deckard-30.xml
