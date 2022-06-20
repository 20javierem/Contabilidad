package org.moreno.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.moreno.controlers.Records;
import org.moreno.utilities.Contabilidad;
import org.moreno.utilities.Utilities;
import org.moreno.views.VPrincipal;

import java.awt.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Record extends Contabilidad {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;
    @Column
    @NotNull(message = "Fecha")
    private Date date;
    @Column
    private Integer typeDocument;
    @Column
    private String numberDocument;
    @Column@javax.validation.constraints.Digits(integer =10,fraction = 2,message = "Cantidad")
    @DecimalMin(value = "0.01",message = "Cantidad")
    private Double quantity;
    @Column
    @javax.validation.constraints.Digits(integer =10,fraction = 2,message = "Precio")
    @DecimalMin(value = "0.0",message = "Precio")
    private Double price;
    @ManyToOne
    @JoinColumn(name = "fk_product",nullable = false)
    @NotNull(message = "Producto")
    private Product product;
    @Column
    private boolean entrance;
    @Column
    private boolean state;
    @Column
    private Double subTotal;
    @Column
    private Double quantityAcount;
    @Column
    private Double subTotalAcount;
    @Column
    @NotBlank(message = "Descripción")
    private String description;
    @Column
    private boolean active;

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(Integer typeDocument) {
        this.typeDocument = typeDocument;
    }

    public String getNumberDocument() {
        return numberDocument;
    }

    public void setNumberDocument(String numberDocument) {
        this.numberDocument = numberDocument;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getQuantityAcount() {
        return quantityAcount;
    }

    public void setQuantityAcount(Double quantityAcount) {
        this.quantityAcount = quantityAcount;
    }

    public Double getSubTotalAcount() {
        return subTotalAcount;
    }

    public void setSubTotalAcount(Double subTotalAcount) {
        this.subTotalAcount = subTotalAcount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEntrance() {
        return entrance;
    }

    public void setEntrance(boolean entrance) {
        this.entrance = entrance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean saveExit(){
        System.out.println("cantidad: "+getQuantity());
        System.out.println("stock actual: "+getProduct().getStockActual());
        if(getQuantity()>getProduct().getStockActual()){
            Utilities.sendNotification("ERROR","No hay suficiente stock", TrayIcon.MessageType.WARNING);
            return false;
        }else{
            getProduct().setStockActual(getProduct().getStockActual()-getQuantity());
            getProduct().save();
            return createRecords();
        }
    }
    private boolean createRecords(){
        Record recordActive= Records.getActive(getProduct());
        if(recordActive!=null){
            Record record;
            do{
                record=new Record();
                if(getQuantity()>recordActive.getQuantityAcount()){
                    record.setQuantity(recordActive.getQuantity());
                    record.setQuantityAcount(0.00);
                    record.setSubTotalAcount(0.00);
                    record.setPrice(recordActive.getPrice());
                    record.setActive(false);
                    setQuantity(getQuantity()-recordActive.getQuantityAcount());
                }else{
                    record.setQuantity(getQuantity());
                    record.setQuantityAcount(recordActive.getQuantityAcount()-getQuantity());
                    record.setSubTotalAcount(record.getQuantityAcount()*recordActive.getPrice());
                    record.setPrice(recordActive.getPrice());
                    record.setActive(true);
                    setQuantity(0.00);
                }
                record.setDate(getDate());
                record.setDescription(getDescription());
                record.setProduct(getProduct());
                record.setTypeDocument(getTypeDocument());
                record.setNumberDocument(getNumberDocument());
                record.setSubTotal(record.getQuantity()* record.getPrice());
                record.setEntrance(false);
                record.save();
                VPrincipal.records.add(0,record);
                recordActive.setActive(false);
                recordActive.save();
                recordActive=Records.getSecondActive(recordActive);
            }while(getQuantity()>0.00);
            return true;
        }else{
            Utilities.sendNotification("ERROR","No hay suficiente stock", TrayIcon.MessageType.WARNING);
            return false;
        }
    };
}
