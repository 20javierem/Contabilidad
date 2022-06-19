package org.moreno.utilitiesTables.tablesModels;
import org.moreno.models.Product;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID","CATEGORÍA","PRODUCTO","UNIDAD DE MEDIDA","STOCK ACTUAL","ÚLTIMO INGRESO","ÚLTIMO PRECIO"};
    private Class[] m_colTypes = {Integer.class,String.class,String.class,String.class,Double.class,Date.class,Double.class};
    private List<Product> vector;

    public ProductTableModel(List<Product> vector){
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
        Product product=vector.get(rowIndex);
        switch (columnIndex){
            case 0:
                return product.getId();
            case 1:
                return product.getCategory().getName();
            case 2:
                return product.getName();
            case 3:
                return product.getUnitMeasure();
            case 4:
                return product.getStockActual();
            case 5:
                return product.getLastEntrance();
            default:
                return product.getLastPrice();
        }
    }
    public Product get(int index){
        return vector.get(index);
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (m_colTypes[columnIndex].equals(JButton.class)) {
            return true;
        }
        return false;
    }
}