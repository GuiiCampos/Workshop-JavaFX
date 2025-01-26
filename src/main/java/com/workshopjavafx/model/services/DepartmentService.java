package com.workshopjavafx.model.services;

import com.workshopjavafx.model.dao.DaoFactory;
import com.workshopjavafx.model.dao.DepartmentDao;
import com.workshopjavafx.model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll() {

        return dao.findAll();
    }
}
