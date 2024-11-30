import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class InventoryManagementSystemAWT extends Frame {

    private TextField productField, quantityField;
    private TextArea displayArea;
    private Map<String, Integer> inventory;

    public InventoryManagementSystemAWT() {
        // Initialize inventory
        inventory = new HashMap<>();

        // Set layout
        setLayout(new FlowLayout());

        // Create UI components
        Label productLabel = new Label("Product Name:");
        productField = new TextField(20);

        Label quantityLabel = new Label("Quantity:");
        quantityField = new TextField(10);

        Button stockInButton = new Button("Stock In");
        Button stockOutButton = new Button("Stock Out");
        Button viewInventoryButton = new Button("View Inventory");

        displayArea = new TextArea(10, 50);
        displayArea.setEditable(false);

        // Add components to frame
        add(productLabel);
        add(productField);
        add(quantityLabel);
        add(quantityField);
        add(stockInButton);
        add(stockOutButton);
        add(viewInventoryButton);
        add(displayArea);

        // Add action listeners
        stockInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stockIn();
            }
        });

        stockOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stockOut();
            }
        });

        viewInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewInventory();
            }
        });

        // Frame settings
        setTitle("Inventory Management System");
        setSize(600, 400);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void stockIn() {
        String product = productField.getText().trim();
        String quantityStr = quantityField.getText().trim();

        if (product.isEmpty() || quantityStr.isEmpty()) {
            displayArea.setText("Error: Product name and quantity must be filled.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            inventory.put(product, inventory.getOrDefault(product, 0) + quantity);
            displayArea.setText("Stock In Successful!\n" + quantity + " units of '" + product + "' added.");
        } catch (NumberFormatException e) {
            displayArea.setText("Error: Quantity must be a valid number.");
        }

        clearFields();
    }

    private void stockOut() {
        String product = productField.getText().trim();
        String quantityStr = quantityField.getText().trim();

        if (product.isEmpty() || quantityStr.isEmpty()) {
            displayArea.setText("Error: Product name and quantity must be filled.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (!inventory.containsKey(product)) {
                displayArea.setText("Error: Product not found in inventory.");
                return;
            }

            int currentStock = inventory.get(product);
            if (quantity > currentStock) {
                displayArea.setText("Error: Not enough stock available.\nCurrent stock: " + currentStock);
                return;
            }

            inventory.put(product, currentStock - quantity);
            displayArea.setText("Stock Out Successful!\n" + quantity + " units of '" + product + "' removed.");
        } catch (NumberFormatException e) {
            displayArea.setText("Error: Quantity must be a valid number.");
        }

        clearFields();
    }

    private void viewInventory() {
        if (inventory.isEmpty()) {
            displayArea.setText("Inventory is empty.");
            return;
        }

        StringBuilder inventoryDisplay = new StringBuilder("Current Inventory:\n");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            inventoryDisplay.append(entry.getKey()).append(": ").append(entry.getValue()).append(" units\n");
        }

        displayArea.setText(inventoryDisplay.toString());
    }

    private void clearFields() {
        productField.setText("");
        quantityField.setText("");
    }

    public static void main(String[] args) {
        new InventoryManagementSystemAWT();
    }
}
