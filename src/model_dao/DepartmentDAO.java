package model_dao;

import model_entities.Department;

import java.util.List;

public interface DepartmentDAO {
    void insert(Department obj);
    void update(Department obj);
    void deleteById(Integer id);
     void deleteMultiById(Integer id1, Integer id2);
    Department findById(Integer id);
    List<Department> findAll();
}
