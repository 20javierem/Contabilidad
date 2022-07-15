package org.moreno.views.tabs;

import com.toedter.calendar.JDateChooser;
import org.moreno.App;
import org.moreno.components.TabPane;
import org.moreno.controlers.Records;
import org.moreno.models.Product;
import org.moreno.models.Record;
import org.moreno.utilities.PlaceHolder;
import org.moreno.utilities.Utilities;
import org.moreno.utilitiesTables.cellRendereds.RecordCellRendered;
import org.moreno.utilitiesTables.tablesModels.RecordTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class TabCardexProduct {
    private TabPane contentPane;
    private JButton btnSearchRecords;
    private JDateChooser dateStart;
    private JDateChooser dateEnd;
    private JButton btnClearFilters;
    private JComboBox cbbType;
    private JTable table;
    private JComboBox cbbCategory;
    private JTextField txtSearch;
    private Product product;
    private JButton mostrar = new JButton();
    private RecordTableModel model;
    private Map<Integer, String> listaFiltros = new HashMap<Integer, String>();
    private TableRowSorter<RecordTableModel> modeloOrdenado;
    private List<RowFilter<RecordTableModel, String>> filtros = new ArrayList<>();
    private RowFilter filtroand;

    public TabCardexProduct(Product product) {
        this.product=product;
        initComponents();
        btnSearchRecords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getRecords();
            }
        });
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrar();
            }
        });
        btnClearFilters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFilters();
            }
        });
        cbbType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrar();
            }
        });
    }

    private void initComponents() {
        contentPane.setTitle("Cardex - "+product.getName());
        loadPlaceHolders();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                cargarMostrarBuscador();
            }
        });
        loadTable(product.getRecords());
    }
    private void loadPlaceHolders(){
        PlaceHolder.placeHolder((JTextComponent) dateStart.getDateEditor().getUiComponent(), "dd/MM/yyyy");
        PlaceHolder.placeHolder((JTextComponent) dateEnd.getDateEditor().getUiComponent(), "dd/MM/yyyy");
        PlaceHolder.placeHolder(txtSearch, "Busqueda...", new ImageIcon(App.class.getResource("Icons/x24/lupa.png")));
    }
    private void loadTable(List<Record> records){
        model=new RecordTableModel(records);
        table.setModel(model);
        RecordCellRendered.setCellRenderer(table,listaFiltros);
        modeloOrdenado = new TableRowSorter<>(model);
        table.setRowSorter(modeloOrdenado);
    }
    private void getRecords(){
        if(dateStart.getDate()!=null&&dateEnd.getDate()!=null){
            List<Record> records= Records.recordsOfProductByStartAndEnd(product,dateStart.getDate(),dateEnd.getDate());
            loadTable(records);
            System.out.println(records.size());
        }else{
            Utilities.sendNotification("Advertencia","Debe ingresar el inicio y el fin", TrayIcon.MessageType.WARNING);
        }
    }
    private void clearFilters(){
        cbbType.setSelectedIndex(0);
        mostrar.doClick();
    }
    public void filtrar() {
        filtros.clear();
        String busqueda = txtSearch.getText().trim().toUpperCase();
        filtros.add(RowFilter.regexFilter(busqueda,0,1,2,3));
        listaFiltros.put(2, busqueda);
        listaFiltros.put(3, busqueda);
        listaFiltros.put(6, busqueda);
        if(cbbType.getSelectedIndex()!=0){
            filtros.add(RowFilter.regexFilter(String.valueOf(cbbType.getSelectedItem()),1));
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
        dateStart=new JDateChooser();
        dateStart.setDateFormatString("dd/MM/yyyy");
        dateEnd=new JDateChooser();
        dateEnd.setDateFormatString("dd/MM/yyyy");
    }
}
