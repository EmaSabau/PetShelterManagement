import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class OwnerActions extends JFrame {

    private ButtonGroup actionGroup;

    public OwnerActions() {
        setTitle("Owner Actions");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crearea componentelor
        JLabel titleLabel = new JLabel("Choose an action");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(150, 107, 157));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Alinează pe axa X

        // Adăugarea grupului de butoane radio pentru acțiuni
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Alinează pe axa X

        actionGroup = new ButtonGroup();
        String[] actions = {"View Animal Data", "Add Animal", "View Transactions", "View Funds", "View Available Spots"};
        for (String action : actions) {
            JRadioButton radioButton = new JRadioButton(action);
            actionGroup.add(radioButton);
            radioPanel.add(radioButton);
            radioButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Alinează pe axa X
        }

        // Crearea butonului pentru executarea acțiunii selectate
        JButton executeButton = new JButton("Execute Selected Action");
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeSelectedAction();
                for (Enumeration<AbstractButton> buttons = actionGroup.getElements(); buttons.hasMoreElements();) {
                    AbstractButton button = buttons.nextElement();
                    if (button.isSelected()) {
                        String selectedAction = button.getText();
                        if ("Add Animal".equals(selectedAction)) {
                            // Deschide pagina AdaugaAnimal
                            SwingUtilities.invokeLater(AdaugaAnimal::new);
                        }
                        else if ("View Animal Data".equals(selectedAction)) {
                            // Deschide pagina ViewAnimalData
                            SwingUtilities.invokeLater(ViewAnimalData::new);
                        }
                        else if ("View Transactions".equals(selectedAction)) {
                            // Deschide pagina ViewTransactions
                            SwingUtilities.invokeLater(ViewTransactions::new);
                        }
                        else if ("View Funds".equals(selectedAction)) {
                            // Deschide pagina ViewTransactions
                            SwingUtilities.invokeLater(ViewFunds::new);
                        }
                        else if ("View Available Spots".equals(selectedAction)) {
                            // Deschide pagina ViewAvailableSpots
                            SwingUtilities.invokeLater(ViewAvailableSpots::new);
                        }
                        else {
                            JOptionPane.showMessageDialog(OwnerActions.this, "Selected Action: " + selectedAction);
                        }
                        break;
                    }
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
        mainPanel.add(radioPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(createButtonPanel(executeButton));

        // Adăugarea panoului principal la fereastră
        add(mainPanel);

        // Ajustarea aspectului ferestrei
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createButtonPanel(JButton button) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(button);

        return buttonPanel;
    }

    private void executeSelectedAction() {
        // Aici adaugă logica pentru execuția acțiunii selectate
        for (Enumeration<AbstractButton> buttons = actionGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                String selectedAction = button.getText();
                JOptionPane.showMessageDialog(this, "Selected Action: " + selectedAction);
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OwnerActions::new);
    }
}
