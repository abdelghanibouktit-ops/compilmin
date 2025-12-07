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

public class Lexer {
    private final String text;
    private int pos = 0, line = 1, column = 1;

    public Lexer(String text) { this.text = text; }

    private char current() { return pos < text.length() ? text.charAt(pos) : '\0'; }
    private void advance() { 
        if (current() == '\n') { line++; column = 1; } 
        else column++; 
        pos++; 
    }

    public List<Token> tokenize() throws LexerError {
        List<Token> tokens = new ArrayList<>();
        while (pos < text.length()) {
            char c = current();
            if (Character.isWhitespace(c)) { advance(); continue; }
            int startCol = column;

            // Identifier or keyword
            if (Character.isLetter(c)) {
                StringBuilder sb = new StringBuilder();
                while (Character.isLetterOrDigit(current())) { sb.append(current()); advance(); }
                String word = sb.toString();
                TokenType type;
                switch(word){
                    case "while": type = TokenType.WHILE; break;
                    case "if": type = TokenType.IF; break;
                    case "else": type = TokenType.ELSE; break;
                    case "int": type = TokenType.INT; break;
                    case "return": type = TokenType.RETURN; break;
                    default: type = TokenType.IDENTIFIER;
                }
                tokens.add(new Token(type, word, line, startCol));
                continue;
            }

            // Number
            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (Character.isDigit(current())) { sb.append(current()); advance(); }
                tokens.add(new Token(TokenType.NUMBER, sb.toString(), line, startCol));
                continue;
            }

            // Operators and punctuation
            switch(c) {
                case '+': tokens.add(new Token(TokenType.PLUS,"+",line,startCol)); advance(); break;
                case '-': tokens.add(new Token(TokenType.MINUS,"-",line,startCol)); advance(); break;
                case '*': tokens.add(new Token(TokenType.STAR,"*",line,startCol)); advance(); break;
                case '/': tokens.add(new Token(TokenType.SLASH,"/",line,startCol)); advance(); break;
                case '=': advance(); if(current()=='='){ tokens.add(new Token(TokenType.EQ,"==",line,startCol)); advance(); } else tokens.add(new Token(TokenType.ASSIGN,"=",line,startCol)); break;
                case '!': advance(); if(current()=='='){ tokens.add(new Token(TokenType.NEQ,"!=",line,startCol)); advance(); } else throw new LexerError("Unexpected '!' without '='"); break;
                case '<': advance(); if(current()=='='){ tokens.add(new Token(TokenType.LE,"<=",line,startCol)); advance(); } else tokens.add(new Token(TokenType.LT,"<",line,startCol)); break;
                case '>': advance(); if(current()=='='){ tokens.add(new Token(TokenType.GE,">=",line,startCol)); advance(); } else tokens.add(new Token(TokenType.GT,">",line,startCol)); break;
                case '(': tokens.add(new Token(TokenType.LPAREN,"(",line,startCol)); advance(); break;
                case ')': tokens.add(new Token(TokenType.RPAREN,")",line,startCol)); advance(); break;
                case '{': tokens.add(new Token(TokenType.LBRACE,"{",line,startCol)); advance(); break;
                case '}': tokens.add(new Token(TokenType.RBRACE,"}",line,startCol)); advance(); break;
                case ';': tokens.add(new Token(TokenType.SEMICOLON,";",line,startCol)); advance(); break;
                default: throw new LexerError("Unknown character: '"+c+"'");
            }
        }
        tokens.add(new Token(TokenType.EOF,"",line,column));
        return tokens;
    }
}
