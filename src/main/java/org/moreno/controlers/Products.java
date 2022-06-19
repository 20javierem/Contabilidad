package org.moreno.controlers;

import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.moreno.models.Product;
import org.moreno.models.Record;
import org.moreno.utilities.Contabilidad;

import java.util.Objects;
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
        root = criteria.from(Product.class);
        criteria.select(root).orderBy(builder.desc(root.get("id")));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }

    public static boolean exist(Product product){
        criteria = builder.createQuery(Product.class);
        root=criteria.from(Product.class);
        criteria.select(root)
                .where(builder.equal(root.get("name"), product.getName()));
        Product product1=session.createQuery(criteria).uniqueResult();
        if(product1!=null){
            if(product1.isActive()){
                if(product.getId()!=null){
                    return !Objects.equals(product.getId(), product1.getId());
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}

