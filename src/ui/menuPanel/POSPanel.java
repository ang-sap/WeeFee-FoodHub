package ui.menuPanel;

import ui.dialogs.ProductCard;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.DBConnection;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import ui.auth.LoginPanel;

public class POSPanel extends javax.swing.JPanel {

    private double currentTotal = 0.0;

    public POSPanel() {
        initComponents();

        menuGrid.setLayout(new java.awt.GridLayout(0, 3, 15, 2));
        menuGrid.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 25, 5, 10));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txtSearch.putClientProperty("JTextField.placeholderText", "ex. Siomai");

        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Item", "", "Qty", "", "Total", "", "ID"}
        ) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };

        tblCart.setModel(model);
        tblCart.setRowHeight(40);
        tblCart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        tblCart.setShowGrid(false);
        tblCart.setIntercellSpacing(new java.awt.Dimension(0, 0));

        tblCart.getTableHeader().setFont(new java.awt.Font("Geist SemiBold", java.awt.Font.PLAIN, 12));
        tblCart.getTableHeader().setBackground(new java.awt.Color(255, 255, 255));
        tblCart.getTableHeader().setForeground(new java.awt.Color(100, 116, 139));
        javax.swing.UIManager.put("TableHeader.separatorColor", new java.awt.Color(0, 0, 0, 0));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tblCart.getColumnCount(); i++) {
            tblCart.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        if (tblCart.getColumnModel().getColumnCount() > 0) {
            tblCart.getColumnModel().getColumn(0).setPreferredWidth(120);
            tblCart.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblCart.getColumnModel().getColumn(2).setPreferredWidth(30);
            tblCart.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblCart.getColumnModel().getColumn(4).setPreferredWidth(70);
            tblCart.getColumnModel().getColumn(5).setPreferredWidth(30);

            tblCart.getColumnModel().getColumn(6).setMinWidth(0);
            tblCart.getColumnModel().getColumn(6).setMaxWidth(0);
            tblCart.getColumnModel().getColumn(6).setWidth(0);
        }

        tblCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tblCart.rowAtPoint(evt.getPoint());
                int col = tblCart.columnAtPoint(evt.getPoint());

                if (row >= 0) {
                    double currentTotal = (double) model.getValueAt(row, 4);
                    int currentQty = (int) model.getValueAt(row, 2);
                    double unitPrice = currentTotal / currentQty;

                    if (col == 1) {
                        if (currentQty > 1) {
                            model.setValueAt(currentQty - 1, row, 2);
                            model.setValueAt((currentQty - 1) * unitPrice, row, 4);
                            updateTotal();
                        }
                    } else if (col == 2) {
                        String input = javax.swing.JOptionPane.showInputDialog(null, "Enter new quantity:", currentQty);
                        if (input != null && !input.trim().isEmpty()) {
                            try {
                                int newQty = Integer.parseInt(input.trim());
                                if (newQty > 0) {
                                    model.setValueAt(newQty, row, 2);
                                    model.setValueAt(newQty * unitPrice, row, 4);
                                    updateTotal();
                                }
                            } catch (NumberFormatException ex) {
                            }
                        }
                    } else if (col == 3) {
                        model.setValueAt(currentQty + 1, row, 2);
                        model.setValueAt((currentQty + 1) * unitPrice, row, 4);
                        updateTotal();
                    } else if (col == 5) {
                        model.removeRow(row);
                        updateTotal();
                    }
                }
            }
        });

        txtCash.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                calculateChange();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                calculateChange();
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                calculateChange();
            }
        });

        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                triggerSearch();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                triggerSearch();
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                triggerSearch();
            }
        });

        loadMenuCards("", "All");
    }

    private void triggerSearch() {
        String search = txtSearch.getText().trim();
        String category = cbCategory.getSelectedItem().toString();
        loadMenuCards(search, category);
    }

    public void loadMenuCards(String searchQuery, String categoryFilter) {
        menuGrid.removeAll();

        try {
            Connection conn = DBConnection.getConnection();

            StringBuilder sql = new StringBuilder(
                    "SELECT p.product_id, p.name, p.price, p.image_path " 
                    + "FROM Products p "
                    + "INNER JOIN Categories c ON p.category_id = c.category_id "
                    + "WHERE p.is_archived = 0 "
            );

            if (!categoryFilter.equals("All")) {
                sql.append("AND c.category_name = ? ");
            }

            if (!searchQuery.isEmpty()) {
                sql.append("AND p.name LIKE ? ");
            }

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            int paramIndex = 1;

            if (!categoryFilter.equals("All")) {
                pstmt.setString(paramIndex++, categoryFilter.trim());
            }

            if (!searchQuery.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + searchQuery.trim() + "%");
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("product_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String imgPath = rs.getString("image_path");

                ProductCard card = new ProductCard(id, name, price, imgPath, this);
                menuGrid.add(card);
            }

            menuGrid.revalidate();
            menuGrid.repaint();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToCart(int id, String name, double price) {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblCart.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            String rowName = (String) model.getValueAt(i, 0);

            if (rowName.equals(name)) {
                int currentQty = (int) model.getValueAt(i, 2);
                int newQty = currentQty + 1;

                model.setValueAt(newQty, i, 2);
                model.setValueAt(newQty * price, i, 4);

                updateTotal();
                return;
            }
        }

        model.addRow(new Object[]{name, "➖", 1, "➕", price, "🗑", id});
        updateTotal();
    }

    private void updateTotal() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblCart.getModel();
        currentTotal = 0.0;

        for (int i = 0; i < model.getRowCount(); i++) {
            currentTotal += (double) model.getValueAt(i, 4);
        }

        lblTotal.setText(String.format("%.2f", currentTotal));
        calculateChange();
    }

    private void calculateChange() {
        try {
            String cashText = txtCash.getText().trim();

            if (cashText.isEmpty()) {
                lblChange.setText(" 0.00");
                lblChange.setForeground(new java.awt.Color(102, 102, 102));
                return;
            }

            double cashReceived = Double.parseDouble(cashText);
            double change = cashReceived - currentTotal;

            if (change < 0) {
                lblChange.setText("Insufficient");
                lblChange.setForeground(java.awt.Color.RED);
            } else {
                lblChange.setText(String.format(" %.2f", change));
                lblChange.setForeground(new java.awt.Color(227, 83, 10));
            }

        } catch (NumberFormatException e) {
            lblChange.setText("Invalid");
            lblChange.setForeground(java.awt.Color.RED);
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
        String search = txtSearch.getText().trim();
        String category = cbCategory.getSelectedItem().toString();
        loadMenuCards(search, category);
    }//GEN-LAST:event_cbCategoryActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblCart.getModel();
        model.setRowCount(0);
        txtCash.setText("");
        updateTotal();
    }//GEN-LAST:event_btnClearActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed

    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnCheckoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckoutActionPerformed
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblCart.getModel();

        if (model.getRowCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "The cart is empty!", "Checkout Error", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        double cashReceived = 0;
        try {
            cashReceived = Double.parseDouble(txtCash.getText().trim());
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please enter a valid cash amount.", "Input Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cashReceived < currentTotal) {
            javax.swing.JOptionPane.showMessageDialog(this, "Insufficient cash! Customer needs ₱ " + String.format("%.2f", (currentTotal - cashReceived)) + " more.", "Payment Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        String stockCheckSql = "SELECT dbo.fn_CheckProductStock(?) AS stock_left";
        try (java.sql.Connection checkConn = database.DBConnection.getConnection(); java.sql.PreparedStatement pstmtCheck = checkConn.prepareStatement(stockCheckSql)) {

            for (int i = 0; i < model.getRowCount(); i++) {
                int productId = (int) model.getValueAt(i, 6);
                int requestedQty = (int) model.getValueAt(i, 2);
                String itemName = (String) model.getValueAt(i, 0);

                pstmtCheck.setInt(1, productId);
                try (java.sql.ResultSet rsCheck = pstmtCheck.executeQuery()) {
                    if (rsCheck.next()) {
                        int stockLeft = rsCheck.getInt("stock_left");
                        if (requestedQty > stockLeft) {
                            javax.swing.JOptionPane.showMessageDialog(this,
                                    "Checkout Failed: Insufficient Stock!\n\nYou requested " + requestedQty + " of '" + itemName + "', but the kitchen only has " + stockLeft + " left.",
                                    "Out of Stock",
                                    javax.swing.JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        java.sql.Connection conn = null;
        try {
            conn = database.DBConnection.getConnection();
            conn.setAutoCommit(false);

            String sqlTrans = "INSERT INTO Transactions (user_id, total_amount, cash_tendered, status) VALUES (?, ?, ?, 'COMPLETED')";
            java.sql.PreparedStatement pstmtTrans = conn.prepareStatement(sqlTrans, java.sql.Statement.RETURN_GENERATED_KEYS);
            pstmtTrans.setInt(1, LoginPanel.loggedInUserId);
            pstmtTrans.setDouble(2, currentTotal);
            pstmtTrans.setDouble(3, cashReceived);
            pstmtTrans.executeUpdate();

            java.sql.ResultSet rsKeys = pstmtTrans.getGeneratedKeys();
            int transactionId = 0;
            if (rsKeys.next()) {
                transactionId = rsKeys.getInt(1);
            }

            String sqlDetails = "INSERT INTO Transaction_Details (transaction_id, product_id, quantity, selling_price) VALUES (?, ?, ?, ?)";
            java.sql.PreparedStatement pstmtDetails = conn.prepareStatement(sqlDetails);

            for (int i = 0; i < model.getRowCount(); i++) {
                int qty = (int) model.getValueAt(i, 2);
                double lineTotal = (double) model.getValueAt(i, 4);
                double unitPrice = lineTotal / qty;

                int productId = (int) model.getValueAt(i, 6);

                pstmtDetails.setInt(1, transactionId);
                pstmtDetails.setInt(2, productId);
                pstmtDetails.setInt(3, qty);
                pstmtDetails.setDouble(4, unitPrice);

                pstmtDetails.addBatch();
            }
            pstmtDetails.executeBatch();

            conn.commit();

            double change = cashReceived - currentTotal;
            double vatableSales = currentTotal / 1.12;
            double vatAmount = currentTotal - vatableSales;

            StringBuilder receipt = new StringBuilder();
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
            receipt.append(String.format("Receipt No : %d\n", transactionId));
            receipt.append(String.format("Date       : %s\n", new java.text.SimpleDateFormat("MMM dd, yyyy hh:mm a").format(new java.util.Date())));
            receipt.append(String.format("Cashier    : %s\n", LoginPanel.loggedInUsername));
            receipt.append("------------------------------------------\n");
            receipt.append(String.format("%-22s %-5s %11s\n", "ITEM", "QTY", "TOTAL"));
            receipt.append("------------------------------------------\n");

            for (int i = 0; i < model.getRowCount(); i++) {
                String rawName = (String) model.getValueAt(i, 0);
                String itemName = rawName.length() > 18 ? rawName.substring(0, 18) : rawName;
                int qty = (int) model.getValueAt(i, 2);
                double lineTotal = (double) model.getValueAt(i, 4);

                receipt.append(String.format("%-22s %-5d   ₱%8.2f\n", itemName, qty, lineTotal));
            }

            receipt.append("------------------------------------------\n");
            receipt.append(String.format("%-25s ₱%11.2f\n", "VATable Sales", vatableSales));
            receipt.append(String.format("%-25s ₱%11.2f\n", "VAT Amount", vatAmount));
            receipt.append(String.format("%-25s ₱%11.2f\n", "TOTAL", currentTotal));
            receipt.append(String.format("%-25s ₱%11.2f\n", "CASH", cashReceived));
            receipt.append(String.format("%-25s ₱%11.2f\n", "CHANGE", change));
            receipt.append("==========================================\n");
            receipt.append("      THIS SERVES AS YOUR SALES INVOICE   \n");
            receipt.append("            THANK YOU! COME AGAIN         \n");
            receipt.append("==========================================\n");

            javax.swing.JTextArea txtReceipt = new javax.swing.JTextArea(receipt.toString());
            txtReceipt.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 14));
            txtReceipt.setEditable(false);
            txtReceipt.setBackground(new java.awt.Color(255, 255, 255));
            txtReceipt.setMargin(new java.awt.Insets(15, 15, 15, 15));

            javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(txtReceipt);
            scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)));

            javax.swing.JOptionPane.showMessageDialog(this, scrollPane, "Transaction Complete", javax.swing.JOptionPane.PLAIN_MESSAGE);

            btnClearActionPerformed(null);

        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
            }

            String errorMsg = e.getMessage();

            if (errorMsg != null && errorMsg.contains("constraint") && errorMsg.contains("current_stock")) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Checkout Failed: Insufficient Stock!\n\nOne or more items in your cart do not have enough stock in the kitchen to complete this sale.",
                        "Out of Stock",
                        javax.swing.JOptionPane.WARNING_MESSAGE);
            } else {
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(this, "Checkout Failed: " + errorMsg, "Database Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }

        } finally {
            try {
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
