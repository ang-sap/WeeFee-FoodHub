package ui.dashboard;

// Imports different panels/screens used in the dashboard
import ui.menuPanel.ExpensesPanel;
import ui.menuPanel.InventoryPanel;
import ui.menuPanel.POSPanel;
import ui.menuPanel.ProductsPanel;
import ui.menuPanel.PurchasesPanel;
import ui.menuPanel.ReportsPanel;
import ui.menuPanel.SuppliersPanel;
import ui.menuPanel.SystemLogsPanel;
import ui.menuPanel.TransactionsPanel;
import ui.menuPanel.HomePanel;

// Import LoginPanel to get logged-in user info
import ui.auth.LoginPanel;

public class DashboardPanel extends javax.swing.JPanel {

    // Declares all screen/panel objects
    private HomePanel homeScreen;
    private POSPanel posScreen;
    private InventoryPanel inventoryScreen;
    private ProductsPanel productsScreen;
    private SuppliersPanel suppliersScreen;
    private PurchasesPanel purchasesScreen;
    private ExpensesPanel expensesScreen;
    private ReportsPanel reportsScreen;
    private TransactionsPanel transactionsScreen;
    private SystemLogsPanel logsScreen;

    // CardLayout used to switch between screens
    private java.awt.CardLayout cardLayout;

    // Default constructor
    // Automatically uses Admin role
    public DashboardPanel() {
        this("Admin");
    }

    // Constructor with role parameter
    public DashboardPanel(String userRole) {

        // Initializes all UI components
        initComponents();

        // Displays the logged-in username
        lblUser.setText(LoginPanel.loggedInUsername);

        // Creates CardLayout for switching screens
        cardLayout = new java.awt.CardLayout();

        // Applies CardLayout to MainCardsPanel
        MainCardsPanel.setLayout(cardLayout);

        // Creates all screen objects
        homeScreen = new HomePanel();
        posScreen = new POSPanel();
        inventoryScreen = new InventoryPanel();
        productsScreen = new ProductsPanel();
        suppliersScreen = new SuppliersPanel();
        purchasesScreen = new PurchasesPanel();
        expensesScreen = new ExpensesPanel();
        reportsScreen = new ReportsPanel();
        transactionsScreen = new TransactionsPanel();
        logsScreen = new SystemLogsPanel();

        // Adds screens into CardLayout
        MainCardsPanel.add(homeScreen, "CardHome");
        MainCardsPanel.add(posScreen, "CardPOS");
        MainCardsPanel.add(inventoryScreen, "CardInventory");
        MainCardsPanel.add(productsScreen, "CardProducts");
        MainCardsPanel.add(suppliersScreen, "CardSuppliers");
        MainCardsPanel.add(purchasesScreen, "CardPurchases");
        MainCardsPanel.add(expensesScreen, "CardExpenses");
        MainCardsPanel.add(reportsScreen, "CardReports");
        MainCardsPanel.add(transactionsScreen, "CardTransactions");
        MainCardsPanel.add(logsScreen, "CardLogs");

        // UI styling settings
        javax.swing.UIManager.put("Component.focusWidth", 2);
        javax.swing.UIManager.put("Component.innerFocusWidth", 0);
        javax.swing.UIManager.put("Component.focusColor",
                new java.awt.Color(226, 232, 240));

        javax.swing.UIManager.put("Button.shadowWidth", 2);

        // Applies role-based security
        applySecurityRules(userRole);
    }

