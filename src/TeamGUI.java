import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class TeamGUI {
    private JFrame frame;
    private JTextField teamNumberField;
    private JTextField nameField;
    private JTextField cityField;
    private JTextField managerNameField;
    private TeamManager teamManager;

    public TeamGUI() {
        teamManager = new TeamManager();
        frame = new JFrame("Team Management");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel teamNumberLabel = new JLabel("Team Number:");
        teamNumberLabel.setBounds(20, 20, 100, 25);
        frame.add(teamNumberLabel);

        teamNumberField = new JTextField();
        teamNumberField.setBounds(150, 20, 200, 25);
        frame.add(teamNumberField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 60, 100, 25);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 60, 200, 25);
        frame.add(nameField);

        JLabel cityLabel = new JLabel("City:");
        cityLabel.setBounds(20, 100, 100, 25);
        frame.add(cityLabel);

        cityField = new JTextField();
        cityField.setBounds(150, 100, 200, 25);
        frame.add(cityField);

        JLabel managerNameLabel = new JLabel("Manager Name:");
        managerNameLabel.setBounds(20, 140, 100, 25);
        frame.add(managerNameLabel);

        managerNameField = new JTextField();
        managerNameField.setBounds(150, 140, 200, 25);
        frame.add(managerNameField);

        JButton addButton = new JButton("Add Team");
        addButton.setBounds(150, 180, 100, 30);
        addButton.addActionListener((ActionEvent e) -> {
            int teamNumber = Integer.parseInt(teamNumberField.getText());
            String name = nameField.getText();
            String city = cityField.getText();
            String managerName = managerNameField.getText();
            if (name.isEmpty() || city.isEmpty() || managerName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields!");
            }
            else if (teamNumber < 0) {
                JOptionPane.showMessageDialog(frame, "Team number must be a positive integer!");
            }
            else if (teamNumber > 999) {
                JOptionPane.showMessageDialog(frame, "Team number must be less than 1000!");
            }
            else if (name.length() > 50) {
                JOptionPane.showMessageDialog(frame, "Name must be less than 50 characters!");
            }
            else if (city.length() > 50) {
                JOptionPane.showMessageDialog(frame, "City must be less than 50 characters!");
            }
            else if (managerName.length() > 50) {
                JOptionPane.showMessageDialog(frame, "Manager name must be less than 50 characters!");
            }
            else {
                try {
                    teamManager.addTeam(teamNumber, name, city, managerName);
                    JOptionPane.showMessageDialog(frame, "Team added successfully!");

                } catch (HeadlessException | ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(frame, "Failed to add team!");
                    e1.printStackTrace();
                }
            }
        });
        frame.add(addButton);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new TeamGUI();
    }
}
