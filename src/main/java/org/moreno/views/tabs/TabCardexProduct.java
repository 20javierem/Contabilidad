package org.moreno.views.tabs;

import com.toedter.calendar.JDateChooser;
import org.moreno.components.TabPane;

import javax.swing.*;

public class TabCardexProduct {
    private TabPane contentPane;
    private JButton btnSearchRecords;
    private JDateChooser dateStart;
    private JDateChooser dateEnd;
    private JButton btnClearFilters;
    private JComboBox cbbType;
    private JTable table;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        dateStart=new JDateChooser();
        dateEnd=new JDateChooser();
    }
}
