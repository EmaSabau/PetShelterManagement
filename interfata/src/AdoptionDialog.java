import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdoptionDialog extends JDialog {

    private int animalId;
    private int locationId;
    private int shelterId;

    public AdoptionDialog(JFrame parent, String animalName) {
        super(parent, "Adopta " + animalName, true);

        // Setările ferestrei de dialog
        setLayout(new BorderLayout());
        setSize(300, 200);
        setLocationRelativeTo(parent);

        this.animalId = animalId;
        this.locationId = locationId;
        this.shelterId = shelterId;

        // Crearea componentelor
        JLabel nameLabel = new JLabel("Nume:");
        JTextField nameField = new JTextField();

        JLabel addressLabel = new JLabel("Adresa:");
        JTextField addressField = new JTextField();

        JLabel phoneLabel = new JLabel("Telefon:");
        JTextField phoneField = new JTextField();

        JButton adoptButton = new JButton("Adopta");
        adoptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementează acțiunile pentru adoptare
                String adopterName = nameField.getText();
                String adopterAddress = addressField.getText();
                String adopterPhone = phoneField.getText();

                // Salvează informațiile în baza de date
                saveAdopterInfo(adopterName, adopterAddress, adopterPhone);

                deleteAnimal(animalId, locationId, shelterId);

                // Închide fereastra de dialog după adoptare
                dispose();
            }
        });

        // Adăugarea componentelor la fereastra de dialog
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10)); // Adăugat spațiu între componente
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adăugat spațiu în jurul panelului

        // Adăugat un panel pentru a centra butonul
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(adoptButton);

        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(addressLabel);
        mainPanel.add(addressField);
        mainPanel.add(phoneLabel);
        mainPanel.add(phoneField);
        mainPanel.add(new JLabel()); // Adăugat un label gol pentru a crea spațiu între telefon și buton
        mainPanel.add(buttonPanel); // Adăugat panelul butonului centrat

        add(mainPanel, BorderLayout.CENTER);
    }

    private void deleteAnimal(int animalId, int locationId, int shelterId) {
        // Conectare la baza de date
        String url = "jdbc:mysql://localhost/AdapostAnimale?user=root&password=root";

        try (Connection connection = DriverManager.getConnection(url)) {
            // Execută procedura stocată StergeAnimal
            String sql = "CALL StergeAnimal(?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, animalId);
                statement.setInt(2, locationId);
                statement.setInt(3, shelterId);

                // Execută procedura stocată
                statement.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void saveAdopterInfo(String name, String address, String phone) {
        // Conectare la baza de date
        String url = "jdbc:mysql://localhost/AdapostAnimale?user=root&password=root";

        try (Connection connection = DriverManager.getConnection(url)) {
            // Executare interogare SQL pentru a salva informațiile în tabela "Client"
            String sql = "INSERT INTO Client (nume_client, adresa_client, telefon_client) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, address);
                statement.setString(3, phone);

                // Execută actualizarea bazei de date
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdoptionDialog dialog = new AdoptionDialog(new JFrame(), "Nume Animal Exemplu");
            dialog.setVisible(true);
        });
    }
}