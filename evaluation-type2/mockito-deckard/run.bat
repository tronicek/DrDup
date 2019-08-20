set DECKARD20="mockito-mintokens-20.txt"
set DECKARD20xml="mockito-mintokens-20.xml"
set DECKARD50="mockito-mintokens-50.txt"
set DECKARD50xml="mockito-mintokens-50.xml"
set DECKARD_SRC_DIR="/home/ubuntu/mockito/mockito-2.2.0/src/main/"
set METHOD_FILE="mockito-methods.txt"
set SRC_DIR="/research/mockito/mockito-2.2.0/src/main"

set DRDUP_JAR="/research/projects/DrDup/dist/DrDup.jar"
set TOOL_JAR="/research/projects/EvalTool/dist/EvalTool.jar"
set SIMILARITY=100

java -ea -Xmx4G -Xss4M -jar %DRDUP_JAR% mockito4.properties
java -cp %TOOL_JAR% drdup.Separator drdup-mockito-4.xml

java -ea -Xmx4G -Xss4M -jar %DRDUP_JAR% mockito14.properties
java -cp %TOOL_JAR% drdup.Separator drdup-mockito-14.xml

java -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD20% %SIMILARITY% %DECKARD_SRC_DIR%
java -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD50% %SIMILARITY% %DECKARD_SRC_DIR%

java -cp %TOOL_JAR% nicad.Diff %DECKARD50xml% drdup-mockito-4-separated.xml deckard-50-drdup-4.xml
java -cp %TOOL_JAR% nicad.Diff drdup-mockito-14-separated.xml %DECKARD20xml% drdup-14-deckard-20.xml

java -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% deckard-50-drdup-4.xml
java -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% drdup-14-deckard-20.xml