    // Changes the active/highlighted navigation button
    private void setActiveNavButton(javax.swing.JButton clickedButton) {

        // Array containing all navigation buttons
        javax.swing.JButton[] navButtons = {
            btnNavHome,
            btnNavPOS,
            btnNavInventory,
            btnNavProducts,
            btnNavSuppliers,
            btnNavPurchases,
            btnNavExpenses,
            btnNavReports,
            btnNavTransactions,
            btnNavAuditLogs
        };

        // Orange color for active button
        java.awt.Color brandOrange
                = new java.awt.Color(227, 83, 10);

        // Resets all buttons to default color
        for (javax.swing.JButton btn : navButtons) {

            if (btn != null) {

                // White background
                btn.setBackground(new java.awt.Color(255, 255, 255));

                // Gray text color
                btn.setForeground(new java.awt.Color(100, 116, 139));

                // Removes outline
                btn.putClientProperty("JComponent.outline", null);
            }
        }

        // Highlights the clicked button
        if (clickedButton != null) {

            // Orange background
            clickedButton.setBackground(brandOrange);

            // White text
            clickedButton.setForeground(new java.awt.Color(255, 255, 255));

            // Gives focus to selected button
            clickedButton.setFocusable(true);
            clickedButton.requestFocusInWindow();
        }
    }

