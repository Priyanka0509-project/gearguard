package com.example.gearguardUser.modules.equipment.infrastructure.repository;

import com.example.gearguardUser.modules.equipment.domain.Equipment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends MongoRepository<Equipment, String> {
}