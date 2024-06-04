import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TJStudentPanel extends JPanel {
    public JTextArea filenamearea;
    public TopButtonsPanel topButtons;
    public BottomButtonsPanel bottomButtons;
    public MiddlePanel middlePanel;
    public TJStudent currentStudent;

    public JTextArea namearea;
    public JTextArea yeararea;
    public JTextArea agearea;

    public TJStudentPanel() {
        setLayout(new BorderLayout());
        topButtons = new TopButtonsPanel();
        add(topButtons, BorderLayout.NORTH);
        bottomButtons = new BottomButtonsPanel();
        add(bottomButtons, BorderLayout.SOUTH);
        middlePanel = new MiddlePanel();
        add(middlePanel, BorderLayout.CENTER);
    }

    class TopButtonsPanel extends JPanel {
        public JButton show;
        public JButton modify;

        public TopButtonsPanel() {
            setLayout(new GridLayout(1, 3));
            add(new JLabel("Object Modification:"));
            show = new JButton("Show Current Values");
            show.addActionListener(new ShowListener());
            add(show);
            modify = new JButton("Modify fields as shown");
            modify.addActionListener(new ModifyListener());
            add(modify);
        }

        private class ShowListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (currentStudent != null) {
                    namearea.setText(currentStudent.getName());
                    yeararea.setText(currentStudent.getYear());
                    agearea.setText(Integer.toString(currentStudent.getAge()));
                }
            }
        }

        private class ModifyListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (currentStudent != null) {
                    currentStudent.setName(namearea.getText().strip());
                    currentStudent.setYear(yeararea.getText().strip());
                    currentStudent.setAge(Integer.parseInt(agearea.getText().strip()));
                }
            }
        }
    }

    class BottomButtonsPanel extends JPanel {
        public JButton save;
        public JButton load;

        public BottomButtonsPanel() {
            setLayout(new GridLayout(1, 4));
            add(new JLabel("Save/Load:"));
            filenamearea = new JTextArea("Enter Filename");
            add(filenamearea);
            save = new JButton("Save to File");
            save.addActionListener(new SaveListener());
            add(save);
            load = new JButton("Load from File");
            load.addActionListener(new LoadListener());
            add(load);
        }

        private class LoadListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String filename = filenamearea.getText();
                try {
                    currentStudent = new TJStudent(filename);
                    namearea.setText(currentStudent.getName());
                    yeararea.setText(currentStudent.getYear());
                    agearea.setText(Integer.toString(currentStudent.getAge()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        private class SaveListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String filename = filenamearea.getText();
                try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
                    out.println(namearea.getText().strip());
                    out.println(yeararea.getText().strip());
                    out.println(agearea.getText().strip());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class MiddlePanel extends JPanel {
        public MiddlePanel() {
            setLayout(new GridLayout(3, 2));
            add(new JLabel("Name:"));
            namearea = new JTextArea();
            add(namearea);
            add(new JLabel("Year:"));
            yeararea = new JTextArea();
            add(yeararea);
            add(new JLabel("Age:"));
            agearea = new JTextArea();
            add(agearea);
        }
    }

    class TJStudent {
        // Fields for student details
        private String name;
        private String year;
        private int age;
        private String filename;

        // Constructor to load student details from a file
        public TJStudent(String filename) throws Exception {
            this.filename = filename;
            Scanner infile = new Scanner(new File(filename));
            name = infile.nextLine().strip();
            year = infile.nextLine().strip();
            age = Integer.parseInt(infile.nextLine().strip());
        }

        // Accessors
        public String getName() {
            return name;
        }

        public String getYear() {
            return year;
        }

        public int getAge() {
            return age;
        }

        // Modifiers
        public void setName(String name) {
            this.name = name;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TJStudentPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(new TJStudentPanel());
        frame.setVisible(true);
    }
}