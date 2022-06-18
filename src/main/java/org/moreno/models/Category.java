package org.moreno.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.moreno.utilities.Contabilidad;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends Contabilidad {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;
    @Column
    @NotEmpty(message = "Nombre")
    private String name;
    @Column
    private boolean active;
    @OneToMany(mappedBy = "category")
    private List<Product> products=new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
