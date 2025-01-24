package com.workshopjavafx.model.services;

import com.workshopjavafx.model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(1, "Books"));
        departments.add(new Department(2, "Computers"));
        departments.add(new Department(3, "Electronics"));

        return departments;
    }
}
