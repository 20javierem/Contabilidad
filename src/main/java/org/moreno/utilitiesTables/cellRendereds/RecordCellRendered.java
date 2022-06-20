package org.moreno.utilitiesTables.cellRendereds;

import org.moreno.utilities.Utilities;
import org.moreno.utilitiesTables.UtilitiesTables;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Map;

import static org.moreno.utilitiesTables.UtilitiesTables.buscarTexto;
import static org.moreno.utilitiesTables.UtilitiesTables.headerNegrita;

public class RecordCellRendered extends DefaultTableCellRenderer {
    private Map<Integer, String> listaFiltros;

    public RecordCellRendered(Map<Integer, String> listaFiltros){
        this.listaFiltros=listaFiltros;
    }

    public static void setCellRenderer(JTable table,Map<Integer,String> listaFiltros){
        headerNegrita(table);
        RecordCellRendered cellRendered=new RecordCellRendered(listaFiltros);
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
                case "FECHA":
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    table.getColumn(table.getColumnName(column)).setMaxWidth(100);
                    table.getColumn(table.getColumnName(column)).setMinWidth(100);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(100);
                    break;
                case "CANT":
                case "SALDO/CANT":
                case "TIPO/DOC":
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    table.getColumn(table.getColumnName(column)).setMaxWidth(90);
                    table.getColumn(table.getColumnName(column)).setMinWidth(90);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(90);
                    break;
                case "SALDO/PRECIO":
                case "PRECIO":
                case "TOTAL":
                case "SALDO/TOTAL":
                    componente.setText(Utilities.moneda.format(value));
                    componente.setHorizontalAlignment(SwingConstants.CENTER);
                    table.getColumn(table.getColumnName(column)).setMaxWidth(90);
                    table.getColumn(table.getColumnName(column)).setMinWidth(90);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(90);
                    break;
                case "NUM/DOC":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(140);
                    table.getColumn(table.getColumnName(column)).setMinWidth(140);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(140);
                    break;
                case "PRODUCTO":
                    table.getColumn(table.getColumnName(column)).setMaxWidth(250);
                    table.getColumn(table.getColumnName(column)).setMinWidth(250);
                    table.getColumn(table.getColumnName(column)).setPreferredWidth(250);
                    break;
                default:
                    break;
            }
            return componente;
        }
    }
}
