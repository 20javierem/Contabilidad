package org.moreno.views.tabs;

import com.toedter.calendar.JDateChooser;
import org.moreno.components.TabPane;
import org.moreno.models.Product;

import javax.swing.*;
import java.util.Date;

public class TabRecords {
    private TabPane contentPane;
    private JButton btnClearFilters;
    private JDateChooser dateStart;
    private JButton btnSearchRecords;
    private JDateChooser dateEnd;
    private JComboBox cbbType;
    private JTable table;

    public TabRecords() {
        initComponents();
    }

    private void initComponents() {
        contentPane.setTitle("Cardex ");
    }

    public TabPane getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        dateEnd=new JDateChooser(new Date());
        dateStart=new JDateChooser(new Date());
    }
}
