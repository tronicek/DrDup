set DECKARD50="h2-0.7-mintokens-50.txt"
set DECKARD50xml="h2-0.7-mintokens-50.xml"
set DECKARD100="h2-0.98-mintokens-100.txt"
set DECKARD100xml="h2-0.98-mintokens-100.xml"
set DECKARD_SRC_DIR="/home/ubuntu/h2/src/main/"
set SRC_DIR="/research/h2/h2database-version-1.4.196/h2/src/main"
set METHOD_FILE="h2-methods.txt"

set DRDUP_JAR="/research/projects/DrDup/dist/DrDup.jar"
set TOOL_JAR="/research/projects/EvalTool/dist/EvalTool.jar"
set OPTIONS=-ea -Xmx8G -Xss4M

java %OPTIONS% -jar %DRDUP_JAR% h2-6-type2.properties

java %OPTIONS% -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD50% 70 %DECKARD_SRC_DIR%
java %OPTIONS% -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD100% 98 %DECKARD_SRC_DIR%

java %OPTIONS% -jar %DRDUP_JAR% h2-6.properties
java %OPTIONS% -jar %DRDUP_JAR% h2-16.properties

java %OPTIONS% -cp %TOOL_JAR% nicad.Diff %DECKARD100xml% drdup-h2-6.xml deckard-100-drdup-6.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-h2-16.xml %DECKARD50xml% drdup-16-deckard-50.xml

java %OPTIONS% -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% deckard-100-drdup-6.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% drdup-16-deckard-50.xml
