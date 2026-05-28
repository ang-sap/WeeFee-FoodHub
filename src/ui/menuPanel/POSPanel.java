package ui.menuPanel;

// Imports ProductCard UI component
import ui.dialogs.ProductCard;

// Imports SQL classes
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Imports database connection class
import database.DBConnection;

// Imports JOptionPane for popup messages
import javax.swing.JOptionPane;

// Imports Swing constants for table alignment
import javax.swing.SwingConstants;

// Imports table cell renderer
import javax.swing.table.DefaultTableCellRenderer;

// Imports LoginPanel to get logged-in user information
import ui.auth.LoginPanel;

public class POSPanel extends javax.swing.JPanel {

    // Stores current total amount of cart
    private double currentTotal = 0.0;

    // Constructor
    // Runs when POSPanel is created
    public POSPanel() {

        // Initializes all UI components
        initComponents();

        // Sets product card layout
        menuGrid.setLayout(
                new java.awt.GridLayout(0, 3, 15, 2)
        );

        // Adds padding around menu grid
        menuGrid.setBorder(
                javax.swing.BorderFactory.createEmptyBorder(
                        5, 25, 5, 10
                )
        );

        // Removes horizontal scrollbar
        jScrollPane1.setHorizontalScrollBarPolicy(
                javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        // Placeholder text inside search field
        txtSearch.putClientProperty(
                "JTextField.placeholderText",
                "ex. Siomai"
        );

        // ================= CART TABLE MODEL =================
        // Creates cart table structure
        javax.swing.table.DefaultTableModel model
                = new javax.swing.table.DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                            "Item",
                            "",
                            "Qty",
                            "",
                            "Total",
                            "",
                            "ID"
                        }
                ) {

            // Prevents table editing
            boolean[] canEdit = new boolean[]{
                false, false, false,
                false, false, false,
                false
            };

            @Override
            public boolean isCellEditable(
                    int rowIndex,
                    int columnIndex
            ) {

                return canEdit[columnIndex];
            }
        };

        // Applies table model
        tblCart.setModel(model);

        // Sets row height
        tblCart.setRowHeight(40);

        // Changes cursor into hand cursor
        tblCart.setCursor(
                new java.awt.Cursor(
                        java.awt.Cursor.HAND_CURSOR
                )
        );

        // Removes table grid lines
        tblCart.setShowGrid(false);

        // Removes spacing between cells
        tblCart.setIntercellSpacing(
                new java.awt.Dimension(0, 0)
        );

        // Styles table header
        tblCart.getTableHeader().setFont(
                new java.awt.Font(
                        "Geist SemiBold",
                        java.awt.Font.PLAIN,
                        12
                )
        );

        tblCart.getTableHeader().setBackground(
                new java.awt.Color(255, 255, 255)
        );

        tblCart.getTableHeader().setForeground(
                new java.awt.Color(100, 116, 139)
        );

        // Removes header separator line
        javax.swing.UIManager.put(
                "TableHeader.separatorColor",
                new java.awt.Color(0, 0, 0, 0)
        );

        // Creates center alignment renderer
        DefaultTableCellRenderer centerRenderer
                = new DefaultTableCellRenderer();

