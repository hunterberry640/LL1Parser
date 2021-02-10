/*
Author: Hunter Berry
Date: Feb 9th, 2021
Description: Command line Java program. LL1 Recursive Descent parser for arithmetic expressions.
Use: In folder with LL1.class file run commmand: java LL1 "arithmetic expression". 
Example: java LL1 "5+(4-3)"     Output: "Valid: 6.0"
*/
import java.util.ArrayList;

public class RecursiveDescent {
    public static ArrayList<String> tokensList = new ArrayList<String>();
    public static String tok;
    public static int curTok;
    public static int openParens;
    public static void main(String[] args) {

        String input = args[0];
        String expr = input;
        String ops = "[+\\-\\*/\\(\\)]"; // regex that matches operators
        String[] tokens = expr.split("(?<=" + ops + ")\\s*|\\s*(?=" + ops + ")"); // tokenize string

        // create arraylist of input tokens
        for (String string : tokens) {
            tokensList.add(string);
        }

        // declare variable to keep count of open parenthesis. 
        // This gets rid of the bug where there is a hanging closing parenthesis ex: 4+5)+7 before this would display as valid
        openParens = 0;
        tokensList.add("$"); // add the ending symbol
        curTok = 0;
        tok= tokensList.get(curTok);
        double value = 0;
        value = E();

        if(openParens == 0 && tokensList.get(curTok).equals("$")){
            System.out.println("Valid: " + Double.toString(value));
        }else{
            System.out.println("Invalid Expression");
        }
    }

    private static double E() {
        double val = 0;
        switch(tok){
            case "(": 
                val = T(); 
                val += Epr(); 
            break;
            default: 
				if (tok.matches("\\d+\\.\\d+")) {
                        val = T(); 
                        val += Epr(); 
                        break;
					}else if(tok.matches("\\d+")){
						val = T(); 
                        val += Epr();
                        break;
                        
					}else{
						System.out.println("Invalid");
					}
        }
        return val;
    }

    private static double Epr() {
        double val = 0;
        switch(tok){
            case "+": 
                curTok++;
                tok = tokensList.get(curTok);
                val += T();
                val += Epr();
                break;
                
            case "-": 
                curTok++;
                tok = tokensList.get(curTok);
                val += 0 - T();
                val += Epr();
                
                break;
            case ")":
                break;
            case "$":
                break;
            default:
        }
        return val;
    }

    private static double T() {
        double val = 0;
        switch(tok){
            case "(": 
            val = F(); 
            val *= Tpr(); 
            break;
            default: 
				if (tok.matches("\\d+\\.\\d+")) {
                        val = F();
                        val *= Tpr();
                        break;
					}else if(tok.matches("\\d+")){
						val = F();
                        val *= Tpr();
                        break;
					}
        }
        return val;
    }

    private static double Tpr() {
        double val = 1;
        switch(tok){
            case "+": break;
            case "-": break;
            case ")": break;
            case "$": break;
            case "*": 
                curTok++;
                tok = tokensList.get(curTok);
                val *= F();
                val *= Tpr();
                break;
            case "/":
                curTok++;
                tok = tokensList.get(curTok);
                val *= 1/F();
                val *= Tpr();
                break;
            default:
                
        }
        return val;
    }
    private static double F() {
        double val = 0;
        switch(tok){
            case "(": 
                openParens++;
                curTok++;
                tok = tokensList.get(curTok);
                val += E();
                // Once E() returns and we continue in this method 
                // we check to see if curTok is our matching closing parenthesis,
                // if so go to next token, else expression is invalid
                if(tok.equals(")")){
                    openParens--;
                    curTok++;
                    tok = tokensList.get(curTok);
                }
                break;
            default: 
				if (tok.matches("\\d+\\.\\d+")) {
                        val = Double.parseDouble(tok);
                        curTok++;
                        tok = tokensList.get(curTok);
                        return val;
					}else if(tok.matches("\\d+")){
                        val = Double.parseDouble(tok);
						curTok++;
                        tok = tokensList.get(curTok);
                        return val;
					}else{
						System.out.println("Invalid");
					}
                }
        return val;
    }


}
