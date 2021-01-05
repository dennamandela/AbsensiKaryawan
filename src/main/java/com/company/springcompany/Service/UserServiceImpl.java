package com.company.springcompany.Service;

import com.company.springcompany.Model.Employee;
import com.company.springcompany.Repository.DutyRepository;
import com.company.springcompany.Repository.EmployeeRepository;
import com.company.springcompany.Repository.PositionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DutyRepository dutyRepository;

    @Autowired
    PositionRepository positionRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(s);
        List<SimpleGrantedAuthority> listSga = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        try{
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(employee));
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        for (int i=0; i<employee.getPosition().getDuty().size();i++){
            listSga.add(new SimpleGrantedAuthority(dutyRepository.getOne(employee.getPosition().getDuty().get(i).getId()).getDutyName()));
        }
        return new UserDetailsServiceImpl(employee,listSga);
    }
}