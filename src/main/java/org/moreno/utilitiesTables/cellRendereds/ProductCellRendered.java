package org.moreno.utilitiesTables.cellRendereds;

import org.moreno.utilities.Utilities;
import org.moreno.utilitiesTables.UtilitiesTables;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;

import static org.moreno.utilitiesTables.UtilitiesTables.buscarTexto;
import static org.moreno.utilitiesTables.UtilitiesTables.headerNegrita;

public class ProductCellRendered extends DefaultTableCellRenderer {
    private Map<Integer, String> listaFiltros;

    public ProductCellRendered(Map<Integer, String> listaFiltros){
        this.listaFiltros=listaFiltros;
    }

    public static void setCellRenderer(JTable table,Map<Integer,String> listaFiltros){
        headerNegrita(table);
        ProductCellRendered cellRendered=new ProductCellRendered(listaFiltros);
        for (int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(cellRendered);
        }
    }
    public static void setCellRenderer(JTable table){
        setCellRenderer(table,null);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(table.getColumnClass(column).equals(JButton.class)){
            table.getColumn(table.getColumnName(column)).setMaxWidth(30);
            table.getColumn(table.getColumnName(column)).setMinWidth(30);
            table.getColumn(table.getColumnName(column)).setPreferredWidth(30);
            return UtilitiesTables.isButonSelected(isSelected,"x16/eliminar.png",table);
        }else{
            JTextField componente=buscarTexto(listaFiltros,value,column,this);
            switch(table.getColumnName(column)){
                case "ID":
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    table.getColumn(table.getColumnName(column)).setMaxWidth(40);
                    table.getColumn(table.getColumnName(column)).setMinWidth(40);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(40);
                    break;
                case "CATEGORÍA":
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    table.getColumn(table.getColumnName(column)).setMaxWidth(100);
                    table.getColumn(table.getColumnName(column)).setMinWidth(100);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(100);
                    break;
                case "ÚLTIMO PRECIO":
                    if(value!=null){
                        componente.setText(Utilities.moneda.format(value));
                    }
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    table.getColumn(table.getColumnName(column)).setMaxWidth(100);
                    table.getColumn(table.getColumnName(column)).setMinWidth(100);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(100);
                    break;
                case "STOCK ACTUAL":
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    table.getColumn(table.getColumnName(column)).setMaxWidth(110);
                    table.getColumn(table.getColumnName(column)).setMinWidth(110);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(110);
                    break;
                case "UNIDAD DE MEDIDA":
                case "ÚLTIMO INGRESO":
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    table.getColumn(table.getColumnName(column)).setMaxWidth(140);
                    table.getColumn(table.getColumnName(column)).setMinWidth(140);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(140);
                    break;
                default:
                    break;
            }
            return componente;
        }
    }
}
