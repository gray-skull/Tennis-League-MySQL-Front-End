import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class TeamGUI {
    private final JFrame mainFrame;
    private final JComboBox<String> crudComboBox;
    private final JComboBox<String> tableComboBox;
    private final JLabel crudComboBoxLabel;
    private final JLabel tableComboBoxLabel;
    private final JLabel titleLabel;
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
        mainFrame.setMinimumSize(new Dimension(500, 400));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);

        // Create a title label
        titleLabel = new JLabel("Tennis League Management");
        titleLabel.setBounds(150, 5, 200, 30);
        mainFrame.add(titleLabel); 
        
        // Create drop-down box for CRUD operations
        String[] crudOperations = {"Create", "Read", "Update", "Delete"};
        crudComboBox = new JComboBox<>(crudOperations);
        crudComboBox.setSelectedIndex(0);
        crudComboBox.setBounds(50, 70, 150, 30); // Set position and size
        crudComboBox.setVisible(true);

        // Create label for the CRUD operations drop-down box
        crudComboBoxLabel = new JLabel("CRUD Operation:");
        crudComboBoxLabel.setBounds(50, 40, 150, 30);
        crudComboBoxLabel.setVisible(true);

        // Add the combo box and label to the main frame
        mainFrame.add(crudComboBox);
        mainFrame.add(crudComboBoxLabel);

        // Create drop-down box for selecting the MySQL table
        String[] tables = {"Teams", "Players", "Coaches"};
        tableComboBox = new JComboBox<>(tables);
        tableComboBox.setSelectedIndex(0);
        tableComboBox.setBounds(250, 70, 150, 30); // Set position and size
        tableComboBox.setVisible(true);

        // Create label for the table selection drop-down box
        tableComboBoxLabel = new JLabel("Table:");
        tableComboBoxLabel.setBounds(250, 40, 150, 30);
        tableComboBoxLabel.setVisible(true);

        // Add the combo box and label to the main frame
        mainFrame.add(tableComboBox);
        mainFrame.add(tableComboBoxLabel);

        // Create the forms
        createAddTeamForm();
        createAddPlayerForm();

        // Add the forms to the main frame
        mainFrame.add(addTeamPanel, 0, 0);
        addTeamPanel.setLocation(10, 100);
        addTeamPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Add Team"), 0, 0);

        mainFrame.add(addPlayerPanel, 0, 0);
        addPlayerPanel.setLocation(10, 100);
        addPlayerPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Add Player"), 0, 0);

        // Add action listeners to the combo boxes
        tableComboBox.addActionListener((ActionEvent e) -> {
            updateFormVisibility();
        });

        crudComboBox.addActionListener((ActionEvent e) -> {
            updateFormVisibility();
        });

    // Set the main frame to be visible
    mainFrame.setVisible(true);
    updateFormVisibility();
    
    }

    // Update the visibility of the forms based on the selected table and CRUD operation
    private void updateFormVisibility() {
        String selectedTable = (String) tableComboBox.getSelectedItem();
        String selectedCrud = (String) crudComboBox.getSelectedItem();

        // Hide all forms
        addTeamPanel.setVisible(false);
        addPlayerPanel.setVisible(false);

        if (null != selectedCrud) // Show the appropriate form based on the selected table and CRUD operation
        switch (selectedCrud) {
            case "Create":
                if ("Teams".equals(selectedTable)) {
                    addTeamPanel.setVisible(true);
                } else if ("Players".equals(selectedTable)) {
                    addPlayerPanel.setVisible(true);
                } else if ("Coaches".equals(selectedTable)) {
                    // *** addCoachPanel.setVisible(true); ***
                }
                break;
            case "Read":
                // *** add more forms for other CRUD operations ***
                break;
            case "Update":
                break;
            case "Delete":
                // *** add more forms for other CRUD operations ***
                break;
            default:
                break;
            }
            mainFrame.repaint();
        }

    // create a new form to add a team to the MySQL database with TeamNumber, Name, City, ManagerName
    public final void createAddTeamForm() {
        teamManager = new TeamManager();
        addTeamPanel = new JPanel();
        addTeamPanel.setSize(400, 300);
        addTeamPanel.setLayout(null);

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

    // create a new form to add a player to the MySQL database with LeagueWideNumber, Name, Age
    public final void createAddPlayerForm() {
        addPlayerPanel = new JPanel();
        addPlayerPanel.setSize(400, 300);
        addPlayerPanel.setLayout(null);

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

    // TODO: add form for create coach
    
    // TODO: add form for read team
    // TODO: add form for read player
    // TODO: add form for read coach
    
    // TODO: add form for update team
    // TODO: add form for update player
    // TODO: add form for update coach
    
    // TODO: add form for delete team
    // TODO: add form for delete player
    // TODO: add form for delete coach
    
    // TODO: add form for read all teams
    // TODO: add form for read all players
    // TODO: add form for read all coaches

    public static void main(String[] args) {
        new TeamGUI();
    }
}
