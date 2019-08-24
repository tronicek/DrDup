set DRDUP_JAR="/research/projects/DrDup/dist/DrDup.jar"
set TOOL_JAR="/research/projects/EvalTool/dist/EvalTool.jar"
set OPTIONS=-ea -Xmx8G -Xss4M

java %OPTIONS% -jar %DRDUP_JAR% h2-full-compressed.properties
java %OPTIONS% -jar %DRDUP_JAR% h2-full-plain.properties
java %OPTIONS% -jar %DRDUP_JAR% h2-simplified-compressed.properties
java %OPTIONS% -jar %DRDUP_JAR% h2-simplified-plain.properties

java %OPTIONS% -jar %DRDUP_JAR% jaxp-full-compressed.properties
java %OPTIONS% -jar %DRDUP_JAR% jaxp-full-plain.properties
java %OPTIONS% -jar %DRDUP_JAR% jaxp-simplified-compressed.properties
java %OPTIONS% -jar %DRDUP_JAR% jaxp-simplified-plain.properties

java %OPTIONS% -jar %DRDUP_JAR% jedit-full-compressed.properties
java %OPTIONS% -jar %DRDUP_JAR% jedit-full-plain.properties
java %OPTIONS% -jar %DRDUP_JAR% jedit-simplified-compressed.properties
java %OPTIONS% -jar %DRDUP_JAR% jedit-simplified-plain.properties

java %OPTIONS% -jar %DRDUP_JAR% mockito-full-compressed.properties
java %OPTIONS% -jar %DRDUP_JAR% mockito-full-plain.properties
java %OPTIONS% -jar %DRDUP_JAR% mockito-simplified-compressed.properties
java %OPTIONS% -jar %DRDUP_JAR% mockito-simplified-plain.properties

java %OPTIONS% -jar %DRDUP_JAR% nashorn-full-compressed.properties
java %OPTIONS% -jar %DRDUP_JAR% nashorn-full-plain.properties
java %OPTIONS% -jar %DRDUP_JAR% nashorn-simplified-compressed.properties
java %OPTIONS% -jar %DRDUP_JAR% nashorn-simplified-plain.properties

java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-h2-full-compressed.xml drdup-h2-full-plain.xml drdup-h2-diff1.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-h2-full-plain.xml drdup-h2-simplified-compressed.xml drdup-h2-diff2.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-h2-simplified-compressed.xml drdup-h2-simplified-plain.xml drdup-h2-diff3.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-h2-simplified-plain.xml drdup-h2-full-compressed.xml drdup-h2-diff4.xml

java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-jaxp-full-compressed.xml drdup-jaxp-full-plain.xml drdup-jaxp-diff1.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-jaxp-full-plain.xml drdup-jaxp-simplified-compressed.xml drdup-jaxp-diff2.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-jaxp-simplified-compressed.xml drdup-jaxp-simplified-plain.xml drdup-jaxp-diff3.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-jaxp-simplified-plain.xml drdup-jaxp-full-compressed.xml drdup-jaxp-diff4.xml

java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-jedit-full-compressed.xml drdup-jedit-full-plain.xml drdup-jedit-diff1.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-jedit-full-plain.xml drdup-jedit-simplified-compressed.xml drdup-jedit-diff2.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-jedit-simplified-compressed.xml drdup-jedit-simplified-plain.xml drdup-jedit-diff3.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-jedit-simplified-plain.xml drdup-jedit-full-compressed.xml drdup-jedit-diff4.xml

java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-mockito-full-compressed.xml drdup-mockito-full-plain.xml drdup-mockito-diff1.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-mockito-full-plain.xml drdup-mockito-simplified-compressed.xml drdup-mockito-diff2.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-mockito-simplified-compressed.xml drdup-mockito-simplified-plain.xml drdup-mockito-diff3.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-mockito-simplified-plain.xml drdup-mockito-full-compressed.xml drdup-mockito-diff4.xml

java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-nashorn-full-compressed.xml drdup-nashorn-full-plain.xml drdup-nashorn-diff1.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-nashorn-full-plain.xml drdup-nashorn-simplified-compressed.xml drdup-nashorn-diff2.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-nashorn-simplified-compressed.xml drdup-nashorn-simplified-plain.xml drdup-nashorn-diff3.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-nashorn-simplified-plain.xml drdup-nashorn-full-compressed.xml drdup-nashorn-diff4.xml

echo "H2"
type drdup-h2-diff1.xml drdup-h2-diff2.xml drdup-h2-diff3.xml drdup-h2-diff4.xml
set /p DUMMY=Hit ENTER to continue...

echo "JAXP"
type drdup-jaxp-diff1.xml drdup-jaxp-diff2.xml drdup-jaxp-diff3.xml drdup-jaxp-diff4.xml
set /p DUMMY=Hit ENTER to continue...

echo "jEdit"
type drdup-jedit-diff1.xml drdup-jedit-diff2.xml drdup-jedit-diff3.xml drdup-jedit-diff4.xml
set /p DUMMY=Hit ENTER to continue...

echo "Mockito"
type drdup-mockito-diff1.xml drdup-mockito-diff2.xml drdup-mockito-diff3.xml drdup-mockito-diff4.xml
set /p DUMMY=Hit ENTER to continue...

echo "Nashorn"
type drdup-nashorn-diff1.xml drdup-nashorn-diff2.xml drdup-nashorn-diff3.xml drdup-nashorn-diff4.xml
set /p DUMMY=Hit ENTER to continue...

del *.xml
