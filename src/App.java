import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class App {
    int width = 360;
    int height = 540;

    Color warna1 = new Color(212, 212, 210); // abu-abu terang
    Color warna2 = new Color(80, 80, 80); // abu-abu gelap
    Color warna3 = new Color(28, 28, 28); // hitam
    Color warna4 = new Color(255, 149, 0); // oren

    String[] buttonValues = {
            "AC", "<-", "+/-", "÷",
            "7", "8", "9", "x",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "%", "0", ".", "=",
    };
    String[] buttonRight = { "÷", "x", "-", "+", "=" };
    String[] buttonTop = { "AC", "<-", "+/-" };

    JFrame frame = new JFrame("Hasan - kalkulator");
    JLabel label = new JLabel();
    JPanel panel = new JPanel();
    JPanel buttonPanel = new JPanel();

    String angka1 = "0";
    String op = null;
    String angka2 = null;

    public static void main(String[] args) {
        new App();
    }

    public App() {
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        label.setBackground(warna3);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 80));
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setText("0");
        label.setOpaque(true);

        panel.setLayout(new BorderLayout());
        panel.add(label);
        frame.add(panel, BorderLayout.NORTH);

        buttonPanel.setLayout(new GridLayout(5, 4));
        buttonPanel.setBackground(warna3);
        frame.add(buttonPanel);

        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(warna3));
            if (Arrays.asList(buttonTop).contains(buttonValue)) {
                button.setBackground(warna1);
                button.setForeground(warna3);
            } else if (Arrays.asList(buttonRight).contains(buttonValue)) {
                button.setBackground(warna4);
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(warna2);
                button.setForeground(Color.WHITE);
            }
            buttonPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
                    if (Arrays.asList(buttonRight).contains(buttonValue)) {
                        if (buttonValue == "=") {
                            if (angka1 != null) {
                                angka2 = label.getText();
                                double numAngka1 = Double.parseDouble(angka1);
                                double numAngka2 = Double.parseDouble(angka2);

                                if (op == "+") {
                                    label.setText(desimal(numAngka1 + numAngka2));
                                } else if (op == "-") {
                                    label.setText(desimal(numAngka1 - numAngka2));
                                } else if (op == "x") {
                                    label.setText(desimal(numAngka1 * numAngka2));
                                } else if (op == "÷") {
                                    label.setText(desimal(numAngka1 / numAngka2));
                                }
                                clearAll();
                            }
                        } else if ("÷-x+".contains(buttonValue)) {
                            if (op == null) {
                                angka1 = label.getText();
                                label.setText("0");
                                angka2 = "0";
                            }
                            op = buttonValue;
                        }
                    } else if (Arrays.asList(buttonTop).contains(buttonValue)) {
                        if (buttonValue == "AC") {
                            clearAll();
                            label.setText("0");
                        } else if (buttonValue == "+/-") {
                            double numDisplay = Double.parseDouble(label.getText());
                            numDisplay *= -1;
                            label.setText(desimal(numDisplay));
                        } else if (buttonValue == "<-") {
                            String current = label.getText();
                            if (current.length() > 1) {
                                label.setText(current.substring(0, current.length() - 1));
                            } else {
                                label.setText("0");
                            }
                        }
                    } else if (buttonValue == "%") {
                        double numDisplay = Double.parseDouble(label.getText());
                        numDisplay /= 100;
                        label.setText(desimal(numDisplay));
                    } else { // digit atau .
                        if (buttonValue == ".") {
                            if (!label.getText().contains(buttonValue)) {
                                label.setText(label.getText() + buttonValue);
                            }
                        } else if ("0123456789".contains(buttonValue)) {
                            if (label.getText() == "0") {
                                label.setText(buttonValue);
                            } else {
                                label.setText(label.getText() + buttonValue);
                            }
                        }
                    }
                }
            });
            frame.setVisible(true);
        }
    }

    void clearAll() {
        angka1 = "0";
        op = null;
        angka2 = null;
    }

    String desimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
}
