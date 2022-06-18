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
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (salir()) {
                    System.out.println("entró");
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

}
