package com.example.gearguardUser.modules.equipment.infrastructure.repository;

import com.example.gearguardUser.modules.equipment.domain.MaintenanceRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRequestRepository extends MongoRepository<MaintenanceRequest, String> {
    List<MaintenanceRequest> findByStatus(String status);
}