package ducc.annh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bai07 extends JFrame {
    private JTextField txtDisplay;
    private JTextArea txtHistory;
    private String currentInput = "";
    private String operator = "";
    private double firstValue = 0;
    private boolean startNewNumber = true;

    public Bai07() {
        setTitle("Bài 7 - Máy tính mini");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        txtDisplay = new JTextField();
        txtDisplay.setEditable(false);
        txtDisplay.setFont(new Font("Arial", Font.BOLD, 24));
        txtDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
        add(txtDisplay, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 8, 8));
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", " ", " ", " "
        };

        for (String label : buttons) {
            if (label.equals(" ")) {
                buttonPanel.add(new JLabel());
                continue;
            }
            JButton btn = new JButton(label);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            buttonPanel.add(btn);
            btn.addActionListener(new CalculatorAction());
        }

        add(buttonPanel, BorderLayout.CENTER);

        txtHistory = new JTextArea();
        txtHistory.setEditable(false);
        txtHistory.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(txtHistory);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lịch sử"));
        scrollPane.setPreferredSize(new Dimension(420, 120));
        add(scrollPane, BorderLayout.SOUTH);
    }

    private class CalculatorAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.matches("[0-9]") || command.equals(".")) {
                appendNumber(command);
            } else if (command.matches("[+\\-*/]")) {
                setOperator(command);
            } else if (command.equals("C")) {
                clear();
            } else if (command.equals("=")) {
                calculate();
            }
        }
    }

    private void appendNumber(String text) {
        if (startNewNumber) {
            currentInput = "";
            startNewNumber = false;
        }
        if (text.equals(".") && currentInput.contains(".")) {
            return;
        }
        currentInput += text;
        txtDisplay.setText(currentInput);
    }

    private void setOperator(String op) {
        if (!currentInput.isEmpty()) {
            try {
                firstValue = Double.parseDouble(currentInput);
            } catch (NumberFormatException ex) {
                txtDisplay.setText("Lỗi nhập");
                return;
            }
        }
        operator = op;
        startNewNumber = true;
        txtDisplay.setText(operator);
    }

    private void calculate() {
        if (operator.isEmpty() || currentInput.isEmpty()) {
            return;
        }
        double secondValue;
        try {
            secondValue = Double.parseDouble(currentInput);
        } catch (NumberFormatException ex) {
            txtDisplay.setText("Lỗi nhập");
            return;
        }

        Double result = null;
        String expression = firstValue + " " + operator + " " + secondValue;
        switch (operator) {
            case "+":
                result = firstValue + secondValue;
                break;
            case "-":
                result = firstValue - secondValue;
                break;
            case "*":
                result = firstValue * secondValue;
                break;
            case "/":
                if (secondValue == 0) {
                    txtDisplay.setText("Không chia được");
                } else {
                    result = firstValue / secondValue;
                }
                break;
        }

        if (result != null) {
            String output = String.format("%.2f", result).replaceAll("\\.0+$", "");
            txtDisplay.setText(output);
            txtHistory.append(expression + " = " + output + "\n");
            currentInput = output;
        }

        operator = "";
        startNewNumber = true;
    }

    private void clear() {
        currentInput = "";
        operator = "";
        firstValue = 0;
        startNewNumber = true;
        txtDisplay.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Bai07 app = new Bai07();
            app.setVisible(true);
        });
    }
}
