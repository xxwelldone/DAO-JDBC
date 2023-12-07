package model_impl;

import db.DBException;
import model_dao.SellerDAO;
import model_entities.Department;
import model_entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
                Department dp = new Department(rs.getString("DepName"),
                        rs.getInt("DepartmentId"));
                Seller seller = new Seller(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getDate("BirthDate"),
                        rs.getString("Email"),
                        rs.getDouble("BaseSalary"),
                        dp
                );
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
}
