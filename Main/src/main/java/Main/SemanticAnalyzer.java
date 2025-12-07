/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

/**
 *
 * @author GHANOUU
 */
import java.util.*;

public class SemanticAnalyzer {
    private final Set<String> vars = new HashSet<>();

    public void declare(String name) { vars.add(name); }

    public void check(ASTNode node) throws SemanticError {
        if(node instanceof NumberExpr) return;
        if(node instanceof VarExpr) {
            VarExpr v = (VarExpr)node;
            if(!vars.contains(v.name)) throw new SemanticError("Undefined variable: " + v.name);
        }
        if(node instanceof BinaryExpr) {
            BinaryExpr b = (BinaryExpr)node;
            check(b.left);
            check(b.right);
        }
        if(node instanceof AssignStatement) {
            AssignStatement a = (AssignStatement)node;
            check(a.expr);
            vars.add(a.varName);
        }
        if(node instanceof ExprStatement) {
            check(((ExprStatement)node).expr);
        }
        if(node instanceof BlockStatement) {
            for(Statement s : ((BlockStatement)node).statements) check(s);
        }
        if(node instanceof WhileStatement) {
            WhileStatement w = (WhileStatement)node;
            check(w.condition);
            check(w.body);
        }
        if(node instanceof IfStatement) {
            IfStatement i = (IfStatement)node;
            check(i.condition);
            check(i.thenBlock);
            if(i.elseBlock != null) check(i.elseBlock);
        }
    }
}
