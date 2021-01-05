package com.company.springcompany.Model;

import javax.persistence.*;

@Entity
@Table(name="employee_duties")
public class Duty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String dutyName;

    public Duty() {
    }

    public Duty(String dutyName) {
        this.dutyName = dutyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }
}
