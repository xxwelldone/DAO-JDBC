package model_dao;

import model_entities.Department;
import model_entities.Seller;

import java.util.List;

public interface SellerDAO {
    void insert(Seller seller);
    void update(Seller seller);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
    List<Seller> findByDepartment(Department dep);
}
