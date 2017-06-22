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
import com.sun.tools.javac.tree.TreeScanner;

/**
 * The scanner that counts the number of AST nodes.
 * 
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class CountingScanner extends TreeScanner {

    private int count;

    public int getCount() {
        return count;
    }
    
    @Override
    public void visitAnnotatedType(JCAnnotatedType t) {
        count++;
        scan(t.annotations);
        scan(t.underlyingType);
    }

    @Override
    public void visitAnnotation(JCAnnotation t) {
        count++;
        scan(t.annotationType);
        scan(t.args);
    }

    @Override
    public void visitApply(JCMethodInvocation t) {
        count++;
        scan(t.typeargs);
        scan(t.meth);
        scan(t.args);
    }

    @Override
    public void visitAssert(JCAssert t) {
        count++;
        scan(t.cond);
        scan(t.detail);
    }

    @Override
    public void visitAssign(JCAssign t) {
        count++;
        scan(t.lhs);
        scan(t.rhs);
    }

    @Override
    public void visitAssignop(JCAssignOp t) {
        count++;
        scan(t.lhs);
        scan(t.rhs);
    }

    @Override
    public void visitBinary(JCBinary t) {
        count++;
        scan(t.lhs);
        scan(t.rhs);
    }

    @Override
    public void visitBlock(JCBlock t) {
        count++;
        scan(t.stats);
    }

    @Override
    public void visitBreak(JCBreak t) {
        count++;
    }

    @Override
    public void visitCase(JCCase t) {
        count++;
        scan(t.pat);
        scan(t.stats);
    }

    @Override
    public void visitCatch(JCCatch t) {
        count++;
        scan(t.param);
        scan(t.body);
    }

    @Override
    public void visitClassDef(JCClassDecl t) {
        count++;
        scan(t.mods);
        scan(t.typarams);
        scan(t.extending);
        scan(t.implementing);
        scan(t.defs);
    }

    @Override
    public void visitConditional(JCConditional t) {
        count++;
        scan(t.cond);
        scan(t.truepart);
        scan(t.falsepart);
    }

    @Override
    public void visitContinue(JCContinue t) {
        count++;
    }

    @Override
    public void visitDoLoop(JCDoWhileLoop t) {
        count++;
        scan(t.body);
        scan(t.cond);
    }

    @Override
    public void visitErroneous(JCErroneous t) {
    }

    @Override
    public void visitExec(JCExpressionStatement t) {
        count++;
        scan(t.expr);
    }

    @Override
    public void visitForLoop(JCForLoop t) {
        count++;
        scan(t.init);
        scan(t.cond);
        scan(t.step);
        scan(t.body);
    }

    @Override
    public void visitForeachLoop(JCEnhancedForLoop t) {
        count++;
        scan(t.var);
        scan(t.expr);
        scan(t.body);
    }

    @Override
    public void visitIdent(JCIdent t) {
        count++;
    }

    @Override
    public void visitIf(JCIf t) {
        count++;
        scan(t.cond);
        scan(t.thenpart);
        scan(t.elsepart);
    }

    @Override
    public void visitImport(JCImport t) {
        count++;
        scan(t.qualid);
    }

    @Override
    public void visitIndexed(JCArrayAccess t) {
        count++;
        scan(t.indexed);
        scan(t.index);
    }

    @Override
    public void visitLabelled(JCLabeledStatement t) {
        count++;
        scan(t.body);
    }

    @Override
    public void visitLambda(JCLambda t) {
        count++;
        scan(t.params);
        scan(t.body);
    }

    @Override
    public void visitLetExpr(LetExpr t) {
        count++;
        scan(t.defs);
        scan(t.expr);
    }

    @Override
    public void visitLiteral(JCLiteral t) {
        count++;
    }

    @Override
    public void visitMethodDef(JCMethodDecl t) {
        count++;
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
        count++;
        scan(t.annotations);
    }

    @Override
    public void visitNewArray(JCNewArray t) {
        count++;
        scan(t.elemtype);
        scan(t.dims);
        scan(t.elems);
    }

    @Override
    public void visitNewClass(JCNewClass t) {
        count++;
        scan(t.encl);
        scan(t.clazz);
        scan(t.typeargs);
        scan(t.args);
        scan(t.def);
    }

    @Override
    public void visitParens(JCParens t) {
        count++;
        scan(t.expr);
    }

    @Override
    public void visitReference(JCMemberReference t) {
        count++;
        scan(t.typeargs);
        scan(t.expr);
    }

    @Override
    public void visitReturn(JCReturn t) {
        count++;
        scan(t.expr);
    }

    @Override
    public void visitSelect(JCFieldAccess t) {
        count++;
        scan(t.selected);
    }

    @Override
    public void visitSkip(JCSkip t) {
    }

    @Override
    public void visitSwitch(JCSwitch t) {
        count++;
        scan(t.selector);
        scan(t.cases);
    }

    @Override
    public void visitSynchronized(JCSynchronized t) {
        count++;
        scan(t.lock);
        scan(t.body);
    }

    @Override
    public void visitThrow(JCThrow t) {
        count++;
        scan(t.expr);
    }

    @Override
    public void visitTopLevel(JCCompilationUnit t) {
        count++;
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
        count++;
        scan(t.body);
        scan(t.catchers);
        scan(t.finalizer);
    }

    @Override
    public void visitTypeApply(JCTypeApply t) {
        count++;
        scan(t.clazz);
        scan(t.arguments);
    }

    @Override
    public void visitTypeArray(JCArrayTypeTree t) {
        count++;
        scan(t.elemtype);
    }

    @Override
    public void visitTypeBoundKind(TypeBoundKind t) {
        count++;
    }

    @Override
    public void visitTypeCast(JCTypeCast t) {
        count++;
        scan(t.clazz);
        scan(t.expr);
    }

    @Override
    public void visitTypeIdent(JCPrimitiveTypeTree t) {
        count++;
    }

    @Override
    public void visitTypeIntersection(JCTypeIntersection t) {
        count++;
        scan(t.bounds);
    }

    @Override
    public void visitTypeParameter(JCTypeParameter t) {
        count++;
        scan(t.bounds);
    }

    @Override
    public void visitTypeTest(JCInstanceOf t) {
        count++;
        scan(t.expr);
        scan(t.clazz);
    }

    @Override
    public void visitTypeUnion(JCTypeUnion t) {
        count++;
        scan(t.alternatives);
    }

    @Override
    public void visitUnary(JCUnary t) {
        count++;
        scan(t.arg);
    }

    @Override
    public void visitVarDef(JCVariableDecl t) {
        count++;
        scan(t.mods);
        scan(t.vartype);
        scan(t.nameexpr);
        scan(t.init);
    }

    @Override
    public void visitWhileLoop(JCWhileLoop t) {
        count++;
        scan(t.cond);
        scan(t.body);
    }

    @Override
    public void visitWildcard(JCWildcard t) {
        count++;
        scan(t.kind);
        scan(t.inner);
    }
}
