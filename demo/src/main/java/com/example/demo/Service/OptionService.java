package com.example.demo.Service;


import com.example.demo.Entity.ServiceOption;
import com.example.demo.Entity.Store;
import com.example.demo.model.request.OptionRequest;
import com.example.demo.repository.OptionRepository;
import com.example.demo.repository.ServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class OptionService {
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ServiceRepository serviceRepository;

    public ServiceOption createNewOption(OptionRequest optionRequest){
        ServiceOption serviceOption = new ServiceOption();
        serviceOption.setImage(optionRequest.getImage());
        serviceOption.setName(optionRequest.getName());
        serviceOption.setDescription(optionRequest.getDescription());
        serviceOption.setPrice(optionRequest.getPrice());


        com.example.demo.Entity.Service service= serviceRepository.findServiceById(optionRequest.getServiceId());
        serviceOption.setService(service);

        optionRepository.save(serviceOption);
        return serviceOption;
    }
    public List<ServiceOption> getAllOption(){
        return optionRepository.findOptionsByIsDeletedFalse();
    }
    public ServiceOption deleteOption(long id){
        ServiceOption option= optionRepository.findOptionById(id);
        option.setDeleted(true);
        return optionRepository.save(option);
    }
}
