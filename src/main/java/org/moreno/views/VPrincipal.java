package org.moreno.views;

import org.moreno.App;
import org.moreno.components.DnDTabbedPane;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VPrincipal extends JFrame{
    private JPanel contentPane;
    private JButton btnNuevaVenta;
    private JButton btnNuevoInventario;
    private JSplitPane splitPane;
    private JPanel panelControles;
    private JButton btnInventario;
    private JButton btnVentas;
    private JButton btnSalir;
    private JButton btnAlmacen;
    private JButton btnGestionar;
    private JPanel panelMenus;
    private JLabel lblUsuario;
    private DnDTabbedPane tabPane;

    public VPrincipal(){
        initComponents();
        btnVentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarMenuVentas();
            }
        });
        btnInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarMenuInventario();
            }
        });
        btnAlmacen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarMenuAlmacen();
            }
        });
        btnGestionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarMenuGestionar();
            }
        });

        btnNuevaVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                menuVentas.loadNewSale();
            }
        });
        btnNuevoInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuInventario.loadStock();
            }
        });
    }
    private void cargarMenuInventario() {
        despintar(btnVentas,new ImageIcon(App.class.getResource("Icons/x32/ventasDefecto.png")));
        despintar(btnAlmacen,new ImageIcon(App.class.getResource("Icons/x32/almacenDefecto.png")));
        despintar(btnGestionar,new ImageIcon(App.class.getResource("Icons/x32/gestionarDefecto.png")));
        pintar(btnInventario,new ImageIcon(App.class.getResource("Icons/x32/inventarioSeleccionado.png")));
        splitPane.setRightComponent(null);
        splitPane.setRightComponent(menuInventario.getContentPane());
        menuInventario.loadStock();
        contentPane.updateUI();
    }

    private void cargarMenuVentas() {
        despintar(btnInventario,new ImageIcon(App.class.getResource("Icons/x32/inventarioDefecto.png")));
        despintar(btnAlmacen,new ImageIcon(App.class.getResource("Icons/x32/almacenDefecto.png")));
        despintar(btnGestionar,new ImageIcon(App.class.getResource("Icons/x32/gestionarDefecto.png")));
        pintar(btnVentas,new ImageIcon(App.class.getResource("Icons/x32/ventasSeleccionado.png")));
        splitPane.setRightComponent(menuVentas.getContentPane());
        menuVentas.loadNewSale();
        contentPane.updateUI();
    }

    private void cargarMenuAlmacen() {
        despintar(btnVentas,new ImageIcon(App.class.getResource("Icons/x32/ventasDefecto.png")));
        despintar(btnInventario,new ImageIcon(App.class.getResource("Icons/x32/inventarioDefecto.png")));
        despintar(btnGestionar,new ImageIcon(App.class.getResource("Icons/x32/gestionarDefecto.png")));
        pintar(btnAlmacen,new ImageIcon(App.class.getResource("Icons/x32/almacenSeleccionado.png")));
        splitPane.setRightComponent(menuAlmacen.getContentPane());
        menuAlmacen.loadAlmacen();
        contentPane.updateUI();
    }

    private void cargarMenuGestionar() {
        despintar(btnVentas,new ImageIcon(App.class.getResource("Icons/x32/ventasDefecto.png")));
        despintar(btnInventario,new ImageIcon(App.class.getResource("Icons/x32/inventarioDefecto.png")));
        despintar(btnAlmacen,new ImageIcon(App.class.getResource("Icons/x32/almacenDefecto.png")));
        pintar(btnGestionar,new ImageIcon(App.class.getResource("Icons/x32/gestionarSeleccionado.png")));
        splitPane.setRightComponent(menuGestionar.getContentPane());
        menuGestionar.loadUsers();
        contentPane.updateUI();
    }
    private void quitarBordes(){
        Border border=BorderFactory.createEmptyBorder();
        btnInventario.setBorder(border);
        btnVentas.setBorder(border);
        btnAlmacen.setBorder(border);
        btnGestionar.setBorder(border);
    }
    private void despintar(JButton boton,Icon icon){
        boton.setIcon(icon);
        boton.setForeground(new java.awt.Color(0xFFFFFF));
        boton.setContentAreaFilled(false);
        ((JPanel)boton.getParent()).setOpaque(false);
    }

    private void pintar(JButton boton,Icon icon){
        boton.setIcon(icon);
        boton.setForeground(new java.awt.Color(0x000000));
        boton.setContentAreaFilled(true);
        ((JPanel)boton.getParent()).setOpaque(true);
    }
    private void initComponents(){
        setTitle("Software-Tienda");
        setContentPane(contentPane);
        setExtendedState(MAXIMIZED_BOTH);
        quitarBordes();
        loadAll();
        menuInventario=new MenuInventario(tabPane);
        menuVentas=new MenuVentas(tabPane);
        menuAlmacen=new MenuAlmacen(tabPane);
        menuGestionar=new MenuGestionar(tabPane);
        cargarMenuVentas();
        pack();
        setLocationRelativeTo(null);
        loadCursors();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (salir()) {
                    Tienda.close();
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                    super.windowClosing(e);
                }
            }
        });
    }
    private boolean salir() {
        int sioNo = JOptionPane.showOptionDialog(null, "¿Está seguro?", "Salir", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");
        return sioNo == 0;
    }
    private void loadAll(){
        brands= Brands.getActives();
        categories= Categorys.getActives();
        products= Products.getActives();
        loadWithAll();
    }
    private void loadWithAll(){
        brandsWithAll.addAll(brands);
        Brand brand=new Brand();
        brand.setName("TODAS");
        brandsWithAll.add(brand);
        categoriesWithAll.addAll(categories);
        Category category=new Category();
        brand.setName("TODAS");
        categoriesWithAll.add(category);
    }
    private void loadCursors(){
        btnAlmacen.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGestionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnInventario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVentas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevaVenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevoInventario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
