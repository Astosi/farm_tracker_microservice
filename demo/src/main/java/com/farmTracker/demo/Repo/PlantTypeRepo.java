package com.farmTracker.demo.Repo;
import com.farmTracker.demo.Model.PlantType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantTypeRepo extends JpaRepository<PlantType,String> {
}
