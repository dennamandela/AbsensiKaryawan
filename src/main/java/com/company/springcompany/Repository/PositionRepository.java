package com.company.springcompany.Repository;

import com.company.springcompany.Model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Integer> {
}
