package model_impl;

import db.DBException;
import model_dao.DepartmentDAO;
import model_entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOJDBC implements DepartmentDAO {
    private Connection conn;
    public DepartmentDAOJDBC(Connection conn){
        this.conn = conn;
    }
    @Override
    public void insert(Department obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO department " +
                    "SET Name = ? ", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,  obj.getName());
            int rowAffected = ps.executeUpdate();
            if(rowAffected>0){
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }

            }
        } catch (SQLException e){
            throw new DBException(e.getMessage());
        }

    }

    @Override
    public void update(Department obj) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE department " +
                    "SET Name = ? " +
                    "WHERE Id = ?");
            ps.setString(1, obj.getName());
            ps.setInt(2, obj.getId());
            ps.executeUpdate();

        } catch (SQLException e){
            throw new DBException(e.getMessage());
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE from department where id =?");
            ps.setInt(1,id);
            ps.executeUpdate();

        } catch (SQLException e){
            throw new DBException(e.getMessage());
        }

    }
    public void deleteMultiById(Integer id1, Integer id2) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE from department where id in (?,?)");
            ps.setInt(1,id1);
            ps.setInt(2, id2);
            ps.executeUpdate();

        } catch (SQLException e){
            throw new DBException(e.getMessage());
        }

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * from department where id = ?");
            ps.setInt(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                Department dep = instantiateDep(rs);
                return dep;
            }
            return null;
        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement ps = null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement("SELECT * from department");
            rs = ps.executeQuery();
            List<Department> results = new ArrayList<>();
            while (rs.next()){
                Department x = instantiateDep(rs);
                results.add(x);
            }
            return results;
        } catch(SQLException e){
            throw new DBException(e.getMessage());
        }

    }

    private Department instantiateDep(ResultSet rs) throws SQLException {
        return new Department(rs.getString("Name"),
                rs.getInt("Id"));
    }
}
