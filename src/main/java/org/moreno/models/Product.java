package org.moreno.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.moreno.utilities.Contabilidad;

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
    @NotEmpty(message = "Cantidad")
    private Integer stockActual;
    @Column
    @NotEmpty(message = "Unida de medida")
    private String unitMeasure;
    @Column
    @NotNull(message = "Fecha")
    private Date lastEntrance;
    @Column
    @javax.validation.constraints.Digits(integer =10,fraction = 2,message = "Precio")
    @DecimalMin(value = "0.0",message = "Precio")
    private Double lastPrice;
    @OneToMany(mappedBy = "product")
    private List<Record> records=new ArrayList<>();

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

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
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
}
