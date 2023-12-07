package model_dao;

import db.DB;
import model_impl.SellerDAOJDBC;

public class DaoFactory {
    public static SellerDAO createSellerDao(){
        return new SellerDAOJDBC(DB.getCon());
    }
}
