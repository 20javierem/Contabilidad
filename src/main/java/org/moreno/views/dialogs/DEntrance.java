package org.moreno.views.dialogs;

import com.toedter.calendar.JDateChooser;
import jakarta.validation.ConstraintViolation;
import org.moreno.controlers.Products;
import org.moreno.controlers.Records;
import org.moreno.models.Category;
import org.moreno.models.Product;
import org.moreno.models.Record;
import org.moreno.utilities.Utilities;
import org.moreno.validators.ProductValidator;
import org.moreno.validators.RecordValidator;
import org.moreno.views.VPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class DEntrance extends JDialog{
    private JPanel contentPane;
    private JButton btnHecho;
    private JDateChooser dateChosser;
    private JComboBox cbbProduct;
    private JSpinner spinnerQuantity;
    private JSpinner spinnerPrice;
    private JComboBox cbbTypeDocument;
    private JTextField txtNumberDocument;
    private JCheckBox compraCheckBox;
    private JTextField txtDescription;
    private JButton btnSave;
    private JPanel paneCompra;
    private Record record;

    public DEntrance(){
        this(new Record());
        compraCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyType();
            }
        });
    }
    private void verifyType(){
        paneCompra.setVisible(compraCheckBox.isSelected());
        pack();
    }
    public DEntrance(Record record){
        this.record=record;
        initComponents();
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
    }
    private void initComponents(){
        setContentPane(contentPane);
        loadProducts();
        getRootPane().setDefaultButton(btnSave);
        if(record.getId()!=null){
            this.setTitle("Editar Entrada");
            btnHecho.setText("Cancelar");
            btnSave.setText("Guardar");
            loadRecord();
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
            this.setTitle("Nueva Entrada");
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
    private void loadProducts(){
        cbbProduct.setModel(new DefaultComboBoxModel(VPrincipal.products));
        cbbProduct.setRenderer(new Product.ListCellRenderer());
        cbbProduct.setSelectedIndex(-1);
    }
    private void loadRecord(){
        txtDescription.setText(record.getDescription());
        cbbTypeDocument.setSelectedIndex(record.getTypeDocument());
        cbbProduct.setSelectedItem(record.getProduct());
        txtNumberDocument.setText(record.getNumberDocument());
        dateChosser.setDate(record.getDate());
        spinnerQuantity.setValue(record.getQuantity());
        spinnerPrice.setValue(record.getPrice());
    }
    private void onDispose(){
        dispose();
    }
    private void onCancel(){
        record.refresh();
        onDispose();
    }
    private void save(){
        record.setDate(dateChosser.getDate());
        record.setDescription(txtDescription.getText().trim());
        record.setPrice((Double) spinnerPrice.getValue());
        record.setProduct((Product) cbbProduct.getSelectedItem());
        record.setQuantity((Double) spinnerQuantity.getValue());
        record.setTypeDocument(cbbTypeDocument.getSelectedIndex());
        record.setNumberDocument(txtNumberDocument.getText().trim());
        record.setQuantityAcount((Double) spinnerQuantity.getValue());
        record.setSubTotalAcount(record.getPrice()*record.getQuantity());
        record.setSubTotal(record.getQuantity()*record.getPrice());
        record.setEntrance(true);
        Set<ConstraintViolation<Record>> errors = RecordValidator.loadViolations(record);
        if (errors.isEmpty()) {
            record.setOnHold(record.getProduct().getStockActual()>0.0);
            record.setActive(record.getProduct().getStockActual()==0.0);
            if(record.getPrice()>0.00){
                if(record.getId()==null){
                    record.getProduct().updateForEntrance(record);
                    record.save();
                    VPrincipal.records.add(0,record);
                    limpiarControles();
                    Utilities.sendNotification("ÉXITO","Entrada registrada", TrayIcon.MessageType.INFO);
                }else{
                    record.save();
                    Utilities.sendNotification("ÉXITO","Cambios guardados", TrayIcon.MessageType.INFO);
                    onDispose();
                }
            }else{
                Utilities.sendNotification("Error", "Verfique el campo: Precio", TrayIcon.MessageType.ERROR);
            }
        } else {
            RecordValidator.mostrarErrores(errors);
        }
    }
    private void limpiarControles(){
        record=new Record();
        txtDescription.setText(null);
        txtNumberDocument.setText(null);
        cbbProduct.setSelectedIndex(-1);
        cbbTypeDocument.setSelectedIndex(-1);
        dateChosser.setDate(null);
        spinnerQuantity.setValue(0.00);
        spinnerPrice.setValue(0.00);
        compraCheckBox.setSelected(false);
        verifyType();
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        spinnerPrice = new JSpinner(new SpinnerNumberModel(0.00, 0.00, 100000.00, 0.50));
        spinnerPrice.setEditor(new JSpinner.NumberEditor(spinnerPrice, "S/#,##0.###"));
        spinnerQuantity = new JSpinner(new SpinnerNumberModel(0.00, 0.00, 100000.00, 1.00));
        spinnerQuantity.setEditor(new JSpinner.NumberEditor(spinnerQuantity, "#,##0.###"));
        dateChosser=new JDateChooser();
    }
}
