package org.moreno.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import org.moreno.utilities.Contabilidad;

@Entity
public class Product extends Contabilidad {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;
    @Column
    @NotEmpty(message = "Nombre")
    private String name;

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
}
