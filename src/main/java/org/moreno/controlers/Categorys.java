package org.moreno.controlers;

import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.moreno.models.Category;
import org.moreno.utilities.Contabilidad;

import java.util.Objects;
import java.util.Vector;

public class Categorys extends Contabilidad {
    private static Root<Category> root;
    private static CriteriaQuery<Category> criteria;
    private static Vector<Category> todos;

    public static Category get(Integer id) {
        Category product = session.find(Category.class, id, LockModeType.NONE);
        return product;
    }
    public static Vector<Category> getTodos(){
        criteria = builder.createQuery(Category.class);
        root = criteria.from(Category.class);
        criteria.select(root).orderBy(builder.desc(root.get("id")));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }

    public static boolean exist(Category category){
        criteria = builder.createQuery(Category.class);
        root=criteria.from(Category.class);
        criteria.select(root).where(builder.equal(root.get("name"), category.getName()));
        Category category1=session.createQuery(criteria).uniqueResult();
        if(category1!=null){
            if(category1.isActive()){
                if(category.getId()!=null){
                    return !Objects.equals(category.getId(), category1.getId());
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
