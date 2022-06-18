package org.moreno.utilities;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Contabilidad {
    protected static Session session;
    protected static CriteriaBuilder builder;

    private static void buildSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        session = sessionFactory.openSession();
        builder = session.getCriteriaBuilder();
    }
    public static void initialize() {
        buildSessionFactory();
    }
    public static void close(){
        session.close();
    }

    public void save(){
        session.beginTransaction();
        session.persist(this);
        session.flush();
        session.getTransaction().commit();
    }
}
