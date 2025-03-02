import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewFunds extends JFrame {

    public ViewFunds() {
        setTitle("View Funds");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crearea modelului de tabel pentru afișarea fondurilor
        FundsTableModel tableModel = new FundsTableModel();

        // Crearea tabelului
        JTable fundsTable = new JTable(tableModel);

        // Adăugarea tabelului într-un panou cu bare de derulare
        JScrollPane scrollPane = new JScrollPane(fundsTable);

        // Crearea panoului principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Adăugarea componentelor la panou
        mainPanel.add(createHeaderPanel("Funds Overview"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(scrollPane);

        // Adăugarea panoului principal la fereastră
        add(mainPanel);

        // Ajustarea aspectului ferestrei
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createHeaderPanel(String headerText) {
        JPanel headerPanel = new JPanel();
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel headerLabel = new JLabel(headerText);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);

        return headerPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewFunds::new);
    }
}

class FundsTableModel extends AbstractTableModel {

    private List<Fund> funds;
    private String[] columnNames = {"Fond ID", "Buget", "Intrări", "Ieșiri", "Sold Rămas"};

    public FundsTableModel() {
        funds = fetchDataFromDatabase();
    }

    @Override
    public int getRowCount() {
        return funds.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Fund fund = funds.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return fund.getIdFond();
            case 1:
                return fund.getBuget();
            case 2:
                return fund.getIntrari();
            case 3:
                return fund.getIesiri();
            case 4:
                return fund.getSoldRamas();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    private List<Fund> fetchDataFromDatabase() {
        List<Fund> fundsList = new ArrayList<>();

        String url = "jdbc:mysql://localhost/AdapostAnimale?user=root&password=root";
        String sql = "SELECT * FROM vederefonduri";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int idFond = resultSet.getInt("id_fond");
                int buget = resultSet.getInt("buget");
                int intrari = resultSet.getInt("intrari");
                int iesiri = resultSet.getInt("iesiri");
                int soldRamas = resultSet.getInt("sold_ramas");

                fundsList.add(new Fund(idFond, buget, intrari, iesiri, soldRamas));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return fundsList;
    }
}

class Fund {
    private int idFond;
    private int buget;
    private int intrari;
    private int iesiri;
    private int soldRamas;

    public Fund(int idFond, int buget, int intrari, int iesiri, int soldRamas) {
        this.idFond = idFond;
        this.buget = buget;
        this.intrari = intrari;
        this.iesiri = iesiri;
        this.soldRamas = soldRamas;
    }

    public int getIdFond() {
        return idFond;
    }

    public int getBuget() {
        return buget;
    }

    public int getIntrari() {
        return intrari;
    }

    public int getIesiri() {
        return iesiri;
    }

    public int getSoldRamas() {
        return soldRamas;
    }
}