        // Centers text horizontally
        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        // Applies center alignment to all columns
        for (int i = 0; i < tblCart.getColumnCount(); i++) {

            tblCart.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centerRenderer);
        }

        // Adjusts column widths
        if (tblCart.getColumnModel()
                .getColumnCount() > 0) {

            tblCart.getColumnModel()
                    .getColumn(0)
                    .setPreferredWidth(120);

            tblCart.getColumnModel()
                    .getColumn(1)
                    .setPreferredWidth(30);

            tblCart.getColumnModel()
                    .getColumn(2)
                    .setPreferredWidth(30);

            tblCart.getColumnModel()
                    .getColumn(3)
                    .setPreferredWidth(30);

            tblCart.getColumnModel()
                    .getColumn(4)
                    .setPreferredWidth(70);

            tblCart.getColumnModel()
                    .getColumn(5)
                    .setPreferredWidth(30);

            // Hides Product ID column
            tblCart.getColumnModel()
                    .getColumn(6)
                    .setMinWidth(0);

            tblCart.getColumnModel()
                    .getColumn(6)
                    .setMaxWidth(0);

            tblCart.getColumnModel()
                    .getColumn(6)
                    .setWidth(0);
        }

        // ================= CART BUTTON ACTIONS =================
        // Handles clicks inside cart table
        tblCart.addMouseListener(
                new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(
                    java.awt.event.MouseEvent evt
            ) {

                // Gets clicked row
                int row
                        = tblCart.rowAtPoint(
                                evt.getPoint()
                        );

                // Gets clicked column
                int col
                        = tblCart.columnAtPoint(
                                evt.getPoint()
                        );

                if (row >= 0) {

                    // Gets current row total
                    double currentTotal
                            = (double) model.getValueAt(
                                    row,
                                    4
                            );

                    // Gets current quantity
                    int currentQty
                            = (int) model.getValueAt(
                                    row,
                                    2
                            );

                    // Calculates unit price
                    double unitPrice
                            = currentTotal / currentQty;

                    // ================= DECREASE QUANTITY =================
                    if (col == 1) {

                        if (currentQty > 1) {

                            model.setValueAt(
                                    currentQty - 1,
                                    row,
                                    2
                            );

                            model.setValueAt(
                                    (currentQty - 1)
                                    * unitPrice,
                                    row,
                                    4
                            );

                            updateTotal();
                        }

                        // ================= MANUAL QUANTITY INPUT =================
                    } else if (col == 2) {

                        // Opens quantity input dialog
                        String input
                                = javax.swing.JOptionPane
                                        .showInputDialog(
                                                null,
                                                "Enter new quantity:",
                                                currentQty
                                        );

                        if (input != null
                                && !input.trim().isEmpty()) {

                            try {

                                // Converts input into integer
                                int newQty
                                        = Integer.parseInt(
                                                input.trim()
                                        );

                                // Validation:
                                // Quantity must be greater than 0
                                if (newQty <= 0) {

                                    JOptionPane.showMessageDialog(
                                            POSPanel.this,
                                            "Quantity must be greater than 0!",
                                            "Invalid Quantity",
                                            JOptionPane.WARNING_MESSAGE
                                    );

                                    return;
                                }

                                // Updates quantity
                                model.setValueAt(
                                        newQty,
                                        row,
                                        2
                                );

                                // Updates total price
                                model.setValueAt(
                                        newQty * unitPrice,
                                        row,
                                        4
                                );

                                // Recalculates cart total
                                updateTotal();

                            } catch (NumberFormatException ex) {

                                // Shows invalid input error
                                JOptionPane.showMessageDialog(
                                        POSPanel.this,
                                        "Please enter numbers only for quantity!",
                                        "Input Error",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        }

                        // ================= INCREASE QUANTITY =================
                    } else if (col == 3) {

                        model.setValueAt(
                                currentQty + 1,
                                row,
                                2
                        );

                        model.setValueAt(
                                (currentQty + 1)
                                * unitPrice,
                                row,
                                4
                        );

                        updateTotal();

                        // ================= REMOVE ITEM =================
                    } else if (col == 5) {

                        // Removes selected row
                        model.removeRow(row);

                        // Recalculates cart total
                        updateTotal();
                    }
                }
            }
        });

        // ================= CASH INPUT LISTENER =================
        txtCash.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

            @Override
            public void changedUpdate(
                    javax.swing.event.DocumentEvent e
            ) {

                calculateChange();
            }

            @Override
            public void removeUpdate(
                    javax.swing.event.DocumentEvent e
            ) {

                calculateChange();
            }

            @Override
            public void insertUpdate(
                    javax.swing.event.DocumentEvent e
            ) {

                calculateChange();
            }
        });

        // ================= SEARCH LISTENER =================
        txtSearch.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

            @Override
            public void changedUpdate(
                    javax.swing.event.DocumentEvent e
            ) {

                triggerSearch();
            }

            @Override
            public void removeUpdate(
                    javax.swing.event.DocumentEvent e
            ) {

                triggerSearch();
            }

            @Override
            public void insertUpdate(
                    javax.swing.event.DocumentEvent e
            ) {

                triggerSearch();
            }
        });

        // Loads all menu products
        loadMenuCards("", "All");
    }

    // Runs search and category filter
    private void triggerSearch() {

        // Gets search text
        String search
                = txtSearch.getText().trim();

        // Gets selected category
        String category
                = cbCategory.getSelectedItem().toString();

        // Reloads menu cards
        loadMenuCards(search, category);
    }

    // Loads product cards from database
    public void loadMenuCards(
            String searchQuery,
            String categoryFilter
    ) {

        // Removes existing product cards
        menuGrid.removeAll();

        try {

            // Connects to database
            Connection conn
                    = DBConnection.getConnection();

            // SQL query:
            // Gets active products with category information
            StringBuilder sql
                    = new StringBuilder(
                            "SELECT p.product_id, "
                            + "p.name, "
                            + "p.price, "
                            + "p.image_path "
                            + "FROM Products p "
                            + "INNER JOIN Categories c "
                            + "ON p.category_id = c.category_id "
                            + "WHERE p.is_archived = 0 "
                    );

            // Adds category filter if category is not "All"
            if (!categoryFilter.equals("All")) {

                sql.append(
                        "AND c.category_name = ? "
                );
            }

            // Adds search filter if search field is not empty
            if (!searchQuery.isEmpty()) {

                sql.append(
                        "AND p.name LIKE ? "
                );
            }

            // Creates PreparedStatement
            PreparedStatement pstmt
                    = conn.prepareStatement(
                            sql.toString()
                    );

            // Keeps track of parameter index
            int paramIndex = 1;

            // Inserts category filter value
            if (!categoryFilter.equals("All")) {

                pstmt.setString(
                        paramIndex++,
                        categoryFilter.trim()
                );
            }

            // Inserts search filter value
            if (!searchQuery.isEmpty()) {

                pstmt.setString(
                        paramIndex++,
                        "%" + searchQuery.trim() + "%"
                );
            }

            // Executes SELECT query
            ResultSet rs
                    = pstmt.executeQuery();

            // Loops through product records
            while (rs.next()) {

                // Gets product ID
                int id
                        = rs.getInt("product_id");

                // Gets product name
                String name
                        = rs.getString("name");

                // Gets product price
                double price
                        = rs.getDouble("price");

                // Gets image path
                String imgPath
                        = rs.getString("image_path");

                // Creates product card
                ProductCard card
                        = new ProductCard(
                                id,
                                name,
                                price,
                                imgPath,
                                this
                        );

                // Adds product card into menu grid
                menuGrid.add(card);
            }

            // Refreshes menu grid
            menuGrid.revalidate();
            menuGrid.repaint();

        } catch (Exception e) {

            // Prints error in console
            e.printStackTrace();
        }
    }

