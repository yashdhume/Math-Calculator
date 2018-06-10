

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
	
public class MatrixOperationCalculator implements Grid {
    private static int column, row;
    private static Matrix myMatrix;
    private static int result;
    private static JButton showMatrix, newMatrix, addMatrix, subtractMatrix, multiplyMatrix, determinantMatrix, exit;
    private static JPanel panel[] = new JPanel[8];
    private static int saveColumn, saveRow;
    
    MatrixOperationCalculator() {
        mainScreenMatrix();
    }

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

        if (column == 0 || row == 0) {
            JOptionPane.showMessageDialog(null, "You haven't entered any matrix");
        } else {

            JOptionPane.showMessageDialog(null, panel, null,
                    JOptionPane.PLAIN_MESSAGE, null);
        }
    }

    public static void main(String[] args) {
        new MatrixOperationCalculator();
    }

    @Override
    public void getDimension() {
        JTextField length = new JTextField(5);
        JTextField width = new JTextField(5);
        JPanel panel[] = new JPanel[2];
        panel[0] = new JPanel();
        panel[1] = new JPanel();
        panel[0].add(new JLabel("Enter Dimensions"));
        panel[1].add(new JLabel("Rows:"));
        panel[1].add(length);
        panel[1].add(Box.createHorizontalStrut(15));
        panel[1].add(new JLabel("Columns:"));
        panel[1].add(width);

        result = JOptionPane.showConfirmDialog(null, panel, null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        saveColumn = column;
        saveRow = row;

        if (result == 0) {
            if (width.getText().equals(""))
                column = 0;
            else {
                if (GeneralMath.isInt(width.getText())) {
                    column = Integer.parseInt(width.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "A Matrix can only have integer dimensions");
                    return;
                }
                if (GeneralMath.isInt(length.getText())) {
                    row = Integer.parseInt(length.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "A Matrix can only have integer dimensions");
                    return;
                }

            }
            if (column < 1 || row < 1) {
                JOptionPane.showConfirmDialog(null, "The Dimensions can only be positive",
                        "Error", JOptionPane.PLAIN_MESSAGE);
            } else {
                if (column == row) {
                    myMatrix = new SquareMatrix(row, row);
                } else {
                    myMatrix = new Matrix(row, column);
                }
                Matrix tempMatrix = myMatrix.clone();
                if (!setElements(myMatrix, "Fill your new matrix")) {
                    myMatrix = tempMatrix;
                }
            }
        } else if (result == 1) {
            column = saveColumn;
            row = saveRow;
        }
    }

    public boolean setElements(Matrix matrix, String title) {
        int temp, temp1;
        String tempString;
        int rows = matrix.getRows();
        int columns = matrix.getColumns();
        JPanel panel[] = new JPanel[row + 2];
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
                        column = saveColumn;
                        row = saveRow;
                        return false;
                    }
                }
            }
            return true;
        } else
            return false;
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

    private void addMatrix() throws Exception {
        if (myMatrix.getRows() < 1) {
            JOptionPane.showMessageDialog(null, "You haven't entered any matrix");
        } else {
            Matrix matrix2 = new Matrix(row, column);

            if (setElements(matrix2, "Fill Additional Matrix")) {
                showMatrix(myMatrix.add(matrix2), "Addition Result");
            }
        }
    }

    private void subtractMatrix() throws Exception {
        if (myMatrix.getRows() < 1) {
            JOptionPane.showMessageDialog(null, "You haven't entered any matrix");
        } else {
            Matrix matrix2 = new Matrix(row, column);

            if (setElements(matrix2, "Fill the Matrix subtracting from the original matrix")) {
                showMatrix(myMatrix.subtract(matrix2), "Subtraction Result");
            }
        }
    }

    private void multiplyMatrix() throws Exception {
        if (myMatrix.getRows() < 1 || myMatrix.getRows() < 1) {
            JOptionPane.showMessageDialog(null, "You haven't entered any matrix");
        } else {
            Matrix m2 = new Matrix(myMatrix.getColumns(), myMatrix.getRows());
            if (setElements(m2, "Fill multiplying matrix")) {
                showMatrix(myMatrix.multiply(m2), "Multiplication Result");
            }
        }
    }

    private void newMatrix() {
        getDimension();
    }

    private void mainScreenMatrix() {
        int temp;

        for (temp = 0; temp < panel.length; temp++) {
            panel[temp] = new JPanel();
        }
        newMatrix = new JButton("New Matrix");
        newMatrix.setPreferredSize(new Dimension(300, 40));
        newMatrix.addActionListener(this);
        panel[0].add(newMatrix);

        showMatrix = new JButton("Show Matrix");
        showMatrix.setPreferredSize(new Dimension(250, 40));
        showMatrix.addActionListener(this);
        panel[1].add(showMatrix);

        addMatrix = new JButton("Add Another Matrix");
        addMatrix.setPreferredSize(new Dimension(250, 40));
        addMatrix.addActionListener(this);
        panel[2].add(addMatrix);

        subtractMatrix = new JButton("Subtract Another Matrix");
        subtractMatrix.setPreferredSize(new Dimension(250, 40));
        subtractMatrix.addActionListener(this);
        panel[3].add(subtractMatrix);

        multiplyMatrix = new JButton("Multiply with Another Matrix");
        multiplyMatrix.setPreferredSize(new Dimension(250, 40));
        multiplyMatrix.addActionListener(this);
        panel[4].add(multiplyMatrix);

        determinantMatrix = new JButton("Determinant of Your Matrix");
        determinantMatrix.setPreferredSize(new Dimension(250, 40));
        determinantMatrix.addActionListener(this);
        panel[5].add(determinantMatrix);

        exit = new JButton("Exit");
        exit.setPreferredSize(new Dimension(80, 40));
        exit.addActionListener(this);
        panel[6].add(exit);


        JOptionPane.showConfirmDialog(null, panel, null,
                JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE);

    }

    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == showMatrix)
            showMatrix(myMatrix, "Your Matrix");
        else if (event.getSource() == newMatrix)
            newMatrix();
        else if (event.getSource() == addMatrix)
            try {
                addMatrix();
            } catch (Exception e) {
                e.printStackTrace();
            }
        else if (event.getSource() == subtractMatrix)
            try {
                subtractMatrix();
            } catch (Exception e) {
                e.printStackTrace();
            }
        else if (event.getSource() == multiplyMatrix) {
            try {
                multiplyMatrix();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == determinantMatrix) {
            try {
                determinantMatrix();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == exit)
            System.exit(0);
    }

    private void determinantMatrix() throws CloneNotSupportedException {
        if (!(myMatrix instanceof SquareMatrix)) {
            JOptionPane.showMessageDialog(null, "You can't find the determinant of a non-square matrix!");
        } else {
            JOptionPane.showMessageDialog(null, ("The determinant of your matrix is ") + ((SquareMatrix) myMatrix).determinant());
        }
    }
}