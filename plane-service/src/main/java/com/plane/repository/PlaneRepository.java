package com.plane.repository;

import com.plane.entity.Plane;
import com.plane.enums.PlaneType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaneRepository extends JpaRepository<Plane, Long> {

    List<Plane> findAllByType(PlaneType type);

    List<Plane> findAllByCapacityGreaterThan(int capacity);

    List<Plane> findAllByModelLikeIgnoreCase(String manufacturer);
}
