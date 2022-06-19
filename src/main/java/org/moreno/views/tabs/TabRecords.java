package org.moreno.views.tabs;

import com.toedter.calendar.JDateChooser;
import org.moreno.components.TabPane;
import org.moreno.utilities.Utilities;
import org.moreno.utilitiesTables.UtilitiesTables;
import org.moreno.utilitiesTables.cellRendereds.RecordCellRendered;
import org.moreno.utilitiesTables.tablesModels.RecordTableModel;
import org.moreno.views.VPrincipal;
import org.moreno.views.dialogs.DEntrance;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class TabRecords {
    private TabPane contentPane;
    private JDateChooser dateStart;
    private JDateChooser dateEnd;
    private JTable table;
    private JButton btnNewEntrance;
    private JButton btnNewExit;
    private JSplitPane splitPane;
    private RecordTableModel model;

    public TabRecords() {
        initComponents();
        btnNewEntrance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNewEntrance();
            }
        });
        btnNewExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    private void loadNewEntrance(){
        DEntrance dEntrance=new DEntrance();
        dEntrance.setVisible(true);
        UtilitiesTables.updateTable(table);
    }
    private void initComponents() {
        contentPane.setTitle("Registros");
        loadTable();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                splitPane.setDividerLocation(100);
            }
        });
    }
    private void loadTable(){
        model=new RecordTableModel(VPrincipal.records);
        table.setModel(model);
        RecordCellRendered.setCellRenderer(table);
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