package edu.icet.crm.dao.util;

import edu.icet.crm.entity.CustomerEntity;
import edu.icet.crm.entity.ItemsEntity;
import edu.icet.crm.entity.OrdersEntity;
import edu.icet.crm.entity.UsersEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory=createSessionFactory();
    private static SessionFactory createSessionFactory(){
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClass(CustomerEntity.class)
                .addAnnotatedClass(ItemsEntity.class)
                .addAnnotatedClass(OrdersEntity.class)
                .addAnnotatedClass(UsersEntity.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

        return metadata.getSessionFactoryBuilder()
                .build();
    }
    public static Session getSession(){
        return sessionFactory.openSession();
    }
}
