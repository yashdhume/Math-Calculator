	

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 *  @author Yash Dhume
 * @author Mahip Singh
 * Provides functionality pertaining to the input, solving, differentiation and critical-point finding of polynomial functions
 */
public class PolynomialSolver implements Grid {
    private static ArrayList<Monomial> polynomial;
    private static JTextField inputfunc, xIntercept, yIntercept, firstDerivative, secondDerivative, criticalPoints;
    private static JLabel xInterceptText, yInterceptText, firstDerivativeText, secondDerivativeText, criticalPointsText;
    private static JPanel panel[] = new JPanel[8];
    private static JButton input, calculate, clear, exit;
    private static int result, term = 5;

    PolynomialSolver() {
        int temp;
        for (temp = 0; temp < panel.length; temp++) {
            panel[temp] = new JPanel();
        }
        //all text fields, labels and buttons
        inputfunc = new JTextField();
        xIntercept = new JTextField();
        yIntercept = new JTextField();
        firstDerivative = new JTextField();
        secondDerivative = new JTextField();
        criticalPoints = new JTextField();

        xInterceptText = new JLabel("X Intercept");
        yInterceptText = new JLabel("Y Intercept");
        firstDerivativeText = new JLabel("First Derivative");
        secondDerivativeText = new JLabel("Second Derivative");
        criticalPointsText = new JLabel("Critical Points");

        input = new JButton("Input");
        calculate = new JButton("Calculate");
        clear = new JButton("Clear");
        exit = new JButton("Exit");

        input.setPreferredSize(new Dimension(300, 40));
        xIntercept.setPreferredSize(new Dimension(300, 40));
        xInterceptText.setPreferredSize(new Dimension(300, 40));
        yIntercept.setPreferredSize(new Dimension(300, 40));
        yInterceptText.setPreferredSize(new Dimension(300, 40));
        firstDerivative.setPreferredSize(new Dimension(300, 40));
        firstDerivativeText.setPreferredSize(new Dimension(300, 40));
        secondDerivative.setPreferredSize(new Dimension(300, 40));
        secondDerivativeText.setPreferredSize(new Dimension(300, 40));
        criticalPoints.setPreferredSize(new Dimension(300, 40));
        criticalPointsText.setPreferredSize(new Dimension(300, 40));

        input.addActionListener(this);
        calculate.addActionListener(this);
        clear.addActionListener(this);
        exit.addActionListener(this);

        panel[0].add(input);
        panel[1].add(xInterceptText);
        panel[1].add(xIntercept);
        panel[2].add(yInterceptText);
        panel[2].add(yIntercept);
        panel[5].add(firstDerivativeText);
        panel[5].add(firstDerivative);
        panel[6].add(secondDerivativeText);
        panel[6].add(secondDerivative);	
        panel[7].add(criticalPointsText);
        panel[7].add(criticalPoints);

        JOptionPane.showConfirmDialog(null, panel, null, JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE);
    }
    //start
    public static void main(String[] args) {
        new PolynomialSolver();
    }
    //evaluates the function at that x coordinate
    static double evaluate(ArrayList<Monomial> polynomial, double x) {
        double ret = 0;
        for (Monomial m : polynomial)
            ret += m.evaluate(x);
        return ret;
    }
    //find one root
    static double findRoot(ArrayList<Monomial> polynomial, double x1, double x2) {
        double y1 = evaluate(polynomial, x1), y2 = evaluate(polynomial, x2);
        boolean neg1 = y1 < 0, neg2 = y2 < 0;

        if (Math.abs(y1) <= GeneralMath.EPS)
            return x1;
        if (Math.abs(y2) <= GeneralMath.EPS)
            return x2;

        if (neg1 == neg2)
            return Double.NaN;

        while (x2 - x1 > GeneralMath.EPS) {
            double x = (x2 + x1) / 2;
            if ((evaluate(polynomial, x) < 0) == neg1)
                x1 = x;
            else
                x2 = x;
        }
        return x1;
    }
    //find all roots
    static ArrayList<Double> findAllRoots(ArrayList<Monomial> polynomial) {
        ArrayList<Monomial> diff = new ArrayList<Monomial>();
        ArrayList<Double> ret = new ArrayList<Double>();

        for (Monomial m : polynomial)
            if (m.exponent > 0)
                diff.add(new Monomial(m.coefficient * m.exponent, m.exponent - 1));

        if (diff.isEmpty())
            return ret;

        ArrayList<Double> diffRoots = findAllRoots(diff);
        diffRoots.add(0, -GeneralMath.INFINITY);
        diffRoots.add(GeneralMath.INFINITY);

        for (int i = 0; i < diffRoots.size() - 1; i++) {
            Double root = findRoot(polynomial, diffRoots.get(i), diffRoots.get(i + 1));
            if (Double.isNaN(root))
                continue;
            if ((ret.isEmpty() || Math.abs(ret.get(ret.size() - 1) - root) > GeneralMath.EPS))
                ret.add(root);
        }

        return ret;
    }
    //calculates derivative
    static ArrayList<Monomial> derivative(ArrayList<Monomial> function) {
        ArrayList<Monomial> derivative = new ArrayList<Monomial>(function.size());
        for (int i = 0; i < function.size(); i++) {
            double exponent = function.get(i).getExponent();
            if (!GeneralMath.isZero(exponent))
                derivative.add(new Monomial(function.get(i).getCoefficient() * function.get(i).getExponent(), function.get(i).getExponent() - 1));
        }
        return derivative;
    }
    //action performed
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == input)
            getDimension();
    }

    @Override
    public void getDimension() {
        JTextField textField = new JTextField(5);
        JPanel panel[] = new JPanel[2];
        panel[0] = new JPanel();
        panel[1] = new JPanel();
        panel[1].add(new JLabel("Terms:"));
        panel[1].add(textField);
        panel[1].add(Box.createHorizontalStrut(15));

        result = JOptionPane.showConfirmDialog(null, panel, null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == 0) {
            if (GeneralMath.isInt(textField.getText())) {
                term = Integer.parseInt(textField.getText());
            } else {
                JOptionPane.showMessageDialog(null, "There can only be a positive integer number of terms");
                return;
            }
            setElements();
        }
    }
    //set
    private void setElements() {
        String tempString, tempString1;
        int rows = term;
        int columns = 2;
        JPanel panel[] = new JPanel[rows + 2];
        panel[0] = new JPanel();
        panel[0].add(new Label("Coefficients     Exponents"));
        panel[panel.length - 1] = new JPanel();
        JTextField[][] inputField = new JTextField[rows][columns];

        for (int temp = 1; temp <= rows; temp++) {
            panel[temp] = new JPanel();
            for (int temp1 = 0; temp1 < columns; temp1++) {
                inputField[temp - 1][temp1] = new JTextField(3);
                panel[temp].add(inputField[temp - 1][temp1]);
                if (temp1 < columns - 1) {
                    panel[temp].add(Box.createHorizontalStrut(15));
                }
            }
        }
        result = JOptionPane.showConfirmDialog(null, panel, null, JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);

        polynomial = new ArrayList<Monomial>(term);
        if (result == 0) {
            checkTextField(inputField);
            for (int temp = 0; temp < term; temp++) {
                tempString = inputField[temp][0].getText();
                tempString1 = inputField[temp][1].getText();
                if (GeneralMath.isDouble(tempString) && GeneralMath.isDouble(tempString1))
                    polynomial.add(new Monomial(Double.parseDouble(tempString), Double.parseDouble(tempString1)));
                else {
                    JOptionPane.showMessageDialog(null, "You entered wrong elements");
                }
            }
        }
        doCalculations();
    }

    //do calculations
    private void doCalculations() {
        inputfunc.setText(polynomial.toString());
        ArrayList<Double> allRoots = findAllRoots(polynomial);
        StringBuilder xint = new StringBuilder();
        for (int i = 0; i < allRoots.size(); i++) {
            double x = allRoots.get(i);
            xint.append("(" + GeneralMath.roundTo(x, 5) + ", " + Math.abs(GeneralMath.roundTo(evaluate(polynomial, x), 5)) + ")");
            xint.append(' ');
        }
        if (allRoots.size() == 0) xint.append("none");
        xIntercept.setText(xint.toString());
        yIntercept.setText("(0, " + Double.toString(evaluate(polynomial, 0)) + ")");
        ArrayList<Monomial> derivative1 = derivative(polynomial);
        firstDerivative.setText(derivative1.toString());
        ArrayList<Monomial> derivative2 = derivative(derivative1);
        secondDerivative.setText(derivative2.toString());
        StringBuilder critpoints = new StringBuilder();
        allRoots = findAllRoots(derivative1);
        for (int i = 0; i < allRoots.size(); i++) {
            double x = allRoots.get(i);
            critpoints.append("(" + GeneralMath.roundTo(x, 5) + ", " + GeneralMath.roundTo(evaluate(polynomial, x), 5) + ")");
            critpoints.append(' ');
        }
        allRoots = findAllRoots(derivative2);
        for (int i = 0; i < allRoots.size(); i++) {
            double x = allRoots.get(i);
            critpoints.append("(" + GeneralMath.roundTo(x, 5) + ", " + GeneralMath.roundTo(evaluate(polynomial, x), 5) + ")");
            critpoints.append(' ');
        }
        criticalPoints.setText(critpoints.toString());
    }

    @Override
    public void checkTextField(JTextField field[][]) {
        for (JTextField[] aField : field) {
            for (int temp1 = 0; temp1 < field[0].length; temp1++) {
                if (aField[temp1].getText().equals(""))
                    aField[temp1].setText("0");
            }
        }
    }

}
