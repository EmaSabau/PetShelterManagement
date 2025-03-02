import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInCustomer extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LogInCustomer() {
        // Setările ferestrei
        setTitle("AdoptaPet - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crearea componentelor
        JLabel titleLabel = new JLabel("Animal Shelter");
        titleLabel.setFont(new Font("Valentine Taste", Font.BOLD, 32));
        titleLabel.setForeground(new Color(150, 107, 157));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton createAccountButton = new JButton("Create Account");

        // Stilizare pentru etichete
        usernameLabel.setForeground(new Color(150, 107, 157));
        passwordLabel.setForeground(new Color(150, 107, 157));

        // Setarea fontului pentru etichete
        Font labelFont = new Font("Times New Roman", Font.PLAIN, 14);
        usernameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);

        // Setarea fontului pentru butoane
        Font buttonFont = new Font("Times New Roman", Font.BOLD, 16);
        loginButton.setFont(buttonFont);
        createAccountButton.setFont(buttonFont);

        // Setarea culorii de fundal pentru butoane
        loginButton.setBackground(new Color(150, 107, 157));
        loginButton.setForeground(Color.WHITE);
        createAccountButton.setBackground(new Color(150, 107, 157));
        createAccountButton.setForeground(Color.WHITE);

        // Setarea culorii pentru conturul textelor din jurul butoanelor
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        createAccountButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Adăugarea acțiunii la butonul de login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificare autentificare la apăsarea butonului "Login"
                authenticateUser();
            }
        });

        // Adăugarea acțiunii la butonul de creare cont
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deschiderea ferestrei de creare cont la apăsarea butonului "Create Account"
                new CreateAccount();
            }
        });

        // Crearea panel-ului principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Adăugarea componentelor la panel
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(createInputPanel(usernameLabel, usernameField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(createInputPanel(passwordLabel, passwordField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(createButtonPanel(loginButton));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(createButtonPanel(createAccountButton));

        // Adăugarea panel-ului principal la fereastră
        add(mainPanel);

        // Ajustarea aspectului ferestrei
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createInputPanel(JLabel label, JTextField textField) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.add(label);
        inputPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPanel.add(textField);

        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textField.setPreferredSize(new Dimension(200, 30));

        return inputPanel;
    }

    private JPanel createButtonPanel(JButton button) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(button);

        return buttonPanel;
    }

    private void authenticateUser() {
        // Conectare la baza de date
        String url = "jdbc:mysql://localhost/adapostanimale?user=root&password=root";
        try (Connection connection = DriverManager.getConnection(url)) {
            // Creare comandă SQL pentru autentificare
            String sql = "SELECT * FROM Utilizatori WHERE nume_utilizator = ? AND parola_utilizator = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, usernameField.getText());
                statement.setString(2, new String(passwordField.getPassword()));

                // Executare comandă SQL pentru autentificare
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    // Deschiderea paginii ShelterList după autentificare reușită
                    new ShelterList();
                    // Închiderea ferestrei de login după autentificare
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed. Invalid username or password.");
                }
            }
        } catch (SQLException ex) {
            System.err.println("An SQL Exception occurred. Details are provided below:");
            ex.printStackTrace(System.err);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LogInCustomer());
    }
}
