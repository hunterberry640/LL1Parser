/*
Author: Hunter Berry
Date: Feb 9th, 2021
Description: Command line Java program. LL1 Recursive Descent parser for arithmetic expressions.
Use: In folder with LL1.class file run commmand: java LL1 "arithmetic expression". 
Example: java LL1 "5+(4-3)"     Output: "Expression is valid"
*/
import java.util.ArrayList;

public class RecursiveDescent {
    public static ArrayList<String> tokensList = new ArrayList<String>();
    public static String tok;
    public static int curTok;
    public static void main(String[] args) {

        String input = args[0];
        String expr = input;
        String ops = "[+\\-\\*/\\(\\)]"; // regex that matches operators
        String[] tokens = expr.split("(?<=" + ops + ")\\s*|\\s*(?=" + ops + ")"); // tokenize string

        // create arraylist of input tokens
        for (String string : tokens) {
            tokensList.add(string);
        }

        tokensList.add("$"); // add the ending symbol
        curTok = 0;
        tok= tokensList.get(curTok);
        
        E();
        
        if(tokensList.get(curTok).equals("$")){
            
            System.out.println("Expression is Valid");
            // System.out.println(value);
            
        }else{
            System.out.println("Expression is invalid");
        }
    }

    private static void E() {
        // int answer = 0;
        System.out.println("in E");
        System.out.println(tok);
        switch(tok){
            case "(": T();   Epr(); break;
            default: 
				if (tok.matches("\\d+\\.\\d+")) {
                        T(); Epr(); break;
					}else if(tok.matches("\\d+")){
						T(); Epr(); break;
					}else{
						System.out.println("Invalid");
					}
        }

        // return answer;
    }

    private static void Epr() {
        System.out.println("in Epr");
        System.out.println(tok);
        switch(tok){
            case "+": 
                curTok++;
                tok = tokensList.get(curTok);
                T();
                Epr();
                break;
            case "-": 
                curTok++;
                tok = tokensList.get(curTok);
                T();
                Epr();
                break;
            case ")":
                break;
            case "$":
                break;
            default:
                System.out.println("Failure in Epr");
        }
    }

    private static void T() {
        System.out.println("in T");
        System.out.println(tok);
        switch(tok){
            case "(": F(); Tpr(); break;
            default: 
				if (tok.matches("\\d+\\.\\d+")) {
                        F(); Tpr(); break;
					}else if(tok.matches("\\d+")){
						F(); Tpr(); break;
					}else{
						System.out.println("Invalid");
					}
        }
    }

    private static void Tpr() {
        System.out.println("in Tpr");
        System.out.println(tok);
        switch(tok){
            case "+": break;
            case "-": break;
            case ")": break;
            case "$": break;
            case "*": 
                curTok++;
                tok = tokensList.get(curTok);
                F();
                Tpr();
                break;
            case "/":
                curTok++;
                tok = tokensList.get(curTok);
                F();
                Tpr();
                break;
            default:
                System.out.println("Failure in Tpr");
        }
    }
    private static void F() {
        System.out.println("in F");
        System.out.println(tok);
        switch(tok){
            case "(": 
                curTok++;
                tok = tokensList.get(curTok);
                E();
                // Once the recursive calls return to this method 
                // we check to see if it is our matching closing parenthesis,
                // if so go to next token else expression is invalid
                if(tok.equals(")")){
                    curTok++;
                    tok = tokensList.get(curTok);
                    break;
                }
                break;
            default: 
				if (tok.matches("\\d+\\.\\d+")) {
                        curTok++;
                        tok = tokensList.get(curTok);
                        
					}else if(tok.matches("\\d+")){
						curTok++;
                        tok = tokensList.get(curTok);
                        System.out.println(tok);
					}else{
						System.out.println("Invalid");
					}
                }
    }


}
