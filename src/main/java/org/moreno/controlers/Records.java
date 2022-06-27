package org.moreno.controlers;

import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.moreno.models.Product;
import org.moreno.models.Record;
import org.moreno.utilities.Contabilidad;
import org.moreno.utilities.Utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    public static Record getActive(Product product){
        criteria = builder.createQuery(Record.class);
        root = criteria.from(Record.class);
        criteria.select(root).where(builder.and(builder.equal(root.get("product"), product),
                builder.isTrue(root.get("active"))));
        return session.createQuery(criteria).uniqueResult();
    }
    public static List<Record> recordsOfProductByStartAndEnd(Product product,Date start,Date end){
        start= Utilities.getDate(start,true);
        end= Utilities.getDate(end,false);
        criteria = builder.createQuery(Record.class);
        root = criteria.from(Record.class);
        criteria.select(root).where(builder.and(
                builder.equal(root.get("product"), product)),
                builder.between(root.get("date"),start,end))
                .orderBy(builder.asc(root.get("date")));
        List<Record> records=session.createQuery(criteria).getResultList();
        return records;
    }
    public static Record getSecondActive(Record record){
        criteria = builder.createQuery(Record.class);
        root = criteria.from(Record.class);
        criteria.select(root).where(builder.and(
                builder.equal(root.get("product"),record.getProduct()),
                builder.isTrue(root.get("entrance")),
                builder.isTrue(root.get("onHold"))))
                .orderBy(builder.asc(root.get("date")));
        List<Record> records=session.createQuery(criteria).getResultList();
        return records.isEmpty()?null:records.get(0);
    }
    public static boolean isFirst(Product product){
        criteria = builder.createQuery(Record.class);
        root = criteria.from(Record.class);
        criteria.select(root).where(builder.equal(root.get("product"), product));
        Vector<Record> p= new Vector<>(session.createQuery(criteria).getResultList());
        return p.isEmpty();
    }
    public static Vector<Record> getUltimos100(){
        criteria = builder.createQuery(Record.class);
        root = criteria.from(Record.class);
        criteria.select(root).orderBy(builder.desc(root.get("id")));
        return new Vector<>(session.createQuery(criteria).setMaxResults(100).getResultList());
    }
}
