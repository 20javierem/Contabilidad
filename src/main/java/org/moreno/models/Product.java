package org.moreno.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.moreno.utilities.Contabilidad;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Product extends Contabilidad {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;
    @Column
    @NotEmpty(message = "Nombre")
    private String name;
    @ManyToOne
    @JoinColumn(name = "fk_category",nullable = false)
    @NotNull(message = "CATEGORÍA")
    private Category category;
    @Column
    private Double stockActual=0.0;
    @Column
    @NotEmpty(message = "Unida de medida")
    private String unitMeasure;
    @Column
    private Date lastEntrance;
    @Column
    private Double lastPrice;
    @OneToMany(mappedBy = "product")
    private List<Record> records=new ArrayList<>();

    private boolean active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getStockActual() {
        return stockActual;
    }

    public void setStockActual(Double stockActual) {
        this.stockActual = stockActual;
    }

    public Date getLastEntrance() {
        return lastEntrance;
    }

    public void setLastEntrance(Date lastEntrance) {
        this.lastEntrance = lastEntrance;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void updateForEntrance(Record record){
        setLastPrice(record.getPrice());
        setStockActual(getStockActual()+record.getQuantity());
        setLastEntrance(record.getDate());
        save();
    }
    public static class ListCellRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Product) {
                value = ((Product) value).getName();
            }
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            return this;
        }
    }
}
