package plain;

import com.sun.source.tree.Tree.Kind;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Symbol.MethodSymbol;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCAnnotatedType;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCArrayAccess;
import com.sun.tools.javac.tree.JCTree.JCArrayTypeTree;
import com.sun.tools.javac.tree.JCTree.JCAssert;
import com.sun.tools.javac.tree.JCTree.JCAssign;
import com.sun.tools.javac.tree.JCTree.JCAssignOp;
import com.sun.tools.javac.tree.JCTree.JCBinary;
import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCBreak;
import com.sun.tools.javac.tree.JCTree.JCCase;
import com.sun.tools.javac.tree.JCTree.JCCatch;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.tree.JCTree.JCConditional;
import com.sun.tools.javac.tree.JCTree.JCContinue;
import com.sun.tools.javac.tree.JCTree.JCDoWhileLoop;
import com.sun.tools.javac.tree.JCTree.JCEnhancedForLoop;
import com.sun.tools.javac.tree.JCTree.JCErroneous;
import com.sun.tools.javac.tree.JCTree.JCExpressionStatement;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;
import com.sun.tools.javac.tree.JCTree.JCForLoop;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCIf;
import com.sun.tools.javac.tree.JCTree.JCImport;
import com.sun.tools.javac.tree.JCTree.JCInstanceOf;
import com.sun.tools.javac.tree.JCTree.JCLabeledStatement;
import com.sun.tools.javac.tree.JCTree.JCLambda;
import com.sun.tools.javac.tree.JCTree.JCLiteral;
import com.sun.tools.javac.tree.JCTree.JCMemberReference;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;
import com.sun.tools.javac.tree.JCTree.JCModifiers;
import com.sun.tools.javac.tree.JCTree.JCNewArray;
import com.sun.tools.javac.tree.JCTree.JCNewClass;
import com.sun.tools.javac.tree.JCTree.JCParens;
import com.sun.tools.javac.tree.JCTree.JCPrimitiveTypeTree;
import com.sun.tools.javac.tree.JCTree.JCReturn;
import com.sun.tools.javac.tree.JCTree.JCSkip;
import com.sun.tools.javac.tree.JCTree.JCSwitch;
import com.sun.tools.javac.tree.JCTree.JCSynchronized;
import com.sun.tools.javac.tree.JCTree.JCThrow;
import com.sun.tools.javac.tree.JCTree.JCTry;
import com.sun.tools.javac.tree.JCTree.JCTypeApply;
import com.sun.tools.javac.tree.JCTree.JCTypeCast;
import com.sun.tools.javac.tree.JCTree.JCTypeIntersection;
import com.sun.tools.javac.tree.JCTree.JCTypeUnion;
import com.sun.tools.javac.tree.JCTree.JCTypeParameter;
import com.sun.tools.javac.tree.JCTree.JCUnary;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.tree.JCTree.JCWhileLoop;
import com.sun.tools.javac.tree.JCTree.JCWildcard;
import com.sun.tools.javac.tree.JCTree.LetExpr;
import com.sun.tools.javac.tree.JCTree.TypeBoundKind;
import com.sun.tools.javac.tree.TreeInfo;
import index.IndexScanner;
import index.Pos;
import java.util.List;
import java.util.Properties;
import javax.lang.model.element.ElementKind;
import javax.tools.JavaFileObject;

