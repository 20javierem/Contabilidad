package org.moreno.utilitiesTables.tablesModels;

import org.moreno.models.Record;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class RecordTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID","FECHA","TIPO","PRODUCTO","TIPO/DOC","NUM/DOC","DESCRIPCIÓN","CANT","PRECIO","TOTAL","SALDO/CANT","SALDO/PRECIO","SALDO/TOTAL"};
    private Class[] m_colTypes = {Integer.class,Date.class,String.class,String.class,String.class,String.class,String.class,Integer.class,Double.class,Double.class,Integer.class,Double.class,Double.class};
    private List<Record> vector;

    public RecordTableModel(List<Record> vector){
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
                return record.getId();
            case 1:
                return record.getDate();
            case 2:
                return record.isEntrance()?"ENTRADA":"SALIDA";
            case 3:
                return record.getProduct().getName();
            case 4:
                return getTipoDocumento(record);
            case 5:
                return record.getNumberDocument();
            case 6:
                return record.getDescription();
            case 7:
                return record.getQuantity();
            case 8:
                return record.getPrice();
            case 9:
                return record.getSubTotal();
            case 10:
                return record.getQuantityAcount();
            case 11:
                return record.getQuantityAcount()==0.0?0.00:record.getPrice();
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
                return "BOLETA";
            case 1:
                return "FACTURA";
            default:
                return "";
        }
    }
}