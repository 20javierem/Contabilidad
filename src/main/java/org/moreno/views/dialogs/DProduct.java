package org.moreno.views.dialogs;

import jakarta.validation.ConstraintViolation;
import org.moreno.controlers.Products;
import org.moreno.models.Category;
import org.moreno.models.Product;
import org.moreno.utilities.Contabilidad;
import org.moreno.utilities.PlaceHolder;
import org.moreno.utilities.Utilities;
import org.moreno.validators.ProductValidator;
import org.moreno.views.VPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class DProduct extends JDialog{
    private JPanel contentPane;
    private JButton btnHecho;
    private JButton btnSave;
    private JComboBox cbbCategory;
    private JButton btnNewCategory;
    private JTextField txtName;
    private JTextField txtUnitMeasure;
    private Product product;

    public DProduct(){
        this(new Product());
    }
    public DProduct(Product product){
        this.product=product;
        initComponents();
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        btnNewCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNewCategory();
            }
        });
    }
    private void loadNewCategory(){
        DCategory dCategory=new DCategory();
        dCategory.setVisible(true);
    }
    private void onDispose(){
        dispose();
    }
    private void loadPlaceHolders(){
        PlaceHolder.placeHolder(txtUnitMeasure,"Unidad de medida");
        PlaceHolder.placeHolder(txtName,"Nombre de producto");
    }
    private void limpiarControles(){
        product=new Product();
        txtName.setText(null);
        txtUnitMeasure.setText(null);
        cbbCategory.setSelectedIndex(-1);
    }
    private void loadCombos(){
        cbbCategory.setModel(new DefaultComboBoxModel(VPrincipal.categories));
        cbbCategory.setRenderer(new Category.ListCellRenderer());
        cbbCategory.setSelectedIndex(-1);
    }
    private void onCancel(){
        product.refresh();
        onDispose();
    }
    private void initComponents(){
        setContentPane(contentPane);
        loadPlaceHolders();
        loadCombos();
        getRootPane().setDefaultButton(btnSave);
        if(product.getId()!=null){
            this.setTitle("Editar producto");
            btnHecho.setText("Cancelar");
            btnSave.setText("Guardar");
            loadProduct();
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    onCancel();
                }
            });
            contentPane.registerKeyboardAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onCancel();
                }
            }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            btnHecho.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onCancel();
                }
            });
        }else{
            this.setTitle("Nuevo producto");
            contentPane.registerKeyboardAction(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onDispose();
                }
            }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            btnHecho.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onDispose();
                }
            });
        }
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
    }
    private void loadProduct(){
        txtName.setText(product.getName());
        txtUnitMeasure.setText(product.getUnitMeasure());
        cbbCategory.setSelectedItem(product.getCategory());
    }
    private void save(){
        product.setName(txtName.getText().trim().toUpperCase());
        product.setCategory((Category) cbbCategory.getSelectedItem());
        product.setUnitMeasure(txtUnitMeasure.getText().trim().toUpperCase());
        product.setActive(true);
        Set<ConstraintViolation<Product>> errors = ProductValidator.loadViolations(product);
        if (errors.isEmpty()) {
            if(!Products.exist(product)){
                if(product.getId()==null){
                    product.save();
                    VPrincipal.products.add(0,product);
                    limpiarControles();
                    Utilities.sendNotification("ÉXITO","Producto registrado", TrayIcon.MessageType.INFO);
                }else{
                    product.save();
                    Utilities.sendNotification("ÉXITO","Cambios guardados", TrayIcon.MessageType.INFO);
                    onDispose();
                }
            }else{
                Utilities.sendNotification("ERROR","El producto ya existe", TrayIcon.MessageType.ERROR);
            }
        } else {
            ProductValidator.mostrarErrores(errors);
        }
    }
}
