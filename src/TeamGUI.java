import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.Console;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class TeamGUI {
    private final JFrame mainFrame;
    private final JComboBox<String> crudComboBox;
    private final JComboBox<String> tableComboBox;
    private final JCheckBox viewAllCheckBox;

    private final JLabel crudComboBoxLabel;
    private final JLabel tableComboBoxLabel;
    private final JLabel titleLabel;
    
    private JPanel createTeamPanel;
    private JPanel createPlayerPanel;
    private JPanel createCoachPanel;
    private JPanel readTeamPanel;
    private JPanel readPlayerPanel;
    private JPanel readCoachPanel;
    private JPanel updateTeamPanel;
    private JPanel updatePlayerPanel;
    private JPanel updateCoachPanel;
    private JPanel deleteTeamPanel;
    private JPanel deletePlayerPanel;
    private JPanel deleteCoachPanel;
    private JPanel readAllTeamsPanel;
    private JPanel readAllPlayersPanel;
    private JPanel readAllCoachesPanel;
    private JScrollPane scrollPane;

    /*private JTextField teamNumberField;
    private JTextField nameField;
    private JTextField cityField;
    private JTextField managerNameField;
    private JTextField playerIDField;
    private JTextField leagueWideNumberField;
    private JTextField ageField;
    private JTextField coachIDField;
    private JTextField coachNameField;
    private JTextField telephoneNumberField;
    private JTextField coachTeamNumberField;
    */ 

    private final TeamManager teamManager;
    private final PlayerManager playerManager;
    private final CoachManager coachManager;

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
        switch (selectedCrud) {
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
            mainFrame.repaint();
        }

    // create a new form to add a team to the MySQL database with TeamNumber, Name, City, ManagerName
    public final void initCreateTeamForm() {
        createTeamPanel = new JPanel();
        createTeamPanel.setSize(400, 300);
        createTeamPanel.setLayout(null);

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
        createTeamPanel.add(addButton);

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

        JButton addButton = new JButton("Add Player");
        addButton.setBounds(150, 140, 150, 30);
        addButton.addActionListener((ActionEvent e) -> {
            int leagueWideNumber = Integer.parseInt(leagueWideNumberField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
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
        createPlayerPanel.add(addButton);

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

        JButton addButton = new JButton("Add Coach");
        addButton.setBounds(150, 180, 150, 30);
        addButton.addActionListener((ActionEvent e) -> {
            String name = coachNameField.getText();
            String telephoneNumber = telephoneNumberField.getText();
            int teamNumber = Integer.parseInt(coachTeamNumberField.getText());
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
        createCoachPanel.add(addButton);

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
            if (teamNumber < 0) {
                JOptionPane.showMessageDialog(readTeamPanel, "Team number must be a positive integer!");
            }
            else if (teamNumber > 999) {
                JOptionPane.showMessageDialog(readTeamPanel, "Team number must be less than 1000!");
            }
            else {
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

        JButton readButton = new JButton("Read Player");
        readButton.setBounds(150, 200, 150, 30);
        readButton.addActionListener((ActionEvent e) -> {
            int PlayerID = Integer.parseInt(playerIDField.getText());
            if (PlayerID < 0) {
                JOptionPane.showMessageDialog(readPlayerPanel, "PlayerID must be a positive integer!");
            }
            else if (PlayerID > 1000) {
                JOptionPane.showMessageDialog(readPlayerPanel, "PlayerID must be less than 1000!");
            }
            else {
                if(playerManager.playerExists(PlayerID)){
                    JOptionPane.showMessageDialog(readPlayerPanel, "Player found!");
                    try{
                        List<Object[]> playerDetailsList = playerManager.readPlayer(PlayerID);
                        DefaultTableModel model = new DefaultTableModel();
                        model.addColumn("Player ID");
                        model.addColumn("League Wide Number");
                        model.addColumn("Name");
                        model.addColumn("Age");

                        for (Object[] details : playerDetailsList) {
                            model.addRow(details);
                        }
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
        readPlayerPanel.add(readButton);

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
            if (coachID < 0) {
                JOptionPane.showMessageDialog(readCoachPanel, "Coach ID must be a positive integer!");
            }
            else if (coachID > 999) {
                JOptionPane.showMessageDialog(readCoachPanel, "Coach ID must be less than 1000!");
            }
            else {
                if(coachManager.coachExists(coachID)){
                    JOptionPane.showMessageDialog(readCoachPanel, "Coach found!");
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

        JButton updateButton = new JButton("Update Team");
        updateButton.setBounds(150, 180, 150, 30);
        updateButton.addActionListener((ActionEvent e) -> {
            int teamNumber = Integer.parseInt(teamNumberField.getText());
            String name = nameField.getText();
            String city = cityField.getText();
            String managerName = managerNameField.getText();
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
        updateTeamPanel.add(updateButton);

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

        JButton updateButton = new JButton("Update Player");
        updateButton.setBounds(150, 180, 150, 30);
        updateButton.addActionListener((ActionEvent e) -> {
            int PlayerID = Integer.parseInt(playerIDField.getText());
            int leagueWideNumber = Integer.parseInt(leagueWideNumberField.getText());
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
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
        updatePlayerPanel.add(updateButton);

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

        JButton updateButton = new JButton("Update Coach");
        updateButton.setBounds(150, 180, 150, 30);
        updateButton.addActionListener((ActionEvent e) -> {
            int coachID = Integer.parseInt(coachIDField.getText());
            String name = coachNameField.getText();
            String telephoneNumber = telephoneNumberField.getText();
            int teamNumber = Integer.parseInt(coachTeamNumberField.getText());
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
        updateCoachPanel.add(updateButton);

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

        JLabel teamNumberLabel = new JLabel("Team Number:");
        teamNumberLabel.setBounds(20, 20, 100, 25);
        deleteTeamPanel.add(teamNumberLabel);

        JTextField teamNumberField = new JTextField();
        teamNumberField.setBounds(150, 20, 200, 25);    
        deleteTeamPanel.add(teamNumberField);

        JButton deleteButton = new JButton("Delete Team");
        deleteButton.setBounds(150, 180, 150, 30);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener((ActionEvent e) -> {
            int teamNumber = Integer.parseInt(teamNumberField.getText());
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
                    if(teamManager.deleteTeam(teamNumber)){
                        JOptionPane.showMessageDialog(deleteTeamPanel, "Team deleted successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(deleteTeamPanel, "Failed to delete team!");
                    }
                }
            }
        });
        deleteTeamPanel.add(deleteButton);

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

        JLabel PlayerIDLabel = new JLabel("Player ID:");
        PlayerIDLabel.setBounds(20, 20, 150, 25);
        deletePlayerPanel.add(PlayerIDLabel);

        JTextField PlayerIDField = new JTextField();
        PlayerIDField.setBounds(200, 20, 150, 25);
        deletePlayerPanel.add(PlayerIDField);

        JButton deleteButton = new JButton("Delete Player");
        deleteButton.setBounds(150, 180, 150, 30);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener((ActionEvent e) -> {
            int PlayerID = Integer.parseInt(PlayerIDField.getText());
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
                    if(playerManager.deletePlayer(PlayerID)){
                        JOptionPane.showMessageDialog(deletePlayerPanel, "Player deleted successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(deletePlayerPanel, "Failed to delete player!");
                    }
                }
            }
        });
        deletePlayerPanel.add(deleteButton);

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

        JLabel coachIDLabel = new JLabel("Coach ID:");
        coachIDLabel.setBounds(20, 20, 100, 25);
        deleteCoachPanel.add(coachIDLabel);

        JTextField coachIDField = new JTextField();
        coachIDField.setBounds(150, 20, 200, 25);
        deleteCoachPanel.add(coachIDField);

        JButton deleteButton = new JButton("Delete Coach");
        deleteButton.setBounds(150, 180, 150, 30);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener((ActionEvent e) -> {
            int coachID = Integer.parseInt(coachIDField.getText());
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
                    if(coachManager.deleteCoach(coachID)){
                        JOptionPane.showMessageDialog(deleteCoachPanel, "Coach deleted successfully!");
                    }
                    else {
                        JOptionPane.showMessageDialog(deleteCoachPanel, "Failed to delete coach!");
                    }
                }
            }
        });
        deleteCoachPanel.add(deleteButton);

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

        JButton readButton = new JButton("Read All Teams");
        readButton.setBounds(150, 200, 150, 30);

        readButton.addActionListener((ActionEvent e) -> {
            try {
                List<Object[]> teamDetailsList = teamManager.readAllTeams();
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Team Number");
                model.addColumn("Name");
                model.addColumn("City");
                model.addColumn("Manager Name");

                for (Object[] details : teamDetailsList) {
                    model.addRow(details);
                }
                teamDetails.setModel(model);
                
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
        readAllTeamsPanel.add(readButton);

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

        JButton readButton = new JButton("Read All Players");
        readButton.setBounds(150, 200, 150, 30);

        readButton.addActionListener((ActionEvent e) -> {
            try {
                List<Object[]> playerDetailsList = playerManager.readAllPlayers();
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Player ID");
                model.addColumn("League Wide Number");
                model.addColumn("Name");
                model.addColumn("Age");

                for (Object[] details : playerDetailsList) {
                    model.addRow(details);
                }
                playerDetails.setModel(model);
                
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
        readAllPlayersPanel.add(readButton);

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

        JButton readButton = new JButton("Read All Coaches");
        readButton.setBounds(150, 200, 150, 30);

        readButton.addActionListener((ActionEvent e) -> {
            try {
                List<Object[]> coachDetailsList = coachManager.readAllCoaches();
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Coach ID");
                model.addColumn("Name");
                model.addColumn("Telephone Number");
                model.addColumn("Team Number");

                for (Object[] details : coachDetailsList) {
                    model.addRow(details);
                }
                
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
        readAllCoachesPanel.add(readButton);

        mainFrame.add(readAllCoachesPanel, 0, 0);
        readAllCoachesPanel.setLocation(10, 100);
        readAllCoachesPanel.setVisible(false); // Hide the panel
        mainFrame.add(new JLabel("Read All Coaches"), 0, 0);
    }

    public static void main(String[] args) {
        new TeamGUI();
    }
}
