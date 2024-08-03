import java.awt.Color;                      // class for setting colors
import java.awt.Dimension;                  // class for setting dimensions
import java.awt.HeadlessException;          // class for handling exceptions
import java.awt.Toolkit;                    // class for getting screen size
import java.awt.event.ActionEvent;          // class for handling action events
import java.io.Console;                     // class for console input/output
import java.util.List;                      // interface for dynamic array
import javax.swing.*;                       // class for creating GUI components
import javax.swing.table.DefaultTableModel; // class for creating a table model
import javax.swing.table.TableColumnModel;  // class for setting column widths

// class to manage the GUI for the Tennis League Management System
public class TeamGUI {
    private final JFrame mainFrame;                 // main frame for the GUI
    private final JComboBox<String> crudComboBox;   // drop-down box for CRUD operations
    private final JComboBox<String> tableComboBox;  // drop-down box for selecting the MySQL table
    private final JCheckBox viewAllCheckBox;        // check box to view all records

    private final JLabel crudComboBoxLabel;         // label for the CRUD operations drop-down box
    private final JLabel tableComboBoxLabel;        // label for the table selection drop-down box
    private final JLabel titleLabel;                // label for the title
    
    private JPanel createTeamPanel;                 // panel for adding a team
    private JPanel createPlayerPanel;               // panel for adding a player
    private JPanel createCoachPanel;                // panel for adding a coach           
    private JPanel readTeamPanel;                   // panel for reading a team
    private JPanel readPlayerPanel;                 // panel for reading a player
    private JPanel readCoachPanel;                  // panel for reading a coach
    private JPanel updateTeamPanel;                 // panel for updating a team
    private JPanel updatePlayerPanel;               // panel for updating a player
    private JPanel updateCoachPanel;                // panel for updating a coach
    private JPanel deleteTeamPanel;                 // panel for deleting a team
    private JPanel deletePlayerPanel;               // panel for deleting a player
    private JPanel deleteCoachPanel;                // panel for deleting a coach
    private JPanel readAllTeamsPanel;               // panel for reading all teams
    private JPanel readAllPlayersPanel;             // panel for reading all players
    private JPanel readAllCoachesPanel;             // panel for reading all coaches
    private JScrollPane scrollPane;                 // scroll pane for displaying the table

    private final TeamManager teamManager;          // manager for the Team table
    private final PlayerManager playerManager;      // manager for the Player table
    private final CoachManager coachManager;        // manager for the Coach table

    // Constructor
    public TeamGUI() {
        // create the TeamManager, PlayerManager, and CoachManager
        teamManager = new TeamManager();
        playerManager = new PlayerManager();
        coachManager = new CoachManager();

        // create the mainFrame
        mainFrame = new JFrame("Tennis League Management");
        mainFrame.setVisible(true);
        mainFrame.setMinimumSize(new Dimension(500, 400));
        mainFrame.setSize(500, 400);
        
        // set the main frame to be in the center of the screen
        mainFrame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 250, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 200);
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

        // create a check box next to the table select drop-down box to view all records
        viewAllCheckBox = new JCheckBox("All Records?");
        viewAllCheckBox.setBounds(400, 40, 150, 50);
        viewAllCheckBox.setHorizontalTextPosition(SwingConstants.CENTER);
        viewAllCheckBox.setVerticalTextPosition(SwingConstants.TOP);
        viewAllCheckBox.setVisible(false);

        // Add the combo box and label to the main frame
        mainFrame.add(tableComboBox);
        mainFrame.add(tableComboBoxLabel);
        mainFrame.add(viewAllCheckBox);

        // initialize the forms
        initCreateTeamForm();
        initCreatePlayerForm();
        initCreateCoachForm();
        initReadTeamForm();
        initReadPlayerForm();
        initReadCoachForm();
        initUpdateTeamForm();
        initUpdatePlayerForm();
        initUpdateCoachForm();
        initDeleteTeamForm();
        initDeletePlayerForm();
        initDeleteCoachForm();
        initReadAllTeamsForm();
        initReadAllPlayersForm();
        initReadAllCoachesForm();

        // Add action listeners to the combo boxes and buttons
        tableComboBox.addActionListener((ActionEvent e) -> {
            updateFormVisibility();
            mainFrame.pack();
        });

        crudComboBox.addActionListener((ActionEvent e) -> {
            updateFormVisibility();
            mainFrame.pack();
        });

        viewAllCheckBox.addActionListener((ActionEvent e) -> {
            updateFormVisibility();
            mainFrame.pack();
        });

