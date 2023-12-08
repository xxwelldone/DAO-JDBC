package application;

import db.DB;
import model_dao.DaoFactory;
import model_dao.SellerDAO;
import model_entities.Department;
import model_entities.Seller;
import model_impl.SellerDAOJDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Testes 1 findById");
        SellerDAO sellerDAO = DaoFactory.createSellerDao();
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);
        System.out.println("Teste 2 findByDepartment");
        List<Seller> lista = sellerDAO.findByDepartment(new Department(null, 2));
        lista.forEach(s-> System.out.println(s));



    }
}