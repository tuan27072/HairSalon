package com.example.demo.Service;

import com.example.demo.Entity.Category;
import com.example.demo.Entity.Store;
import com.example.demo.model.request.ServiceRequest;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceService {
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CategoryRepository categoryRepository;
    public com.example.demo.Entity.Service createNewService(ServiceRequest serviceRequest){
        //serviceRequest=> service
        com.example.demo.Entity.Service service= modelMapper.map(serviceRequest, com.example.demo.Entity.Service.class);
        Category category= categoryRepository.findCategoryById(serviceRequest.getCategoryId());
        service.setCategory(category);
        serviceRepository.save(service);
        return service;
    }
    public List<com.example.demo.Entity.Service> getAllService(){
        return serviceRepository.findAll();
    }
    public com.example.demo.Entity.Service deleteService(long id){
        com.example.demo.Entity.Service service= serviceRepository.findServiceById(id);
        service.setDeleted(true);
        return serviceRepository.save(service);
    }
}
