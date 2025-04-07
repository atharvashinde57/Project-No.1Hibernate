package com.Controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;
import com.entity.EmployeeEntity;
import com.util.HibernateUtil;

public class Controller {
    public static void main(String[] args) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();

        try {
            // Inserting a new employee record
            session.beginTransaction();
            EmployeeEntity st = new EmployeeEntity();
            st.setEid(116);
            st.setEname("Roshan");
            st.setAddress("Gabhanavadi");
            session.save(st);
            session.getTransaction().commit();
            System.out.println("Data inserted");

            // Fetching and displaying all records from EmployeeEntity
            Query<EmployeeEntity> query = session.createQuery("FROM EmployeeEntity", EmployeeEntity.class);
            List<EmployeeEntity> employees = query.getResultList();
            System.out.println("Employee Records:");
            for (EmployeeEntity emp : employees) {
                System.out.println("ID: " + emp.getEid() + ", Name: " + emp.getEname() + ", Address: " + emp.getAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
