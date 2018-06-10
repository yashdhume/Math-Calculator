
/**
 *  @author Yash Dhume
 * @author Mahip Singh
 **/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class QuadraticSolver extends JFrame {
    private JTextField CoA, CoB, CoC;
    private JLabel Root1, Root2;
    private JLabel CoAx, CoBx, CoCx, Root1x, Root2x;
    private JButton calculateB, exitB;
    private CalculateButtonHandler cbHandler;
    private ExitButtonHandler ebHandler;
//constructor
    public QuadraticSolver() {
        setTitle("Quadratic Solver");
        this.setEnabled(true);
        CoAx = new JLabel("a", SwingConstants.CENTER);
        CoBx = new JLabel("b", SwingConstants.CENTER);
        CoCx = new JLabel("c", SwingConstants.CENTER);
        Root1x = new JLabel("Root1", SwingConstants.CENTER);
        Root2x = new JLabel("Root2", SwingConstants.CENTER);

        CoA = new JTextField(10);
        CoB = new JTextField(10);
        CoC = new JTextField(10);
        Root1 = new JLabel("", SwingConstants.RIGHT);
        Root2 = new JLabel("", SwingConstants.RIGHT);

        calculateB = new JButton("Calculate");
        cbHandler = new CalculateButtonHandler();
        calculateB.addActionListener(cbHandler);
        exitB = new JButton("Exit");
        ebHandler = new ExitButtonHandler();
        exitB.addActionListener(ebHandler);

        Container pane = getContentPane();
        pane.setLayout(new GridLayout(6, 2));
        pane.add(CoAx);
        pane.add(CoA);
        pane.add(CoBx);
        pane.add(CoB);
        pane.add(CoCx);
        pane.add(CoC);
        pane.add(Root1x);
        pane.add(Root1);
        pane.add(Root2x);
        pane.add(Root2);
        pane.add(calculateB);
        pane.add(exitB);

        setSize(400, 300);
        setVisible(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JOptionPane.showConfirmDialog(null, pane, null, JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE);
    }
    //starts Quadratic Solver
    public static void main(String[] args) {
        new QuadraticSolver();
    }
    //calculation
    private class CalculateButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            double a = Double.parseDouble(CoA.getText());
            double b = Double.parseDouble(CoB.getText());
            double c = Double.parseDouble(CoC.getText());
            double discriminant = 0;
            try {
                discriminant = Math.sqrt(b * b - 4 * a * c);
            } catch (ArithmeticException negativeArgument) {
                Root1.setText("No real roots");
                Root2.setText("No real roots");
            }
            Root1.setText("" + ((-b - discriminant) / (2 * a)));
            Root2.setText("" + ((-b + discriminant) / (2 * a)));
        }
    }

    private class ExitButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
