package org.moreno.tablesModels;

import org.moreno.models.Product;
import org.moreno.models.Record;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {
    private String[] columnNames = {"FECHA","TIPO/DOC","NUM/DOC","DESCRIPCIÓN","CANT","PRECIO","TOTAL","SALDO/CANT","SALDO/PRECIO","SALDO/TOTAL"};
    private Class[] m_colTypes = {Date.class,String.class,String.class,String.class,Integer.class,Double.class,Double.class,Integer.class,Double.class,Double.class};
    private List<Record> vector;

    public ProductTableModel(List<Record> vector){
        this.vector=vector;
    }
    @Override
    public int getRowCount() {
        return vector.size();
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Class getColumnClass(int col) {
        return m_colTypes[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Record record=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return record.getDate();
            case 1:
                return getTipoDocumento(record);
            case 2:
                return record.getNumberDocument();
            case 3:
                return record.getDescription();
            case 4:
                return record.getQuantity();
            case 5:
                return record.getPrice();
            case 6:
                return record.getSubTotal();
            case 7:
                return record.getQuantityAcount();
            case 8:
                return record.getPrice();
            default:
                return record.getSubTotalAcount();
        }
    }
    public Record get(int index){
        return vector.get(index);
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (m_colTypes[columnIndex].equals(JButton.class)) {
            return true;
        }
        return false;
    }
    private String getTipoDocumento(Record record){
        switch (record.getTypeDocument()){
            case 0:
                return "Boleta";
            default:
                return "Factura";
        }
    }
}