    // Controls access based on user role
    public void applySecurityRules(String role) {

        // If user is Cashier
        if (role.equals("Cashier")) {

            // Hide admin-only modules
            btnNavInventory.setVisible(false);
            btnNavProducts.setVisible(false);
            btnNavSuppliers.setVisible(false);
            btnNavPurchases.setVisible(false);
            btnNavExpenses.setVisible(false);
            btnNavReports.setVisible(false);
            btnNavAuditLogs.setVisible(false);

            // Show Home screen
            cardLayout.show(MainCardsPanel, "CardHome");

            // Highlight Home button
            setActiveNavButton(btnNavHome);

            // If user is Admin
        } else if (role.equals("Admin")) {

            // Show Home screen
            cardLayout.show(MainCardsPanel, "CardHome");

            // Highlight Home button
            setActiveNavButton(btnNavHome);
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

        sidebarPanel = new javax.swing.JPanel();
        btnNavPOS = new javax.swing.JButton();
        btnNavInventory = new javax.swing.JButton();
        btnNavProducts = new javax.swing.JButton();
        btnNavSuppliers = new javax.swing.JButton();
        btnNavAuditLogs = new javax.swing.JButton();
        btnNavLogout = new javax.swing.JButton();
        btnNavTransactions = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnNavPurchases = new javax.swing.JButton();
        btnNavExpenses = new javax.swing.JButton();
        btnNavReports = new javax.swing.JButton();
        btnNavHome = new javax.swing.JButton();
        ContentWrapperPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        MainCardsPanel = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(1280, 720));
        setPreferredSize(new java.awt.Dimension(1280, 720));
        setLayout(new java.awt.BorderLayout());

        sidebarPanel.setBackground(new java.awt.Color(255, 255, 255));
        sidebarPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(226, 232, 240)));
        sidebarPanel.setPreferredSize(new java.awt.Dimension(240, 720));

        btnNavPOS.setFont(new java.awt.Font("Geist", 0, 13)); // NOI18N
        btnNavPOS.setForeground(new java.awt.Color(100, 116, 139));
        btnNavPOS.setText("POS");
        btnNavPOS.setBorderPainted(false);
        btnNavPOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavPOS.setFocusPainted(false);
        btnNavPOS.setPreferredSize(new java.awt.Dimension(225, 23));
        btnNavPOS.addActionListener(this::btnNavPOSActionPerformed);

        btnNavInventory.setFont(new java.awt.Font("Geist", 0, 13)); // NOI18N
        btnNavInventory.setForeground(new java.awt.Color(100, 116, 139));
        btnNavInventory.setText("Inventory");
        btnNavInventory.setBorderPainted(false);
        btnNavInventory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavInventory.setFocusPainted(false);
        btnNavInventory.setPreferredSize(new java.awt.Dimension(225, 23));
        btnNavInventory.addActionListener(this::btnNavInventoryActionPerformed);

        btnNavProducts.setFont(new java.awt.Font("Geist", 0, 13)); // NOI18N
        btnNavProducts.setForeground(new java.awt.Color(100, 116, 139));
        btnNavProducts.setText("Products");
        btnNavProducts.setBorderPainted(false);
        btnNavProducts.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavProducts.setFocusPainted(false);
        btnNavProducts.setPreferredSize(new java.awt.Dimension(225, 23));
        btnNavProducts.addActionListener(this::btnNavProductsActionPerformed);

        btnNavSuppliers.setFont(new java.awt.Font("Geist", 0, 13)); // NOI18N
        btnNavSuppliers.setForeground(new java.awt.Color(100, 116, 139));
        btnNavSuppliers.setText("Suppliers");
        btnNavSuppliers.setBorderPainted(false);
        btnNavSuppliers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavSuppliers.setFocusPainted(false);
        btnNavSuppliers.setPreferredSize(new java.awt.Dimension(225, 23));
        btnNavSuppliers.addActionListener(this::btnNavSuppliersActionPerformed);

        btnNavAuditLogs.setFont(new java.awt.Font("Geist", 0, 13)); // NOI18N
        btnNavAuditLogs.setForeground(new java.awt.Color(100, 116, 139));
        btnNavAuditLogs.setText("System Logs");
        btnNavAuditLogs.setBorderPainted(false);
        btnNavAuditLogs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavAuditLogs.setFocusPainted(false);
        btnNavAuditLogs.setPreferredSize(new java.awt.Dimension(225, 23));
        btnNavAuditLogs.addActionListener(this::btnNavAuditLogsActionPerformed);

        btnNavLogout.setFont(new java.awt.Font("Geist", 1, 14)); // NOI18N
        btnNavLogout.setForeground(new java.awt.Color(100, 116, 139));
        btnNavLogout.setText("Logout");
        btnNavLogout.setBorderPainted(false);
        btnNavLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavLogout.setFocusPainted(false);
        btnNavLogout.setPreferredSize(new java.awt.Dimension(225, 23));
        btnNavLogout.addActionListener(this::btnNavLogoutActionPerformed);

        btnNavTransactions.setFont(new java.awt.Font("Geist", 0, 13)); // NOI18N
        btnNavTransactions.setForeground(new java.awt.Color(100, 116, 139));
        btnNavTransactions.setText("Transactions");
        btnNavTransactions.setBorderPainted(false);
        btnNavTransactions.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavTransactions.setFocusPainted(false);
        btnNavTransactions.setPreferredSize(new java.awt.Dimension(225, 23));
        btnNavTransactions.addActionListener(this::btnNavTransactionsActionPerformed);

        jLabel3.setFont(new java.awt.Font("Geist SemiBold", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("MAIN MENU");

        btnNavPurchases.setFont(new java.awt.Font("Geist", 0, 13)); // NOI18N
        btnNavPurchases.setForeground(new java.awt.Color(100, 116, 139));
        btnNavPurchases.setText("Purchases");
        btnNavPurchases.setBorderPainted(false);
        btnNavPurchases.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavPurchases.setFocusPainted(false);
        btnNavPurchases.setPreferredSize(new java.awt.Dimension(225, 23));
        btnNavPurchases.addActionListener(this::btnNavPurchasesActionPerformed);

        btnNavExpenses.setFont(new java.awt.Font("Geist", 0, 13)); // NOI18N
        btnNavExpenses.setForeground(new java.awt.Color(100, 116, 139));
        btnNavExpenses.setText("Expenses");
        btnNavExpenses.setBorderPainted(false);
        btnNavExpenses.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavExpenses.setFocusPainted(false);
        btnNavExpenses.setPreferredSize(new java.awt.Dimension(225, 23));
        btnNavExpenses.addActionListener(this::btnNavExpensesActionPerformed);

        btnNavReports.setFont(new java.awt.Font("Geist", 0, 13)); // NOI18N
        btnNavReports.setForeground(new java.awt.Color(100, 116, 139));
        btnNavReports.setText("Reports");
        btnNavReports.setBorderPainted(false);
        btnNavReports.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavReports.setFocusPainted(false);
        btnNavReports.setPreferredSize(new java.awt.Dimension(225, 23));
        btnNavReports.addActionListener(this::btnNavReportsActionPerformed);

        btnNavHome.setFont(new java.awt.Font("Geist", 0, 13)); // NOI18N
        btnNavHome.setForeground(new java.awt.Color(100, 116, 139));
        btnNavHome.setText("Home");
        btnNavHome.setBorderPainted(false);
        btnNavHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavHome.setFocusPainted(false);
        btnNavHome.setPreferredSize(new java.awt.Dimension(225, 23));
        btnNavHome.addActionListener(this::btnNavHomeActionPerformed);

        javax.swing.GroupLayout sidebarPanelLayout = new javax.swing.GroupLayout(sidebarPanel);
        sidebarPanel.setLayout(sidebarPanelLayout);
        sidebarPanelLayout.setHorizontalGroup(
            sidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(sidebarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNavPOS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNavInventory, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(btnNavProducts, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(btnNavSuppliers, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(btnNavAuditLogs, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(btnNavLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(btnNavTransactions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNavPurchases, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(btnNavExpenses, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(btnNavReports, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(btnNavHome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        sidebarPanelLayout.setVerticalGroup(
            sidebarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarPanelLayout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNavHome, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNavPOS, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNavTransactions, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNavInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNavProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNavSuppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNavPurchases, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNavExpenses, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNavReports, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNavAuditLogs, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                .addComponent(btnNavLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        add(sidebarPanel, java.awt.BorderLayout.LINE_START);

        ContentWrapperPanel.setBackground(new java.awt.Color(248, 250, 252));
        ContentWrapperPanel.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(226, 232, 240)));
        jPanel3.setPreferredSize(new java.awt.Dimension(1040, 60));

        jLabel4.setFont(new java.awt.Font("Geist Medium", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Welcome");

        lblUser.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        lblUser.setForeground(new java.awt.Color(15, 23, 42));
        lblUser.setText("User");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(417, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ContentWrapperPanel.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        MainCardsPanel.setBackground(new java.awt.Color(248, 250, 252));
        MainCardsPanel.setLayout(new java.awt.CardLayout());
        ContentWrapperPanel.add(MainCardsPanel, java.awt.BorderLayout.CENTER);

        add(ContentWrapperPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNavAuditLogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavAuditLogsActionPerformed
        // Loads logs data
        logsScreen.loadLogs("");

        // Shows logs screen
        cardLayout.show(MainCardsPanel, "CardLogs");

        // Highlights clicked button
        setActiveNavButton(btnNavAuditLogs);
    }//GEN-LAST:event_btnNavAuditLogsActionPerformed

    private void btnNavLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavLogoutActionPerformed
        // Confirmation dialog
        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out of the system?",
                "Confirm Logout",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        // If user clicked YES
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {

            // Clears logged-in user info
            LoginPanel.loggedInUserId = 0;
            LoginPanel.loggedInUsername = "";

            // Gets current window
            java.awt.Window currentWindow
                    = javax.swing.SwingUtilities.getWindowAncestor(this);

            // Closes current dashboard window
            if (currentWindow != null) {
                currentWindow.dispose();
            }

            // Opens Login Window again
            java.awt.EventQueue.invokeLater(() -> {

                javax.swing.JFrame loginFrame
                        = new javax.swing.JFrame("WeeFee FoodHub - Login");

                loginFrame.setDefaultCloseOperation(
                        javax.swing.JFrame.EXIT_ON_CLOSE
                );

                // Adds LoginPanel into frame
                loginFrame.getContentPane().add(
                        new ui.auth.LoginPanel()
                );

                // Adjusts frame size
                loginFrame.pack();

                // Centers window
                loginFrame.setLocationRelativeTo(null);

                // Shows login window
                loginFrame.setVisible(true);
            });
        }
    }//GEN-LAST:event_btnNavLogoutActionPerformed

    private void btnNavPOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavPOSActionPerformed
        // Loads menu cards
        posScreen.loadMenuCards("", "All");

        // Shows POS screen
        cardLayout.show(MainCardsPanel, "CardPOS");

        // Highlights button
        setActiveNavButton(btnNavPOS);
    }//GEN-LAST:event_btnNavPOSActionPerformed

    private void btnNavInventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavInventoryActionPerformed
        // Loads inventory table data
        inventoryScreen.loadInventoryToTable();

        // Shows Inventory screen
        cardLayout.show(MainCardsPanel, "CardInventory");

        // Highlights button
        setActiveNavButton(btnNavInventory);
    }//GEN-LAST:event_btnNavInventoryActionPerformed

    private void btnNavProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavProductsActionPerformed
        // Loads product records
        productsScreen.loadProducts("");

        // Shows Products screen
        cardLayout.show(MainCardsPanel, "CardProducts");

        // Highlights button
        setActiveNavButton(btnNavProducts);
    }//GEN-LAST:event_btnNavProductsActionPerformed

    private void btnNavSuppliersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavSuppliersActionPerformed
        // Loads suppliers records
        suppliersScreen.loadSuppliers("");

        // Shows Suppliers screen
        cardLayout.show(MainCardsPanel, "CardSuppliers");

        // Highlights button
        setActiveNavButton(btnNavSuppliers);
    }//GEN-LAST:event_btnNavSuppliersActionPerformed

    private void btnNavTransactionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavTransactionsActionPerformed
        // Loads transaction records
        transactionsScreen.loadTransactions("");

        // Shows Transactions screen
        cardLayout.show(MainCardsPanel, "CardTransactions");

        // Highlights button
        setActiveNavButton(btnNavTransactions);
    }//GEN-LAST:event_btnNavTransactionsActionPerformed

    private void btnNavPurchasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavPurchasesActionPerformed
        // Loads purchase records
        purchasesScreen.loadPurchases();

        // Shows Purchases screen
        cardLayout.show(MainCardsPanel, "CardPurchases");

        // Highlights button
        setActiveNavButton(btnNavPurchases);
    }//GEN-LAST:event_btnNavPurchasesActionPerformed

    private void btnNavExpensesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavExpensesActionPerformed
        // Loads expenses records
        expensesScreen.loadExpenses();
        
        // Shows Expenses screen
        cardLayout.show(MainCardsPanel, "CardExpenses");

        // Highlights button
        setActiveNavButton(btnNavExpenses);
    }//GEN-LAST:event_btnNavExpensesActionPerformed

    private void btnNavReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavReportsActionPerformed
        // Shows Reports screen
        cardLayout.show(MainCardsPanel, "CardReports");

        // Highlights button
        setActiveNavButton(btnNavReports);
    }//GEN-LAST:event_btnNavReportsActionPerformed

    private void btnNavHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavHomeActionPerformed
        // Loads dashboard summary data
        homeScreen.loadDashboardData();

        // Shows Home screen
        cardLayout.show(MainCardsPanel, "CardHome");

        // Highlights button
        setActiveNavButton(btnNavHome);
    }//GEN-LAST:event_btnNavHomeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ContentWrapperPanel;
    private javax.swing.JPanel MainCardsPanel;
    private javax.swing.JButton btnNavAuditLogs;
    private javax.swing.JButton btnNavExpenses;
    private javax.swing.JButton btnNavHome;
    private javax.swing.JButton btnNavInventory;
    private javax.swing.JButton btnNavLogout;
    private javax.swing.JButton btnNavPOS;
    private javax.swing.JButton btnNavProducts;
    private javax.swing.JButton btnNavPurchases;
    private javax.swing.JButton btnNavReports;
    private javax.swing.JButton btnNavSuppliers;
    private javax.swing.JButton btnNavTransactions;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPanel sidebarPanel;
    // End of variables declaration//GEN-END:variables
}