/**
 * The AST scanner that builds the full index.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class FullIndexScanner extends IndexScanner {

    private final Trie trie;
    private int methodCount;
    private JCCompilationUnit currentUnit;
    private Stack stack = new Stack();
    private String srcFile;
    private int inMethod;

    public FullIndexScanner(Properties conf) {
        super(conf);
        trie = new Trie(conf);
        logger.setIndex(trie);
    }

    @Override
    public Trie getTrie() {
        return trie;
    }

    @Override
    public int getTrieNodeCount() {
        return TrieNode.getCount();
    }

    @Override
    public int getTrieEdgeCount() {
        return TrieEdge.getCount();
    }

    private void scanBlock(JCTree t) {
        if (t instanceof JCBlock) {
            scan(t);
        } else {
            addChild("BLOCK");
            scan(t);
            addChild("BLOCK_END");
        }
    }

    private void scanConstructor(JCTree t) {
        String s = renameStrategy.rename(ElementKind.CONSTRUCTOR, t.type.toString(), false);
        addChild(s);
    }

    private void addChild(JCTree t) {
        Kind k = t.getKind();
        addChild(k.name());
    }

    private void addChildEnd(JCTree t) {
        Kind k = t.getKind();
        addChild(k.name() + "_END");
    }

    private void addChild(String label) {
        Stack s = new Stack();
        for (StackNode node : stack) {
            Pos pos = node.getPos();
            TrieNode tnode = node.getNode();
            TrieNode p = tnode.addChild(label, pos);
            s.push(new StackNode(p, pos));
        }
        stack = s;
    }

    private Pos pos(JCTree t) {
        int startpos = TreeInfo.getStartPos(t);
        int endpos = TreeInfo.getEndPos(t, currentUnit.endPositions);
        int startLine = currentUnit.lineMap.getLineNumber(startpos);
        int endLine = currentUnit.lineMap.getLineNumber(endpos);
        int realLines = endLine - startLine + 1;
        int ppLines = countLines(t.toString());
        int lines = max(realLines, ppLines);
        return new Pos(methodCount, srcFile, lines, startpos, endpos, startLine, endLine);
    }

    private int countLines(String str) {
        int lines = 0;
        for (char c : str.toCharArray()) {
            if (c == '\n') {
                lines++;
            }
        }
        return lines;
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }

    @Override
    public void visitAnnotatedType(JCAnnotatedType t) {
        scan(t.annotations);
        scan(t.underlyingType);
    }

    @Override
    public void visitAnnotation(JCAnnotation t) {
        // ignore annotations
        scan(t.annotationType);
        scan(t.args);
    }

    @Override
    public void visitApply(JCMethodInvocation t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        if (!ignoreTypeArgs && !t.typeargs.isEmpty()) {
            addChild("TYPE_ARGS");
            scan(t.typeargs);
            addChild("TYPE_ARGS_END");
        }
        scan(t.meth);
        addChild("ARGS");
        scan(t.args);
        addChild("ARGS_END");
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitAssert(JCAssert t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.cond);
        scan(t.detail);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitAssign(JCAssign t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.lhs);
        scan(t.rhs);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitAssignop(JCAssignOp t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.lhs);
        scan(t.rhs);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitBinary(JCBinary t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.lhs);
        scan(t.rhs);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitBlock(JCBlock t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        renameStrategy.enterBlock();
        addChild(t);
        scan(t.stats);
        addChildEnd(t);
        renameStrategy.exitBlock();
        stack.pop();
    }

    @Override
    public void visitBreak(JCBreak t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        stack.pop();
    }

    @Override
    public void visitCase(JCCase t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.pat);
        scan(t.stats);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitCatch(JCCatch t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        renameStrategy.enterBlock();
        addChild(t);
        scan(t.param);
        scan(t.body);
        addChildEnd(t);
        renameStrategy.exitBlock();
        stack.pop();
    }

    @Override
    public void visitClassDef(JCClassDecl t) {
        logger.enterClass(t);
        if (inMethod == 0) {
            scan(t.mods);
            scan(t.typarams);
            scan(t.extending);
            scan(t.implementing);
            scan(t.defs);
        } else {
            stack.push(trie.root, pos(t));
            addChild(t);
            scan(t.mods);
            scan(t.typarams);
            scan(t.extending);
            if (!t.implementing.isEmpty()) {
                addChild("IMPLEMENTS");
                scan(t.implementing);
                addChild("IMPLEMENTS_END");
            }
            scan(t.defs);
            addChildEnd(t);
            stack.pop();
        }
        logger.exitClass(t);
    }

    @Override
    public void visitConditional(JCConditional t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.cond);
        scan(t.truepart);
        scan(t.falsepart);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitContinue(JCContinue t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        stack.pop();
    }

    @Override
    public void visitDoLoop(JCDoWhileLoop t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scanBlock(t.body);
        scan(t.cond);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitErroneous(JCErroneous t) {
        // nothing to do
    }

    @Override
    public void visitExec(JCExpressionStatement t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.expr);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitForLoop(JCForLoop t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        renameStrategy.enterBlock();
        addChild(t);
        scan(t.init);
        scan(t.cond);
        scan(t.step);
        scanBlock(t.body);
        addChildEnd(t);
        renameStrategy.exitBlock();
        stack.pop();
    }

    @Override
    public void visitForeachLoop(JCEnhancedForLoop t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        renameStrategy.enterBlock();
        addChild(t);
        scan(t.var);
        scan(t.expr);
        scanBlock(t.body);
        addChildEnd(t);
        renameStrategy.exitBlock();
        stack.pop();
    }

    @Override
    public void visitIdent(JCIdent t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addIdentChild(t);
        stack.pop();
    }

    private void addIdentChild(JCIdent t) {
        Symbol sym = t.sym;
        ElementKind k = sym.getKind();
        String name;
        switch (k) {
            case CLASS:
            case ENUM:
            case INTERFACE:
            case TYPE_PARAMETER:
                name = t.sym.type.toString();
                break;
            default:
                name = t.name.toString();
        }
        String s = renameStrategy.rename(k, name, sym.isStatic());
        if (s != null) {
            addChild(s);
        }
    }

    @Override
    public void visitIf(JCIf t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.cond);
        scanBlock(t.thenpart);
        scanBlock(t.elsepart);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitImport(JCImport t) {
        scan(t.qualid);
    }

    @Override
    public void visitIndexed(JCArrayAccess t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.indexed);
        scan(t.index);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitLabelled(JCLabeledStatement t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.body);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitLambda(JCLambda t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        renameStrategy.enterBlock();
        addChild(t);
        scan(t.params);
        scan(t.body);
        addChildEnd(t);
        renameStrategy.exitBlock();
        stack.pop();
    }

    @Override
    public void visitLetExpr(LetExpr t) {
        // LetExpr is not a part of public API
    }

    @Override
    public void visitLiteral(JCLiteral t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild("LITERAL");
        stack.pop();
    }

    @Override
    public void visitMethodDef(JCMethodDecl t) {
        MethodSymbol ms = t.sym;
        if ((ms.flags() & (Flags.ABSTRACT | Flags.GENERATEDCONSTR)) != 0) {
            return;
        }
        methodCount++;
        logger.enterMethod(t);
        inMethod++;
        stack.push(trie.root, pos(t));
        renameStrategy.enterMethod();
        addChild(t);
        scan(t.mods);
        scan(t.typarams);
        scan(t.restype);
        addChild("PARAMS");
        scan(t.params);
        addChild("PARAMS_END");
        if (!ignoreExceptions) {
            scan(t.thrown);
        }
        scan(t.defaultValue);
        scan(t.body);
        addChildEnd(t);
        renameStrategy.exitMethod();
        stack.pop();
        inMethod--;
        logger.exitMethod(t);
    }

    @Override
    public void visitModifiers(JCModifiers t) {
        scan(t.annotations);
    }

    @Override
    public void visitNewArray(JCNewArray t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.annotations);
        scan(t.elemtype);
        scan(t.dims);
        scan(t.elems);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitNewClass(JCNewClass t) {
        if (inMethod == 0) {
            scan(t.def);
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.encl);
        scanConstructor(t.clazz);
        if (!ignoreTypeArgs) {
            //t.typeargs is empty
            //scan(t.typeargs);
            parseTypeArgs(t.clazz.type.getTypeArguments());
        }
        addChild("ARGS");
        scan(t.args);
        addChild("ARGS_END");
        scan(t.def);
        addChildEnd(t);
        stack.pop();
    }

    private void parseTypeArgs(List<Type> typeargs) {
        if (typeargs.isEmpty()) {
            return;
        }
        addChild("TYPE_ARGS");
        for (Type t : typeargs) {
            String s = renameStrategy.renameTypeArg(t.toString());
            addChild(s);
        }
        addChild("TYPE_ARGS_END");
    }

    @Override
    public void visitParens(JCParens t) {
        // ignore parens
        scan(t.expr);
    }

    @Override
    public void visitReference(JCMemberReference t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.expr);
        scan(t.typeargs);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitReturn(JCReturn t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.expr);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitSelect(JCFieldAccess t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        Symbol sym = t.sym;
        if (sym != null) {
            ElementKind k = sym.getKind();
            String sel = t.selected.toString();
            if ("super".equals(sel) || "this".equals(sel)) {
                sel = t.name.toString();
            } else {
                sel = t.sym.owner + "." + t.name;
            }
            String s = renameStrategy.rename(k, sel, sym.isStatic());
            if (s != null) {
                addChild(s);
            }
        }
        scan(t.selected);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitSkip(JCSkip t) {
        // nothing to do
    }

    @Override
    public void visitSwitch(JCSwitch t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.selector);
        scan(t.cases);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitSynchronized(JCSynchronized t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.lock);
        scan(t.body);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitThrow(JCThrow t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.expr);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitTopLevel(JCCompilationUnit t) {
        currentUnit = t;
        JavaFileObject jfo = t.getSourceFile();
        srcFile = filename(jfo.getName());
        assert srcFile != null;
        scan(t.packageAnnotations);
        scan(t.pid);
        scan(t.defs);
        srcFile = null;
    }

    private String filename(String path) {
        int dirLen;
        if (srcDir.endsWith("/") || srcDir.endsWith("\\")) {
            dirLen = srcDir.length();
        } else {
            dirLen = srcDir.length() + 1;
        }
        return path.substring(dirLen);
    }

    @Override
    public void visitTree(JCTree t) {
        assert false;
    }

    @Override
    public void visitTry(JCTry t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        renameStrategy.enterBlock();
        addChild(t);
        scan(t.resources);
        scan(t.body);
        scan(t.catchers);
        scan(t.finalizer);
        addChildEnd(t);
        renameStrategy.exitBlock();
        stack.pop();
    }

    @Override
    public void visitTypeApply(JCTypeApply t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.clazz);
        if (!ignoreTypeArgs && !t.arguments.isEmpty()) {
            addChild("TYPE_ARGS");
            scan(t.arguments);
            addChild("TYPE_ARGS_END");
        }
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitTypeArray(JCArrayTypeTree t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.elemtype);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitTypeBoundKind(TypeBoundKind t) {
        // nothing to do
    }

    @Override
    public void visitTypeCast(JCTypeCast t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.clazz);
        scan(t.expr);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitTypeUnion(JCTypeUnion t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.alternatives);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitTypeIdent(JCPrimitiveTypeTree t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        String s = renameStrategy.renamePrimitiveType(t.getPrimitiveTypeKind());
        addChild(s);
        stack.pop();
    }

    @Override
    public void visitTypeIntersection(JCTypeIntersection t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.bounds);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitTypeParameter(JCTypeParameter t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.annotations);
        scan(t.bounds);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitTypeTest(JCInstanceOf t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.expr);
        scan(t.clazz);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitUnary(JCUnary t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.arg);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitVarDef(JCVariableDecl t) {
        if (inMethod == 0) {
            scan(t.init);
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.mods);
        scan(t.vartype);
        scan(t.nameexpr);
        Symbol sym = t.sym;
        ElementKind k = sym.getKind();
        renameStrategy.declareVar(k, t.name.toString());
        scan(t.init);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitWhileLoop(JCWhileLoop t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.cond);
        scanBlock(t.body);
        addChildEnd(t);
        stack.pop();
    }

    @Override
    public void visitWildcard(JCWildcard t) {
        if (inMethod == 0) {
            return;
        }
        stack.push(trie.root, pos(t));
        addChild(t);
        scan(t.kind);
        scan(t.inner);
        addChildEnd(t);
        stack.pop();
    }
}
