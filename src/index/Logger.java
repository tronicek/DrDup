package index;

import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * The logger class.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class Logger {

    private static final Logger instance = new Logger();
    private final Deque<String> classes = new ArrayDeque<>();
    private final Deque<String> methods = new ArrayDeque<>();
    private Index trie;
    private boolean output;

    private Logger() {
    }

    public static Logger getInstance() {
        return instance;
    }

    public void setIndex(Index trie) {
        this.trie = trie;
    }

    public void turnOn() {
        output = true;
    }

    public void turnOff() {
        output = false;
    }

    public void enterClass(JCClassDecl t) {
        String clazz = t.sym.fullname.toString();
        if (clazz.isEmpty()) {
            String s = t.sym.flatname.toString();
            int i = s.lastIndexOf('$');
            clazz = s.substring(0, i + 1);
        }
        classes.addLast(clazz);
    }

    public void exitClass(JCClassDecl t) {
        String cls = classes.removeLast();
        if (cls.equals("#org.gjt.sp.jedit.bsh.Parser")) {
        }
    }

    public void enterMethod(JCMethodDecl t) {
        String clazz = classes.peekLast();
        String method = formatMethod(t);
        String fqn = String.format("%s.%s", clazz, method);
        methods.addLast(fqn);
        if (fqn.startsWith("#service.A.next")) {
//            System.out.println("--- trie ---");
//            trie.print();
//            System.out.println("--- end of trie ---");
            System.out.printf("--- entering method: %s ---%n", fqn);
            output = true;
        }
    }

    private String formatMethod(JCMethodDecl t) {
        StringBuilder sb = new StringBuilder();
        sb.append(t.name).append("(");
        boolean comma = false;
        for (JCVariableDecl var : t.getParameters()) {
            if (comma) {
                sb.append(",");
            }
            sb.append(var.getType().type.tsym.getQualifiedName());
            comma = true;
        }
        sb.append(")");
        if (t.getReturnType() != null) {
            sb.append(t.getReturnType().type.tsym.getQualifiedName());
        }
        return sb.toString();
    }

    public void exitMethod(JCMethodDecl t) {
        String fqn = methods.removeLast();
        if (output) {
            System.out.printf("--- exiting method: %s ---%n", fqn);
//            System.out.println("--- trie ---");
//            trie.print();
//            System.out.println("--- end of trie ---");
            output = false;
        }
    }

    public void printf(String format, Object... args) {
        if (output) {
            System.out.printf(format, args);
        }
    }

    public void println(Object o) {
        println(o.toString());
    }

    public void println(String s) {
        if (output) {
            System.out.println(s);
        }
    }
}
