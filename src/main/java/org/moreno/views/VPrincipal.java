package org.moreno.views;

import org.moreno.App;
import org.moreno.components.TabbedPane;
import org.moreno.controlers.Categorys;
import org.moreno.controlers.Products;
import org.moreno.controlers.Records;
import org.moreno.models.Category;
import org.moreno.models.Product;
import org.moreno.models.Record;
import org.moreno.utilities.Contabilidad;
import org.moreno.views.menus.MenuProductos;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class VPrincipal extends JFrame{
    private JPanel contentPane;
    private JButton btnNuevoProducts;
    private JSplitPane splitPane;
    private JPanel panelControles;
    private JButton btnProducts;
    private JButton btnSalir;
    private JButton btnGestionar;
    private JPanel panelMenus;
    private JLabel lblUsuario;
    private JPanel panelDeTabPane;
    private TabbedPane tabContenido;
    private MenuProductos menuProductos;
    public static Vector<Product> products;
    public static Vector<Category> categories;
    public static Vector<Record> records;
    public static Vector<Category> categoriesWithAll;
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

        btnNuevoProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuProductos.loadProducts();
            }
        });
    }

    private void cargarMenuInventario() {
        despintar(btnGestionar, new ImageIcon(App.class.getResource("Icons/x32/gestionarDefecto.png")));
        pintar(btnProducts, new ImageIcon(App.class.getResource("Icons/x32/inventarioSeleccionado.png")));
        splitPane.setRightComponent(null);
        splitPane.setRightComponent(menuProductos.getContentPane());
        menuProductos.loadRecords();
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
        menuProductos = new MenuProductos(tabContenido);
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
        cargarMenuInventario();
        pack();
        setLocationRelativeTo(null);
    }


    private boolean salir() {
        int sioNo = JOptionPane.showOptionDialog(tabContenido.getRootPane(), "¿Está seguro?", "Salir", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");
        return sioNo == 0;
    }

    private void loadAll() {
        products = Products.getTodos();
        categories= Categorys.getTodos();
        records= Records.getUltimos100();
        loadWithAll();
    }
    private void loadWithAll(){
        categoriesWithAll=new Vector<>(categories);
        Category category=new Category();
        category.setName("TODAS");
        categoriesWithAll.add(0,category);
    }
    private void loadCursors() {
        btnGestionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProducts.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevoProducts.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

}
