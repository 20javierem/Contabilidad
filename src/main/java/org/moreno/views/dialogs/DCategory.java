package org.moreno.views.dialogs;

import jakarta.validation.ConstraintViolation;
import org.moreno.controlers.Categorys;
import org.moreno.models.Category;
import org.moreno.utilities.Contabilidad;
import org.moreno.utilities.PlaceHolder;
import org.moreno.utilities.Utilities;
import org.moreno.validators.CategoryValidator;
import org.moreno.views.VPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Set;

public class DCategory extends JDialog{
    private JTextField txtName;
    private JButton btnHecho;
    private JButton btnSave;
    private JPanel contentPane;
    private Category category;

    public DCategory(){
        this(new Category());
    }
    public DCategory(Category category){
        this.category=category;
        initComponents();
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
    }
    private void onDispose(){
        dispose();
    }
    private void limpiarControles(){
        category=new Category();
        txtName.setText(null);
    }
    private void onCancel(){
        category.refresh();
        onDispose();
    }
    private void initComponents(){
        setContentPane(contentPane);
        getRootPane().setDefaultButton(btnSave);
        if(category.getId()!=null){
            this.setTitle("Editar categoría");
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
            this.setTitle("Nuevo categoría");
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
        txtName.setText(category.getName());
    }
    private void save(){
        category.setName(txtName.getText().trim().toUpperCase());
        category.setActive(true);
        Set<ConstraintViolation<Category>> errors = CategoryValidator.loadViolations(category);
        if (errors.isEmpty()) {
            if(!Categorys.exist(category)){
                if(category.getId()==null){
                    category.save();
                    VPrincipal.categories.add(0,category);
                    limpiarControles();
                    Utilities.sendNotification("ÉXITO","Categoría registrada", TrayIcon.MessageType.INFO);
                }else{
                    category.save();
                    Utilities.sendNotification("ÉXITO","Cambios guardados", TrayIcon.MessageType.INFO);
                    onDispose();
                }
            }else{
                Utilities.sendNotification("ERROR","La categoría ya existe", TrayIcon.MessageType.ERROR);
            }
        } else {
            CategoryValidator.mostrarErrores(errors);
        }
    }
}
