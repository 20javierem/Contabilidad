package org.moreno.views.tabs;

import org.moreno.App;
import org.moreno.components.TabPane;
import org.moreno.components.TabbedPane;
import org.moreno.models.Category;
import org.moreno.models.Product;
import org.moreno.utilitiesTables.UtilitiesTables;
import org.moreno.utilitiesTables.cellRendereds.ProductCellRendered;
import org.moreno.utilitiesTables.tablesModels.ProductTableModel;
import org.moreno.utilities.PlaceHolder;
import org.moreno.views.VPrincipal;
import org.moreno.views.dialogs.DCategory;
import org.moreno.views.dialogs.DProduct;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabProducts {
    private TabPane contentPane;
    private JTable table;
    private JButton btnNewProduct;
    private JButton btnClearFilters;
    private JButton btnNewCategory;
    private JComboBox cbbCategory;
    private JTextField txtSearch;
    private JSplitPane splitPane;
    private JButton mostrar = new JButton();
    private ProductTableModel model;
    private Map<Integer, String> listaFiltros = new HashMap<Integer, String>();
    private TableRowSorter<ProductTableModel> modeloOrdenado;
    private List<RowFilter<ProductTableModel, String>> filtros = new ArrayList<>();
    private RowFilter filtroand;
    private TabbedPane dnDTabbedPane;

    public TabProducts(TabbedPane dnDTabbedPane) {
        this.dnDTabbedPane=dnDTabbedPane;
        initComponents();
        btnNewProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNewProduct();
            }
        });
        btnNewCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNewCategory();
            }
        });
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrar();
            }
        });
        cbbCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrar();
            }
        });
        btnClearFilters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFiltros();
            }
        });
    }

    private void initComponents() {
        contentPane.setTitle("Productos");
        PlaceHolder.placeHolder(txtSearch, "Producto...", new ImageIcon(App.class.getResource("Icons/x24/lupa.png")));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                cargarMostrarBuscador();
            }
        });
        loadProducts();
        insertarMenuPopUp();
    }
    private void limpiarFiltros(){
        cbbCategory.setSelectedIndex(0);
        mostrar.doClick();
    }
    private void insertarMenuPopUp(){
        JPopupMenu pop_up = new JPopupMenu();
        JMenuItem editarProducto = new JMenuItem("Editar Producto", new ImageIcon(App.class.getResource("Icons/x16/editar.png")));
        JMenuItem verCardex = new JMenuItem("Ver Cardex", new ImageIcon(App.class.getResource("Icons/x16/mostrarContraseña.png")));
        editarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarEditarProducto();
            }
        });
        verCardex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCardexofProduct();
            }
        });
        pop_up.add(editarProducto);
        pop_up.add(verCardex);
        table.addMouseListener( new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = table.rowAtPoint( e.getPoint() );
                    table.setRowSelectionInterval(row,row);
                    pop_up.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }
    private void cargarEditarProducto(){
        if(table.getSelectedRow()!=-1){
            Product product= model.get(table.convertRowIndexToModel(table.getSelectedRow()));
            DProduct dNuevoProducto=new DProduct(product);
            dNuevoProducto.setVisible(true);
        }
    }
    private void loadCardexofProduct(){
        if(table.getSelectedRow()!=-1){
            Product product= model.get(table.convertRowIndexToModel(table.getSelectedRow()));
            TabCardexProduct cardexProduct=new TabCardexProduct(product);
            if(dnDTabbedPane.indexOfTab(cardexProduct.getContentPane().getTitle())==-1){
                dnDTabbedPane.addTab(cardexProduct.getContentPane().getTitle(), cardexProduct.getContentPane().getIcon(), cardexProduct.getContentPane());
            }
            dnDTabbedPane.setSelectedIndex(dnDTabbedPane.indexOfTab(cardexProduct.getContentPane().getTitle()));

        }
    }
    private void loadProducts(){
        model=new ProductTableModel(VPrincipal.products);
        table.setModel(model);
        modeloOrdenado = new TableRowSorter<>(model);
        table.setRowSorter(modeloOrdenado);
        ProductCellRendered.setCellRenderer(table,listaFiltros);
        cbbCategory.setModel(new DefaultComboBoxModel(VPrincipal.categoriesWithAll));
        cbbCategory.setRenderer(new Category.ListCellRenderer());
    }
    private void loadNewProduct(){
        DProduct dProduct=new DProduct();
        dProduct.setVisible(true);
        UtilitiesTables.updateTable(table);
    }
    private void loadNewCategory(){
        DCategory dCategory=new DCategory();
        dCategory.setVisible(true);
    }
    public void filtrar() {
        filtros.clear();
        String busqueda = txtSearch.getText().trim();
        filtros.add(RowFilter.regexFilter("(?i)"+ busqueda,0,1,2,3));
        listaFiltros.put(0, busqueda);
        listaFiltros.put(1, busqueda);
        listaFiltros.put(2, busqueda);
        listaFiltros.put(3, busqueda);
        if(cbbCategory.getSelectedIndex()!=0){
            Category category=(Category) cbbCategory.getSelectedItem();
            filtros.add(RowFilter.regexFilter(category.getName(),1));
        }
        filtroand = RowFilter.andFilter(filtros);
        modeloOrdenado.setRowFilter(filtroand);
    }
    private void cargarMostrarBuscador() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new OverlayLayout(panel));
        mostrar.setAlignmentX(1.0f);
        mostrar.setAlignmentY(0.0f);
        panel.add(mostrar);
        txtSearch.setMargin(new Insets(2,6,2,-3));
        txtSearch.add(panel);
        mostrar.setVisible(false);
        mostrar.setBorderPainted(false);
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                mostrar.setVisible(txtSearch.getText().length() > 0);
            }
        });
        mostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtSearch.setText(null);
                mostrar.setVisible(false);
                filtrar();
            }
        });
        mostrar.setContentAreaFilled(false);
        mostrar.setOpaque(false);
        mostrar.setIcon(new ImageIcon(App.class.getResource("Icons/x24/cerrar.png")));
        mostrar.setRolloverIcon(new ImageIcon(App.class.getResource("Icons/x24/cerrar2.png")));
        mostrar.setPressedIcon(new ImageIcon(App.class.getResource("Icons/x24/cerrar3.png")));
        mostrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mostrar.setFocusable(false);
    }
    public TabPane getContentPane() {
        return contentPane;
    }

}
