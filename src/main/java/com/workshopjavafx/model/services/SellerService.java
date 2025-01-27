package com.workshopjavafx.model.services;

import com.workshopjavafx.model.dao.DaoFactory;
import com.workshopjavafx.model.dao.SellerDao;
import com.workshopjavafx.model.entities.Seller;

import java.util.List;

public class SellerService {

    private SellerDao dao = DaoFactory.createSellerDao();

    public List<Seller> findAll() {

        return dao.findAll();
    }

    public void saveOrUpdate(Seller Seller) {
        if (Seller.getId() == null) {
            dao.insert(Seller);
        }else {
            dao.update(Seller);
        }
    }

    public void remove(Seller dp) {
        dao.deleteById(dp.getId());
    }
}
