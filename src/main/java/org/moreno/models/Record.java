package org.moreno.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.moreno.utilities.Contabilidad;

import java.util.Date;

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
    @Column
    @NotEmpty(message = "Cantidad")
    @Min(value = 1,message = "Cantidad")
    private Integer quantity;
    @Column
    @javax.validation.constraints.Digits(integer =10,fraction = 2,message = "Precio")
    @DecimalMin(value = "0.0",message = "Precio")
    private Double price;
    @ManyToOne
    @JoinColumn(name = "fk_product")
    @NotNull(message = "Producto")
    private Product product;
    @Column
    private boolean state;
    @Column
    private Double subTotal;
    @Column
    @NotEmpty(message = "Cantidad")
    @Min(value = 1,message = "Cantidad")
    private Integer quantityAcount;
    @Column
    private Double subTotalAcount;
    @Column
    @NotBlank(message = "Descripción")
    private String description;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
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

    public Integer getQuantityAcount() {
        return quantityAcount;
    }

    public void setQuantityAcount(Integer quantityAcount) {
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
}
