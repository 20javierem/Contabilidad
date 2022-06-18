package org.moreno.views.tabs;

import org.moreno.components.TabPane;

import javax.swing.*;

public class TabProducts {
    private TabPane contentPane;
    private JTable table;
    private JButton nuevoProductoButton;
    private JButton btnClearFilters;

    public TabProducts() {
        initComponents();
    }

    private void initComponents() {
        contentPane.setTitle("Productos");
    }

    public TabPane getContentPane() {
        return contentPane;
    }

}
