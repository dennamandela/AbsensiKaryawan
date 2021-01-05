package com.company.springcompany.Component;

import com.company.springcompany.Model.Duty;
import com.company.springcompany.Model.Position;
import com.company.springcompany.Repository.DutyRepository;
import com.company.springcompany.Repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class Seeder {

    @Autowired
    DutyRepository dutyRepository;

    @Autowired
    PositionRepository positionRepository;

    @PostConstruct
    public void seed(){

        seedDuty();
        seedPosition();
    }

    private void seedPosition(){
        if(positionRepository.count()==0){
            Position positionA = new Position("IT Programmer", new ArrayList<Duty>(dutyRepository.findAll()));
            positionRepository.saveAll(Arrays.asList(positionA));
        }
    }

    private void seedDuty() {
        if(dutyRepository.count()==0){
            Duty dutyA = new Duty("POSITION_LANDING_PAGE");
            Duty dutyB = new Duty("POSITION_LIST_EMPLOYEE");
            Duty dutyC = new Duty("POSITION_CREATEEMPLOYEE");
            Duty dutyD = new Duty("POSITION_LIST_POSITION");
            Duty dutyE = new Duty("POSITION_CREATEPOSITION");
            dutyRepository.saveAll(Arrays.asList(dutyA,dutyB,dutyC,dutyD,dutyE));
        }
    }
}
