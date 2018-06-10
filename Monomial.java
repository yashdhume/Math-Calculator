
/**
 *  @author Yash Dhume
 * @author Mahip Singh
 **/
public class Monomial {
    double coefficient, exponent;

    Monomial(double coefficient, double exponent) {
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    double evaluate(double x) {
        return coefficient * Math.pow(x, exponent);
    }

    public String toString() {
        return String.format("%fx^%f", coefficient, exponent);
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public double getExponent() {
        return exponent;
    }

    public void setExponent(double exponent) {
        this.exponent = exponent;
    }
}
