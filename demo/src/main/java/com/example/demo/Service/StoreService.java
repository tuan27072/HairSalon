package com.example.demo.Service;

import com.example.demo.Entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.StoreRepository;

import java.util.List;

@Service
public class StoreService {
    @Autowired
    StoreRepository storeRepository;
    public Store createNewStore(Store store){
        storeRepository.save(store);
        return store;
    }
    public List<Store> getAllStore(){
        return storeRepository.findAll();
    }
    public Store deleteStore(long id){
        Store store= storeRepository.findStoreById(id);
        store.setDeleted(true);
        return storeRepository.save(store);
    }
}
