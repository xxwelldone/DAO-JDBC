package application;

import model_dao.DaoFactory;
import model_dao.DepartmentDAO;
import model_entities.Department;

import java.util.ArrayList;
import java.util.List;

public class TesteDepartment {
    public static void main(String[] args) {
        System.out.println("=========Teste1 - INSERT");
        DepartmentDAO depDao = DaoFactory.createDepartmentDao();
//        Department teste1 = new Department("Comics", null);
//        depDao.insert(teste1);
        System.out.println("=========");
        System.out.println("=========Teste2 - Find by ID");
//        Department teste2 = depDao.findById(teste1.getId());
//        System.out.println(teste2);
        System.out.println("=========");
        System.out.println("=========Teste3 - UPDATE");
        depDao.update(new Department("Clothing", 7));
        System.out.println(depDao.findById(7));
        System.out.println("=========");
        System.out.println("=========Teste4 - DELETE BY ID");
        depDao.deleteById(8);
        System.out.println("=========");
        System.out.println("=========Teste5 - MULTI DELETION BY ID");
        depDao.deleteMultiById(9,10);
        System.out.println("=========");
        System.out.println("=========Teste6 - FIND ALL");
        List<Department> dep =depDao.findAll();
        dep.forEach(d->System.out.println(d));


    }
}
