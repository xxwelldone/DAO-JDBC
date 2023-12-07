package application;

import db.DB;
import model_dao.DaoFactory;
import model_dao.SellerDAO;
import model_entities.Seller;
import model_impl.SellerDAOJDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        SellerDAO sellerDAO = DaoFactory.createSellerDao();
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);


    }
}