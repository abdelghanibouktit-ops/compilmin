package Main;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez votre code (vide pour terminer):");
        StringBuilder sb = new StringBuilder();
        while(true){
            String line = sc.nextLine();
            if(line.isEmpty()) break;
            sb.append(line).append("\n");
        }
        String code = sb.toString();

        try {
            Lexer lexer = new Lexer(code);
            List<Token> tokens = lexer.tokenize();
            System.out.println("--- Tokens ---");
            for(Token t : tokens) System.out.println(t);

            Parser parser = new Parser(tokens);
            BlockStatement ast = parser.parseProgram();

            SemanticAnalyzer sem = new SemanticAnalyzer();
            sem.check(ast);

            System.out.println("Compilation reussie ✓");
        } catch(Exception e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }
}
