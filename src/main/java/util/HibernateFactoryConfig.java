package util;

import entity.Reservation;
import entity.Room;
import entity.Student;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class HibernateFactoryConfig {
    private static HibernateFactoryConfig hibernateFactoryConfig;
    private SessionFactory sessionFactory;

    private HibernateFactoryConfig(){
        Configuration configuration = new Configuration();
        Properties properties = new Properties();

        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        configuration.setProperties(properties);
        configuration
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Reservation.class)
                .addAnnotatedClass(Room.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public static HibernateFactoryConfig getInstance(){
        return hibernateFactoryConfig == null ? (hibernateFactoryConfig = new HibernateFactoryConfig()) : hibernateFactoryConfig;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}
