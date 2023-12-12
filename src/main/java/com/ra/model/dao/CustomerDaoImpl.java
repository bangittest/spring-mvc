package com.ra.model.dao;

import com.ra.model.dao.CustomerDao;
import com.ra.model.entity.customer.Customer;
import com.ra.model.entity.customer.RoleName;
import com.ra.utils.ConnectionDatabase;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CustomerDaoImpl implements CustomerDao {
    @Override
    public List<Customer> findAll() {
        Connection connection= ConnectionDatabase.openConnection();
        List<Customer>customerList=new ArrayList<>();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_FIND_ALL_CUSTOMER");
            ResultSet resultSet=callableStatement.executeQuery();
            while (resultSet.next()) {
                Customer customer=new Customer();
                customer.setCustomerId(resultSet.getInt("id"));
                customer.setCustomerName(resultSet.getString("name"));
                customer.setCustomerEmail(resultSet.getString("email"));
                customer.setAddress(resultSet.getString("address"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setPassword(resultSet.getString("password"));
                customer.setImage(resultSet.getString("image"));
                RoleName role = RoleName.valueOf(resultSet.getString("roles"));
                customer.setRoles(role);
                customer.setStatus(resultSet.getBoolean("cus_status"));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return customerList;
    }

    @Override
    public boolean save(Customer customer) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_CREATE_CUSTOMER(?,?,?,?,?)");
            callableStatement.setString(1,customer.getCustomerName());
            callableStatement.setString(2,customer.getCustomerEmail());
            callableStatement.setString(3,customer.getAddress());
            callableStatement.setString(4,customer.getPhone());
            callableStatement.setString(5,customer.getPassword());
            int check= callableStatement.executeUpdate();
            if (check>0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean update(Customer customer) {
        return false;
    }

    @Override
    public void role(Integer id,String Roles) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("Call PROC_ROLE_CUSTOMER(?,?)");
            callableStatement.setInt(1,id);
            callableStatement.setString(2,Roles);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }

    }

    @Override
    public Customer findByEmail(String email) {
        Connection connection = null;
        Customer customer = null;

        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_FIND_BY_EMAIL_CUSTOMER(?)");
            callableStatement.setString(1, email);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                customer = new Customer();
                customer.setCustomerId(resultSet.getInt("id"));
                customer.setCustomerName(resultSet.getString("name"));
                customer.setCustomerEmail(resultSet.getString("email"));
                customer.setAddress(resultSet.getString("address"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setPassword(resultSet.getString("password"));
                RoleName role = RoleName.valueOf(resultSet.getString("roles"));
                customer.setRoles(role);
                customer.setStatus(resultSet.getBoolean("cus_status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return customer;
    }

    @Override
    public boolean changeStatus(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_CHANGE_STATUS_CUSTOMER(?)");
            callableStatement.setInt(1,id);
            int check= callableStatement.executeUpdate();
            if (check>0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Customer findByIdCustomer(Integer id) {
        Connection connection = null;
        Customer customer = null;

        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_FIND_BY_ID_CUSTOMER(?)");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                customer = new Customer();
                customer.setCustomerId(resultSet.getInt("id"));
                customer.setCustomerName(resultSet.getString("name"));
                customer.setCustomerEmail(resultSet.getString("email"));
                customer.setAddress(resultSet.getString("address"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setPassword(resultSet.getString("password"));
                RoleName role = RoleName.valueOf(resultSet.getString("roles"));
                customer.setRoles(role);
                customer.setStatus(resultSet.getBoolean("cus_status"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return customer;
    }
}
