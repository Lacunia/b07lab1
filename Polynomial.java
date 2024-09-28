import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.PrintStream;

public class Polynomial {
	//fields
	double[] coe;
	int[] exp;
	
	//constructor 1: no argument
	public Polynomial() {
		coe = null;
		exp = null;
	}

	//constructor 2: take in one array of coefficients and one array of exponents
	public Polynomial(double [] coefficient, int [] exponent) { 
		coe = coefficient;
		exp = exponent;
	}
	
	//constructor 3: take in one argument of type File
	public Polynomial(File file) {
		try (// create a scanner for reading the file
		Scanner scanner = new Scanner(file)) {
			// read and store the first line of the file
			String polynomial = scanner.nextLine();
			// split the string using "+" and "-" and the lookahead assertion to preserve the sign of the coefficient
			String[] terms = polynomial.split("((?=[+-]))");
			// initialize the two arrays
			coe = new double[terms.length];
			exp = new int[terms.length];

			for (int i = 0; i < terms.length; i++) {
				// split each string again, to obtain the coefficient at index 0
				// and the exponent, if any, at index 1
				String[] values = terms[i].split("x", -1);

				// first, check if values is empty, if so, it means that the term is "x"
				if(values.length == 0) {
					coe[i] = 1.0;
					exp[i] = 1;
				}
				// make sure that the coefficient is nonzero
				else {
					// first, handle the coefficient (value[0])
					// if it's + or "" (if the x is the first letter in the string), it means the coe is 1
					// note to self: in Java, .equals compare the content of the object (while == compare the reference)
					if(values[0].equals("+") || values[0] == "") {
						coe[i] = 1.0;
					}
					// if it's -, it means the coe is -1
					else if(values[0].equals("-")) {
						coe[i] = -1.0;
					}
					// otherwise, use parsedouble
					else {
						// note to self: parseDouble is able to convert "+5" to 5.0 correctly
						coe[i] = Double.parseDouble(values[0]); 
					}
					
					// now handle the exponents
					// if the values array has length 1, it means we only have the coefficient part, so exponent is 0
					if (values.length == 1) {
						exp[i] = 0;
					}
					// if it's "", it means that there's no number after x, meaning x has power of 1
					else if (values[1].equals("")) {
						exp[i] = 1;
					}
					// otherwise use parseint
					else {
						exp[i] = Integer.parseInt(values[1]);
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}
	}

	//methods
	public Polynomial add(Polynomial other) {
		// handle the case when the other polynomial has null coe (possibly constructed using the no-argument constructor)
		if(other.coe == null) {
			return new Polynomial(coe, exp);
		}

		// use a hashmap where keys are the exponent and the values
		// are the coefficients
		HashMap<Integer, Double> result = new HashMap<>();

		// use for loop to loop through the coefficients and exponents for this polynomial
		for(int i = 0; i < coe.length; i++) {
			double this_coe = coe[i];
			int this_exp = exp[i];
			
			// add the coefficient from this polynomial to the hashmap
			// under the corresponding key (exponent)
			if (result.containsKey(this_exp)) { 
				result.put(this_exp, result.get(this_exp) + this_coe);
			}
			else {
				result.put(this_exp, this_coe);
			}
		}
		
		// use for loop to loop through the coefficients and exponents for the other polynomial
		// we use separate loop for the polynomials to take into consideration of the difference in length
		for(int j = 0; j < other.coe.length; j++) {
			double other_coe = other.coe[j];
			int other_exp = other.exp[j];

			// add the coefficient from the other polynomial to the hashmap
			// under the corresponding key (exponent)
			if (result.containsKey(other_exp)) {
				result.put(other_exp, result.get(other_exp) + other_coe);
			}
			else {
				result.put(other_exp, other_coe);
			}
		}

		// count the number of non-zero coefficients and set that count as the size for the arrays
        int size = 0;
        for (Map.Entry<Integer, Double> entry : result.entrySet()) {
            if (entry.getValue() != 0.0) {
                size++;
            }
        }

		// create an array of exponents and coefficients from the key-value pairs from the hashmap
		int[] result_exp = new int[size];
		double[] result_coe = new double[size];
		int i = 0;
		for (Map.Entry<Integer,Double> entry : result.entrySet()) {
			// only if the coefficient is not 0 do we put this pair into the corresponding arrays
			if(entry.getValue() != 0.0) {
				result_exp[i] = entry.getKey();
				result_coe[i] = entry.getValue();
				i++;
			}
		}

		// return the new Polynomial
		return new Polynomial(result_coe, result_exp);
	}
	
	public double evaluate(double x) {
		// handle the case when coe is null
		if(coe == null) {
			return 0.0;
		}
		
		// create an double variable to store the evaluation result
		double result = 0;
		// use for loop to multiply the coefficient by the x value raised to the corresponding exponent
		for(int i = 0; i < coe.length; i++)
			result += coe[i] * Math.pow(x, exp[i]);
		
		// return the evaluation result
		return result;
	}	
	
	public boolean hasRoot(double root) {
		// this result is for storing the evaluation result using the potential root
		double result = evaluate(root);

		// if the result is 0, it means that root is an actual root of this polynomial, so return true
		if (result == 0)
			return true;
		// otherwise return false
		return false;
	}

	public Polynomial multiply(Polynomial other) {
		// handle the case when the other polynomial has null coe (possibly constructed using the no-argument constructor)
		// if the other polynomial has null coe, it means it's 0. so mulitplying any polynomial by 0 gives 0
		if(other.coe == null) {
			return new Polynomial();
		}

		// use a hashmap where keys are the exponent and the values
		// are the coefficients
		HashMap<Integer, Double> result = new HashMap<>();

		// use double for loop to go through every coefficients in both polynomials
		for(int i = 0; i < coe.length; i++) {
			// now for each term in this polynomial, we mulitply it to every terms in the other polynomial
			for(int j = 0; j < other.coe.length; j++) {
				double coefficient = coe[i] * other.coe[j]; 
				int exponent = exp[i] + other.exp[j];

				// add the resulting coefficient to the hashmap
				// under the resulting key (exponent)
				if (result.containsKey(exponent)) { 
					result.put(exponent, result.get(exponent) + coefficient);
				}
				else {
					result.put(exponent, coefficient);
				}
			}
		}
		
		// count the number of non-zero coefficients and set that count as the size for the arrays
        int size = 0;
        for (Map.Entry<Integer, Double> entry : result.entrySet()) {
            if (entry.getValue() != 0.0) {
                size++;
            }
        }

		// create an array of exponents and coefficients from the key-value pairs from the hashmap
		int[] result_exp = new int[size];
		double[] result_coe = new double[size];
		int i = 0;
		for (Map.Entry<Integer,Double> entry : result.entrySet()) {
			// only if the coefficient is not 0 do we put this pair into the corresponding arrays
			if(entry.getValue() != 0.0) {
				result_exp[i] = entry.getKey();
				result_coe[i] = entry.getValue();
				i++;
			}
		}

		// return the new Polynomial
		return new Polynomial(result_coe, result_exp);
	}

	public void saveToFile(String filename) {
		try (PrintStream ps = new PrintStream(filename)) {
			// handle the case where coe is null
			if(coe == null) {
				ps.print("0");
				return;
			}

			// loop through all the coefficients (exponents) of this polynomial
			for(int i = 0; i < coe.length; i++) {
				double coefficient = coe[i];
				int exponent = exp[i];

				// if it's not the first term in the line and the coefficient is positive, we need to add + before it
				if(i > 0 && coefficient > 0) {
					// use print so that we are writing on the same line
					ps.print("+");
				}

				// for the first term and negative coefficients, just print direcly
				if(exponent == 0) {
					ps.print(coefficient);
				}
				else if(exponent == 1) {
					ps.print(coefficient + "x");
				}
				else {
					ps.print(coefficient + "x" + exponent);
				}
			}	
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + e.getMessage());
		}
	}
}
