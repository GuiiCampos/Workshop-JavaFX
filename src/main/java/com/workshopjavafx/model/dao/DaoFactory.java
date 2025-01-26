package com.workshopjavafx.model.dao;

import com.workshopjavafx.db.DB;
import com.workshopjavafx.model.dao.impl.DepartmentDaoJDBC;
import com.workshopjavafx.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
