import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class TeamGUI {
    private JFrame mainFrame;
    private JPanel addTeamPanel;
    private JPanel addPlayerPanel;
    private JTextField teamNumberField;
    private JTextField nameField;
    private JTextField cityField;
    private JTextField managerNameField;
    private TeamManager teamManager;

    public TeamGUI() {
        // create the mainFrame
        mainFrame = new JFrame("Tennis League Management");
        mainFrame.setVisible(true);
        mainFrame.setSize(800, 800);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);
        
        // create the addTeamForm and addPlayerForm
        addTeamForm();
        addPlayerForm();
        
        // add the addTeamPanel and addPlayerPanel to the mainFrame
        mainFrame.add(addTeamPanel, 0, 0);
        addTeamPanel.setLocation(10, 10);
        mainFrame.add(new JLabel("Add Team"), 0, 0);
        
        mainFrame.add(addPlayerPanel, 0, 1);
        addPlayerPanel.setLocation(10, 250);
        mainFrame.add(new JLabel("Add Player"), 0, 1);
        


    }
    // create a new form to add a team to the MySQL database with TeamNumber, Name, City, ManagerName
    public final void addTeamForm() {
        teamManager = new TeamManager();
        addTeamPanel = new JPanel();
        addTeamPanel.setSize(400, 300);
        addTeamPanel.setLayout(null);
        addTeamPanel.setVisible(true);

        JLabel teamNumberLabel = new JLabel("Team Number:");
        teamNumberLabel.setBounds(20, 20, 100, 25);
        addTeamPanel.add(teamNumberLabel);

        teamNumberField = new JTextField();
        teamNumberField.setBounds(150, 20, 200, 25);
        addTeamPanel.add(teamNumberField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 60, 100, 25);
        addTeamPanel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 60, 200, 25);
        addTeamPanel.add(nameField);

        JLabel cityLabel = new JLabel("City:");
        cityLabel.setBounds(20, 100, 100, 25);
        addTeamPanel.add(cityLabel);

        cityField = new JTextField();
        cityField.setBounds(150, 100, 200, 25);
        addTeamPanel.add(cityField);

        JLabel managerNameLabel = new JLabel("Manager Name:");
        managerNameLabel.setBounds(20, 140, 100, 25);
        addTeamPanel.add(managerNameLabel);

        managerNameField = new JTextField();
        managerNameField.setBounds(150, 140, 200, 25);
        addTeamPanel.add(managerNameField);

        JButton addButton = new JButton("Add Team");
        addButton.setBounds(150, 180, 100, 30);
        addButton.addActionListener((ActionEvent e) -> {
            int teamNumber = Integer.parseInt(teamNumberField.getText());
            String name = nameField.getText();
            String city = cityField.getText();
            String managerName = managerNameField.getText();
            // validate the input fields
            if (name.isEmpty() || city.isEmpty() || managerName.isEmpty()) {
                JOptionPane.showMessageDialog(addTeamPanel, "Please fill in all fields!");
            }
            else if (teamNumber < 0) {
                JOptionPane.showMessageDialog(addTeamPanel, "Team number must be a positive integer!");
            }
            else if (teamNumber > 999) {
                JOptionPane.showMessageDialog(addTeamPanel, "Team number must be less than 1000!");
            }
            else if (name.length() > 50) {
                JOptionPane.showMessageDialog(addTeamPanel, "Name must be less than 50 characters!");
            }
            else if (city.length() > 50) {
                JOptionPane.showMessageDialog(addTeamPanel, "City must be less than 50 characters!");
            }
            else if (managerName.length() > 50) {
                JOptionPane.showMessageDialog(addTeamPanel, "Manager name must be less than 50 characters!");
            }
            else {
                try {
                    if(teamManager.addTeam(teamNumber, name, city, managerName)){
                        JOptionPane.showMessageDialog(addTeamPanel, "Team added successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(addTeamPanel, "Failed to add team!");
                    }
                } 
                catch (HeadlessException | ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(addTeamPanel, "Failed to add team!");
                    e1.printStackTrace();
                }
            }
        });
        addTeamPanel.add(addButton);
    }

    // create a new form next to the addTeamForm to add a player to the MySQL database with LeagueWideNumber, Name, Age
    public final void addPlayerForm() {
        addPlayerPanel = new JPanel();
        addPlayerPanel.setSize(400, 300);
        addPlayerPanel.setLayout(null);
        addPlayerPanel.setVisible(true);

        JLabel leagueWideNumberLabel = new JLabel("League Wide Number:");
        leagueWideNumberLabel.setBounds(20, 20, 150, 25);
        addPlayerPanel.add(leagueWideNumberLabel);

        JTextField leagueWideNumberField = new JTextField();
        leagueWideNumberField.setBounds(200, 20, 150, 25);
        addPlayerPanel.add(leagueWideNumberField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 60, 100, 25);
        addPlayerPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(200, 60, 150, 25);
        addPlayerPanel.add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(20, 100, 100, 25);
        addPlayerPanel.add(ageLabel);

        JTextField ageField = new JTextField();
        ageField.setBounds(200, 100, 150, 25);
        addPlayerPanel.add(ageField);

        JButton addButton = new JButton("Add Player");
        addButton.setBounds(150, 140, 100, 30);
        addButton.addActionListener((ActionEvent e) -> {
            int leagueWideNumber = Integer.parseInt(leagueWideNumberField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(addPlayerPanel, "Please fill in all fields!");
            }
            else if (leagueWideNumber < 0) {
                JOptionPane.showMessageDialog(addPlayerPanel, "League wide number must be a positive integer!");
            }
            else if (leagueWideNumber > 999) {
                JOptionPane.showMessageDialog(addPlayerPanel, "League wide number must be less than 1000!");
            }
            else if (name.length() > 50) {
                JOptionPane.showMessageDialog(addPlayerPanel, "Name must be less than 50 characters!");
            }
            else if (age < 0) {
                JOptionPane.showMessageDialog(addPlayerPanel, "Age must be a positive integer!");
            }
            else if (age > 150) {
                JOptionPane.showMessageDialog(addPlayerPanel, "Age must be less than 150!");
            }
            else {
                try {
                    if(teamManager.addPlayer(leagueWideNumber, name, age)){
                        JOptionPane.showMessageDialog(addPlayerPanel, "Player added successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(addPlayerPanel, "Failed to add player!");
                    }
                } 
                catch (HeadlessException | ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(addPlayerPanel, "Failed to add player!");
                    e1.printStackTrace();
                }
            }
        });
        addPlayerPanel.add(addButton);
    }

    public static void main(String[] args) {
        new TeamGUI();
    }
}
