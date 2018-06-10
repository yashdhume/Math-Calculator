
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *  @author Yash Dhume
 * @author Mahip Singh
 *         This is a Math Calculator that supports high school math operations including, but not limited to, finding the derivative of a polynomial, finding the x and y intercepts of a polynomial, finding all critical points, providing a general template for a Matrix class that can be reused in other environments, supporting all elementary matrix operations including addition, subtraction, multiplication, and determining the determinant of a square matrix, solving a system of equations using an O(n^3) standard Gaussian elimiation procedure that reduces the given System (represented as a matrix) to its Row Reduced Echelon Form. A general purpose simple arithmetic calculator is included for consistency.
 *         <p>
 *         An Interface "Grid" links the three Grid-style classes of PolynomialSolver, MatrixOperationCalculator and System of Equations solver together.
 *         <p>
 *         A "GeneralMath" class was used as a utility class providing support functions and fields used elsewhere.
 */
public class TitlePage implements ActionListener {
 private static JButton polynomials, matrix, calculator, quadratic, soe;
 private static JPanel panel[] = new JPanel[7];
 private static JLabel title;

 /**
  * This constructor creates all buttons for the different classes and presents them on a Dialog Box. For the sake of brevity and relevance, the GUI is kept simple.
  */
 TitlePage() {
  int temp;
  for (temp = 0; temp < panel.length; temp++)
   panel[temp] = new JPanel();

  //title
  title = new JLabel ("Super Math Calculator");
  title.setFont(new Font("Goudy Stout",Font.HANGING_BASELINE, 35));
  panel[0].add(title);

  //Add all buttons to the "Frame"
 // ImageIcon calcPic = new ImageIcon(getClass().getResource("calculator.png"));
 // JLabel calcPicLabel = new JLabel (calcPic);
  //panel[1].add(calcPicLabel);

  //GP-Calculator
  calculator = new JButton("General Purpose Calculator");
  calculator.setPreferredSize(new Dimension(250, 40));
  calculator.addActionListener(this);
  panel[2].add(calculator);
 
  //ImageIcon polyPic = new ImageIcon(getClass().getResource("download.jpg"));
//  JLabel polyPicLabel = new JLabel (polyPic);
 // panel[3].add(polyPicLabel);
  
  //Quadratic-solver
  quadratic = new JButton("Quadratic Solver");
  quadratic.setPreferredSize(new Dimension(150, 40));
  quadratic.addActionListener(this);
  panel[4].add(quadratic);

  //Polynomials
  polynomials = new JButton("Polynomial Solver (with Differentiation)");
  polynomials.setPreferredSize(new Dimension(350, 40));
  polynomials.addActionListener(this);
  panel[4].add(polynomials);
  
  //ImageIcon matrixPic = new ImageIcon(getClass().getResource("MATRIX-logo-bg1.png"));
  //JLabel matrixPicLabel = new JLabel (matrixPic);
 //
  panel[5].add(matrixPicLabel);

  //Matrix
  matrix = new JButton("Matrix Calculator");
  matrix.setPreferredSize(new Dimension(150, 40));
  matrix.addActionListener(this);
  panel[6].add(matrix);

  //System of Equations solver
  soe = new JButton("System of Equations (Gaussian Elimination) Solver");
  soe.setPreferredSize(new Dimension(350, 40));
  soe.addActionListener(this);
  panel[6].add(soe);


  JOptionPane.showConfirmDialog(null, panel, null, JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE);
 }

 /**
  * Starts the program by calling the constructor of TitlePage
  *
  * @param args
  */
 public static void main(String[] args) {
  new TitlePage();
 }

 /**
  * Initializes the respective classes depending on which button is clicked by the user
  *
  * @param event
  */
 public void actionPerformed(ActionEvent event) {
  if (event.getSource() == polynomials)
   new PolynomialSolver();
  else if (event.getSource() == matrix)
   new MatrixOperationCalculator();
  else if (event.getSource() == soe)
   new SystemOfEquationsSolver();
  else if (event.getSource() == calculator)
   new GeneralCalculator();
  else if (event.getSource() == quadratic)
   new QuadraticSolver();
 }
}