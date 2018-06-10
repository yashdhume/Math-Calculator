
/**
 *  @author Yash Dhume
 * @author Mahip Singh
 **/
import javax.swing.*;
import java.awt.event.ActionListener;

public interface Grid extends ActionListener {
    void getDimension();
    void checkTextField(JTextField field[][]);
}
