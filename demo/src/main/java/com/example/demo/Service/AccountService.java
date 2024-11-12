package com.example.demo.Service;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.Enums.Role;
import com.example.demo.Entity.Store;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.request.RegisterRequest;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.StoreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    ServiceRepository serviceRepository;

    public Account register(RegisterRequest registerRequest) {
        // registerRequest=> Account
        try {
            Account account = modelMapper.map(registerRequest, Account.class);
            String password = registerRequest.getPassword();
            account.setPassword(passwordEncoder.encode(password));
            account.setRole(Role.CUSTOMER);
            return accountRepository.save(account);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getPhone(),
                    loginRequest.getPassword()
            ));
            Account account = (Account) authentication.getPrincipal();
            LoginResponse loginResponse = modelMapper.map(account, LoginResponse.class);
            loginResponse.setToken(tokenService.generateToken(account));

            return loginResponse;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Invalid username or password!");

        }

    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return accountRepository.findAccountByPhone(phone);
    }

    public Account createStaff(RegisterRequest registerRequest, long storeId) {
        try {
            List<com.example.demo.Entity.Service> services = new ArrayList<>();
            Account account = modelMapper.map(registerRequest, Account.class);
            Store store = storeRepository.findStoreById(storeId);
            String password = registerRequest.getPassword();
            account.setPassword(passwordEncoder.encode(password));
            account.setRole(Role.STAFF);
            account.setStore(store);

            // list service id
            for (Long serviceId : registerRequest.getServiceIds()) {
                com.example.demo.Entity.Service service = serviceRepository.findServiceById(serviceId);
                service.getAccounts().add(account);
                services.add(service);
            }

            account.setServices(services);

            return accountRepository.save(account);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public Account createManager(RegisterRequest registerRequest, long storeId) {
        try {
            Account account = modelMapper.map(registerRequest, Account.class);
            Store store = storeRepository.findStoreById(storeId);
            String password = registerRequest.getPassword();
            account.setPassword(passwordEncoder.encode(password));
            account.setRole(Role.MANAGER);
            account.setStore(store);
            return accountRepository.save(account);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public List<Account> getManagerByStoreId(long storeId) {
        return accountRepository.findAccountsByStoreId(storeId);

    }

    public List<Account> getStaffByStoreId(long storeId) {
        return accountRepository.findAccountsByStoreId(storeId);

    }

    public Account getCurrentAccount() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findAccountById(account.getId());
    }

}
