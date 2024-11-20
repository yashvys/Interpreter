package interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplexInterpreter {

	public static void main(String[] args) {
		String expression = "x = (2 + 3) * (4 - 2) / 2 ^ 2"; // Example expression with exponentiation and variables
		Interpreter interpreter = new Interpreter();

		// Parse and evaluate the expression
		int result = interpreter.evaluate(expression);
		System.out.println("Result: " + result);

		// Test variable usage
		String expressionWithVar = "y = x + 2";
		int resultVar = interpreter.evaluate(expressionWithVar);
		System.out.println("Result (y): " + resultVar);
		System.out.println("Variables: " + interpreter.getVariables());
	}

	// Token Types
	enum TokenType {
		NUMBER, PLUS, MINUS, MULTIPLY, DIVIDE, EXPONENT, LEFT_PAREN, RIGHT_PAREN, ASSIGN, IDENTIFIER, EOF
	}

	// Token class to represent each token and its type
	static class Token {
		final TokenType type;
		final String value;

		Token(TokenType type, String value) {
			this.type = type;
			this.value = value;
		}

		@Override
		public String toString() {
			return "Token{" + "type=" + type + ", value='" + value + "'}";
		}
	}

	// The Interpreter class to handle the parsing and evaluating logic
	static class Interpreter {

		private final Map<String, Integer> variables = new HashMap<>();
		private List<Token> tokens;
		private int index;

		// Public method to evaluate an expression
		public int evaluate(String expression) {
			this.tokens = tokenize(expression);
			this.index = 0;
			return parseAssignment();
		}

		// Public method to retrieve all stored variables
		public Map<String, Integer> getVariables() {
			return new HashMap<>(variables);
		}

		// Tokenize the input expression
		private List<Token> tokenize(String expression) {
			List<Token> tokens = new ArrayList<>();
			int i = 0;

			while (i < expression.length()) {
				char currentChar = expression.charAt(i);

				// Skip whitespaces
				if (Character.isWhitespace(currentChar)) {
					i++;
					continue;
				}

				// Process numbers
				if (Character.isDigit(currentChar)) {
					StringBuilder number = new StringBuilder();
					while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
						number.append(expression.charAt(i));
						i++;
					}
					tokens.add(new Token(TokenType.NUMBER, number.toString()));
					continue;
				}

				// Process identifiers (variables)
				if (Character.isLetter(currentChar)) {
					StringBuilder identifier = new StringBuilder();
					while (i < expression.length() && Character.isLetterOrDigit(expression.charAt(i))) {
						identifier.append(expression.charAt(i));
						i++;
					}
					tokens.add(new Token(TokenType.IDENTIFIER, identifier.toString()));
					continue;
				}

				// Process operators and parentheses
				switch (currentChar) {
				case '+':
					tokens.add(new Token(TokenType.PLUS, "+"));
					break;
				case '-':
					tokens.add(new Token(TokenType.MINUS, "-"));
					break;
				case '*':
					tokens.add(new Token(TokenType.MULTIPLY, "*"));
					break;
				case '/':
					tokens.add(new Token(TokenType.DIVIDE, "/"));
					break;
				case '^':
					tokens.add(new Token(TokenType.EXPONENT, "^"));
					break;
				case '=':
					tokens.add(new Token(TokenType.ASSIGN, "="));
					break;
				case '(':
					tokens.add(new Token(TokenType.LEFT_PAREN, "("));
					break;
				case ')':
					tokens.add(new Token(TokenType.RIGHT_PAREN, ")"));
					break;
				default:
					throw new SyntaxException("Unexpected character: " + currentChar);
				}
				i++;
			}

			tokens.add(new Token(TokenType.EOF, ""));
			return tokens;
		}

		// Parse and evaluate assignment expressions
		private int parseAssignment() {
			Token token = tokens.get(index);
			if (token.type == TokenType.IDENTIFIER) {
				String varName = token.value;
				index++;
				if (tokens.get(index).type == TokenType.ASSIGN) {
					index++;
					int value = parseExpression();
					variables.put(varName, value);
					return value;
				}
			}
			return parseExpression();
		}

		// Parse an expression (handles +, -, *, /, ^)
		private int parseExpression() {
			int result = parseTerm();
			while (index < tokens.size()) {
				Token token = tokens.get(index);
				switch (token.type) {
				case PLUS:
					index++;
					result += parseTerm();
					break;
				case MINUS:
					index++;
					result -= parseTerm();
					break;
				default:
					return result;
				}
			}
			return result;
		}

		// Parse terms (handles *, /, and exponentiation)
		private int parseTerm() {
			int result = parseExponent();
			while (index < tokens.size()) {
				Token token = tokens.get(index);
				switch (token.type) {
				case MULTIPLY:
					index++;
					result *= parseExponent();
					break;
				case DIVIDE:
					index++;
					int divisor = parseExponent();
					if (divisor == 0) {
						throw new ArithmeticException("Division by zero");
					}
					result /= divisor;
					break;
				default:
					return result;
				}
			}
			return result;
		}

		// Parse exponentiation (handles ^ operator)
		private int parseExponent() {
			int result = parseFactor();
			while (index < tokens.size() && tokens.get(index).type == TokenType.EXPONENT) {
				index++;
				result = (int) Math.pow(result, parseFactor());
			}
			return result;
		}

		// Parse factors (numbers, variables, and parentheses)
		private int parseFactor() {
			Token token = tokens.get(index);
			if (token.type == TokenType.NUMBER) {
				index++;
				return Integer.parseInt(token.value);
			}
			if (token.type == TokenType.IDENTIFIER) {
				index++;
				Integer value = variables.get(token.value);
				if (value == null) {
					throw new SyntaxException("Undefined variable: " + token.value);
				}
				return value;
			}

			if (token.type == TokenType.LEFT_PAREN) {
				index++;
				int result = parseExpression();
				if (tokens.get(index).type != TokenType.RIGHT_PAREN) {
					throw new SyntaxException("Mismatched parentheses");
				}
				index++;
				return result;
			}
			throw new SyntaxException("Expected a number, variable, or '(', got: " + token.value);
		}
	}

	// Custom Exception for syntax errors
	static class SyntaxException extends RuntimeException {
		SyntaxException(String message) {
			super(message);
		}
	}
}
