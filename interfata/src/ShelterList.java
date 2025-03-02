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

public class ShelterList extends JFrame {

    private JComboBox<String> shelterComboBox;

    public ShelterList() {
        // Setări pentru fereastră
        setTitle("Shelter List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creare panou principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Adăugare titlu
        JLabel titleLabel = new JLabel("Available shelters");
        titleLabel.setFont(new Font("Valentine Taste", Font.BOLD, 24));
        titleLabel.setForeground(new Color(150, 107, 157));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Adăugare lista derulantă pentru adaposturi cu un mesaj de selecție
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.X_AXIS));

        JLabel selectLabel = new JLabel("Select shelter");
        selectLabel.setForeground(Color.BLACK);  // Culorile pot fi ajustate
        comboBoxPanel.add(selectLabel);
        comboBoxPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        shelterComboBox = new JComboBox<>(getShelters().toArray(new String[0]));
        shelterComboBox.setSelectedIndex(0);
        shelterComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBoxPanel.add(shelterComboBox);

        mainPanel.add(comboBoxPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Adăugare butoane pentru fiecare adapost
        JButton donateButton = new JButton("Donate");
        JButton adoptButton = new JButton("Adopt");

        donateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deschide pagina de donații pentru adapostul specific
                openDonationPage(getSelectedShelter());
            }
        });

        adoptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deschide pagina de adopții pentru adapostul specific
                openAdoptionPage(getSelectedShelter());
            }
        });

        JPanel buttonPanel = createButtonPanel(donateButton, adoptButton);
        mainPanel.add(buttonPanel);

        // Adăugare panou principal la fereastră
        add(mainPanel);

        // Setări pentru fereastră
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createButtonPanel(JButton donateButton, JButton adoptButton) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(donateButton);
        buttonPanel.add(adoptButton);
        return buttonPanel;
    }

    private List<String> getShelters() {
        // Implementarea metodei pentru a obține lista de adaposturi din baza de date
        List<String> shelters = new ArrayList<>();
        // Conexiunea la baza de date și execuția unui query
        String url = "jdbc:mysql://localhost/adapostanimale?user=root&password=root";
        String sql = "SELECT nume_adapost FROM adapost";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                shelters.add(resultSet.getString("nume_adapost"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shelters;
    }

    private String getSelectedShelter() {
        return shelterComboBox.getSelectedItem().toString();
    }

    private void openAdoptionPage(String shelterName) {
        SwingUtilities.invokeLater(() -> new AdoptionPage(shelterName));
    }

    private void openDonationPage(String shelterName) {
        JOptionPane.showMessageDialog(this, "Open Donation Page for " + shelterName);
        DonationDialog donationDialog = new DonationDialog(this, shelterName);
        donationDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShelterList::new);
    }
}
