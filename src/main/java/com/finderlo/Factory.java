package com.finderlo;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Factory{
    {
        try {
            setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    SessionFactory sessionFactory;

    public static Factory instance = new Factory();

    public static SessionFactory sessionFactory(){
        return getInstance().sessionFactory;
    }

    private static Factory getInstance(){
        return instance;
    }

    protected void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

}