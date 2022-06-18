package org.moreno.controlers;

import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.moreno.models.Product;
import org.moreno.models.Record;
import org.moreno.utilities.Contabilidad;

import java.util.Vector;

public class Products extends Contabilidad {
    private static Root<Product> root;
    private static CriteriaQuery<Product> criteria;
    private static Vector<Product> todos;

    public static Product get(Integer id) {
        Product product = session.find(Product.class, id, LockModeType.NONE);
        return product;
    }
    public static Vector<Product> getTodos(){
        criteria = builder.createQuery(Product.class);
        criteria.select(criteria.from(Product.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }
}

