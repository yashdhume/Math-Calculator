

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class SystemOfEquationsSolver implements Grid {
	/**
	 *  @author Yash Dhume
	 * @author Mahip Singh
	 **/
    private static int equations, variables;
    private static Matrix mySystem;
    private static int result;
    private static JButton showSystem, newSystem, solveSystem, exit;
    private static JPanel panel[] = new JPanel[8];
    private static int saveColumn, saveRow;

    SystemOfEquationsSolver() {
        mainScreenMatrix();
    }

    /**
     * retrieves the number of variables and equations from the user
     */
    @Override
    public void getDimension() {
        JTextField length = new JTextField(5);
        JTextField width = new JTextField(5);
        JPanel panel[] = new JPanel[2];
        panel[0] = new JPanel();
        panel[1] = new JPanel();
        panel[0].add(new JLabel("Enter Dimensions"));
        panel[1].add(new JLabel("#Equations:"));
        panel[1].add(length);
        panel[1].add(Box.createHorizontalStrut(15));
        panel[1].add(new JLabel("#Variables:"));
        panel[1].add(width);

        result = JOptionPane.showConfirmDialog(null, panel, null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        saveColumn = variables;
        saveRow = equations;

        if (result == 0) {
            if (width.getText().equals(""))
                variables = 0;
            else {
                if (GeneralMath.isInt(width.getText())) {
                    variables = Integer.parseInt(width.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "There can only be an integer number of variables");
                    return;
                }
                if (GeneralMath.isInt(length.getText())) {
                    equations = Integer.parseInt(length.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "There can only be an integer number of equations");
                    return;
                }

            }
            if (equations < 1 || variables < 1) {
                JOptionPane.showConfirmDialog(null, "The Dimensions can only be positive",
                        "Error", JOptionPane.PLAIN_MESSAGE);
            } else {
                    mySystem = new Matrix(equations, variables + 1);
                }
                Matrix tempMatrix = mySystem.clone();
                if (!setElements(mySystem, "Your new system")) {
                    mySystem = tempMatrix;
                }
            } else if (result == 1) {
            variables = saveColumn;
            equations = saveRow;
        }
    }

    /**
     * checks the system and adds to Matrix if true
     * @param matrix
     * @param title
     * @return
     */
    public boolean setElements(Matrix matrix, String title) {
        int temp, temp1;
        String tempString;
        int rows = matrix.getRows(); int columns = matrix.getColumns();
        JPanel panel[] = new JPanel[equations + 2];
        panel[0] = new JPanel();
        panel[0].add(new Label(title));
        panel[panel.length - 1] = new JPanel();
        JTextField[][] inputField = new JTextField[rows][columns];

        for (temp = 1; temp <= rows; temp++) {
            panel[temp] = new JPanel();
            for (temp1 = 0; temp1 < columns; temp1++) {
                inputField[temp - 1][temp1] = new JTextField(3);
                panel[temp].add(inputField[temp - 1][temp1]);
                if (temp1 < columns - 1) {
                    panel[temp].add(Box.createHorizontalStrut(15));
                }
            }
        }

        result = JOptionPane.showConfirmDialog(null, panel, null, JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == 0) {
            checkTextField(inputField);
            for (temp = 0; temp < rows; temp++) {
                for (temp1 = 0; temp1 < columns; temp1++) {
                    tempString = inputField[temp][temp1].getText();
                    if (GeneralMath.isDouble(tempString))
                        matrix.setMAt(temp, temp1, Double.parseDouble(inputField[temp][temp1].getText()));
                    else {
                        JOptionPane.showMessageDialog(null, "You entered wrong elements");
                        variables = saveColumn;
                        equations = saveRow;
                        return false;
                    }
                }
            }
            return true;
        } else
            return false;
    }

    /**
     * checks text field
     * @param field
     */
    @Override
    public void checkTextField(JTextField field[][]) {
        for (JTextField[] aField : field) {
            for (int temp1 = 0; temp1 < field[0].length; temp1++) {
                if (aField[temp1].getText().equals(""))
                    aField[temp1].setText("0");
            }
        }
    }

    /**
     * Displays the
     * @param matrix
     * with
     * @param title
     */
    private static void showMatrix(Matrix matrix, String title) {
        int temp, temp1;
        int rows = matrix.getRows();
        int columns = matrix.getColumns();
        JPanel panel[] = new JPanel[rows + 1];
        panel[0] = new JPanel();
        panel[0].add(new JLabel(title));

        for (temp = 1; temp <= rows; temp++) {
            panel[temp] = new JPanel();


            for (temp1 = 0; temp1 < columns; temp1++) {
                if (GeneralMath.isZero(matrix.getMAt(temp - 1, temp1))) {
                    matrix.setMAt(temp - 1, temp1, 0);
                }
                panel[temp].add(new JLabel(String.format("%.2f", matrix.getMAt(temp - 1, temp1))));

                if (temp1 < columns - 1) {
                    panel[temp].add(Box.createHorizontalStrut(15));
                }

            }
        }

        if (equations == 0 || variables == 0) {
            JOptionPane.showMessageDialog(null, "You haven't entered any system");
        } else {

            JOptionPane.showMessageDialog(null, panel, null,
                    JOptionPane.PLAIN_MESSAGE, null);
        }
    }

    /**
     * calls the solve method of the Matrix
     * @throws Exception
     */
    private void gaussianElimination() throws Exception {
        if (variables == 0 || equations == 0) {
            JOptionPane.showMessageDialog(null, "You haven't entered any system");
        }
        else {
            double[] sol = mySystem.solve();
            if(sol == null)JOptionPane.showMessageDialog(null, "There are either no or infinite solutions for this system.");
            else JOptionPane.showMessageDialog(null, Arrays.toString(mySystem.solve()));
        }
    }

    private void newMatrix() {
        getDimension();
    }

    public static void main(String[] args) {
        new SystemOfEquationsSolver();
    }

    /**
     * main screen display
     */
    private void mainScreenMatrix() {
        int temp;

        for (temp = 0; temp < panel.length; temp++) {
            panel[temp] = new JPanel();
        }
        newSystem = new JButton("New System");
        newSystem.setPreferredSize(new Dimension(300, 40));
        newSystem.addActionListener(this);
        panel[0].add(newSystem);

        showSystem = new JButton("Show System");
        showSystem.setPreferredSize(new Dimension(250, 40));
        showSystem.addActionListener(this);
        panel[1].add(showSystem);

        solveSystem = new JButton("Solve by Gaussian Elimination");
        solveSystem.setPreferredSize(new Dimension(250, 40));
        solveSystem.addActionListener(this);
        panel[2].add(solveSystem);


        exit = new JButton("Exit");
        exit.setPreferredSize(new Dimension(80, 40));
        exit.addActionListener(this);
        panel[3].add(exit);


        JOptionPane.showConfirmDialog(null, panel, null,
                JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE);

    }

    /**
     * Which button was clicked?
     * @param event
     */
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == showSystem)
            showMatrix(mySystem, "Your System");
        else if (event.getSource() == newSystem)
            newMatrix();
        else if (event.getSource() == solveSystem)
            try {
                gaussianElimination();
            } catch (Exception e) {
                e.printStackTrace();
            }
        else if (event.getSource() == exit)
            System.exit(0);
    }
}