public class Polynomial {
	//fields
	double[] coe;
	
	//constructors
	public Polynomial() {
		coe = new double[1];
		coe[0] = 0;
	}
	
	public Polynomial(double [] a) { 
		coe = new double[a.length];
		for (int i = 0; i < a.length; i++)
			coe[i] = a[i];
	}
	
	//methods
	public Polynomial add(Polynomial other) {
		int maxlength = Math.max(coe.length, other.coe.length);

		// create an array for storing the resulting coefficients after adding the two polynomials
		// note that the array should have the same length as the longer polynomial
		double[] result = new double[maxlength];

		// use for loop to populate the array
		for(int i = 0; i < maxlength; i++) {
			double this_coe;
			double other_coe;

			// check if the index is out of bound for the current polynomial
			if (i >= coe.length)
				this_coe = 0;
			else
				this_coe = coe[i];

			// check if the index is out of bound for the other polynomial
			if (i >= other.coe.length)
				other_coe = 0;
			else
				other_coe = other.coe[i];

			// add the processed coefficients from both polynomial
			result[i] = this_coe + other_coe;
		}


		// return the new Polynomial
		return new Polynomial(result);
	}
	
	public double evaluate(double x) {
		// create an double variable to store the evaluation result
		double result = 0;
		// use for loop to multiply the coefficient by the x value raised to the corresponding exponent
		for(int i = 0; i < coe.length; i++)
			result += coe[i] * Math.pow(x, i);
		
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
}