// Adds selected product into cart
    public void addToCart(
            int id,
            String name,
            double price
    ) {

        // Gets table model
        javax.swing.table.DefaultTableModel model
                = (javax.swing.table.DefaultTableModel) tblCart.getModel();

        // Checks if item already exists in cart
        for (int i = 0; i < model.getRowCount(); i++) {

            // Gets existing item name
            String rowName
                    = (String) model.getValueAt(i, 0);

            // If item already exists
            if (rowName.equals(name)) {

                // Gets current quantity
                int currentQty
                        = (int) model.getValueAt(i, 2);

                // Increases quantity
                int newQty = currentQty + 1;

                // Updates quantity
                model.setValueAt(newQty, i, 2);

                // Updates total price
                model.setValueAt(
                        newQty * price,
                        i,
                        4
                );

                // Recalculates cart total
                updateTotal();

                return;
            }
        }

        // Adds new item into cart
        model.addRow(new Object[]{
            name,
            "➖",
            1,
            "➕",
            price,
            "🗑",
            id
        });

        // Recalculates cart total
        updateTotal();
    }

// Recalculates cart total amount
    private void updateTotal() {

        // Gets table model
        javax.swing.table.DefaultTableModel model
                = (javax.swing.table.DefaultTableModel) tblCart.getModel();

        // Resets total
        currentTotal = 0.0;

        // Loops through cart rows
        for (int i = 0; i < model.getRowCount(); i++) {

            // Adds item total into overall total
            currentTotal
                    += (double) model.getValueAt(i, 4);
        }

        // Displays formatted total amount
        lblTotal.setText(
                String.format("%.2f", currentTotal)
        );

        // Recalculates customer change
        calculateChange();
    }

