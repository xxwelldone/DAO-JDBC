package model_impl;

import db.DBException;
import model_dao.SellerDAO;
import model_entities.Department;
import model_entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDAOJDBC implements SellerDAO {
    private Connection conn;

    public SellerDAOJDBC(Connection c) {
        this.conn = c;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("" +
                    "INSERT INTO seller" +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId)" +
                    "VALUES " +
                    "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,seller.getName());
            ps.setString(2,seller.getEmail());
            ps.setDate(3, new java.sql.Date(seller.getBirthday().getTime()));
            ps.setDouble(4, seller.getSalary());
            ps.setInt(5, seller.getDepartment().getId());

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected>0){
                rs = ps.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    seller.setId(id);
                }
            } else {
                throw new DBException("Error inserting");
            }


        } catch (SQLException e){
            throw new DBException(e.getMessage());
        }

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE seller.Id = ? "
            );
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department dp = instantiateDep(rs);
                Seller seller = instantiateSeller(rs, dp);
                return seller;
            }
            return null;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "ORDER BY Name");
            rs = ps.executeQuery();
            List<Seller> sellersReturn = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()) {
                Department d = map.get(rs.getInt("DepartmentId"));
                if(d==null){
                    d = instantiateDep(rs);
                    map.put(rs.getInt("DepartmentId"), d);
                }

                sellersReturn.add(instantiateSeller(rs, d));

            }

            return sellersReturn;
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }


    }

    @Override
    public List<Seller> findByDepartment(Department dep) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE DepartmentId = ? " +
                    "ORDER BY Name");
            ps.setInt(1, dep.getId());
            rs = ps.executeQuery();
            List<Seller> sellerReturn = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department department = map.get(rs.getInt("DepartmentId"));

                if (department == null) {
                    department = instantiateDep(rs);
                    map.put(rs.getInt("DepartmentId"), department);
                }
                Seller seller = instantiateSeller(rs, department);
                sellerReturn.add(seller);
            }
            return sellerReturn;

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department dp) throws SQLException {
        Seller s = new Seller(
                rs.getInt("Id"),
                rs.getString("Name"),
                rs.getDate("BirthDate"),
                rs.getString("Email"),
                rs.getDouble("BaseSalary"),
                dp
        );
        return s;
    }

    private Department instantiateDep(ResultSet rs) throws SQLException {
        Department dp = new Department(rs.getString("DepName"),
                rs.getInt("DepartmentId"));
        return dp;
    }
}
