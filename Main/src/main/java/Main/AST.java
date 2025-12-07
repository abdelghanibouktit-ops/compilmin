/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

/**
 *
 * @author GHANOUU
 */
abstract class ASTNode {}

// Expressions
abstract class Expr extends ASTNode {}
class NumberExpr extends Expr {
    public final double value;
    public NumberExpr(double value) { this.value = value; }
}
class VarExpr extends Expr {
    public final String name;
    public VarExpr(String name) { this.name = name; }
}
class BinaryExpr extends Expr {
    public final Expr left;
    public final Token operator;
    public final Expr right;
    public BinaryExpr(Expr left, Token operator, Expr right) { 
        this.left = left; 
        this.operator = operator; 
        this.right = right; 
    }
}

// Statements
abstract class Statement extends ASTNode {}
class ExprStatement extends Statement {
    public final Expr expr;
    public ExprStatement(Expr expr) { this.expr = expr; }
}
class AssignStatement extends Statement {
    public final String varName;
    public final Expr expr;
    public AssignStatement(String varName, Expr expr) { 
        this.varName = varName; 
        this.expr = expr; 
    }
}
class BlockStatement extends Statement {
    public final java.util.List<Statement> statements = new java.util.ArrayList<>();
    public void add(Statement stmt) { statements.add(stmt); }
}
class WhileStatement extends Statement {
    public final Expr condition;
    public final BlockStatement body;
    public WhileStatement(Expr condition, BlockStatement body) { 
        this.condition = condition; 
        this.body = body; 
    }
}
class IfStatement extends Statement {
    public final Expr condition;
    public final BlockStatement thenBlock;
    public final BlockStatement elseBlock;
    public IfStatement(Expr condition, BlockStatement thenBlock, BlockStatement elseBlock) { 
        this.condition = condition; 
        this.thenBlock = thenBlock; 
        this.elseBlock = elseBlock; 
    }
}

