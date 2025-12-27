package com.example.gearguardUser.modules.equipment.application.impl;

import com.example.gearguardUser.modules.equipment.domain.Equipment;
import com.example.gearguardUser.modules.equipment.domain.MaintenanceRequest;
import com.example.gearguardUser.modules.equipment.infrastructure.repository.EquipmentRepository;
import com.example.gearguardUser.modules.equipment.infrastructure.repository.MaintenanceRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MaintenanceScheduler {

    private final EquipmentRepository equipmentRepository;
    private final MaintenanceRequestRepository requestRepository;

    // Runs every day at midnight. For testing, you can use "0 */5 * * * *" for every 5 minutes.
    @Scheduled(cron = "0 0 0 * * *")
    public void schedulePreventiveMaintenance() {
        log.info("Starting background check for preventive maintenance due today...");

        List<Equipment> allEquipment = equipmentRepository.findAll();

        for (Equipment equip : allEquipment) {
            if (!equip.isUsable()) continue; // Skip scrapped machines

            LocalDate lastService = equip.getLastMaintenanceDate() != null ?
                    equip.getLastMaintenanceDate() : equip.getPurchaseDate();

            long daysSinceLastService = ChronoUnit.DAYS.between(lastService, LocalDate.now());

            if (daysSinceLastService >= equip.getRegularMaintenanceDays()) {
                createAutoRequest(equip);
            }
        }
    }

    private void createAutoRequest(Equipment equip) {
        log.info("Auto-raising Preventive Maintenance for: {}", equip.getName());

        MaintenanceRequest autoRequest = MaintenanceRequest.builder()
                .equipmentId(equip.getId())
                .subject("Routine Checkup: " + equip.getName())
                .description("Automated preventive maintenance requested based on " + equip.getRegularMaintenanceDays() + " day cycle.")
                .deviceType(equip.getDeviceType())
                .status("NEW")
                .createdAt(LocalDateTime.now())
                .build();

        // Routing logic
        if ("ELECTRICAL".equalsIgnoreCase(equip.getDeviceType())) {
            autoRequest.setAssignedManagerId("ELEC_MANAGER_ID");
        } else {
            autoRequest.setAssignedManagerId("MECH_MANAGER_ID");
        }

        requestRepository.save(autoRequest);

        // Update last maintenance date to today to reset the timer
        equip.setLastMaintenanceDate(LocalDate.now());
        equipmentRepository.save(equip);
    }
}