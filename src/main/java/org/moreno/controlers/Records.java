package org.moreno.controlers;

import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.moreno.models.Record;
import org.moreno.utilities.Contabilidad;

import java.util.Vector;

public class Records extends Contabilidad {
    private static Root<Record> root;
    private static CriteriaQuery<Record> criteria;
    private static Vector<Record> todos;

    public static Record get(Integer id) {
        Record record = session.find(Record.class, id, LockModeType.NONE);
        return record;
    }
    public static Vector<Record> getTodos(){
        criteria = builder.createQuery(Record.class);
        criteria.select(criteria.from(Record.class));
        todos = new Vector<>(session.createQuery(criteria).getResultList());
        return todos;
    }
    public static Vector<Record> getUltimos100(){
        criteria = builder.createQuery(Record.class);
        root = criteria.from(Record.class);
        criteria.select(root).orderBy(builder.desc(root.get("id")));
        Vector<Record> p= new Vector<>(session.createQuery(criteria).setMaxResults(100).getResultList());
        return p;
    }
}
