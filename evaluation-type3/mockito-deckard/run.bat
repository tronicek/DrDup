set DECKARD30="mockito-0.7-mintokens-30.txt"
set DECKARD30xml="mockito-0.7-mintokens-30.xml"
set DECKARD60="mockito-0.98-mintokens-60.txt"
set DECKARD60xml="mockito-0.98-mintokens-60.xml"
set DECKARD_SRC_DIR="/home/ubuntu/mockito/mockito-2.2.0/src/main/"
set SRC_DIR="/research/mockito/mockito-2.2.0/src/main"
set METHOD_FILE="mockito-methods.txt"

set DRDUP_JAR="/research/projects/DrDup/dist/DrDup.jar"
set TOOL_JAR="/research/projects/EvalTool/dist/EvalTool.jar"
set OPTIONS=-ea -Xmx8G -Xss4M

java %OPTIONS% -jar %DRDUP_JAR% mockito6-type2.properties

java %OPTIONS% -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD30% 70 %DECKARD_SRC_DIR%
java %OPTIONS% -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD60% 98 %DECKARD_SRC_DIR%

java %OPTIONS% -jar %DRDUP_JAR% mockito6.properties
java %OPTIONS% -jar %DRDUP_JAR% mockito10.properties

java %OPTIONS% -cp %TOOL_JAR% nicad.Diff %DECKARD60xml% drdup-mockito-6.xml deckard-60-drdup-6.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-mockito-10.xml %DECKARD30xml% drdup-10-deckard-30.xml

java %OPTIONS% -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% deckard-60-drdup-6.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% drdup-10-deckard-30.xml
