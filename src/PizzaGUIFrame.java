import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PizzaGUIFrame extends JFrame {

    // Crust
    private JRadioButton thinButton;
    private JRadioButton regularButton;
    private JRadioButton deepDishButton;
    private ButtonGroup crustGroup;

    // Size
    private JComboBox<String> sizeComboBox;

    // Toppings
    private JCheckBox pepperoniBox;
    private JCheckBox sausageBox;
    private JCheckBox mushroomBox;
    private JCheckBox onionBox;
    private JCheckBox baconBox;
    private JCheckBox extraCheeseBox;

    // Receipt
    private JTextArea receiptArea;

    // Buttons
    private JButton orderButton;
    private JButton clearButton;
    private JButton quitButton;

    public PizzaGUIFrame() {

        setTitle("Pizza Order Form");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        // ---------------- CRUST PANEL ----------------
        JPanel crustPanel = new JPanel(new GridLayout(3, 1));
        crustPanel.setBorder(new TitledBorder("Crust"));

        thinButton = new JRadioButton("Thin");
        regularButton = new JRadioButton("Regular");
        deepDishButton = new JRadioButton("Deep-dish");

        crustGroup = new ButtonGroup();
        crustGroup.add(thinButton);
        crustGroup.add(regularButton);
        crustGroup.add(deepDishButton);

        crustPanel.add(thinButton);
        crustPanel.add(regularButton);
        crustPanel.add(deepDishButton);

        // ---------------- SIZE PANEL ----------------
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(new TitledBorder("Size"));

        String[] sizes = {
                "Select Size",
                "Small - $8.00",
                "Medium - $12.00",
                "Large - $16.00",
                "Super - $20.00"
        };

        sizeComboBox = new JComboBox<>(sizes);
        sizePanel.add(sizeComboBox);

        // ---------------- TOPPINGS PANEL ----------------
        JPanel toppingsPanel = new JPanel(new GridLayout(3, 2));
        toppingsPanel.setBorder(
                new TitledBorder("Toppings - $1.00 Each")
        );

        pepperoniBox = new JCheckBox("Pepperoni");
        sausageBox = new JCheckBox("Sausage");
        mushroomBox = new JCheckBox("Mushrooms");
        onionBox = new JCheckBox("Onions");
        baconBox = new JCheckBox("Bacon");
        extraCheeseBox = new JCheckBox("Extra Cheese");

        toppingsPanel.add(pepperoniBox);
        toppingsPanel.add(sausageBox);
        toppingsPanel.add(mushroomBox);
        toppingsPanel.add(onionBox);
        toppingsPanel.add(baconBox);
        toppingsPanel.add(extraCheeseBox);

        // ---------------- OPTIONS PANEL ----------------
        JPanel optionsPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        optionsPanel.add(crustPanel);
        optionsPanel.add(sizePanel);
        optionsPanel.add(toppingsPanel);

        // ---------------- RECEIPT ----------------
        receiptArea = new JTextArea(20, 50);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(receiptArea);

        JPanel receiptPanel = new JPanel(new BorderLayout());
        receiptPanel.setBorder(new TitledBorder("Order Receipt"));
        receiptPanel.add(scrollPane, BorderLayout.CENTER);

        // ---------------- BUTTONS ----------------
        JPanel buttonPanel = new JPanel();

        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");

        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        // ---------------- ACTION LISTENERS ----------------
        orderButton.addActionListener(e -> createOrder());

        clearButton.addActionListener(e -> clearForm());

        quitButton.addActionListener(e -> quitProgram());

        // ---------------- ADD PANELS ----------------
        add(optionsPanel, BorderLayout.NORTH);
        add(receiptPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void createOrder() {

        // Validate crust
        String crust;

        if (thinButton.isSelected()) {
            crust = "Thin";
        } else if (regularButton.isSelected()) {
            crust = "Regular";
        } else if (deepDishButton.isSelected()) {
            crust = "Deep-dish";
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a crust."
            );
            return;
        }

        // Validate size
        int sizeIndex = sizeComboBox.getSelectedIndex();

        if (sizeIndex == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a pizza size."
            );
            return;
        }

        String size;
        double basePrice;

        if (sizeIndex == 1) {
            size = "Small";
            basePrice = 8.00;
        } else if (sizeIndex == 2) {
            size = "Medium";
            basePrice = 12.00;
        } else if (sizeIndex == 3) {
            size = "Large";
            basePrice = 16.00;
        } else {
            size = "Super";
            basePrice = 20.00;
        }

        // Count toppings
        int toppingCount = 0;
        StringBuilder toppings = new StringBuilder();

        if (pepperoniBox.isSelected()) {
            toppings.append(
                    String.format("%-25s $%6.2f%n", "Pepperoni", 1.00)
            );
            toppingCount++;
        }

        if (sausageBox.isSelected()) {
            toppings.append(
                    String.format("%-25s $%6.2f%n", "Sausage", 1.00)
            );
            toppingCount++;
        }

        if (mushroomBox.isSelected()) {
            toppings.append(
                    String.format("%-25s $%6.2f%n", "Mushrooms", 1.00)
            );
            toppingCount++;
        }

        if (onionBox.isSelected()) {
            toppings.append(
                    String.format("%-25s $%6.2f%n", "Onions", 1.00)
            );
            toppingCount++;
        }

        if (baconBox.isSelected()) {
            toppings.append(
                    String.format("%-25s $%6.2f%n", "Bacon", 1.00)
            );
            toppingCount++;
        }

        if (extraCheeseBox.isSelected()) {
            toppings.append(
                    String.format("%-25s $%6.2f%n", "Extra Cheese", 1.00)
            );
            toppingCount++;
        }

        // At least one topping is required
        if (toppingCount == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select at least one topping."
            );
            return;
        }

        // Calculate totals
        double subtotal = basePrice + toppingCount;
        double tax = subtotal * 0.07;
        double total = subtotal + tax;

        // Build receipt
        StringBuilder receipt = new StringBuilder();

        receipt.append("====================================\n");
        receipt.append("          PIZZA ORDER RECEIPT\n");
        receipt.append("====================================\n");

        receipt.append(
                String.format(
                        "%-25s $%6.2f%n",
                        crust + " " + size,
                        basePrice
                )
        );

        receipt.append(toppings);

        receipt.append("------------------------------------\n");

        receipt.append(
                String.format(
                        "%-25s $%6.2f%n",
                        "Sub-total:",
                        subtotal
                )
        );

        receipt.append(
                String.format(
                        "%-25s $%6.2f%n",
                        "Tax:",
                        tax
                )
        );

        receipt.append("------------------------------------\n");

        receipt.append(
                String.format(
                        "%-25s $%6.2f%n",
                        "Total:",
                        total
                )
        );

        receipt.append("====================================\n");

        receiptArea.setText(receipt.toString());
    }

    private void clearForm() {

        crustGroup.clearSelection();

        sizeComboBox.setSelectedIndex(0);

        pepperoniBox.setSelected(false);
        sausageBox.setSelected(false);
        mushroomBox.setSelected(false);
        onionBox.setSelected(false);
        baconBox.setSelected(false);
        extraCheeseBox.setSelected(false);

        receiptArea.setText("");
    }

    private void quitProgram() {

        int answer = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Confirm Quit",
                JOptionPane.YES_NO_OPTION
        );

        if (answer == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}