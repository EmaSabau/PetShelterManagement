import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateAccount extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;

    public CreateAccount() {
        // Setările ferestrei
        setTitle("AdoptaPet - Create Account");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Închide doar fereastra curentă, nu întreaga aplicație

        // Crearea componentelor
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Valentine Taste", Font.BOLD, 24));
        titleLabel.setForeground(new Color(150, 107, 157));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel emailLabel = new JLabel("Email:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        emailField = new JTextField(20);
        JButton createAccountButton = new JButton("Create Account");

        // Setarea fontului pentru etichete
        Font labelFont = new Font("Times New Roman", Font.PLAIN, 14);
        usernameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);

        // Adăugarea acțiunii la butonul de creare cont
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Salvare date în baza de date la apăsarea butonului "Create Account"
                saveUserData();
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
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(createInputPanel(emailLabel, emailField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
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

    private void saveUserData() {
        // Conectare la baza de date
        String url = "jdbc:mysql://localhost/adapostanimale?user=root&password=root";
        try (Connection connection = DriverManager.getConnection(url)) {
            // Creare comandă SQL pentru inserarea datelor în tabelul Utilizatori
            String sql = "INSERT INTO Utilizatori (nume_utilizator, parola_utilizator, email_utilizator) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, usernameField.getText());
                statement.setString(2, new String(passwordField.getPassword()));
                statement.setString(3, emailField.getText());

                // Executare comandă SQL pentru inserarea datelor
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Account created successfully!");
                    // Închiderea ferestrei CreateAccount după crearea contului
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to create account. Please try again.");
                }
            }
        } catch (SQLException ex) {
            System.err.println("An SQL Exception occurred. Details are provided below:");
            ex.printStackTrace(System.err);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CreateAccount());
    }
}
