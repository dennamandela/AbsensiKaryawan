package com.company.springcompany.Controller;

import com.company.springcompany.Model.Position;
import com.company.springcompany.Repository.DutyRepository;
import com.company.springcompany.Repository.PositionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController{

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    DutyRepository dutyRepository;

    @GetMapping("/showPosition")
    public String showListPosition(Position position, Model model){
        model.addAttribute("positions", positionRepository.findAll());
        return "show-home";
    }

    @GetMapping(value = "/addposition")
    public List<Position> getAllPosition(){
        return positionRepository.findAll();
    }

    @PostMapping("/addposition")
        Position createOrSavePosition(@RequestBody Position newPosition){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(newPosition));
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return positionRepository.save(newPosition);
    }

    @GetMapping("/addposition/{id}")
    Position getPositionById(@PathVariable Integer positionId){
        return positionRepository.findById(positionId).get();
    }

    @PutMapping("/addposition/{positionId}")
    Position updatePosition(@RequestBody Position newPosition, @PathVariable Integer positionId){

        return positionRepository.findById(positionId).map(positions -> {
            positions.setPositionName(newPosition.getPositionName());
            return positionRepository.save(positions);
        }).orElseGet(() -> {
            newPosition.setPositionId(positionId);
            return positionRepository.save(newPosition);
        });
    }

    @DeleteMapping("/addposition/{positionId}")
    void deletePosition(@PathVariable Integer positionId) {
        positionRepository.deleteById(positionId);
    }
}