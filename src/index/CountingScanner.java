package index;

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
import com.sun.tools.javac.tree.TreeScanner;

/**
 * The scanner that counts the number of lines and AST nodes.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class CountingScanner extends TreeScanner {

    private int lines;
    private int nodes;

    public int getLines() {
        return lines;
    }

    public int getNodes() {
        return nodes;
    }

    @Override
    public void visitAnnotatedType(JCAnnotatedType t) {
        nodes++;
        scan(t.annotations);
        scan(t.underlyingType);
    }

    @Override
    public void visitAnnotation(JCAnnotation t) {
        nodes++;
        scan(t.annotationType);
        scan(t.args);
    }

    @Override
    public void visitApply(JCMethodInvocation t) {
        nodes++;
        scan(t.typeargs);
        scan(t.meth);
        scan(t.args);
    }

    @Override
    public void visitAssert(JCAssert t) {
        nodes++;
        scan(t.cond);
        scan(t.detail);
    }

    @Override
    public void visitAssign(JCAssign t) {
        nodes++;
        scan(t.lhs);
        scan(t.rhs);
    }

    @Override
    public void visitAssignop(JCAssignOp t) {
        nodes++;
        scan(t.lhs);
        scan(t.rhs);
    }

    @Override
    public void visitBinary(JCBinary t) {
        nodes++;
        scan(t.lhs);
        scan(t.rhs);
    }

    @Override
    public void visitBlock(JCBlock t) {
        nodes++;
        scan(t.stats);
    }

    @Override
    public void visitBreak(JCBreak t) {
        nodes++;
    }

    @Override
    public void visitCase(JCCase t) {
        nodes++;
        scan(t.pat);
        scan(t.stats);
    }

    @Override
    public void visitCatch(JCCatch t) {
        nodes++;
        scan(t.param);
        scan(t.body);
    }

    @Override
    public void visitClassDef(JCClassDecl t) {
        nodes++;
        scan(t.mods);
        scan(t.typarams);
        scan(t.extending);
        scan(t.implementing);
        scan(t.defs);
    }

    @Override
    public void visitConditional(JCConditional t) {
        nodes++;
        scan(t.cond);
        scan(t.truepart);
        scan(t.falsepart);
    }

    @Override
    public void visitContinue(JCContinue t) {
        nodes++;
    }

    @Override
    public void visitDoLoop(JCDoWhileLoop t) {
        nodes++;
        scan(t.body);
        scan(t.cond);
    }

    @Override
    public void visitErroneous(JCErroneous t) {
    }

    @Override
    public void visitExec(JCExpressionStatement t) {
        nodes++;
        scan(t.expr);
    }

    @Override
    public void visitForLoop(JCForLoop t) {
        nodes++;
        scan(t.init);
        scan(t.cond);
        scan(t.step);
        scan(t.body);
    }

    @Override
    public void visitForeachLoop(JCEnhancedForLoop t) {
        nodes++;
        scan(t.var);
        scan(t.expr);
        scan(t.body);
    }

    @Override
    public void visitIdent(JCIdent t) {
        nodes++;
    }

    @Override
    public void visitIf(JCIf t) {
        nodes++;
        scan(t.cond);
        scan(t.thenpart);
        scan(t.elsepart);
    }

    @Override
    public void visitImport(JCImport t) {
        nodes++;
        scan(t.qualid);
    }

    @Override
    public void visitIndexed(JCArrayAccess t) {
        nodes++;
        scan(t.indexed);
        scan(t.index);
    }

    @Override
    public void visitLabelled(JCLabeledStatement t) {
        nodes++;
        scan(t.body);
    }

    @Override
    public void visitLambda(JCLambda t) {
        nodes++;
        scan(t.params);
        scan(t.body);
    }

    @Override
    public void visitLetExpr(LetExpr t) {
        nodes++;
        scan(t.defs);
        scan(t.expr);
    }

    @Override
    public void visitLiteral(JCLiteral t) {
        nodes++;
    }

    @Override
    public void visitMethodDef(JCMethodDecl t) {
        nodes++;
        scan(t.mods);
        scan(t.typarams);
        scan(t.restype);
        scan(t.params);
        scan(t.thrown);
        scan(t.defaultValue);
        scan(t.body);
    }

    @Override
    public void visitModifiers(JCModifiers t) {
        nodes++;
        scan(t.annotations);
    }

    @Override
    public void visitNewArray(JCNewArray t) {
        nodes++;
        scan(t.elemtype);
        scan(t.dims);
        scan(t.elems);
    }

    @Override
    public void visitNewClass(JCNewClass t) {
        nodes++;
        scan(t.encl);
        scan(t.clazz);
        scan(t.typeargs);
        scan(t.args);
        scan(t.def);
    }

    @Override
    public void visitParens(JCParens t) {
        nodes++;
        scan(t.expr);
    }

    @Override
    public void visitReference(JCMemberReference t) {
        nodes++;
        scan(t.typeargs);
        scan(t.expr);
    }

    @Override
    public void visitReturn(JCReturn t) {
        nodes++;
        scan(t.expr);
    }

    @Override
    public void visitSelect(JCFieldAccess t) {
        nodes++;
        scan(t.selected);
    }

    @Override
    public void visitSkip(JCSkip t) {
    }

    @Override
    public void visitSwitch(JCSwitch t) {
        nodes++;
        scan(t.selector);
        scan(t.cases);
    }

    @Override
    public void visitSynchronized(JCSynchronized t) {
        nodes++;
        scan(t.lock);
        scan(t.body);
    }

    @Override
    public void visitThrow(JCThrow t) {
        nodes++;
        scan(t.expr);
    }

    @Override
    public void visitTopLevel(JCCompilationUnit t) {
        nodes++;
        int startpos = TreeInfo.getStartPos(t);
        int endpos = TreeInfo.getEndPos(t, t.endPositions);
        int startLine = t.lineMap.getLineNumber(startpos);
        int endLine = t.lineMap.getLineNumber(endpos);
        lines += endLine - startLine + 1;
        scan(t.packageAnnotations);
        scan(t.pid);
        scan(t.defs);
    }

    @Override
    public void visitTree(JCTree t) {
        assert false;
    }

    @Override
    public void visitTry(JCTry t) {
        nodes++;
        scan(t.body);
        scan(t.catchers);
        scan(t.finalizer);
    }

    @Override
    public void visitTypeApply(JCTypeApply t) {
        nodes++;
        scan(t.clazz);
        scan(t.arguments);
    }

    @Override
    public void visitTypeArray(JCArrayTypeTree t) {
        nodes++;
        scan(t.elemtype);
    }

    @Override
    public void visitTypeBoundKind(TypeBoundKind t) {
        nodes++;
    }

    @Override
    public void visitTypeCast(JCTypeCast t) {
        nodes++;
        scan(t.clazz);
        scan(t.expr);
    }

    @Override
    public void visitTypeIdent(JCPrimitiveTypeTree t) {
        nodes++;
    }

    @Override
    public void visitTypeIntersection(JCTypeIntersection t) {
        nodes++;
        scan(t.bounds);
    }

    @Override
    public void visitTypeParameter(JCTypeParameter t) {
        nodes++;
        scan(t.bounds);
    }

    @Override
    public void visitTypeTest(JCInstanceOf t) {
        nodes++;
        scan(t.expr);
        scan(t.clazz);
    }

    @Override
    public void visitTypeUnion(JCTypeUnion t) {
        nodes++;
        scan(t.alternatives);
    }

    @Override
    public void visitUnary(JCUnary t) {
        nodes++;
        scan(t.arg);
    }

    @Override
    public void visitVarDef(JCVariableDecl t) {
        nodes++;
        scan(t.mods);
        scan(t.vartype);
        scan(t.nameexpr);
        scan(t.init);
    }

    @Override
    public void visitWhileLoop(JCWhileLoop t) {
        nodes++;
        scan(t.cond);
        scan(t.body);
    }

    @Override
    public void visitWildcard(JCWildcard t) {
        nodes++;
        scan(t.kind);
        scan(t.inner);
    }
}
