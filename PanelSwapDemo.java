import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelSwapDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Panel Swapping Demo");
        frame.setLocation(20, 20);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new PanelSwapPanel(frame));
        frame.pack();
        frame.setVisible(true);
    }
}

class PanelSwapPanel extends JPanel {
    private JFrame myOwner;
    private AddPanel adder;
    private ResultsPanel results;

    public PanelSwapPanel(JFrame f) {
        myOwner = f;
        setLayout(new BorderLayout());
        add(new JLabel("Add two numbers!"), BorderLayout.NORTH);

        adder = new AddPanel(this);
        add(adder, BorderLayout.CENTER);

        results = new ResultsPanel(this);
    }

    public void switchToResultsPanel(int sum) {
        remove(adder);
        results.setResult(sum);
        add(results, BorderLayout.CENTER);
        repaint();
        revalidate();
        myOwner.pack();
    }

    public void switchToAddPanel() {
        remove(results);
        add(adder, BorderLayout.CENTER);
        repaint();
        revalidate();
        myOwner.pack();
    }
}

class AddPanel extends JPanel {
    private PanelSwapPanel myOwner;
    private JTextField num1Field;
    private JTextField num2Field;

    public AddPanel(PanelSwapPanel p) {
        myOwner = p;
        setPreferredSize(new Dimension(300, 200));
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Number 1:"));
        num1Field = new JTextField();
        add(num1Field);

        add(new JLabel("Number 2:"));
        num2Field = new JTextField();
        add(num2Field);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new AddListener());
        add(addButton);
    }

    private class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int num1 = Integer.parseInt(num1Field.getText().strip());
                int num2 = Integer.parseInt(num2Field.getText().strip());
                int sum = num1 + num2;
                myOwner.switchToResultsPanel(sum);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(myOwner, "Please enter valid numbers.");
            }
        }
    }
}

class ResultsPanel extends JPanel {
    private PanelSwapPanel myOwner;
    private JLabel resultLabel;

    public ResultsPanel(PanelSwapPanel p) {
        myOwner = p;
        setPreferredSize(new Dimension(200, 100));
        setLayout(new BorderLayout());

        resultLabel = new JLabel("", SwingConstants.CENTER);
        add(resultLabel, BorderLayout.CENTER);

        JButton newNumbersButton = new JButton("New Numbers");
        newNumbersButton.addActionListener(new NewNumbersListener());
        add(newNumbersButton, BorderLayout.SOUTH);
    }

    public void setResult(int result) {
        resultLabel.setText("Result: " + result);
    }

    private class NewNumbersListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            myOwner.switchToAddPanel();
        }
    }
}
