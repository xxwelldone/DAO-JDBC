package model_dao;

import model_entities.Department;
import model_entities.Seller;

import java.util.List;

public interface SellerDAO {
    void insert(Seller seller);
    void update(Seller seller);
    void deleById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAllbyId();
    List<Seller> findByDepartment(Department dep);
}