    // Set the main frame to be visible
    mainFrame.setVisible(true);
    updateFormVisibility();
    mainFrame.pack();
    
    }

    // Update the visibility of the forms based on the selected table and CRUD operation
    private void updateFormVisibility() {
        String selectedTable = (String) tableComboBox.getSelectedItem();
        String selectedCrud = (String) crudComboBox.getSelectedItem();

        // Hide all forms
        createTeamPanel.setVisible(false);
        createPlayerPanel.setVisible(false);
        createCoachPanel.setVisible(false);
        updateTeamPanel.setVisible(false);
        updatePlayerPanel.setVisible(false);
        updateCoachPanel.setVisible(false);
        deleteTeamPanel.setVisible(false);
        deletePlayerPanel.setVisible(false);
        deleteCoachPanel.setVisible(false);
        readTeamPanel.setVisible(false);
        readPlayerPanel.setVisible(false);
        readCoachPanel.setVisible(false);
        scrollPane.setVisible(false);
        viewAllCheckBox.setVisible(false);
        readAllTeamsPanel.setVisible(false);
        readAllPlayersPanel.setVisible(false);
        readAllCoachesPanel.setVisible(false);

        // Show the appropriate form based on the selected table and CRUD operation
        if (null != selectedCrud)
        // switch statement to determine which form to show based on the selected CRUD operation 
        switch (selectedCrud) {
            // if the selected CRUD operation is Create
            case "Create" -> {
                if (null != selectedTable)
                    switch (selectedTable) {
                        case "Teams" -> {
                            createTeamPanel.setVisible(true);
                            createPlayerPanel.setVisible(false);
                            createCoachPanel.setVisible(false);
                            readTeamPanel.setVisible(false);
                            readPlayerPanel.setVisible(false);
                            readCoachPanel.setVisible(false);
                            updateTeamPanel.setVisible(false);
                            updatePlayerPanel.setVisible(false);
                            updateCoachPanel.setVisible(false);
                            deleteTeamPanel.setVisible(false);
                            deletePlayerPanel.setVisible(false);
                            deleteCoachPanel.setVisible(false);

                        }
                        case "Players" -> {
                            createPlayerPanel.setVisible(true);
                            createTeamPanel.setVisible(false);
                            createCoachPanel.setVisible(false);
                            readTeamPanel.setVisible(false);
                            readPlayerPanel.setVisible(false);
                            readCoachPanel.setVisible(false);
                            updateTeamPanel.setVisible(false);
                            updatePlayerPanel.setVisible(false);
                            updateCoachPanel.setVisible(false);
                            deleteTeamPanel.setVisible(false);
                            deletePlayerPanel.setVisible(false);
                            deleteCoachPanel.setVisible(false);

                        }
                        case "Coaches" -> {
                            createCoachPanel.setVisible(true);
                            createTeamPanel.setVisible(false);
                            createPlayerPanel.setVisible(false);
                            readTeamPanel.setVisible(false);
                            readPlayerPanel.setVisible(false);
                            readCoachPanel.setVisible(false);
                            updateTeamPanel.setVisible(false);
                            updatePlayerPanel.setVisible(false);
                            updateCoachPanel.setVisible(false);
                            deleteTeamPanel.setVisible(false);
                            deletePlayerPanel.setVisible(false);
                            deleteCoachPanel.setVisible(false);

                        }
                        default -> {}
                    }
                scrollPane.setVisible(false);
                viewAllCheckBox.setVisible(false);
            }

            // if the selected CRUD operation is Read
            case "Read" -> {
                if (null != selectedTable)
                    switch (selectedTable) {
                        case "Teams" -> {
                            if(viewAllCheckBox.isSelected()){
                                readAllTeamsPanel.setVisible(true);
                                
                                readTeamPanel.setVisible(false);
                                readPlayerPanel.setVisible(false);
                                readCoachPanel.setVisible(false);
                                updateTeamPanel.setVisible(false);
                                updatePlayerPanel.setVisible(false);
                                updateCoachPanel.setVisible(false);
                                deleteTeamPanel.setVisible(false);
                                deletePlayerPanel.setVisible(false);
                                deleteCoachPanel.setVisible(false);
                                createPlayerPanel.setVisible(false);
                                createCoachPanel.setVisible(false);
                                createTeamPanel.setVisible(false);

                            }
                            else {
                                readTeamPanel.setVisible(true);
                                
                                readAllTeamsPanel.setVisible(false);
                                readPlayerPanel.setVisible(false);
                                readCoachPanel.setVisible(false);
                                updateTeamPanel.setVisible(false);
                                updatePlayerPanel.setVisible(false);
                                updateCoachPanel.setVisible(false);
                                deleteTeamPanel.setVisible(false);
                                deletePlayerPanel.setVisible(false);
                                deleteCoachPanel.setVisible(false);
                                createPlayerPanel.setVisible(false);
                                createCoachPanel.setVisible(false);
                                createTeamPanel.setVisible(false);

                            }
                        }
                        case "Players" -> {
                            if(viewAllCheckBox.isSelected()){
                                readAllPlayersPanel.setVisible(true);
                                
                                readTeamPanel.setVisible(false);
                                readPlayerPanel.setVisible(false);
                                readCoachPanel.setVisible(false);
                                updateTeamPanel.setVisible(false);
                                updatePlayerPanel.setVisible(false);
                                updateCoachPanel.setVisible(false);
                                deleteTeamPanel.setVisible(false);
                                deletePlayerPanel.setVisible(false);
                                deleteCoachPanel.setVisible(false);
                                createPlayerPanel.setVisible(false);
                                createCoachPanel.setVisible(false);
                                createTeamPanel.setVisible(false);

                            }
                            else {
                                readPlayerPanel.setVisible(true);
                                
                                readAllPlayersPanel.setVisible(false);
                                readTeamPanel.setVisible(false);
                                readCoachPanel.setVisible(false);
                                updateTeamPanel.setVisible(false);
                                updatePlayerPanel.setVisible(false);
                                updateCoachPanel.setVisible(false);
                                deleteTeamPanel.setVisible(false);
                                deletePlayerPanel.setVisible(false);
                                deleteCoachPanel.setVisible(false);
                                createPlayerPanel.setVisible(false);
                                createCoachPanel.setVisible(false);
                                createTeamPanel.setVisible(false);

                            }
                        }
                        case "Coaches" -> {
                            if(viewAllCheckBox.isSelected()){
                                readAllCoachesPanel.setVisible(true);
                                
                                readTeamPanel.setVisible(false);
                                readPlayerPanel.setVisible(false);
                                readCoachPanel.setVisible(false);
                                updateTeamPanel.setVisible(false);
                                updatePlayerPanel.setVisible(false);
                                updateCoachPanel.setVisible(false);
                                deleteTeamPanel.setVisible(false);
                                deletePlayerPanel.setVisible(false);
                                deleteCoachPanel.setVisible(false);
                                createPlayerPanel.setVisible(false);
                                createCoachPanel.setVisible(false);
                                createTeamPanel.setVisible(false);

                            }
                            else {
                                readCoachPanel.setVisible(true);
                                
                                readAllCoachesPanel.setVisible(false);
                                readTeamPanel.setVisible(false);
                                readPlayerPanel.setVisible(false);
                                updateTeamPanel.setVisible(false);
                                updatePlayerPanel.setVisible(false);
                                updateCoachPanel.setVisible(false);
                                deleteTeamPanel.setVisible(false);
                                deletePlayerPanel.setVisible(false);
                                deleteCoachPanel.setVisible(false);
                                createPlayerPanel.setVisible(false);
                                createCoachPanel.setVisible(false);
                                createTeamPanel.setVisible(false);

                            }
                        }
                        default -> {}
                    }
                scrollPane.setVisible(true);
                viewAllCheckBox.setVisible(true);
            }

            // if the selected CRUD operation is Update
            case "Update" -> {
                if (null != selectedTable)
                    switch (selectedTable) {
                        case "Teams" -> {
                            updateTeamPanel.setVisible(true);
                            updateCoachPanel.setVisible(false);
                            updatePlayerPanel.setVisible(false);
                            deleteTeamPanel.setVisible(false);
                            deleteCoachPanel.setVisible(false);
                            deletePlayerPanel.setVisible(false);
                            createPlayerPanel.setVisible(false);
                            createCoachPanel.setVisible(false);
                            createTeamPanel.setVisible(false);
                            readCoachPanel.setVisible(false);
                            readPlayerPanel.setVisible(false);
                            readTeamPanel.setVisible(false);

                        }
                        case "Players" -> {
                            updatePlayerPanel.setVisible(true);
                            updateCoachPanel.setVisible(false);
                            updateTeamPanel.setVisible(false);
                            deleteTeamPanel.setVisible(false);
                            deleteCoachPanel.setVisible(false);
                            deletePlayerPanel.setVisible(false);
                            createPlayerPanel.setVisible(false);
                            createCoachPanel.setVisible(false);
                            createTeamPanel.setVisible(false);
                            readCoachPanel.setVisible(false);
                            readPlayerPanel.setVisible(false);
                            readTeamPanel.setVisible(false);

                        }
                        case "Coaches" -> {
                            updateCoachPanel.setVisible(true);
                            updatePlayerPanel.setVisible(false);
                            updateTeamPanel.setVisible(false);
                            deleteTeamPanel.setVisible(false);
                            deleteCoachPanel.setVisible(false);
                            deletePlayerPanel.setVisible(false);
                            createPlayerPanel.setVisible(false);
                            createCoachPanel.setVisible(false);
                            createTeamPanel.setVisible(false);
                            readCoachPanel.setVisible(false);
                            readPlayerPanel.setVisible(false);
                            readTeamPanel.setVisible(false);

                        }
                        default -> {}
                    }
                scrollPane.setVisible(false);
                viewAllCheckBox.setVisible(false);
            }

            // if the selected CRUD operation is Delete
            case "Delete" -> {
                if (null != selectedTable)
                    switch (selectedTable) {
                        case "Teams" -> {
                            deleteTeamPanel.setVisible(true);
                            deleteCoachPanel.setVisible(false);
                            deletePlayerPanel.setVisible(false);
                            createPlayerPanel.setVisible(false);
                            createCoachPanel.setVisible(false);
                            createTeamPanel.setVisible(false);
                            readCoachPanel.setVisible(false);
                            readPlayerPanel.setVisible(false);
                            readTeamPanel.setVisible(false);
                            updateCoachPanel.setVisible(false);
                            updatePlayerPanel.setVisible(false);
                            updateTeamPanel.setVisible(false);

                        }
                        case "Players" -> {
                            deletePlayerPanel.setVisible(true);
                            deleteCoachPanel.setVisible(false);
                            deleteTeamPanel.setVisible(false);
                            createPlayerPanel.setVisible(false);
                            createCoachPanel.setVisible(false);
                            createTeamPanel.setVisible(false);
                            readCoachPanel.setVisible(false);
                            readPlayerPanel.setVisible(false);
                            readTeamPanel.setVisible(false);
                            updateCoachPanel.setVisible(false);
                            updatePlayerPanel.setVisible(false);
                            updateTeamPanel.setVisible(false);

                        }
                        case "Coaches" -> {
                            deleteCoachPanel.setVisible(true);
                            deletePlayerPanel.setVisible(false);
                            deleteTeamPanel.setVisible(false);
                            createPlayerPanel.setVisible(false);
                            createCoachPanel.setVisible(false);
                            createTeamPanel.setVisible(false);
                            readCoachPanel.setVisible(false);
                            readPlayerPanel.setVisible(false);
                            readTeamPanel.setVisible(false);
                            updateCoachPanel.setVisible(false);
                            updatePlayerPanel.setVisible(false);
                            updateTeamPanel.setVisible(false);

                        }
                        default -> {}
                    }
                scrollPane.setVisible(false);
                viewAllCheckBox.setVisible(false);
            }

            default -> {
            }
            }
            // update the main frame
            mainFrame.repaint();
        }

    // create a new form to add a team to the MySQL database with TeamNumber, Name, City, ManagerName
    public final void initCreateTeamForm() {
        createTeamPanel = new JPanel();
        createTeamPanel.setSize(400, 300);
        createTeamPanel.setLayout(null);

        // elements to input the team number, name, city, and manager name
        JLabel teamNumberLabel = new JLabel("Team Number:");
        teamNumberLabel.setBounds(20, 20, 100, 25);
        createTeamPanel.add(teamNumberLabel);

        JTextField teamNumberField = new JTextField();
        teamNumberField.setBounds(150, 20, 200, 25);
        createTeamPanel.add(teamNumberField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 60, 100, 25);
        createTeamPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 60, 200, 25);
        createTeamPanel.add(nameField);

        JLabel cityLabel = new JLabel("City:");
        cityLabel.setBounds(20, 100, 100, 25);
        createTeamPanel.add(cityLabel);

        JTextField cityField = new JTextField();
        cityField.setBounds(150, 100, 200, 25);
        createTeamPanel.add(cityField);

        JLabel managerNameLabel = new JLabel("Manager Name:");
        managerNameLabel.setBounds(20, 140, 100, 25);
        createTeamPanel.add(managerNameLabel);

        JTextField managerNameField = new JTextField();
        managerNameField.setBounds(150, 140, 200, 25);
        createTeamPanel.add(managerNameField);

        // button to add the team to the database
        JButton addButton = new JButton("Create Team");
        addButton.setBounds(150, 180, 150, 30);
        addButton.addActionListener((ActionEvent e) -> {
            int teamNumber = Integer.parseInt(teamNumberField.getText());
            String name = nameField.getText();
            String city = cityField.getText();
            String managerName = managerNameField.getText();
            
            // validate the input fields
            if (name.isEmpty() || city.isEmpty() || managerName.isEmpty()) {
                JOptionPane.showMessageDialog(createTeamPanel, "Please fill in all fields!");
            }
            else if (teamNumber < 0) {
                JOptionPane.showMessageDialog(createTeamPanel, "Team number must be a positive integer!");
            }
            else if (teamNumber > 999) {
                JOptionPane.showMessageDialog(createTeamPanel, "Team number must be less than 1000!");
            }
            else if (name.length() > 50) {
                JOptionPane.showMessageDialog(createTeamPanel, "Name must be less than 50 characters!");
            }
            else if (city.length() > 50) {
                JOptionPane.showMessageDialog(createTeamPanel, "City must be less than 50 characters!");
            }
            else if (managerName.length() > 50) {
                JOptionPane.showMessageDialog(createTeamPanel, "Manager name must be less than 50 characters!");
            }
            else {
                try {
                    // create the team in the database
                    if(teamManager.createTeam(teamNumber, name, city, managerName)){
                        JOptionPane.showMessageDialog(createTeamPanel, "Team created successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(createTeamPanel, "Failed to create team!");
                    }
                } 
                catch (HeadlessException | ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(createTeamPanel, "Failed to create team!");
                }
            }
        });
        // add the button to the panel
        createTeamPanel.add(addButton);

        // add the panel to the main frame
        mainFrame.add(createTeamPanel, 0, 0);
        createTeamPanel.setLocation(10, 100);
        createTeamPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Add Team"), 0, 0);
    }

    // create a new form to add a player to the MySQL database with LeagueWideNumber, Name, Age
    public final void initCreatePlayerForm() {
        createPlayerPanel = new JPanel();
        createPlayerPanel.setSize(400, 300);
        createPlayerPanel.setLayout(null);

        // elements to input the league wide number, name, and age
        JLabel leagueWideNumberLabel = new JLabel("League Wide Number:");
        leagueWideNumberLabel.setBounds(20, 20, 150, 25);
        createPlayerPanel.add(leagueWideNumberLabel);

        JTextField leagueWideNumberField = new JTextField();
        leagueWideNumberField.setBounds(200, 20, 150, 25);
        createPlayerPanel.add(leagueWideNumberField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 60, 100, 25);
        createPlayerPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(200, 60, 150, 25);
        createPlayerPanel.add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(20, 100, 100, 25);
        createPlayerPanel.add(ageLabel);

        JTextField ageField = new JTextField();
        ageField.setBounds(200, 100, 150, 25);
        createPlayerPanel.add(ageField);

        // button to add the player to the database
        JButton addButton = new JButton("Add Player");
        addButton.setBounds(150, 140, 150, 30);
        addButton.addActionListener((ActionEvent e) -> {
            int leagueWideNumber = Integer.parseInt(leagueWideNumberField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());

            // validate the input fields
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(createPlayerPanel, "Please fill in all fields!");
            }
            else if (leagueWideNumber < 0) {
                JOptionPane.showMessageDialog(createPlayerPanel, "League wide number must be a positive integer!");
            }
            else if (leagueWideNumber > 100000) {
                JOptionPane.showMessageDialog(createPlayerPanel, "League wide number must be less than 100000!");
            }
            else if (name.length() > 50) {
                JOptionPane.showMessageDialog(createPlayerPanel, "Name must be less than 50 characters!");
            }
            else if (age < 0) {
                JOptionPane.showMessageDialog(createPlayerPanel, "Age must be a positive integer!");
            }
            else if (age > 150) {
                JOptionPane.showMessageDialog(createPlayerPanel, "Age must be less than 150!");
            }
            else {
                try {
                    // create the player in the database
                    if(playerManager.createPlayer(leagueWideNumber, name, age)){
                        JOptionPane.showMessageDialog(createPlayerPanel, "Player created successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(createPlayerPanel, "Failed to create player!");
                    }
                } 
                catch (HeadlessException | ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(createPlayerPanel, "Failed to create player!");
                }
            }
        });
        // add the button to the panel
        createPlayerPanel.add(addButton);

        // add the panel to the main frame
        mainFrame.add(createPlayerPanel, 0, 0);
        createPlayerPanel.setLocation(10, 100);
        createPlayerPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Add Player"), 0, 0);
    }

    // create a new form to add a coach to the MySQL database with CoachName, TelephoneNumber, CoachTeamNumber
    public final void initCreateCoachForm(){
        createCoachPanel = new JPanel();
        createCoachPanel.setSize(400, 300);
        createCoachPanel.setLayout(null);

        // elements to input the coach name, telephone number, and team number
        JLabel coachNameLabel = new JLabel("Name:");
        coachNameLabel.setBounds(20, 20, 100, 25);
        createCoachPanel.add(coachNameLabel);

        JTextField coachNameField = new JTextField();
        coachNameField.setBounds(150, 20, 200, 25);
        createCoachPanel.add(coachNameField);

        JLabel telephoneNumberLabel = new JLabel("Telephone #:");
        telephoneNumberLabel.setBounds(20, 60, 100, 25);
        createCoachPanel.add(telephoneNumberLabel);

        JTextField telephoneNumberField = new JTextField();
        telephoneNumberField.setBounds(150, 60, 200, 25);
        createCoachPanel.add(telephoneNumberField);

        JLabel coachTeamNumberLabel = new JLabel("Team Number:");
        coachTeamNumberLabel.setBounds(20, 100, 100, 25);
        createCoachPanel.add(coachTeamNumberLabel);

        JTextField coachTeamNumberField = new JTextField();
        coachTeamNumberField.setBounds(150, 100, 200, 25);
        createCoachPanel.add(coachTeamNumberField);

        // button to add the coach to the database
        JButton addButton = new JButton("Add Coach");
        addButton.setBounds(150, 180, 150, 30);
        addButton.addActionListener((ActionEvent e) -> {
            String name = coachNameField.getText();
            String telephoneNumber = telephoneNumberField.getText();
            int teamNumber = Integer.parseInt(coachTeamNumberField.getText());
            
            // validate the input fields
            if (name.isEmpty() || telephoneNumber.isEmpty() || coachTeamNumberField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(createCoachPanel, "Please fill in all fields!");
            }
            else if (name.length() > 50) {
                JOptionPane.showMessageDialog(createCoachPanel, "Name must be less than 50 characters!");
            }
            else if (telephoneNumber.length() > 50) {
                JOptionPane.showMessageDialog(createCoachPanel, "Telephone number must be less than 50 characters!");
            }
            else if (teamNumber < 0) {
                JOptionPane.showMessageDialog(createCoachPanel, "Team number must be a positive integer!");
            }
            else if (teamNumber > 999) {
                JOptionPane.showMessageDialog(createCoachPanel, "Team number must be less than 1000!");
            }
            // check if teamNumber exists in the Team table
            else if (!teamManager.teamExists(teamNumber)) {
                JOptionPane.showMessageDialog(createCoachPanel, "Team number does not exist!");
            }
            else {
                try {
                    // create the coach in the database
                    if(coachManager.createCoach(name, telephoneNumber, teamNumber)){
                        JOptionPane.showMessageDialog(createCoachPanel, "Coach created successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(createCoachPanel, "Failed to create coach!");
                    }
                } 
                catch (HeadlessException | ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(createCoachPanel, "Failed to create coach!");
                }
            }
        });
        // add the button to the panel
        createCoachPanel.add(addButton);

        // add the panel to the main frame
        mainFrame.add(createCoachPanel, 0, 0);
        createCoachPanel.setLocation(10, 100);
        createCoachPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Add Coach"), 0, 0);
    }

    // create a new form to read a team from the MySQL database with TeamNumber
    public final void initReadTeamForm() {
        readTeamPanel = new JPanel();
        readTeamPanel.setSize(500, 400);
        readTeamPanel.setLayout(null);

        //element to input the team number
        JLabel teamNumberLabel = new JLabel("Team Number:");
        teamNumberLabel.setBounds(20, 20, 100, 25);
        readTeamPanel.add(teamNumberLabel);

        //element to input the team number
        JTextField teamNumberField = new JTextField();
        teamNumberField.setBounds(150, 20, 200, 25);
        readTeamPanel.add(teamNumberField);

        //element to display the team details
        JTable teamDetails = new JTable();
        scrollPane = new JScrollPane(teamDetails);
        //readTeamPanel.add(scrollPane);
        
        //set the bounds of the scroll pane
        scrollPane.setBounds(20, 60, 420, 120);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        
        //set the scroll pane to always show the vertical and horizontal scroll bars
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        //add the scroll pane to the panel
        readTeamPanel.add(scrollPane);

        // create a button to read the team
        JButton readButton = new JButton("Read Team");
        readButton.setBounds(150, 200, 150, 30);
        // add an action listener to the button
        readButton.addActionListener((ActionEvent e) -> {
            int teamNumber = Integer.parseInt(teamNumberField.getText());
            
            // validate the input field
            if (teamNumber < 0) {
                JOptionPane.showMessageDialog(readTeamPanel, "Team number must be a positive integer!");
            }
            else if (teamNumber > 999) {
                JOptionPane.showMessageDialog(readTeamPanel, "Team number must be less than 1000!");
            }
            else {
                // check if the team exists in the Team table
                if(teamManager.teamExists(teamNumber)){
                    //read team from database
                    JOptionPane.showMessageDialog(readTeamPanel, "Team found!");
                    try{
                        // get the team details from the database
                        List<Object[]> teamDetailsList = teamManager.readTeam(teamNumber);
                        DefaultTableModel model = new DefaultTableModel();
                        // set the column names
                        model.addColumn("Team #");
                        model.addColumn("Name");
                        model.addColumn("City");
                        model.addColumn("Manager Name");

                        // add the team details to the table
                        for (Object[] details : teamDetailsList) {
                            model.addRow(details);
                        }
                        teamDetails.setModel(model);
                        
                        // edit the column names and sizes
                        TableColumnModel columnModel = teamDetails.getColumnModel();
                        columnModel.getColumn(0).setHeaderValue(teamDetails.getColumnName(0));
                        columnModel.getColumn(0).setResizable(true);
                        columnModel.getColumn(0).setPreferredWidth(40);

                        columnModel.getColumn(1).setHeaderValue(teamDetails.getColumnName(1));
                        columnModel.getColumn(1).setResizable(true);
                        columnModel.getColumn(1).setPreferredWidth(140);

                        columnModel.getColumn(2).setHeaderValue(teamDetails.getColumnName(2));
                        columnModel.getColumn(2).setResizable(true);
                        columnModel.getColumn(2).setPreferredWidth(100);

                        columnModel.getColumn(3).setHeaderValue(teamDetails.getColumnName(3));
                        columnModel.getColumn(3).setResizable(true);
                        columnModel.getColumn(3).setPreferredWidth(100);

                        // set the table to the scroll pane
                        teamDetails.setColumnModel(columnModel);
                        // set the table to be visible
                        teamDetails.doLayout();
                    }                
                    catch (Exception e1) {
                        // display an error message if the team details could not be read
                        JOptionPane.showMessageDialog(readTeamPanel, "Failed to read team!");
                        Console console = System.console();
                        console.printf("Error: %s\n", e1.getMessage());
                    }
                }
                else {
                    JOptionPane.showMessageDialog(readTeamPanel, "Team not found!");
                }
            }
        });
        // add the button to the panel
        readTeamPanel.add(readButton);

        // add the panel to the main frame
        mainFrame.add(readTeamPanel, 0, 0);
        readTeamPanel.setLocation(10, 100);
        readTeamPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Read Team"), 0, 0);

    }
    
    // create a new form to read a player from the MySQL database with PlayerID
    public final void initReadPlayerForm(){
        readPlayerPanel = new JPanel();
        readPlayerPanel.setSize(500, 400);
        readPlayerPanel.setLayout(null);

        //elements to input the player ID, and display the player details
        JLabel PlayerIDLabel = new JLabel("Player ID:");
        PlayerIDLabel.setBounds(20, 20, 100, 25);
        readPlayerPanel.add(PlayerIDLabel);

        JTextField playerIDField = new JTextField();
        playerIDField.setBounds(150, 20, 200, 25);
        readPlayerPanel.add(playerIDField);

        JTable playerDetails = new JTable();
        scrollPane = new JScrollPane(playerDetails);
        scrollPane.setBounds(20, 60, 420, 120);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        readPlayerPanel.add(scrollPane);

        // create a button to read the player
        JButton readButton = new JButton("Read Player");
        readButton.setBounds(150, 200, 150, 30);
        readButton.addActionListener((ActionEvent e) -> {
            int PlayerID = Integer.parseInt(playerIDField.getText());

            // validate the input field
            if (PlayerID < 0) {
                JOptionPane.showMessageDialog(readPlayerPanel, "PlayerID must be a positive integer!");
            }
            else if (PlayerID > 1000) {
                JOptionPane.showMessageDialog(readPlayerPanel, "PlayerID must be less than 1000!");
            }
            else {
                // check if the player exists in the Player table
                if(playerManager.playerExists(PlayerID)){
                    JOptionPane.showMessageDialog(readPlayerPanel, "Player found!");
                    //read player from database
                    try{
                        List<Object[]> playerDetailsList = playerManager.readPlayer(PlayerID);
                        DefaultTableModel model = new DefaultTableModel();
                        model.addColumn("Player ID");
                        model.addColumn("League Wide Number");
                        model.addColumn("Name");
                        model.addColumn("Age");

                        // add the player details to the table
                        for (Object[] details : playerDetailsList) {
                            model.addRow(details);
                        }
                        // set the column names and sizes
                        playerDetails.setModel(model);
                        TableColumnModel columnModel = playerDetails.getColumnModel();
                        columnModel.getColumn(0).setHeaderValue(playerDetails.getColumnName(0));
                        columnModel.getColumn(0).setResizable(true);
                        columnModel.getColumn(0).setPreferredWidth(100);

                        columnModel.getColumn(1).setHeaderValue(playerDetails.getColumnName(1));
                        columnModel.getColumn(1).setResizable(true);
                        columnModel.getColumn(1).setPreferredWidth(140);

                        columnModel.getColumn(2).setHeaderValue(playerDetails.getColumnName(2));
                        columnModel.getColumn(2).setResizable(true);
                        columnModel.getColumn(2).setPreferredWidth(100);

                        columnModel.getColumn(3).setHeaderValue(playerDetails.getColumnName(3));
                        columnModel.getColumn(3).setResizable(true);
                        columnModel.getColumn(3).setPreferredWidth(100);

                        playerDetails.setColumnModel(columnModel);
                        playerDetails.doLayout();
                    }                
                    catch (Exception e1) {
                        JOptionPane.showMessageDialog(readPlayerPanel, "Failed to read player!");
                        Console console = System.console();
                        console.printf("Error: %s\n", e1.getMessage());

                    }
                }
                else {
                    JOptionPane.showMessageDialog(readPlayerPanel, "Player not found!");
                }
            }
           
        });
        // add the button to the panel
        readPlayerPanel.add(readButton);

        // add the panel to the main frame
        mainFrame.add(readPlayerPanel, 0, 0);
        readPlayerPanel.setLocation(10, 100);
        readPlayerPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Read Player"), 0, 0);
    }
    
    // create a new form to read a coach from the MySQL database with CoachID
    public final void initReadCoachForm () {
        readCoachPanel = new JPanel();
        readCoachPanel.setSize(500, 400);
        readCoachPanel.setLayout(null);

        //element to input the coach ID, and display the coach details
        JLabel coachIDLabel = new JLabel("Coach ID:");
        coachIDLabel.setBounds(20, 20, 100, 25);
        readCoachPanel.add(coachIDLabel);

        JTextField coachIDField = new JTextField();
        coachIDField.setBounds(150, 20, 200, 25);
        readCoachPanel.add(coachIDField);

        //element to display the coach details
        JTable coachDetails = new JTable();
        scrollPane = new JScrollPane(coachDetails);
        scrollPane.setBounds(20, 60, 420, 120);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        readCoachPanel.add(scrollPane);

        // create a button to read the coach
        JButton readButton = new JButton("Read Coach");
        readButton.setBounds(150, 200, 150, 30);
        readButton.addActionListener((ActionEvent e) -> {
            int coachID = Integer.parseInt(coachIDField.getText());

            // validate the input field
            if (coachID < 0) {
                JOptionPane.showMessageDialog(readCoachPanel, "Coach ID must be a positive integer!");
            }
            else if (coachID > 999) {
                JOptionPane.showMessageDialog(readCoachPanel, "Coach ID must be less than 1000!");
            }
            else {
                // check if the coach exists in the Coach table
                if(coachManager.coachExists(coachID)){
                    JOptionPane.showMessageDialog(readCoachPanel, "Coach found!");
                    // read coach from database
                    try{
                        // get the coach details from the database
                        List<Object[]> coachDetailsList = coachManager.readCoach(coachID);
                        DefaultTableModel model = new DefaultTableModel();
                        model.addColumn("Coach ID");
                        model.addColumn("Name");
                        model.addColumn("Telephone Number");
                        model.addColumn("Team Number");

                        // add the coach details to the table
                        for (Object[] details : coachDetailsList) {
                            model.addRow(details);
                        }

                        // set the column names and sizes
                        coachDetails.setModel(model);
                        TableColumnModel columnModel = coachDetails.getColumnModel();
                        columnModel.getColumn(0).setHeaderValue(coachDetails.getColumnName(0));
                        columnModel.getColumn(0).setResizable(true);
                        columnModel.getColumn(0).setPreferredWidth(100);

                        columnModel.getColumn(1).setHeaderValue(coachDetails.getColumnName(1));
                        columnModel.getColumn(1).setResizable(true);
                        columnModel.getColumn(1).setPreferredWidth(140);

                        columnModel.getColumn(2).setHeaderValue(coachDetails.getColumnName(2));
                        columnModel.getColumn(2).setResizable(true);
                        columnModel.getColumn(2).setPreferredWidth(100);

                        columnModel.getColumn(3).setHeaderValue(coachDetails.getColumnName(3));
                        columnModel.getColumn(3).setResizable(true);
                        columnModel.getColumn(3).setPreferredWidth(100);

                        // set the table to the scroll pane
                        coachDetails.setColumnModel(columnModel);
                        scrollPane = new JScrollPane(coachDetails);
                        scrollPane.setBounds(20, 60, 420, 120);
                        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
                        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                        readCoachPanel.add(scrollPane);
                        coachDetails.doLayout();
                    }                
                    catch (Exception e1) {
                        JOptionPane.showMessageDialog(readCoachPanel, "Failed to read coach!");
                        Console console = System.console();
                        console.printf("Error: %s\n", e1.getMessage());
                    }
                }
                else {
                    JOptionPane.showMessageDialog(readCoachPanel, "Coach not found!");
                }
            }
        });
        // add the button to the panel
        readCoachPanel.add(readButton);

        // add the panel to the main frame
        mainFrame.add(readCoachPanel, 0, 0);
        readCoachPanel.setLocation(10, 100);
        readCoachPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Read Coach"), 0, 0);

    }
    
    // create a new form to update a team in the MySQL database with TeamNumber, Name, City, ManagerName
    public final void initUpdateTeamForm() {
        updateTeamPanel = new JPanel();
        updateTeamPanel.setSize(400, 300);
        updateTeamPanel.setLayout(null);

        // elements to input the team number, name, city, and manager name
        JLabel teamNumberLabel = new JLabel("Team Number:");
        teamNumberLabel.setBounds(20, 20, 100, 25);
        updateTeamPanel.add(teamNumberLabel);

        JTextField teamNumberField = new JTextField();
        teamNumberField.setBounds(150, 20, 200, 25);
        updateTeamPanel.add(teamNumberField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 60, 100, 25);
        updateTeamPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 60, 200, 25);
        updateTeamPanel.add(nameField);

        JLabel cityLabel = new JLabel("City:");
        cityLabel.setBounds(20, 100, 100, 25);
        updateTeamPanel.add(cityLabel);

        JTextField cityField = new JTextField();
        cityField.setBounds(150, 100, 200, 25);
        updateTeamPanel.add(cityField);

        JLabel managerNameLabel = new JLabel("Manager Name:");
        managerNameLabel.setBounds(20, 140, 100, 25);
        updateTeamPanel.add(managerNameLabel);

        JTextField managerNameField = new JTextField();
        managerNameField.setBounds(150, 140, 200, 25);
        updateTeamPanel.add(managerNameField);

        // button to update the team in the database
        JButton updateButton = new JButton("Update Team");
        updateButton.setBounds(150, 180, 150, 30);
        updateButton.addActionListener((ActionEvent e) -> {
            int teamNumber = Integer.parseInt(teamNumberField.getText());
            String name = nameField.getText();
            String city = cityField.getText();
            String managerName = managerNameField.getText();
            // validate the input fields
            if (teamNumberField.getText().isEmpty() || name.isEmpty() || city.isEmpty() || managerName.isEmpty()) {
                JOptionPane.showMessageDialog(updateTeamPanel, "Please fill in all fields!");
            }
            else if (teamNumber < 0) {
                JOptionPane.showMessageDialog(updateTeamPanel, "Team number must be a positive integer!");
            }
            else if (teamNumber > 999) {
                JOptionPane.showMessageDialog(updateTeamPanel, "Team number must be less than 1000!");
            }
            else if (name.length() > 50) {
                JOptionPane.showMessageDialog(updateTeamPanel, "Name must be less than 50 characters!");
            }
            else if (city.length() > 50) {
                JOptionPane.showMessageDialog(updateTeamPanel, "City must be less than 50 characters!");
            }
            else if (managerName.length() > 50) {
                JOptionPane.showMessageDialog(updateTeamPanel, "Manager name must be less than 50 characters!");
            }
            else if (!teamManager.teamExists(teamNumber)) {
                JOptionPane.showMessageDialog(updateTeamPanel, "Team number does not exist!");
            }
            else {
                try {
                    // update the team in the database
                    if(teamManager.updateTeam(teamNumber, name, city, managerName)){
                        JOptionPane.showMessageDialog(updateTeamPanel, "Team updated successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(updateTeamPanel, "Failed to update team!");
                    }
                } 
                catch (HeadlessException e1) {
                    JOptionPane.showMessageDialog(updateTeamPanel, "Failed to update team!");
                }
            }
        });
        // add the button to the panel
        updateTeamPanel.add(updateButton);

        // add the panel to the main frame
        mainFrame.add(updateTeamPanel, 0, 0);
        updateTeamPanel.setLocation(10, 100);
        updateTeamPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Update Team"), 0, 0);
    }

    // create a new form to update a player in the MySQL database with PlayerID, LeagueWideNumber, Name, Age
    public final void initUpdatePlayerForm() {
        updatePlayerPanel = new JPanel();
        updatePlayerPanel.setSize(400, 300);
        updatePlayerPanel.setLayout(null);

        // elements to input the player ID, league wide number, name, and age
        JLabel playerIDLabel = new JLabel("Player ID:");
        playerIDLabel.setBounds(20, 20, 100, 25);
        updatePlayerPanel.add(playerIDLabel);

        JTextField playerIDField = new JTextField();
        playerIDField.setBounds(200, 20, 150, 25);
        updatePlayerPanel.add(playerIDField);

        JLabel leagueWideNumberLabel = new JLabel("League Wide Number:");
        leagueWideNumberLabel.setBounds(20, 60, 150, 25);
        updatePlayerPanel.add(leagueWideNumberLabel);

        JTextField leagueWideNumberField = new JTextField();
        leagueWideNumberField.setBounds(200, 60, 150, 25);
        updatePlayerPanel.add(leagueWideNumberField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 100, 100, 25);
        updatePlayerPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(200, 100, 150, 25);
        updatePlayerPanel.add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(20, 140, 100, 25);
        updatePlayerPanel.add(ageLabel);

        JTextField ageField = new JTextField();
        ageField.setBounds(200, 140, 150, 25);
        updatePlayerPanel.add(ageField);

        // button to update the player in the database
        JButton updateButton = new JButton("Update Player");
        updateButton.setBounds(150, 180, 150, 30);
        updateButton.addActionListener((ActionEvent e) -> {
            int PlayerID = Integer.parseInt(playerIDField.getText());
            int leagueWideNumber = Integer.parseInt(leagueWideNumberField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            
            // validate the input fields
            if (leagueWideNumber < 0) {
                JOptionPane.showMessageDialog(updatePlayerPanel, "League wide number must be a positive integer!");
            }
            else if (leagueWideNumber > 100000) {
                JOptionPane.showMessageDialog(updatePlayerPanel, "League wide number must be less than 1000!");
            }
            else if (name.isEmpty() || ageField.getText().isEmpty() || playerIDField.getText().isEmpty() || leagueWideNumberField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(updatePlayerPanel, "Please fill in all fields!");
            }
            else if (name.length() > 50) {
                JOptionPane.showMessageDialog(updatePlayerPanel, "Name must be less than 50 characters!");
            }
            else if (age < 0) {
                JOptionPane.showMessageDialog(updatePlayerPanel, "Age must be a positive integer!");
            }
            else if (age > 150) {
                JOptionPane.showMessageDialog(updatePlayerPanel, "Age must be less than 150!");
            }
            else if (!playerManager.playerExists(PlayerID)) {
                JOptionPane.showMessageDialog(updatePlayerPanel, "Player does not exist!");
            }
            else {
                try {
                    // update the player in the database
                    if(playerManager.updatePlayer(PlayerID, leagueWideNumber, name, age)){
                        JOptionPane.showMessageDialog(updatePlayerPanel, "Player updated successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(updatePlayerPanel, "Failed to update player!");
                    }
                } 
                catch (HeadlessException e1) {
                    JOptionPane.showMessageDialog(updatePlayerPanel, "Failed to update player!");

                }
            }
        });
        // add the button to the panel
        updatePlayerPanel.add(updateButton);

        // add the panel to the main frame
        mainFrame.add(updatePlayerPanel, 0, 0);
        updatePlayerPanel.setLocation(10, 100);
        updatePlayerPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Update Player"), 0, 0);
    }

    // create a new form to update a coach in the MySQL database with CoachID, Name, TelephoneNumber, CoachTeamNumber
    public final void initUpdateCoachForm() {
        updateCoachPanel = new JPanel();
        updateCoachPanel.setSize(400, 300);
        updateCoachPanel.setLayout(null);

        // elements to input the coach ID, name, telephone number, and team number
        JLabel coachIDLabel = new JLabel("Coach ID:");
        coachIDLabel.setBounds(20, 20, 100, 25);
        updateCoachPanel.add(coachIDLabel);

        JTextField coachIDField = new JTextField();
        coachIDField.setBounds(150, 20, 200, 25);
        updateCoachPanel.add(coachIDField);

        JLabel coachNameLabel = new JLabel("Name:");
        coachNameLabel.setBounds(20, 60, 100, 25);
        updateCoachPanel.add(coachNameLabel);

        JTextField coachNameField = new JTextField();
        coachNameField.setBounds(150, 60, 200, 25);
        updateCoachPanel.add(coachNameField);

        JLabel telephoneNumberLabel = new JLabel("Telephone #:");
        telephoneNumberLabel.setBounds(20, 100, 100, 25);
        updateCoachPanel.add(telephoneNumberLabel);

        JTextField telephoneNumberField = new JTextField();
        telephoneNumberField.setBounds(150, 100, 200, 25);
        updateCoachPanel.add(telephoneNumberField);

        JLabel coachTeamNumberLabel = new JLabel("Team Number:");
        coachTeamNumberLabel.setBounds(20, 140, 100, 25);
        updateCoachPanel.add(coachTeamNumberLabel);

        JTextField coachTeamNumberField = new JTextField();
        coachTeamNumberField.setBounds(150, 140, 200, 25);
        updateCoachPanel.add(coachTeamNumberField);

        // button to update the coach in the database
        JButton updateButton = new JButton("Update Coach");
        updateButton.setBounds(150, 180, 150, 30);
        updateButton.addActionListener((ActionEvent e) -> {
            int coachID = Integer.parseInt(coachIDField.getText());
            String name = coachNameField.getText();
            String telephoneNumber = telephoneNumberField.getText();
            int teamNumber = Integer.parseInt(coachTeamNumberField.getText());

            // validate the input fields
            if (coachID < 0) {
                JOptionPane.showMessageDialog(updateCoachPanel, "Coach ID must be a positive integer!");
            }
            else if (coachID > 999) {
                JOptionPane.showMessageDialog(updateCoachPanel, "Coach ID must be less than 1000!");
            }
            else if (name.isEmpty() || telephoneNumber.isEmpty() || coachTeamNumberField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(updateCoachPanel, "Please fill in all fields!");
            }
            else if (name.length() > 50) {
                JOptionPane.showMessageDialog(updateCoachPanel, "Name must be less than 50 characters!");
            }
            else if (telephoneNumber.length() > 50) {
                JOptionPane.showMessageDialog(updateCoachPanel, "Telephone number must be less than 50 characters!");
            }
            else if (teamNumber < 0) {
                JOptionPane.showMessageDialog(updateCoachPanel, "Team number must be a positive integer!");
            }
            else if (teamNumber > 999) {
                JOptionPane.showMessageDialog(updateCoachPanel, "Team number must be less than 1000!");
            }
            else if (!coachManager.coachExists(coachID)) {
                JOptionPane.showMessageDialog(updateCoachPanel, "Coach does not exist!");
            }
            else {
                try {
                    // update the coach in the database
                    if(coachManager.updateCoach(coachID, name, telephoneNumber, teamNumber)){
                        JOptionPane.showMessageDialog(updateCoachPanel, "Coach updated successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(updateCoachPanel, "Failed to update coach!");
                    }
                } 
                catch (HeadlessException e1) {
                    JOptionPane.showMessageDialog(updateCoachPanel, "Failed to update coach!");
                }
            }
        });
        // add the button to the panel
        updateCoachPanel.add(updateButton);

        // add the panel to the main frame
        mainFrame.add(updateCoachPanel, 0, 0);
        updateCoachPanel.setLocation(10, 100);
        updateCoachPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Update Coach"), 0, 0);
    }

    // create a new form to delete a team from the MySQL database with TeamNumber
    public final void initDeleteTeamForm(){
        deleteTeamPanel = new JPanel();
        deleteTeamPanel.setSize(400, 300);
        deleteTeamPanel.setLayout(null);

        // elements to input the team number
        JLabel teamNumberLabel = new JLabel("Team Number:");
        teamNumberLabel.setBounds(20, 20, 100, 25);
        deleteTeamPanel.add(teamNumberLabel);

        JTextField teamNumberField = new JTextField();
        teamNumberField.setBounds(150, 20, 200, 25);    
        deleteTeamPanel.add(teamNumberField);

        // button to delete the team from the database
        JButton deleteButton = new JButton("Delete Team");
        deleteButton.setBounds(150, 180, 150, 30);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener((ActionEvent e) -> {
            int teamNumber = Integer.parseInt(teamNumberField.getText());

            // validate the input field
            if (teamNumber < 0) {
                JOptionPane.showMessageDialog(deleteTeamPanel, "Team number must be a positive integer!");
            }
            else if (teamNumber > 999) {
                JOptionPane.showMessageDialog(deleteTeamPanel, "Team number must be less than 1000!");
            }
            else if (!teamManager.teamExists(teamNumber)) {
                JOptionPane.showMessageDialog(deleteTeamPanel, "Team does not exist!");
            }
            else {
                // display a confirmation dialog
                int response = JOptionPane.showConfirmDialog(deleteTeamPanel, "Are you sure you want to delete this team?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.NO_OPTION) {
                }
                else {
                    // delete the team from the database
                    if(teamManager.deleteTeam(teamNumber)){
                        JOptionPane.showMessageDialog(deleteTeamPanel, "Team deleted successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(deleteTeamPanel, "Failed to delete team!");
                    }
                }
            }
        });
        // add the button to the panel
        deleteTeamPanel.add(deleteButton);

        // add the panel to the main frame
        mainFrame.add(deleteTeamPanel, 0, 0);
        deleteTeamPanel.setLocation(10, 100);
        deleteTeamPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Delete Team"), 0, 0);
    }

    // create a new form to delete a player from the MySQL database with PlayerID
    public final void initDeletePlayerForm(){
        deletePlayerPanel = new JPanel();
        deletePlayerPanel.setSize(400, 300);
        deletePlayerPanel.setLayout(null);

        // elements to input the player ID
        JLabel PlayerIDLabel = new JLabel("Player ID:");
        PlayerIDLabel.setBounds(20, 20, 150, 25);
        deletePlayerPanel.add(PlayerIDLabel);

        JTextField PlayerIDField = new JTextField();
        PlayerIDField.setBounds(200, 20, 150, 25);
        deletePlayerPanel.add(PlayerIDField);

        // button to delete the player from the database
        JButton deleteButton = new JButton("Delete Player");
        deleteButton.setBounds(150, 180, 150, 30);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener((ActionEvent e) -> {
            int PlayerID = Integer.parseInt(PlayerIDField.getText());

            // validate the input field
            if (PlayerID < 0) {
                JOptionPane.showMessageDialog(deletePlayerPanel, "League wide number must be a positive integer!");
            }
            else if (PlayerID > 1000) {
                JOptionPane.showMessageDialog(deletePlayerPanel, "League wide number must be less than 1000!");
            }
            else if (!playerManager.playerExists(PlayerID)) {
                JOptionPane.showMessageDialog(deletePlayerPanel, "Player does not exist!");
            }
            else {
                // display a confirmation dialog
                int response = JOptionPane.showConfirmDialog(deletePlayerPanel, "Are you sure you want to delete this player?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.NO_OPTION) {}
                else {
                    // delete the player from the database
                    if(playerManager.deletePlayer(PlayerID)){
                        JOptionPane.showMessageDialog(deletePlayerPanel, "Player deleted successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(deletePlayerPanel, "Failed to delete player!");
                    }
                }
            }
        });
        // add the button to the panel
        deletePlayerPanel.add(deleteButton);

        // add the panel to the main frame
        mainFrame.add(deletePlayerPanel, 0, 0);
        deletePlayerPanel.setLocation(10, 100);
        deletePlayerPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Delete Player"), 0, 0);
    }

    // create a new form to delete a coach from the MySQL database with CoachID
    public final void initDeleteCoachForm(){
        deleteCoachPanel = new JPanel();
        deleteCoachPanel.setSize(400, 300);
        deleteCoachPanel.setLayout(null);

        // elements to input the coach ID
        JLabel coachIDLabel = new JLabel("Coach ID:");
        coachIDLabel.setBounds(20, 20, 100, 25);
        deleteCoachPanel.add(coachIDLabel);

        JTextField coachIDField = new JTextField();
        coachIDField.setBounds(150, 20, 200, 25);
        deleteCoachPanel.add(coachIDField);

        // button to delete the coach from the database
        JButton deleteButton = new JButton("Delete Coach");
        deleteButton.setBounds(150, 180, 150, 30);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener((ActionEvent e) -> {
            int coachID = Integer.parseInt(coachIDField.getText());

            // validate the input field
            if (coachID < 0) {
                JOptionPane.showMessageDialog(deleteCoachPanel, "Coach ID must be a positive integer!");
            }
            else if (coachID > 999) {
                JOptionPane.showMessageDialog(deleteCoachPanel, "Coach ID must be less than 1000!");
            }
            else if (!coachManager.coachExists(coachID)) {
                JOptionPane.showMessageDialog(deleteCoachPanel, "Coach does not exist!");
            }
            else {
                // display a confirmation dialog
                int response = JOptionPane.showConfirmDialog(deleteCoachPanel, "Are you sure you want to delete this coach?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.NO_OPTION) {}
                else {
                    // delete the coach from the database
                    if(coachManager.deleteCoach(coachID)){
                        JOptionPane.showMessageDialog(deleteCoachPanel, "Coach deleted successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(deleteCoachPanel, "Failed to delete coach!");
                    }
                }
            }
        });
        // add the button to the panel
        deleteCoachPanel.add(deleteButton);

        // add the panel to the main frame
        mainFrame.add(deleteCoachPanel, 0, 0);
        deleteCoachPanel.setLocation(10, 100);
        deleteCoachPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Delete Coach"), 0, 0);
    }

    // create a new form to read all teams from the MySQL database
    public final void initReadAllTeamsForm(){
        readAllTeamsPanel = new JPanel();
        readAllTeamsPanel.setSize(600, 500);
        readAllTeamsPanel.setLayout(null);

        //element to display the team details
        JTable teamDetails = new JTable();
        scrollPane = new JScrollPane(teamDetails);
        scrollPane.setBounds(20, 20, 420, 160);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        readAllTeamsPanel.add(scrollPane);

        // button to read all teams from the database
        JButton readButton = new JButton("Read All Teams");
        readButton.setBounds(150, 200, 150, 30);

        // read all teams from the database
        readButton.addActionListener((ActionEvent e) -> {
            try {
                // get the team details from the database
                List<Object[]> teamDetailsList = teamManager.readAllTeams();
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Team Number");
                model.addColumn("Name");
                model.addColumn("City");
                model.addColumn("Manager Name");

                // add the team details to the table
                for (Object[] details : teamDetailsList) {
                    model.addRow(details);
                }
                teamDetails.setModel(model);
                
                // set the column names and sizes
                TableColumnModel columnModel = teamDetails.getColumnModel();
                columnModel.getColumn(0).setHeaderValue(teamDetails.getColumnName(0));
                columnModel.getColumn(1).setHeaderValue(teamDetails.getColumnName(1));
                columnModel.getColumn(2).setHeaderValue(teamDetails.getColumnName(2));
                columnModel.getColumn(3).setHeaderValue(teamDetails.getColumnName(3));

                teamDetails.setColumnModel(columnModel);
                teamDetails.doLayout();
            }                
            catch (Exception e1) {
                JOptionPane.showMessageDialog(readAllTeamsPanel, "Failed to read teams!");
                Console console = System.console();
                console.printf("Error: %s\n", e1.getMessage());
            }

        });
        // add the button to the panel
        readAllTeamsPanel.add(readButton);

        // add the panel to the main frame
        mainFrame.add(readAllTeamsPanel, 0, 0);
        readAllTeamsPanel.setLocation(10, 100);
        readAllTeamsPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Read All Teams"), 0, 0);
    }
    
    // create a new form to read all players from the MySQL database
    public final void initReadAllPlayersForm(){
        readAllPlayersPanel = new JPanel();
        readAllPlayersPanel.setSize(600, 500);
        readAllPlayersPanel.setLayout(null);

        //element to display the player details
        JTable playerDetails = new JTable();
        scrollPane = new JScrollPane(playerDetails);
        readAllPlayersPanel.add(scrollPane);

        scrollPane.setBounds(20, 20, 420, 160);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        readAllPlayersPanel.add(scrollPane);

        // button to read all players from the database
        JButton readButton = new JButton("Read All Players");
        readButton.setBounds(150, 200, 150, 30);

        // read all players from the database
        readButton.addActionListener((ActionEvent e) -> {
            try {
                // get the player details from the database
                List<Object[]> playerDetailsList = playerManager.readAllPlayers();
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Player ID");
                model.addColumn("League Wide Number");
                model.addColumn("Name");
                model.addColumn("Age");

                // add the player details to the table
                for (Object[] details : playerDetailsList) {
                    model.addRow(details);
                }
                playerDetails.setModel(model);
      
                // set the column names and sizes
                TableColumnModel columnModel = playerDetails.getColumnModel();
                columnModel.getColumn(0).setHeaderValue(playerDetails.getColumnName(0));
                columnModel.getColumn(1).setHeaderValue(playerDetails.getColumnName(1));
                columnModel.getColumn(2).setHeaderValue(playerDetails.getColumnName(2));
                columnModel.getColumn(3).setHeaderValue(playerDetails.getColumnName(3));

                playerDetails.setColumnModel(columnModel);
                playerDetails.doLayout();
            }                
            catch (Exception e1) {
                JOptionPane.showMessageDialog(readAllPlayersPanel, "Failed to read players!");
                Console console = System.console();
                console.printf("Error: %s\n", e1.getMessage());
            }

        });
        // add the button to the panel
        readAllPlayersPanel.add(readButton);

        // add the panel to the main frame
        mainFrame.add(readAllPlayersPanel, 0, 0);
        readAllPlayersPanel.setLocation(10, 100);
        readAllPlayersPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Read All Players"), 0, 0);
    }
    
    // create a new form to read all coaches from the MySQL database
    public final void initReadAllCoachesForm(){
        readAllCoachesPanel = new JPanel();
        readAllCoachesPanel.setSize(600, 500);
        readAllCoachesPanel.setLayout(null);

        //element to display the coach details
        JTable coachDetails = new JTable();
        scrollPane = new JScrollPane(coachDetails);
        readAllCoachesPanel.add(scrollPane);

        scrollPane.setBounds(20, 20, 420, 160);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        readAllCoachesPanel.add(scrollPane);

        // button to read all coaches from the database
        JButton readButton = new JButton("Read All Coaches");
        readButton.setBounds(150, 200, 150, 30);

        // read all coaches from the database
        readButton.addActionListener((ActionEvent e) -> {
            try {
                // get the coach details from the database
                List<Object[]> coachDetailsList = coachManager.readAllCoaches();
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Coach ID");
                model.addColumn("Name");
                model.addColumn("Telephone Number");
                model.addColumn("Team Number");

                for (Object[] details : coachDetailsList) {
                    model.addRow(details);
                }
                
                // set the column names and sizes
                coachDetails.setModel(model);
                TableColumnModel columnModel = coachDetails.getColumnModel();
                columnModel.getColumn(0).setHeaderValue(coachDetails.getColumnName(0));
                columnModel.getColumn(1).setHeaderValue(coachDetails.getColumnName(1));
                columnModel.getColumn(2).setHeaderValue(coachDetails.getColumnName(2));
                columnModel.getColumn(3).setHeaderValue(coachDetails.getColumnName(3));

                coachDetails.setColumnModel(columnModel);
                coachDetails.doLayout();
            }                
            catch (Exception e1) {
                JOptionPane.showMessageDialog(readAllCoachesPanel, "Failed to read coaches!");
                Console console = System.console();
                console.printf("Error: %s\n", e1.getMessage());
            }

        });
        // add the button to the panel
        readAllCoachesPanel.add(readButton);

        // add the panel to the main frame
        mainFrame.add(readAllCoachesPanel, 0, 0);
        readAllCoachesPanel.setLocation(10, 100);
        readAllCoachesPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Read All Coaches"), 0, 0);
    }

    // main method to run the program
    public static void main(String[] args) {
        // create a new TeamGUI object
        new TeamGUI();
    }
}
