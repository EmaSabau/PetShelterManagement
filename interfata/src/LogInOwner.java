import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogInOwner extends JFrame {

    private JComboBox<String> adapostComboBox;
    private JTextField ownerNameField;

    public LogInOwner() {
        setTitle("Owner Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crearea componentelor
        JLabel titleLabel = new JLabel("Owner Login");
        titleLabel.setFont(new Font("Valentine Taste", Font.BOLD, 24));
        titleLabel.setForeground(new Color(150, 107, 157));

        // Adăugare lista derulantă pentru adaposturi cu un mesaj de selecție
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.X_AXIS));

        JLabel adapostLabel = new JLabel("Select Shelter");
        adapostLabel.setForeground(Color.BLACK);
        comboBoxPanel.add(adapostLabel);
        comboBoxPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        adapostComboBox = new JComboBox<>(getAdapostNames().toArray(new String[0]));
        adapostComboBox.setSelectedIndex(0);
        adapostComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBoxPanel.add(adapostComboBox);

        // Adăugare câmp pentru introducerea numelui ownerului
        JLabel ownerNameLabel = new JLabel("Owner Name:");
        ownerNameField = new JTextField();

        // Crearea butonului de login
        JButton loginButton = new JButton("Log In");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAdapost = (String) adapostComboBox.getSelectedItem();
                String ownerName = ownerNameField.getText();

                // Verificare nume owner înainte de autentificare
                if (checkOwnerName(selectedAdapost, ownerName)) {
                    // Nume owner corect, deschide pagina "OwnerActions.java"
                    openOwnerActions(selectedAdapost, ownerName);
                } else {
                    // Nume owner incorect, afișează mesaj de eroare
                    JOptionPane.showMessageDialog(LogInOwner.this, "Nume owner incorect pentru adapostul selectat!", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Crearea panoului principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Adăugarea componentelor la panou
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(comboBoxPanel);
        mainPanel.add(createInputPanel(ownerNameLabel, ownerNameField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(createButtonPanel(loginButton));

        // Adăugarea panoului principal la fereastră
        add(mainPanel);

        // Ajustarea aspectului ferestrei
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private List<String> getAdapostNames() {
        // Înlocuiește această metodă cu codul specific pentru a obține numele adaposturilor din baza de date
        List<String> adapostNames = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/AdapostAnimale?user=root&password=root")) {
            String sql = "SELECT nume_adapost FROM Adapost";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String numeAdapost = resultSet.getString("nume_adapost");
                        adapostNames.add(numeAdapost);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adapostNames;
    }

    private boolean checkOwnerName(String adapostName, String ownerName) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/AdapostAnimale?user=root&password=root")) {
            String sql = "SELECT COUNT(*) FROM Proprietar P " +
                    "JOIN Adapost A ON P.id_adapost = A.id_adapost " +
                    "WHERE P.nume_proprietar = ? AND A.nume_adapost = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, ownerName);
                statement.setString(2, adapostName);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void login() {
        String selectedAdapost = (String) adapostComboBox.getSelectedItem();
        String ownerName = ownerNameField.getText();

        if (checkOwnerName(selectedAdapost, ownerName)) {
            // Autentificare corectă, deschide pagina "OwnerActions.java"
            System.out.println("Autentificare corectă! Deschide OwnerActions.java");
        } else {
            // Autentificare incorectă, afișează mesaj de eroare
            System.out.println("Numele introdus nu este corect pentru adapostul selectat.");
            JOptionPane.showMessageDialog(LogInOwner.this, "Autentificare eșuată. Numele introdus nu este corect pentru adapostul selectat.", "Eroare de autentificare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openOwnerActions(String adapostName, String ownerName) {
        // Deschide pagina "OwnerActions.java" cu parametrii specifici
        OwnerActions ownerActions = new OwnerActions();
        //ownerActions.setVisible(true);

        // Închide fereastra curentă după autentificare
        dispose();
    }

    private JPanel createInputPanel(JLabel label, JComponent component) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.add(label);
        inputPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPanel.add(component);

        if (component instanceof JTextField) {
            ((JTextField) component).setBorder(BorderFactory.createLineBorder(Color.BLACK));
            component.setPreferredSize(new Dimension(200, 30));
        }

        return inputPanel;
    }

    private JPanel createButtonPanel(JButton button) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(button);

        return buttonPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LogInOwner::new);
    }
}
