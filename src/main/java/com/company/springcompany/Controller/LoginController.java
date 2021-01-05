package com.company.springcompany.Controller;

import com.company.springcompany.Model.Employee;
import com.company.springcompany.Model.Login;
import com.company.springcompany.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/login")
    public String showLogin(Login login, Model model){
        model.addAttribute("login", new Login());
        return "login";
    }

    @PostMapping("/ProcessLogin")
    public String processLogin(@Valid Login login, BindingResult result, Model model) throws Exception{

        System.out.println("CHECK IF API GOT HIT");
        if(result.hasErrors()) {
            System.out.println(result);
            return "login";
        }
        Employee employee = employeeRepository.findByEmail(login.getEmail());
        if(null==employee){
            throw new Exception("Employee Not Found");
        }else{
            if(!login.getPassword().equals(login.getPassword())){
                throw new Exception("User Do Not Match");
            }
            model.addAttribute("employee", employeeRepository.findAll());
            System.out.println(employeeRepository.findAll().get(0));
        }
        List<SimpleGrantedAuthority> listSga = new ArrayList<>();
        for (int i=0;i<employee.getPosition().getDuty().size();i++){
            listSga.add(new SimpleGrantedAuthority(employee.getPosition().getDuty().get(i).getDutyName()));
        }

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(employee.getEmail(),employee.getPassword(),
                listSga));
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        System.out.println("LOGIN SUCCESS");
        return "redirect:/landing-page";
    }

    @GetMapping("/homepage")
    public String showHomePage(){
        return "landing-page";
    }
}