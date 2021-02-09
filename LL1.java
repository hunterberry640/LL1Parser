/*
Author: Hunter Berry
Date: Feb 4th, 2021
Description: Command line Java program. LL1 parser for arithmetic expressions.
Use: In folder with LL1.class file run commmand: java LL1 "arithmetic expression". 
Example: java LL1 "5+(4-3)" Returns "Expression is valid"
*/
import java.util.ArrayList;
import java.util.Stack;

public class LL1 {

	
	public static Stack<String> stack = new Stack<String>();
	public static ArrayList<String> tokensList = new ArrayList<String>();
	public static String[][] parseTable = 
	{
		{ ""   , "n"  , "+" ,  "-"  ,  "*"  ,  "/"  , "("  , ")"  , "$"  },
		{ "E"  , "1"  , ""  ,  ""   ,  ""   ,  ""   , "1"  , ""   , ""  },
		{ "Epr" , ""   , "2" ,  "3"  ,  ""   ,  ""   , ""   , "4"  , "4" },
		{ "T"  , "5"  , ""  ,  ""   ,  ""   ,  ""   , "5"  , ""   , ""  },
		{ "Tpr" , ""   , "8" ,  "8"  ,  "6"  ,  "7"  , ""   , "8"  , "8" },
		{ "F"  , "10" , ""  ,  ""   ,  ""   ,  ""   , "9"  , ""   , ""  }
	};
	public static void main(String[] args) {
		
		String input = args[0];
		

		//tokenize input
		String expr =input;   
		String ops = "[+\\-\\*/\\(\\)]"; // regex that matches operators
		String[] tokens = expr.split("(?<="+ops+")\\s*|\\s*(?="+ops+")"); //tokenize string
		
		//create arraylist of input tokens
		for (String string : tokens) {
			tokensList.add(string);
		}

		//add terminal symbol to input string
		tokensList.add("$");

		//push starting symbol to stack
		stack.push("E");

		// boolean valid;
		// valid = checkExpression(tokensList);

		if(!checkExpression(tokensList)){
			System.out.println("expression is not valid");
		}else{
			System.out.println("Expression is valid");
		}
	}

	public static boolean checkExpression(ArrayList<String> tokensList){
		
		for(int counter = 0; counter < tokensList.size(); counter++){ // loop through input tokens
			String tok = tokensList.get(counter);
			boolean terminal = false;
			
			//logic for parsing using parse table and stack
			int col = getCol(tok); // get col is a switch statement that returns column number based on token or 0 if invalid token
			if(col == 0){
				System.out.println("Invalid token");
				return false;
			}

			while(!terminal){ //loop through tokens list until we reach the terminal symbol $
				
				String top = stack.pop();
				int row = getRow(top);
				

				if(parseTable[row][col].equals("")){ //if we end up selecting an empty cell return invalid expression
					
					return false;
				}else if((row == 2 && col == 8) || ((row == 4 && col == 8))){ // reached end symbol successfully
					
					return true;
				}else if(row == 5 && col == 6){ // logic to handle parenthesis: loop through tokens list from back to front looking for first closing paren if found remove if not found invalid expression.
					boolean valid = false;
					for (int i = tokensList.size()-1; i >= 0; i--) {
						if(tokensList.get(i).equals(")")){
							tokensList.remove(i);
							valid = true;
							break;
						}
					}
					if(valid == false){
						
						return false;
					}
					terminal = rules(parseTable[row][col]); // rules is a switch statement that takes the string token in the parse table. 
															//It handles stack operations for the rule and returns true if at a terminal symbol 
															//and exits sub loop going to main loop and proceding to next token and false if not terminal symbol and continuing the loop 
				}
				else{
					terminal = rules(parseTable[row][col]);
				}
			}
		}
		return true;
	}

	public static boolean rules(String rule){ // returns true if at a terminal symbol
		
		switch (rule) {
			case "1": //rule: E -> T E'
				stack.push("E'"); 
				stack.push("T");
				return false;
				 
			case "2": //rule: E' -> + T E'
				stack.push("E'"); 
				stack.push("T");
				return true;
				 
			case "3": //rule: E' -> - T E'
				stack.push("E'"); 
				stack.push("T");
				return true;
				  
			case "4": //rule: E' -> lambda
				return false;
				  
			case "5": //rule: T -> F T'
				stack.push("T'"); 
				stack.push("F");
				return false;
				 
			case "6": //rule: T' -> * F T'
				stack.push("T'"); 
				stack.push("F");
				return true;
				
			case "7": //rule: T' -> / F T'
				stack.push("T'"); 
				stack.push("F");
				return true;
				
			case "8": //rule: T' -> lambda
				return false;
				
			case "9": //rule: F -> (E)
				stack.push("E"); 
				return true;
				
			case "10": //rule: F -> n
				return true;
			default: System.out.println("Something went wrong: 111"); return false;	 
		}
	}

	public static int getRow(String top){
		switch (top) {
			case "E": return 1; 
			case "E'": return 2; 
			case "T": return 3; 
			case "T'": return 4;
			case "F": return 5;
			default: System.out.println("Error: " + top );break;
		}
		return 0;
	}

	public static int getCol(String tok){
		switch (tok) {
			case "+": return 2; 
			case "-": return 3; 
			case "/": return 4; 
			case "*": return 5;
			case "(": return 6;
			case ")": return 7;
			case "$": return 8;
			default: 
				if (tok.matches("\\d+\\.\\d+")) {
						return 1;
					}else if(tok.matches("\\d+")){
						return 1;
					}else{
						return 0;
					}
			
		}
	}
}