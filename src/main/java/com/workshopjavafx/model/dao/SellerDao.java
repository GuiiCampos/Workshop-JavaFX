package com.workshopjavafx.model.dao;

import java.util.List;

import com.workshopjavafx.model.entities.Seller;
import com.workshopjavafx.model.entities.Department;

public interface SellerDao {

	void insert(Seller obj);
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findById(Integer id);
	List<Seller> findAll();
	List<Seller> findByDepartment(Department department);
}
