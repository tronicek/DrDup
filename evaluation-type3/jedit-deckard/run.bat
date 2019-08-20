set DECKARD30="jedit-0.7-mintokens-30.txt"
set DECKARD30xml="jedit-0.7-mintokens-30.xml"
set DECKARD60="jedit-0.98-mintokens-60.txt"
set DECKARD60xml="jedit-0.98-mintokens-60.xml"
set DECKARD_SRC_DIR="/home/ubuntu/jEdit/jEdit5.3.0/"
set METHOD_FILE="jedit-methods.txt"
set SRC_DIR="/research/jEdit5.3.0/src"

set DRDUP_JAR="/research/projects/DrDup/dist/DrDup.jar"
set TOOL_JAR="/research/projects/EvalTool/dist/EvalTool.jar"
set OPTIONS=-ea -Xmx8G -Xss4M

java %OPTIONS% -jar %DRDUP_JAR% jedit6-type2.properties

java %OPTIONS% -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD30% 70 %DECKARD_SRC_DIR%
java %OPTIONS% -cp %TOOL_JAR% deckard.Deckard2NiCadConvertor %METHOD_FILE% %DECKARD60% 98 %DECKARD_SRC_DIR%

java %OPTIONS% -jar %DRDUP_JAR% jedit6.properties
java %OPTIONS% -jar %DRDUP_JAR% jedit10.properties

java %OPTIONS% -cp %TOOL_JAR% nicad.Diff %DECKARD60xml% drdup-jedit-6.xml deckard-60-drdup-6.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-jedit-10.xml %DECKARD30xml% drdup-10-deckard-30.xml

java %OPTIONS% -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% deckard-60-drdup-6.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% drdup-10-deckard-30.xml
