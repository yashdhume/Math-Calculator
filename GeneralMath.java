

/**
 *  @author Mahip Singh
 **/
public class GeneralMath {
    final static double EPS = 1e-15;
    final static double INFINITY = 1e30;

    static double roundTo(double number, int sigDigs) {
        if (isZero(number, 1e-10))
            return 0;
        final double d = Math.ceil(Math.log10(number < 0 ? -number : number));
        final int power = sigDigs - (int) d;

        final double magnitude = power(10, power);
        final long shifted = Math.round(number * magnitude);
        return shifted / magnitude;
    }

    static boolean isZero(double value) {
        return value >= -EPS && value <= EPS;
    }

    static boolean isZero(double value, double threshold) {
        return value >= -threshold && value <= threshold;
    }

    static long power(long base, long exponent) {
        if (base == 0) return 0;
        if (exponent == 0) return 1;
        if (base == 1) return 1;
        if (exponent == 1) return base;
        if (exponent % 2 == 1) return base * power(base * base, exponent / 2);
        return power(base * base, exponent / 2);
    }


    static boolean isInt(String str) {
        int temp;
        if (str.length() == '0')
            return false;

        for (temp = 0; temp < str.length(); temp++) {
            if (str.charAt(temp) != '+' && str.charAt(temp) != '-' && !Character.isDigit(str.charAt(temp))) {
                return false;
            }
        }
        return true;
    }

    static boolean isDouble(String str) {
        int temp;
        if (str.length() == '0')
            return false;

        for (temp = 0; temp < str.length(); temp++) {
            if (str.charAt(temp) != '+' && str.charAt(temp) != '-' && str.charAt(temp) != '.' && !Character.isDigit(str.charAt(temp))
                    ) {
                return false;
            }
        }
        return true;
    }
}