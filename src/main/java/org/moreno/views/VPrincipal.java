package org.moreno.views;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.moreno.App;
import org.moreno.components.DnDTabbedPane;
import org.moreno.components.TabPane;
import org.moreno.controlers.Products;
import org.moreno.models.Product;
import org.moreno.utilities.Contabilidad;
import org.moreno.utilities.Utilities;
import org.moreno.views.menus.MenuProductos;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Locale;

public class VPrincipal extends JFrame {
    private JPanel contentPane;
    private JButton btnNuevoProducts;
    private JSplitPane splitPane;
    private JPanel panelControles;
    private JButton btnProducts;
    private JButton btnSalir;
    private JButton btnGestionar;
    private JPanel panelMenus;
    private JLabel lblUsuario;
    private DnDTabbedPane tabContenido;
    private JPanel panelDeTabPane;
    private MenuProductos menuProductos;
    private List<Product> products;
    private JButton jButton;

    public VPrincipal() {
        initComponents();
        btnProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarMenuInventario();
            }
        });
        btnGestionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }

    private void cargarMenuInventario() {
        despintar(btnGestionar, new ImageIcon(App.class.getResource("Icons/x32/gestionarDefecto.png")));
        pintar(btnProducts, new ImageIcon(App.class.getResource("Icons/x32/inventarioSeleccionado.png")));
        splitPane.setRightComponent(null);
        splitPane.setRightComponent(menuProductos.getContentPane());
        menuProductos.loadProducts();
        contentPane.updateUI();
    }

    private void quitarBordes() {
        Border border = BorderFactory.createEmptyBorder();
        btnProducts.setBorder(border);
        btnGestionar.setBorder(border);
    }

    private void despintar(JButton boton, Icon icon) {
        boton.setIcon(icon);
        boton.setForeground(new Color(0xFFFFFF));
        boton.setContentAreaFilled(false);
        ((JPanel) boton.getParent()).setOpaque(false);
    }

    private void pintar(JButton boton, Icon icon) {
        boton.setIcon(icon);
        boton.setForeground(new Color(0x000000));
        boton.setContentAreaFilled(true);
        ((JPanel) boton.getParent()).setOpaque(true);
    }

    private void initComponents() {
        setTitle("Software-Tienda");
        setContentPane(contentPane);
        setExtendedState(MAXIMIZED_BOTH);
        quitarBordes();
        loadAll();
        añadirButtonOnJTabedpane();
        menuProductos = new MenuProductos(tabContenido);
        cargarMenuInventario();
        pack();
        setLocationRelativeTo(null);
        loadCursors();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (salir()) {
                    Contabilidad.close();
                    dispose();
                }
            }
        });
    }

    private void añadirButtonOnJTabedpane() {
        tabContenido.setAlignmentX(1.0f);
        tabContenido.setAlignmentY(0.0f);
        panelDeTabPane.setLayout(new OverlayLayout(panelDeTabPane));
        jButton = new JButton();
        jButton.setMargin(new Insets(6, 3, 6, 3));
        jButton.setAlignmentX(1.0f);
        jButton.setAlignmentY(0.0f);
        jButton.setFocusPainted(false);
        jButton.setIcon(new ImageIcon(App.class.getResource("Icons/x16/menu1.png")));
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Utilities.buttonSelectedOrEntered(jButton);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Utilities.buttonExited(jButton);
            }
        });
        panelDeTabPane.add(jButton);
        panelDeTabPane.add(tabContenido);
        JPopupMenu pop_up = new JPopupMenu();
        JMenuItem cerrarPestaña = new JMenuItem("Cerrar Pestaña");
        JMenuItem cerrarOtras = new JMenuItem("Cerrar Otras Pestañas");
        JMenuItem cerrarTodas = new JMenuItem("Cerrar Todas Las Pestañas");
        cerrarPestaña.addActionListener(e -> {
            if (tabContenido.getSelectedIndex() != -1) {
                tabContenido.removeTabAt(tabContenido.getSelectedIndex());
            }
        });
        cerrarOtras.addActionListener(e -> {
            if (tabContenido.getSelectedIndex() != -1) {
                TabPane tab = (TabPane) tabContenido.getComponentAt(tabContenido.getSelectedIndex());
                tabContenido.removeAll();
                tabContenido.addTab(tab.getTitle(), tab.getIcon(), tab);
            }
        });
        cerrarTodas.addActionListener(e -> {
            tabContenido.removeAll();
        });
        pop_up.add(cerrarPestaña);
        pop_up.addSeparator();
        pop_up.add(cerrarOtras);
        pop_up.add(cerrarTodas);
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1) {
                    pop_up.show(jButton, e.getX(), e.getY());
                }
            }
        });
        tabContenido.setBotonEsquina(jButton);
    }

    private boolean salir() {
        int sioNo = JOptionPane.showOptionDialog(null, "¿Está seguro?", "Salir", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");
        return sioNo == 0;
    }

    private void loadAll() {
        products = Products.getTodos();
    }

    private void loadCursors() {
        btnGestionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProducts.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevoProducts.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JToolBar toolBar1 = new JToolBar();
        panel1.add(toolBar1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        btnNuevoProducts = new JButton();
        Font btnNuevoProductsFont = this.$$$getFont$$$(null, -1, 14, btnNuevoProducts.getFont());
        if (btnNuevoProductsFont != null) btnNuevoProducts.setFont(btnNuevoProductsFont);
        btnNuevoProducts.setIcon(new ImageIcon(getClass().getResource("/org/moreno/Icons/x32/inventario.png")));
        btnNuevoProducts.setText("Productos");
        toolBar1.add(btnNuevoProducts);
        panelDeTabPane = new JPanel();
        panelDeTabPane.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panelDeTabPane, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tabContenido = new DnDTabbedPane();
        panelDeTabPane.add(tabContenido, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, 0));
        contentPane.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        splitPane = new JSplitPane();
        splitPane.setDividerSize(0);
        Font splitPaneFont = this.$$$getFont$$$(null, -1, -1, splitPane.getFont());
        if (splitPaneFont != null) splitPane.setFont(splitPaneFont);
        panel2.add(splitPane, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(200, 200), null, 0, false));
        panelControles = new JPanel();
        panelControles.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, 0));
        panelControles.setBackground(new Color(-12566462));
        splitPane.setLeftComponent(panelControles);
        final JSeparator separator1 = new JSeparator();
        panelControles.add(separator1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 5, 0, 0), -1, -1));
        panel3.setBackground(new Color(-12811323));
        panel3.setOpaque(false);
        panelControles.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnProducts = new JButton();
        btnProducts.setBackground(new Color(-4342339));
        btnProducts.setContentAreaFilled(false);
        btnProducts.setFocusPainted(false);
        Font btnProductsFont = this.$$$getFont$$$(null, -1, 16, btnProducts.getFont());
        if (btnProductsFont != null) btnProducts.setFont(btnProductsFont);
        btnProducts.setForeground(new Color(-1));
        btnProducts.setHorizontalAlignment(0);
        btnProducts.setHorizontalTextPosition(0);
        btnProducts.setIcon(new ImageIcon(getClass().getResource("/org/moreno/Icons/x32/inventarioDefecto.png")));
        btnProducts.setIconTextGap(-4);
        btnProducts.setRolloverEnabled(true);
        btnProducts.setText("Productos");
        btnProducts.setVerticalAlignment(0);
        btnProducts.setVerticalTextPosition(3);
        panel3.add(btnProducts, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(80, 70), new Dimension(80, 70), new Dimension(80, 70), 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 3, new Insets(2, 0, 2, 0), -1, -1));
        panel4.setOpaque(false);
        panelControles.add(panel4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel4.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel4.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        btnSalir = new JButton();
        btnSalir.setIcon(new ImageIcon(getClass().getResource("/org/moreno/Icons/x16/iniciar-sesion.png")));
        btnSalir.setMaximumSize(new Dimension(38, 38));
        btnSalir.setMinimumSize(new Dimension(38, 38));
        btnSalir.setPreferredSize(new Dimension(38, 38));
        btnSalir.setText("");
        panel4.add(btnSalir, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panelControles.add(spacer3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 5, 0, 0), -1, -1));
        panel5.setBackground(new Color(-12811323));
        panel5.setOpaque(false);
        panelControles.add(panel5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnGestionar = new JButton();
        btnGestionar.setBackground(new Color(-4342339));
        btnGestionar.setContentAreaFilled(false);
        btnGestionar.setFocusPainted(false);
        Font btnGestionarFont = this.$$$getFont$$$(null, -1, 16, btnGestionar.getFont());
        if (btnGestionarFont != null) btnGestionar.setFont(btnGestionarFont);
        btnGestionar.setForeground(new Color(-1));
        btnGestionar.setHorizontalAlignment(0);
        btnGestionar.setHorizontalTextPosition(0);
        btnGestionar.setIcon(new ImageIcon(getClass().getResource("/org/moreno/Icons/x32/gestionarDefecto.png")));
        btnGestionar.setIconTextGap(-4);
        btnGestionar.setRolloverEnabled(true);
        btnGestionar.setText("Gestionar");
        btnGestionar.setVerticalAlignment(0);
        btnGestionar.setVerticalTextPosition(3);
        panel5.add(btnGestionar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(80, 70), new Dimension(80, 70), new Dimension(80, 70), 0, false));
        panelMenus = new JPanel();
        panelMenus.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane.setRightComponent(panelMenus);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 10, 0, 0), 5, -1));
        panel6.setBackground(new Color(-12566462));
        panel2.add(panel6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 33), new Dimension(-1, 33), new Dimension(-1, 33), 0, false));
        lblUsuario = new JLabel();
        Font lblUsuarioFont = this.$$$getFont$$$(null, -1, 16, lblUsuario.getFont());
        if (lblUsuarioFont != null) lblUsuario.setFont(lblUsuarioFont);
        lblUsuario.setForeground(new Color(-1));
        lblUsuario.setText("Usuario");
        panel6.add(lblUsuario, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
