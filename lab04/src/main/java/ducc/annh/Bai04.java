package ducc.annh;

import javax.swing.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Bai04 extends JFrame {
    private JTextField textFieldN;
    private JButton buttonFind;
    private JLabel labelResult;
    private JProgressBar progressBar;
    
    public Bai04() {
        setTitle("Fibonacci Finder with Memoization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create components
        JLabel labelInput = new JLabel("Enter N:");
        textFieldN = new JTextField(10);
        
        buttonFind = new JButton("Tìm");
        buttonFind.addActionListener(e -> startFibonacciCalculation());
        
        labelResult = new JLabel("Result: ");
        labelResult.setFont(labelResult.getFont().deriveFont(14f));
        
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);
        
        // Create layout
        JPanel panelInput = new JPanel();
        panelInput.add(labelInput);
        panelInput.add(textFieldN);
        panelInput.add(buttonFind);
        
        JPanel panelResult = new JPanel();
        panelResult.add(labelResult);
        
        JPanel panelProgress = new JPanel();
        panelProgress.add(progressBar);
        
        // Add to frame
        add(panelInput, "North");
        add(panelResult, "Center");
        add(panelProgress, "South");
    }
    
    private void startFibonacciCalculation() {
        String input = textFieldN.getText().trim();
        
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a number", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int n = Integer.parseInt(input);
            
            if (n < 0) {
                JOptionPane.showMessageDialog(this, "Please enter a non-negative number", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            buttonFind.setEnabled(false);
            progressBar.setVisible(true);
            progressBar.setValue(0);
            
            FibonacciTask task = new FibonacciTask(n);
            task.execute();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private class FibonacciTask extends SwingWorker<BigInteger, Integer> {
        private int n;
        private Map<Integer, BigInteger> memo;
        
        public FibonacciTask(int n) {
            this.n = n;
            this.memo = new HashMap<>();
        }
        
        @Override
        protected BigInteger doInBackground() throws Exception {
            // Initialize base cases
            memo.put(0, BigInteger.ZERO);
            memo.put(1, BigInteger.ONE);
            
            return calculateFibonacci(n);
        }
        
        private BigInteger calculateFibonacci(int num) throws InterruptedException {
            if (memo.containsKey(num)) {
                return memo.get(num);
            }
            
            // Check for cancellation
            if (isCancelled()) {
                throw new InterruptedException("Calculation cancelled");
            }
            
            // Update progress
            int progress = (int) ((num * 100.0) / n);
            publish(progress);
            
            // Calculate Fibonacci recursively with memoization
            BigInteger result = calculateFibonacci(num - 1).add(calculateFibonacci(num - 2));
            memo.put(num, result);
            
            return result;
        }
        
        @Override
        protected void process(java.util.List<Integer> chunks) {
            for (int progress : chunks) {
                progressBar.setValue(Math.min(progress, 100));
            }
        }
        
        @Override
        protected void done() {
            try {
                BigInteger result = get();
                labelResult.setText("F(" + n + ") = " + result.toString());
                progressBar.setValue(100);
            } catch (Exception ex) {
                if (!(ex.getCause() instanceof InterruptedException)) {
                    JOptionPane.showMessageDialog(Bai04.this, "Error: " + ex.getMessage(), "Calculation Error", JOptionPane.ERROR_MESSAGE);
                }
                labelResult.setText("Result: Cancelled or Error");
            } finally {
                buttonFind.setEnabled(true);
                progressBar.setVisible(false);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Bai04 frame = new Bai04();
            frame.setVisible(true);
        });
    }
}
