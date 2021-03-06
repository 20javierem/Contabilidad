package org.moreno.views.menus;

import org.moreno.components.TabbedPane;
import org.moreno.utilities.Utilities;
import org.moreno.views.tabs.TabProducts;
import org.moreno.views.tabs.TabRecords;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class MenuProductos {
    private JPanel contentPane;
    private JButton btnProducts;
    private JButton btnCardex;
    private JButton btnEntradasCanceladas;
    private TabbedPane tabContenido;
    private TabProducts tabProducts;
    private TabRecords tabRecords;

    public MenuProductos(TabbedPane tabContenido) {
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
        if (tabContenido.indexOfTab(tabProducts.getContentPane().getTitle()) == -1) {
            tabProducts = new TabProducts(tabContenido);
            tabProducts.getContentPane().setOption(btnProducts);
            tabContenido.addTab(tabProducts.getContentPane().getTitle(), tabProducts.getContentPane().getIcon(), tabProducts.getContentPane());

        }
        tabContenido.setSelectedIndex(tabContenido.indexOfTab(tabProducts.getContentPane().getTitle()));
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
        if (tabContenido.indexOfTab(tabRecords.getContentPane().getTitle()) == -1) {
            tabRecords = new TabRecords();
            tabRecords.getContentPane().setOption(btnCardex);
            tabContenido.addTab(tabRecords.getContentPane().getTitle(), tabRecords.getContentPane().getIcon(), tabRecords.getContentPane());

        }
        tabContenido.setSelectedIndex(tabContenido.indexOfTab(tabRecords.getContentPane().getTitle()));
    }
    public JPanel getContentPane() {
        return contentPane;
    }

}
