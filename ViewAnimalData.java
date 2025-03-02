import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewAnimalData extends JFrame {

    private DefaultTableModel tableModel;

    public ViewAnimalData() {
        setTitle("View Animal Data");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crearea modelului pentru tabel
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Animal");
        tableModel.addColumn("Nume Animal");
        tableModel.addColumn("Detalii Veterinare");

        // Crearea tabelului
        JTable animalTable = new JTable(tableModel);

        // Adăugarea tabelului la un JScrollPane pentru a permite derularea
        JScrollPane scrollPane = new JScrollPane(animalTable);

        // Adăugarea JScrollPane la fereastra principală
        add(scrollPane);

        // Afișarea datelor în tabel
        displayAnimalData();

        // Ajustarea aspectului ferestrei
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void displayAnimalData() {
        // Conectarea la baza de date
        String url = "jdbc:mysql://localhost/AdapostAnimale?user=root&password=root";

        try (Connection connection = DriverManager.getConnection(url)) {
            // Executarea interogării SQL pentru a obține datele din vederea istoricveterinaranimal
            String sql = "SELECT * FROM adapostanimale.istoricveterinaranimal";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Adăugarea datelor în modelul tabelului
                while (resultSet.next()) {
                    int idAnimal = resultSet.getInt("id_animal");
                    String numeAnimal = resultSet.getString("nume_animal");
                    String detaliiVeterinare = resultSet.getString("detalii_veterinare");

                    tableModel.addRow(new Object[]{idAnimal, numeAnimal, detaliiVeterinare});
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewAnimalData::new);
    }
}
