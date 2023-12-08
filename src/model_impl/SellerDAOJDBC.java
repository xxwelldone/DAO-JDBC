package model_impl;

import db.DBException;
import model_dao.SellerDAO;
import model_entities.Department;
import model_entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleById(Integer id) {

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
    public List<Seller> findAllbyId() {

        return null;
    }

    @Override
    public List<Seller> findByDepartment(Department dep) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement("SELECT seller.*,department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE DepartmentId = ? " +
                    "ORDER BY Name");
            ps.setInt(1, dep.getId());
            rs = ps.executeQuery();
            List<Seller> sellerReturn = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()){
                Department department = map.get(rs.getInt("DepartmentId"));

                if(department==null){
                    department = instantiateDep(rs);
                    map.put(rs.getInt("DepartmentId"), department);
                }
                Seller seller = instantiateSeller(rs, department);
                sellerReturn.add(seller);
            }
            return sellerReturn;

        } catch (SQLException e){
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
