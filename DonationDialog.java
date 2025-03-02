import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;

public class DonationDialog extends JDialog {
    private JTextField amountField;
    private JTextField cardNumberField;
    private JTextField donorNameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTextField IDFond;

    public DonationDialog(JFrame parent, String shelterName) {
        super(parent, "Make a Donation", true);

        // Crearea componentelor
        JLabel titleLabel = new JLabel("Make a Donation for " + shelterName);
        titleLabel.setFont(new Font("Valentine Taste", Font.BOLD, 16));

        JLabel idLabel = new JLabel("ID:");
        JLabel amountLabel = new JLabel("Amount:");
        JLabel cardNumberLabel = new JLabel("Card Number:");
        JLabel donorNameLabel = new JLabel("Donor Name:");
        JLabel addressLabel = new JLabel("Address:");
        JLabel phoneLabel = new JLabel("Phone:");


        IDFond= new JTextField(10);
        amountField = new JTextField(10);
        cardNumberField = new JTextField(16);
        donorNameField = new JTextField(20);
        addressField = new JTextField(20);
        phoneField = new JTextField(12);

        JButton donateButton = new JButton("Donate");

        // Adăugarea acțiunii la butonul de donație
        donateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeDonation(shelterName);
            }
        });

        // Crearea panoului principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Adăugarea componentelor la panou
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(createInputPanel(amountLabel, IDFond));
        mainPanel.add(createInputPanel(amountLabel, amountField));
        mainPanel.add(createInputPanel(cardNumberLabel, cardNumberField));
        mainPanel.add(createInputPanel(donorNameLabel, donorNameField));
        mainPanel.add(createInputPanel(addressLabel, addressField));
        mainPanel.add(createInputPanel(phoneLabel, phoneField));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(createButtonPanel(donateButton));

        // Adăugarea panoului principal la fereastra modală
        add(mainPanel);

        // Setările fereastra modală
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
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

    private void makeDonation(String shelterName) {
        String ID = IDFond.getText();
        String amount = amountField.getText();
        String cardNumber = cardNumberField.getText();
        String donorName = donorNameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();

        saveDonationData(donorName, address, phone, amount, cardNumber, shelterName);

        String message = "Donation Details:\n" +
                "ID: " + ID + "\n" +
                "Amount: " + amount + "\n" +
                "Card Number: " + cardNumber + "\n" +
                "Donor Name: " + donorName + "\n" +
                "Address: " + address + "\n" +
                "Phone: " + phone + "\n" +
                "For Shelter: " + shelterName;

        JOptionPane.showMessageDialog(this, message, "Donation Successful", JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }

    private void saveDonationData(String donorName, String address, String phone, String amount, String cardNumber, String shelterName) {
        String url = "jdbc:mysql://localhost/adapostanimale?user=root&password=root";

        try (Connection connection = DriverManager.getConnection(url)) {
            String insertDonorSql = "INSERT INTO Donator (nume_donator, adresa_donator, telefon_donator) VALUES (?, ?, ?)";
            try (PreparedStatement donorStatement = connection.prepareStatement(insertDonorSql, Statement.RETURN_GENERATED_KEYS)) {
                donorStatement.setString(1, donorName);
                donorStatement.setString(2, address);
                donorStatement.setString(3, phone);

                int affectedRows = donorStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating donor failed, no rows affected.");
                }

                try (ResultSet generatedKeys = donorStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int donorId = generatedKeys.getInt(1);

                        String insertDonationSql = "INSERT INTO Donatie (id_donator, suma_donata) VALUES (?, ?)";
                        try (PreparedStatement donationStatement = connection.prepareStatement(insertDonationSql)) {
                            donationStatement.setInt(1, donorId);
                            donationStatement.setBigDecimal(2, new BigDecimal(amount));
                            donationStatement.executeUpdate();
                        }
                    } else {
                        throw new SQLException("Creating donor failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ShelterList shelterList = new ShelterList();
            shelterList.setVisible(true);
        });
    }
}