// Calculates customer change
    private void calculateChange() {

        try {

            // Gets cash input
            String cashText
                    = txtCash.getText().trim();

            // Validation:
            // If cash field is empty
            if (cashText.isEmpty()) {

                lblChange.setText(" 0.00");

                lblChange.setForeground(
                        new java.awt.Color(102, 102, 102)
                );

                return;
            }

            // Converts cash input into double
            double cashReceived
                    = Double.parseDouble(cashText);

            // Calculates change
            double change
                    = cashReceived - currentTotal;

            // If cash is insufficient
            if (change < 0) {

                lblChange.setText("Insufficient");

                lblChange.setForeground(
                        java.awt.Color.RED
                );

            } else {

                // Displays calculated change
                lblChange.setText(
                        String.format(" %.2f", change)
                );

                lblChange.setForeground(
                        new java.awt.Color(227, 83, 10)
                );
            }

        } catch (NumberFormatException e) {

            // Displays invalid input message
            lblChange.setText("Invalid");

            lblChange.setForeground(
                    java.awt.Color.RED
            );
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuContainer = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        cbCategory = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        menuGrid = new javax.swing.JPanel();
        cartContainer = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCart = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        Total1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        Total = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCash = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lblChange = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        btnCheckout = new javax.swing.JButton();

        setBackground(new java.awt.Color(248, 250, 252));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuContainer.setBackground(new java.awt.Color(255, 255, 255));
        menuContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(226, 232, 240)));

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jLabel1.setText("Menu");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(589, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        menuContainer.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 50));

        txtSearch.setFont(new java.awt.Font("Geist", 0, 12)); // NOI18N
        txtSearch.addActionListener(this::txtSearchActionPerformed);
        menuContainer.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 410, 30));

        cbCategory.setFont(new java.awt.Font("Geist", 0, 12)); // NOI18N
        cbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Rice Meals", "Burgers", "Fries", "Drinks", "Add-ons", "Desserts", "Noodles", "Pasta", "Pizza", "Appetizers", "Salads", "Soups", "Sandwiches", "Combos", "Snacks", "Specials", "Breakfast", "Lunch", "Dinner", "Catering" }));
        cbCategory.addActionListener(this::cbCategoryActionPerformed);
        menuContainer.add(cbCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 190, 30));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        menuGrid.setBackground(new java.awt.Color(255, 255, 255));
        menuGrid.setLayout(new java.awt.GridLayout(1, 3, 15, 15));
        jScrollPane1.setViewportView(menuGrid);

        menuContainer.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 610, 490));

        add(menuContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 10, 650, 610));

        cartContainer.setBackground(new java.awt.Color(255, 255, 255));
        cartContainer.setPreferredSize(new java.awt.Dimension(334, 600));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(226, 232, 240)));
        jPanel2.setPreferredSize(new java.awt.Dimension(310, 50));

        jLabel2.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jLabel2.setText("Current Order");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        tblCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Item", "Qty", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblCart);

        jPanel3.setBackground(new java.awt.Color(252, 251, 248));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        Total1.setFont(new java.awt.Font("Geist Medium", 0, 18)); // NOI18N
        Total1.setForeground(new java.awt.Color(227, 83, 10));
        Total1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Total1.setText("₱");

        lblTotal.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(227, 83, 10));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotal.setText(" 0.00");

        Total.setFont(new java.awt.Font("Geist Medium", 0, 18)); // NOI18N
        Total.setText("Total");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(Total)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Total1)
                .addGap(0, 0, 0)
                .addComponent(lblTotal)
                .addGap(13, 13, 13))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(Total)
                    .addComponent(Total1))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Geist Medium", 0, 12)); // NOI18N
        jLabel3.setText("Cash Received");

        jLabel4.setFont(new java.awt.Font("Geist Medium", 0, 12)); // NOI18N
        jLabel4.setText("Change ");

        txtCash.setFont(new java.awt.Font("Geist", 0, 12)); // NOI18N

        lblChange.setFont(new java.awt.Font("Geist SemiBold", 0, 12)); // NOI18N
        lblChange.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblChange.setText("₱ 0.00");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblChange, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblChange)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        btnClear.setFont(new java.awt.Font("Geist SemiBold", 0, 12)); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(this::btnClearActionPerformed);

        btnCheckout.setBackground(new java.awt.Color(227, 83, 10));
        btnCheckout.setFont(new java.awt.Font("Geist SemiBold", 0, 12)); // NOI18N
        btnCheckout.setForeground(new java.awt.Color(255, 255, 255));
        btnCheckout.setText("Pay");
        btnCheckout.addActionListener(this::btnCheckoutActionPerformed);

        javax.swing.GroupLayout cartContainerLayout = new javax.swing.GroupLayout(cartContainer);
        cartContainer.setLayout(cartContainerLayout);
        cartContainerLayout.setHorizontalGroup(
            cartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(cartContainerLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(cartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(cartContainerLayout.createSequentialGroup()
                        .addGroup(cartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtCash, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(cartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(cartContainerLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(95, 95, 95))
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(cartContainerLayout.createSequentialGroup()
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCheckout, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        cartContainerLayout.setVerticalGroup(
            cartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cartContainerLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(cartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCash)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(cartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(btnCheckout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 25, Short.MAX_VALUE))
        );

        add(cartContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 10, 310, 610));
    }// </editor-fold>//GEN-END:initComponents

    private void cbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoryActionPerformed
        // Gets text from search field
        String search
                = txtSearch.getText().trim();

        // Gets selected category from combo box
        String category
                = cbCategory.getSelectedItem().toString();

        // Reloads menu cards based on search and category filter
        loadMenuCards(search, category);
    }//GEN-LAST:event_cbCategoryActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // Gets cart table model
        javax.swing.table.DefaultTableModel model
                = (javax.swing.table.DefaultTableModel) tblCart.getModel();

        // Removes all rows from cart table
        model.setRowCount(0);

        // Clears cash input field
        txtCash.setText("");

        // Recalculates total amount
        updateTotal();
    }//GEN-LAST:event_btnClearActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckoutActionPerformed
        // Gets cart table model
        javax.swing.table.DefaultTableModel model
                = (javax.swing.table.DefaultTableModel) tblCart.getModel();

        // Validation:
        // Checks if cart is empty
        if (model.getRowCount() == 0) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "The cart is empty!",
                    "Checkout Error",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Stores customer cash payment
        double cashReceived = 0;

        try {

            // Converts cash input into double
            cashReceived = Double.parseDouble(
                    txtCash.getText().trim()
            );

        } catch (NumberFormatException e) {

            // Runs if cash input is invalid
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid cash amount.",
                    "Input Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Validation:
        // Checks if customer cash is enough
        if (cashReceived < currentTotal) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Insufficient cash! Customer needs ₱ "
                    + String.format(
                            "%.2f",
                            (currentTotal - cashReceived)
                    )
                    + " more.",
                    "Payment Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // ================= STOCK VALIDATION =================
        // SQL query:
        // Calls SQL function to check remaining stock
        String stockCheckSql
                = "SELECT dbo.fn_CheckProductStock(?) "
                + "AS stock_left";

        try (
                // Connects to database
                java.sql.Connection checkConn
                = database.DBConnection.getConnection(); // Creates PreparedStatement
                 java.sql.PreparedStatement pstmtCheck
                = checkConn.prepareStatement(stockCheckSql)) {

            // Loops through cart items
            for (int i = 0; i < model.getRowCount(); i++) {

                // Gets product ID
                int productId
                        = (int) model.getValueAt(i, 6);

                // Gets requested quantity
                int requestedQty
                        = (int) model.getValueAt(i, 2);

                // Gets item name
                String itemName
                        = (String) model.getValueAt(i, 0);

                // Inserts product ID into SQL query
                pstmtCheck.setInt(1, productId);

                try (
                        // Executes stock check query
                        java.sql.ResultSet rsCheck
                        = pstmtCheck.executeQuery()) {

                    // Checks if stock exists
                    if (rsCheck.next()) {

                        // Gets remaining stock quantity
                        int stockLeft
                                = rsCheck.getInt("stock_left");

                        // Validation:
                        // Checks if requested quantity exceeds stock
                        if (requestedQty > stockLeft) {

                            javax.swing.JOptionPane.showMessageDialog(
                                    this,
                                    "Checkout Failed: Insufficient Stock!\n\n"
                                    + "You requested "
                                    + requestedQty
                                    + " of '"
                                    + itemName
                                    + "', but the kitchen only has "
                                    + stockLeft
                                    + " left.",
                                    "Out of Stock",
                                    javax.swing.JOptionPane.WARNING_MESSAGE
                            );

                            return;
                        }
                    }
                }
            }

        } catch (Exception e) {

            // Prints error in console
            e.printStackTrace();

            return;
        }

        java.sql.Connection conn = null;

        try {

            // Connects to database
            conn = database.DBConnection.getConnection();

            // Starts transaction mode
            // All queries must succeed together
            conn.setAutoCommit(false);

            // ================= TRANSACTIONS TABLE =================
            // SQL query for creating transaction record
            String sqlTrans
                    = "INSERT INTO Transactions "
                    + "(user_id, total_amount, cash_tendered, status) "
                    + "VALUES (?, ?, ?, 'COMPLETED')";

            // RETURN_GENERATED_KEYS gets new transaction ID
            java.sql.PreparedStatement pstmtTrans
                    = conn.prepareStatement(
                            sqlTrans,
                            java.sql.Statement.RETURN_GENERATED_KEYS
                    );

            // Inserts logged-in user ID
            pstmtTrans.setInt(
                    1,
                    LoginPanel.loggedInUserId
            );

            // Inserts total amount
            pstmtTrans.setDouble(2, currentTotal);

            // Inserts customer cash payment
            pstmtTrans.setDouble(3, cashReceived);

            // Executes INSERT query
            pstmtTrans.executeUpdate();

            // Gets generated transaction ID
            java.sql.ResultSet rsKeys
                    = pstmtTrans.getGeneratedKeys();

            int transactionId = 0;

            // Checks if transaction was created
            if (rsKeys.next()) {

                // Gets new transaction ID
                transactionId = rsKeys.getInt(1);
            }

            // ================= TRANSACTION DETAILS =================
            // SQL query for transaction details
            String sqlDetails
                    = "INSERT INTO Transaction_Details "
                    + "(transaction_id, product_id, quantity, selling_price) "
                    + "VALUES (?, ?, ?, ?)";

            // Creates PreparedStatement
            java.sql.PreparedStatement pstmtDetails
                    = conn.prepareStatement(sqlDetails);

            // Loops through cart items
            for (int i = 0; i < model.getRowCount(); i++) {

                // Gets quantity
                int qty
                        = (int) model.getValueAt(i, 2);

                // Gets line total
                double lineTotal
                        = (double) model.getValueAt(i, 4);

                // Calculates unit price
                double unitPrice
                        = lineTotal / qty;

                // Gets product ID
                int productId
                        = (int) model.getValueAt(i, 6);

                // Inserts transaction ID
                pstmtDetails.setInt(1, transactionId);

                // Inserts product ID
                pstmtDetails.setInt(2, productId);

                // Inserts quantity
                pstmtDetails.setInt(3, qty);

                // Inserts selling price
                pstmtDetails.setDouble(4, unitPrice);

                // Adds query into batch
                pstmtDetails.addBatch();
            }

            // Executes all INSERT queries together
            pstmtDetails.executeBatch();

            // Saves all database changes permanently
            conn.commit();

            // ================= RECEIPT COMPUTATION =================
            // Calculates customer change
            double change
                    = cashReceived - currentTotal;

            // Calculates VATable sales
            double vatableSales
                    = currentTotal / 1.12;

            // Calculates VAT amount
            double vatAmount
                    = currentTotal - vatableSales;

            // Creates receipt text
            StringBuilder receipt
                    = new StringBuilder();

            // ================= RECEIPT HEADER =================
            receipt.append("==========================================\n");
            receipt.append("              WEEFEE FOODHUB\n");
            receipt.append("        1878 Tayuman St. Sta. Cruz\n");
            receipt.append("              Manila Philippines\n");
            receipt.append("------------------------------------------\n");
            receipt.append("              SALES INVOICE\n");
            receipt.append("TIN: 123-456-789-000\n");
            receipt.append("VAT REG TIN\n");
            receipt.append("MIN: 24010123456789012\n");
            receipt.append("==========================================\n");

            // Displays receipt number
            receipt.append(
                    String.format(
                            "Receipt No : %d\n",
                            transactionId
                    )
            );

            // Displays transaction date
            receipt.append(
                    String.format(
                            "Date       : %s\n",
                            new java.text.SimpleDateFormat(
                                    "MMM dd, yyyy hh:mm a"
                            ).format(new java.util.Date())
                    )
            );

            // Displays cashier name
            receipt.append(
                    String.format(
                            "Cashier    : %s\n",
                            LoginPanel.loggedInUsername
                    )
            );

            receipt.append("------------------------------------------\n");

            // Receipt table header
            receipt.append(
                    String.format(
                            "%-22s %-5s %11s\n",
                            "ITEM",
                            "QTY",
                            "TOTAL"
                    )
            );

            receipt.append("------------------------------------------\n");

            // ================= RECEIPT ITEMS =================
            // Loops through cart items
            for (int i = 0; i < model.getRowCount(); i++) {

                // Gets item name
                String rawName
                        = (String) model.getValueAt(i, 0);

                // Shortens long product names
                String itemName
                        = rawName.length() > 18
                        ? rawName.substring(0, 18)
                        : rawName;

                // Gets quantity
                int qty
                        = (int) model.getValueAt(i, 2);

                // Gets line total
                double lineTotal
                        = (double) model.getValueAt(i, 4);

                // Adds item into receipt
                receipt.append(
                        String.format(
                                "%-22s %-5d   ₱%8.2f\n",
                                itemName,
                                qty,
                                lineTotal
                        )
                );
            }

            receipt.append("------------------------------------------\n");

            // Displays VATable sales
            receipt.append(
                    String.format(
                            "%-25s ₱%11.2f\n",
                            "VATable Sales",
                            vatableSales
                    )
            );

            // Displays VAT amount
            receipt.append(
                    String.format(
                            "%-25s ₱%11.2f\n",
                            "VAT Amount",
                            vatAmount
                    )
            );

            // Displays total amount
            receipt.append(
                    String.format(
                            "%-25s ₱%11.2f\n",
                            "TOTAL",
                            currentTotal
                    )
            );

            // Displays customer cash payment
            receipt.append(
                    String.format(
                            "%-25s ₱%11.2f\n",
                            "CASH",
                            cashReceived
                    )
            );

            // Displays customer change
            receipt.append(
                    String.format(
                            "%-25s ₱%11.2f\n",
                            "CHANGE",
                            change
                    )
            );

            receipt.append("==========================================\n");
            receipt.append("      THIS SERVES AS YOUR SALES INVOICE   \n");
            receipt.append("            THANK YOU! COME AGAIN         \n");
            receipt.append("==========================================\n");

            // ================= RECEIPT DISPLAY =================
            // Creates text area for receipt
            javax.swing.JTextArea txtReceipt
                    = new javax.swing.JTextArea(
                            receipt.toString()
                    );

            // Receipt font style
            txtReceipt.setFont(
                    new java.awt.Font(
                            "Monospaced",
                            java.awt.Font.BOLD,
                            14
                    )
            );

            // Prevents editing
            txtReceipt.setEditable(false);

            // White background
            txtReceipt.setBackground(
                    new java.awt.Color(255, 255, 255)
            );

            // Adds padding
            txtReceipt.setMargin(
                    new java.awt.Insets(15, 15, 15, 15)
            );

            // Creates scroll pane for receipt
            javax.swing.JScrollPane scrollPane
                    = new javax.swing.JScrollPane(
                            txtReceipt
                    );

            // Adds border
            scrollPane.setBorder(
                    javax.swing.BorderFactory.createLineBorder(
                            new java.awt.Color(226, 232, 240)
                    )
            );

            // Displays receipt dialog
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    scrollPane,
                    "Transaction Complete",
                    javax.swing.JOptionPane.PLAIN_MESSAGE
            );

            // Clears cart after successful checkout
            btnClearActionPerformed(null);

        } catch (Exception e) {

            try {

                // Cancels all database changes if an error happens
                if (conn != null) {

                    conn.rollback();
                }

            } catch (Exception ex) {

            }

            // Gets error message
            String errorMsg = e.getMessage();

            // Handles stock constraint errors
            if (errorMsg != null
                    && errorMsg.contains("constraint")
                    && errorMsg.contains("current_stock")) {

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Checkout Failed: Insufficient Stock!\n\n"
                        + "One or more items in your cart "
                        + "do not have enough stock in the kitchen "
                        + "to complete this sale.",
                        "Out of Stock",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );

            } else {

                // Prints technical error in console for developers
                e.printStackTrace();

                // Shows a safe, user-friendly error to the cashier
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "A system error occurred during checkout. Please try again or contact the administrator.",
                        "System Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                );
            }

        } finally {

            try {

                // Turns auto-commit back on
                if (conn != null) {

                    conn.setAutoCommit(true);
                }

            } catch (Exception ex) {

            }
        }
    }//GEN-LAST:event_btnCheckoutActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Total;
    private javax.swing.JLabel Total1;
    private javax.swing.JButton btnCheckout;
    private javax.swing.JButton btnClear;
    private javax.swing.JPanel cartContainer;
    private javax.swing.JComboBox<String> cbCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblChange;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel menuContainer;
    private javax.swing.JPanel menuGrid;
    private javax.swing.JTable tblCart;
    private javax.swing.JTextField txtCash;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
