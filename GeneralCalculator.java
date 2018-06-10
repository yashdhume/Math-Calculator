
/**
 *  @author Yash Dhume
 * @author Mahip Singh
 **/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class GeneralCalculator extends JFrame {

    private JTextField textfield;
    private boolean number = true;
    private String equalOp = "=";
    private CalculatorOp op = new CalculatorOp();

    public GeneralCalculator() {
        textfield = new JTextField("", 12);
        textfield.setHorizontalAlignment(JTextField.RIGHT);

        ActionListener numberListener = new NumberListener();
        String buttonOrder = "1234567890 ";
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 4, 4));
        for (int i = 0; i < buttonOrder.length(); i++) {
            String key = buttonOrder.substring(i, i + 1);
            if (key.equals(" ")) {
                buttonPanel.add(new JLabel(""));
            } else {
                JButton button = new JButton(key);
                button.addActionListener(numberListener);
                buttonPanel.add(button);
            }
        }
        ActionListener operatorListener = new OperatorListener();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 4, 4));
        String[] opOrder = {"+", "-", "*", "/", "=", "C", "sin", "cos", "log"};
        for (int i = 0; i < opOrder.length; i++) {
            JButton button = new JButton(opOrder[i]);
            button.addActionListener(operatorListener);
            panel.add(button);
        }
        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout(4, 4));
        pan.add(textfield, BorderLayout.NORTH);
        pan.add(buttonPanel, BorderLayout.CENTER);
        pan.add(panel, BorderLayout.EAST);
        this.setContentPane(pan);
        this.pack();
        this.setTitle("Calculator");
        this.setResizable(false);
        JOptionPane.showConfirmDialog(null, pan, null, JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        JFrame frame = new GeneralCalculator();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
    }

    private void action() {
        number = true;
        textfield.setText("");
        equalOp = "=";
        op.setTotal("");
    }

    class OperatorListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String displayText = textfield.getText();
            if (e.getActionCommand().equals("sin")) {
                textfield.setText("" + Math.sin(Double.valueOf(displayText)));
            } else if (e.getActionCommand().equals("cos")) {
                textfield.setText("" + Math.cos(Double.valueOf(displayText)));
            } else if (e.getActionCommand().equals("log")) {
                textfield.setText("" + Math.log(Double.valueOf(displayText)));
            } else if (e.getActionCommand().equals("C")) {
                textfield.setText("");
            } else {
                if (number) {
                    action();
                    textfield.setText("");
                } else {
                    number = true;
                    switch (equalOp) {
                        case "=":
                            op.setTotal(displayText);
                            break;
                        case "+":
                            op.add(displayText);
                            break;
                        case "-":
                            op.subtract(displayText);
                            break;
                        case "*":
                            op.multiply(displayText);
                            break;
                        case "/":
                            op.divide(displayText);
                            break;
                    }

                    textfield.setText("" + op.getTotalString());
                    equalOp = e.getActionCommand();
                }
            }
        }
    }

    class NumberListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String digit = event.getActionCommand();
            if (number) {
                textfield.setText(digit);
                number = false;
            } else {
                textfield.setText(textfield.getText() + digit);
            }
        }
    }

    public class CalculatorOp {
        private double total;

        CalculatorOp() {
            total = 0;
        }

        String getTotalString() {
            return "" + total;
        }

        public void setTotal(String n) {
            total = convertToNumber(n);
        }

        public void add(String n) {
            total += convertToNumber(n);
        }

        public void subtract(String n) {
            total -= convertToNumber(n);
        }

        public void multiply(String n) {
            total *= convertToNumber(n);
        }

        public void divide(String n) {
            total /= convertToNumber(n);
        }

        private double convertToNumber(String n) {
            return Double.parseDouble(n);
        }
    }
}