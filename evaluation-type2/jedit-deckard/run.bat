set DECKARD20="jedit-mintokens-20.txt"
set DECKARD20xml="jedit-mintokens-20.xml"
set DECKARD50="jedit-mintokens-50.txt"
set DECKARD50xml="jedit-mintokens-50.xml"
set DECKARD_SRC_DIR="/home/tronicek/jEdit/jEdit5.3.0/"
set METHOD_FILE="jedit-methods.txt"
set SRC_DIR="/research/jEdit5.3.0/src"

set DRDUP_JAR="/research/projects/DrDup/dist/DrDup.jar"
set TOOL_JAR="/research/projects/EvalTool/dist/EvalTool.jar"
set SIMILARITY=100

java -ea -Xmx4G -Xss4M -jar %DRDUP_JAR% jedit4.properties
java -cp %TOOL_JAR% drdup.Separator drdup-jedit-4.xml

java -ea -Xmx4G -Xss4M -jar %DRDUP_JAR% jedit14.properties
java -cp %TOOL_JAR% drdup.Separator drdup-jedit-14.xml

java -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD20% %SIMILARITY% %DECKARD_SRC_DIR%
java -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD50% %SIMILARITY% %DECKARD_SRC_DIR%

java -cp %TOOL_JAR% nicad.Diff %DECKARD50xml% drdup-jedit-4-separated.xml deckard-50-drdup-4.xml
java -cp %TOOL_JAR% nicad.Diff drdup-jedit-14-separated.xml %DECKARD20xml% drdup-14-deckard-20.xml

java -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% deckard-50-drdup-4.xml
java -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% drdup-14-deckard-20.xml
