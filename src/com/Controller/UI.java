package com.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.entity.EmployeeEntity;
import com.util.HibernateUtil;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UI {
    private JFrame frame;
    private JTextField idField, nameField, addressField;
    private DefaultTableModel tableModel;
    private JTable table;

    public UI() {
        frame = new JFrame("Employee Management");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Employee Details"));
        
        inputPanel.add(new JLabel("Employee ID:"));
        idField = new JTextField();
        inputPanel.add(idField);
        
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        
        inputPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        inputPanel.add(addressField);
        
        JButton addButton = new JButton("Add Employee");
        inputPanel.add(addButton);
        
        frame.add(inputPanel, BorderLayout.NORTH);
        
        // Table Panel
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Employee ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Address");
        
        table = new JTable(tableModel);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Button Action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });
        
        // Load existing employees
        loadEmployees();
        
        frame.setVisible(true);
    }

    private void addEmployee() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String address = addressField.getText();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        EmployeeEntity employee = new EmployeeEntity();
        employee.setEid(id);
        employee.setEname(name);
        employee.setAddress(address);

        session.save(employee);
        tx.commit();
        session.close();

        // Refresh table
        loadEmployees();
    }

    private void loadEmployees() {
        tableModel.setRowCount(0);
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<EmployeeEntity> employees = session.createQuery("from EmployeeEntity", EmployeeEntity.class).list();
        session.close();
        
        for (EmployeeEntity emp : employees) {
            tableModel.addRow(new Object[]{emp.getEid(), emp.getEname(), emp.getAddress()});
        }
    }

    public static void main(String[] args) {
        new UI();
    }
}
