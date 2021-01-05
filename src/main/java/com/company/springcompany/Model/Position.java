package com.company.springcompany.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="positions")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer positionId;

    private String positionName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "positions_employee_duties", joinColumns = @JoinColumn(name = "position_id"), inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Duty> duty;

    public Position() {
    }

    public Position(String positionName, List<Duty> duties) {
        this.positionName = positionName;
        this.duty = duties;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public List<Duty> getDuty() {
        return duty;
    }

    public void setDuty(List<Duty> duty) {
        this.duty = duty;
    }
}