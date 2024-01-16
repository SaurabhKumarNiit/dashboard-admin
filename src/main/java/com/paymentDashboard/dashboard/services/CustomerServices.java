package com.paymentDashboard.dashboard.services;

import com.paymentDashboard.dashboard.domain.Customer;
import com.paymentDashboard.dashboard.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServices {
    @Autowired
    CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getCustomer(){return customerRepository.findAll();}


    public Customer updateCustomerDetails(Customer customer, Long _id)  {
        Optional<Customer> optionalCustomer = customerRepository.findById(_id.intValue());
//        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByEmail(email));
        if(optionalCustomer.isEmpty()){
            return null;
        }
        Customer existingCustomer = optionalCustomer.get();
        if (customer.getUserName()!=null){
            existingCustomer.setUserName(customer.getUserName());
        }
        if (customer.getPhoneNo()!=null){
            existingCustomer.setPhoneNo(customer.getPhoneNo());
        }
        if (customer.getEmail()!=null){
            existingCustomer.setEmail(customer.getEmail());
        }
        return customerRepository.save(existingCustomer);
    }
}
