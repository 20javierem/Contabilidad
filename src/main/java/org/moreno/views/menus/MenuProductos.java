package org.moreno.views.menus;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.moreno.components.DnDTabbedPane;
import org.moreno.utilities.Utilities;
import org.moreno.views.tabs.TabProducts;
import org.moreno.views.tabs.TabRecords;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class MenuProductos {
    private JPanel contentPane;
    private JButton btnProducts;
    private JButton btnCardex;
    private JButton btnEntradasCanceladas;
    private DnDTabbedPane tabContenido;
    private TabProducts tabProducts;
    private TabRecords tabRecords;

    public MenuProductos(DnDTabbedPane tabContenido) {
        this.tabContenido = tabContenido;
        btnProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProducts();
            }
        });
        btnEntradasCanceladas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnCardex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRecords();
            }
        });
    }

    public void loadProducts() {
        btnEntradasCanceladas.setBackground(new JButton().getBackground());
        btnEntradasCanceladas.setForeground(new JButton().getForeground());
        btnCardex.setBackground(new JButton().getBackground());
        btnCardex.setForeground(new JButton().getForeground());
        Utilities.buttonSelectedOrEntered2(btnProducts);
        btnProducts.setForeground(Color.white);
        if (tabProducts == null) {
            tabProducts = new TabProducts(tabContenido);
        }
        if (tabContenido.indexOfComponent(tabProducts.getContentPane()) == -1) {
            tabProducts = new TabProducts(tabContenido);
            tabProducts.getContentPane().setOption(btnProducts);
            tabContenido.addTab(tabProducts.getContentPane().getTitle(), tabProducts.getContentPane().getIcon(), tabProducts.getContentPane());

        }
        tabContenido.setSelectedComponent(tabProducts.getContentPane());
    }
    public void loadRecords() {
        btnEntradasCanceladas.setBackground(new JButton().getBackground());
        btnEntradasCanceladas.setForeground(new JButton().getForeground());
        btnProducts.setBackground(new JButton().getBackground());
        btnProducts.setForeground(new JButton().getForeground());
        Utilities.buttonSelectedOrEntered2(btnCardex);
        btnCardex.setForeground(Color.white);
        if (tabRecords == null) {
            tabRecords = new TabRecords();
        }
        if (tabContenido.indexOfComponent(tabRecords.getContentPane()) == -1) {
            tabRecords = new TabRecords();
            tabRecords.getContentPane().setOption(btnCardex);
            tabContenido.addTab(tabRecords.getContentPane().getTitle(), tabRecords.getContentPane().getIcon(), tabRecords.getContentPane());

        }
        tabContenido.setSelectedComponent(tabRecords.getContentPane());
    }
    public JPanel getContentPane() {
        return contentPane;
    }

}
