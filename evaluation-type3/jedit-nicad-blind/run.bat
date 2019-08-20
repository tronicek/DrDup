set NICAD6="jedit-blind-0.50-minsize-6.xml"
set NICAD6d="jedit-blind-0.50-minsize-6-dir.xml"
set NICAD16="jedit-blind-0.05-minsize-16.xml"
set NICAD16d="jedit-blind-0.05-minsize-16-dir.xml"
set NICAD_SRC_DIR="/home/ubuntu/jEdit/jEdit5.3.0/"
set SRC_DIR="/research/jEdit5.3.0/src"

set DRDUP_JAR="/research/projects/DrDup/dist/DrDup.jar"
set TOOL_JAR="/research/projects/EvalTool/dist/EvalTool.jar"
set OPTIONS=-ea -Xmx8G -Xss4M

java %OPTIONS% -cp %TOOL_JAR% nicad.ChangeDir %NICAD_SRC_DIR% %NICAD6%
java %OPTIONS% -cp %TOOL_JAR% nicad.ChangeDir %NICAD_SRC_DIR% %NICAD16%

java %OPTIONS% -jar %DRDUP_JAR% jedit8.properties
java %OPTIONS% -jar %DRDUP_JAR% jedit16.properties

java %OPTIONS% -cp %TOOL_JAR% nicad.Diff %NICAD16d% drdup-jedit-8.xml nicad-blind-16-drdup-8.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Diff drdup-jedit-16.xml %NICAD6d% drdup-16-nicad-blind-6.xml

java %OPTIONS% -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% nicad-blind-16-drdup-8.xml
java %OPTIONS% -cp %TOOL_JAR% nicad.Sourcer %SRC_DIR% drdup-16-nicad-blind-6.xml
