import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdaugaAnimal extends JFrame {

    private JTextField numeField;
    private JTextField specieField;
    private JTextField idLocField;
    private JTextField idTranzactieField;
    private JTextField idAdapostField;

    public AdaugaAnimal() {
        setTitle("Adaugare Animal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crearea componentelor
        JLabel titleLabel = new JLabel("Adauga un animal nou");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(150, 107, 157));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Alinează pe axa X

        // Adăugare field-uri pentru introducerea datelor animalului
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        numeField = new JTextField(20);
        specieField = new JTextField(20);
        idLocField = new JTextField(5);
        idTranzactieField = new JTextField(5);
        idAdapostField = new JTextField(5);

        inputPanel.add(createInputPanel("Nume Animal:", numeField));
        inputPanel.add(createInputPanel("Specie:", specieField));
        inputPanel.add(createInputPanel("ID Loc:", idLocField));
        inputPanel.add(createInputPanel("ID Tranzactie:", idTranzactieField));
        inputPanel.add(createInputPanel("ID Adapost:", idAdapostField));

        // Crearea butonului pentru adăugarea animalului
        JButton adaugaButton = new JButton("Adauga");
        adaugaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adaugaAnimal();
            }
        });

        // Crearea panoului principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Adăugarea componentelor la panou
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(createButtonPanel(adaugaButton));

        // Adăugarea panoului principal la fereastră
        add(mainPanel);

        // Ajustarea aspectului ferestrei
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createInputPanel(String label, JComponent component) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));

        JLabel inputLabel = new JLabel(label);
        inputPanel.add(inputLabel);
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

    private void adaugaAnimal() {
        String nume = numeField.getText();
        String specie = specieField.getText();
        int idLoc = Integer.parseInt(idLocField.getText());
        int idTranzactie = Integer.parseInt(idTranzactieField.getText());
        int idAdapost = Integer.parseInt(idAdapostField.getText());

        // Adaugarea logicii pentru verificarea dacă adapostul este plin
        if (isAdapostFull(idAdapost)) {
            JOptionPane.showMessageDialog(this, "Adapostul este plin. Nu se mai pot adauga animale.");
        } else {
            // Adaugarea animalului in baza de date utilizand procedura stocata
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/AdapostAnimale?user=root&password=root")) {
                String sql = "{CALL AdaugaAnimal(?, ?, ?, ?, ?)}";
                try (CallableStatement statement = connection.prepareCall(sql)) {
                    statement.setString(1, nume);
                    statement.setString(2, specie);
                    statement.setInt(3, idLoc);
                    statement.setInt(4, idTranzactie);
                    statement.setInt(5, idAdapost);

                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean isAdapostFull(int idAdapost) {
        // Adaugarea logicii pentru a verifica dacă adapostul este plin
        // Poți implementa această verificare în funcție de condițiile tale specifice
        // Exemplu: numărul total de locuri comparat cu numărul de animale existente în adapost
        return false; // Schimba condiția în funcție de logica ta
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdaugaAnimal::new);
    }
}
