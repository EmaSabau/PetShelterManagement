import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewTransactions extends JFrame {

    private JTable transactionsTable;

    public ViewTransactions() {
        setTitle("View Transactions");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crearea butoanelor
        JButton viewDonationsButton = new JButton("View Donations");
        JButton viewPurchasesButton = new JButton("View Purchases");

        // Adăugarea acțiunilor la butoane
        viewDonationsButton.addActionListener(e -> openTransactionsWindow("donatiidonatori", "Donations"));
        viewPurchasesButton.addActionListener(e -> openTransactionsWindow("Provizii_View", "Purchases"));

        // Crearea panoului principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        // Adăugarea butoanelor la panou
        mainPanel.add(viewDonationsButton);
        mainPanel.add(viewPurchasesButton);

        // Adăugarea panoului la fereastră
        add(mainPanel);

        // Ajustarea aspectului ferestrei
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void openTransactionsWindow(String viewName, String windowTitle) {
        // Crearea ferestrei pentru afișarea datelor din view
        JFrame transactionsFrame = new JFrame(windowTitle);
        transactionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        transactionsFrame.setLayout(new BorderLayout());

        // Crearea modelului tabelului
        DefaultTableModel tableModel = new DefaultTableModel();

        // Crearea tabelului și adăugarea lui la o fereastră cu panou de derulare
        transactionsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(transactionsTable);
        transactionsFrame.add(scrollPane, BorderLayout.CENTER);

        // Afișarea datelor în tabel
        displayTransactionsData(viewName, tableModel);

        // Ajustarea aspectului ferestrei de afișare a tranzacțiilor
        transactionsFrame.pack();
        transactionsFrame.setLocationRelativeTo(null);
        transactionsFrame.setVisible(true);
    }

    private void displayTransactionsData(String viewName, DefaultTableModel tableModel) {
        // Conectare la baza de date
        String url = "jdbc:mysql://localhost/AdapostAnimale?user=root&password=root";

        try (Connection connection = DriverManager.getConnection(url)) {
            // Executare interogare SQL pentru a obține datele din view
            String sql = "SELECT * FROM " + viewName;
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Afișarea datelor în tabel
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Adăugarea numelor coloanelor la modelul tabelului
                for (int i = 1; i <= columnCount; i++) {
                    tableModel.addColumn(metaData.getColumnName(i));
                }

                // Adăugarea datelor la modelul tabelului
                while (resultSet.next()) {
                    Object[] rowData = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        rowData[i - 1] = resultSet.getObject(i);
                    }
                    tableModel.addRow(rowData);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewTransactions::new);
    }
}
