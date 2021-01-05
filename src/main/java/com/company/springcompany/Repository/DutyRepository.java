package com.company.springcompany.Repository;

import com.company.springcompany.Model.Duty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DutyRepository extends JpaRepository<Duty, Integer> {
}
