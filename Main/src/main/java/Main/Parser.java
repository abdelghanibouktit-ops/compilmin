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

public class Parser {
    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens){ this.tokens = tokens; }
    private Token current(){ return tokens.get(pos); }
    private void advance(){ if(pos < tokens.size()) pos++; }
    private void consume(TokenType type) throws ParserError {
        if(current().type != type) throw new ParserError("Expected "+type+" but found "+current());
        advance();
    }

    public BlockStatement parseProgram() throws ParserError {
        BlockStatement block = new BlockStatement();
        while(current().type != TokenType.EOF) {
            block.add(parseStatement());
        }
        return block;
    }

    private Statement parseStatement() throws ParserError {
        if(current().type == TokenType.WHILE) return parseWhile();
        if(current().type == TokenType.IF) return parseIf();
        if(current().type == TokenType.IDENTIFIER) return parseAssign();
        throw new ParserError("Unexpected token in statement: " + current());
    }

    private WhileStatement parseWhile() throws ParserError {
        consume(TokenType.WHILE);
        consume(TokenType.LPAREN);
        Expr cond = parseExpression();
        consume(TokenType.RPAREN);
        BlockStatement body = parseBlock();
        return new WhileStatement(cond, body);
    }

    private IfStatement parseIf() throws ParserError {
        consume(TokenType.IF);
        consume(TokenType.LPAREN);
        Expr cond = parseExpression();
        consume(TokenType.RPAREN);
        BlockStatement thenBlock = parseBlock();
        BlockStatement elseBlock = null;
        if(current().type == TokenType.ELSE) { advance(); elseBlock = parseBlock(); }
        return new IfStatement(cond, thenBlock, elseBlock);
    }

    private AssignStatement parseAssign() throws ParserError {
        String var = current().lexeme;
        advance();
        consume(TokenType.ASSIGN);
        Expr expr = parseExpression();
        consume(TokenType.SEMICOLON);
        return new AssignStatement(var, expr);
    }

    private BlockStatement parseBlock() throws ParserError {
        consume(TokenType.LBRACE);
        BlockStatement block = new BlockStatement();
        while(current().type != TokenType.RBRACE) {
            block.add(parseStatement());
        }
        consume(TokenType.RBRACE);
        return block;
    }

    private Expr parseExpression() throws ParserError {
        Expr left = parseTerm();
        while(current().type == TokenType.PLUS || current().type == TokenType.MINUS) {
            Token op = current(); advance();
            Expr right = parseTerm();
            left = new BinaryExpr(left, op, right);
        }
        return left;
    }

    private Expr parseTerm() throws ParserError {
        Expr left = parseFactor();
        while(current().type == TokenType.STAR || current().type == TokenType.SLASH) {
            Token op = current(); advance();
            Expr right = parseFactor();
            left = new BinaryExpr(left, op, right);
        }
        return left;
    }

    private Expr parseFactor() throws ParserError {
        if(current().type == TokenType.NUMBER) {
            double val = Double.parseDouble(current().lexeme);
            advance();
            return new NumberExpr(val);
        }
        if(current().type == TokenType.IDENTIFIER) {
            String name = current().lexeme;
            advance();
            return new VarExpr(name);
        }
        if(current().type == TokenType.LPAREN) {
            advance();
            Expr e = parseExpression();
            consume(TokenType.RPAREN);
            return e;
        }
        throw new ParserError("Unexpected token in expression: " + current());
    }
}

