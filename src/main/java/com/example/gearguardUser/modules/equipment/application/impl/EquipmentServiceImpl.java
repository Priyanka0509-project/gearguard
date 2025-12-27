package com.example.gearguardUser.modules.equipment.application.impl;

import com.example.gearguardUser.modules.equipment.application.EquipmentService;
import com.example.gearguardUser.modules.equipment.domain.Equipment;
import com.example.gearguardUser.modules.equipment.domain.MaintenanceRequest;
import com.example.gearguardUser.modules.equipment.infrastructure.repository.EquipmentRepository;
import com.example.gearguardUser.modules.equipment.infrastructure.repository.MaintenanceRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final MaintenanceRequestRepository requestRepository;

    @Override
    public Equipment addEquipment(Equipment equipment) {
        log.info("Saving equipment: {}", equipment.getName());
        return equipmentRepository.save(equipment);
    }

    @Override
    public MaintenanceRequest createBreakdownRequest(MaintenanceRequest request) {
        log.info("Creating breakdown request for type: {}", request.getDeviceType());

        // Smart Routing: Assigning Manager ID based on type
        if ("ELECTRICAL".equalsIgnoreCase(request.getDeviceType())) {
            request.setAssignedManagerId("ELEC_MANAGER_ID");
        } else if ("MECHANICAL".equalsIgnoreCase(request.getDeviceType())) {
            request.setAssignedManagerId("MECH_MANAGER_ID");
        }

        request.setStatus("NEW");
        request.setCreatedAt(LocalDateTime.now());
        return requestRepository.save(request);
    }

    @Override
    public MaintenanceRequest assignTicket(String requestId, String technicianId) {
        MaintenanceRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request Not Found"));
        request.setAssignedTechnicianId(technicianId);
        return requestRepository.save(request);
    }

    @Override
    public MaintenanceRequest updateStatus(String requestId, String status) {
        MaintenanceRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request Not Found"));

        request.setStatus(status);

        // Required Feature: Scrap Logic
        if ("SCRAP".equalsIgnoreCase(status)) {
            Equipment equip = equipmentRepository.findById(request.getEquipmentId()).orElse(null);
            if (equip != null) {
                equip.setUsable(false);
                equipmentRepository.save(equip);
            }
        }
        return requestRepository.save(request);
    }

    @Override
    public Map<String, List<MaintenanceRequest>> getKanbanBoard() {
        log.info("Fetching Kanban board data...");

        // We group them manually for a clean frontend response
        return Map.of(
                "NEW", requestRepository.findByStatus("NEW"),
                "IN_PROGRESS", requestRepository.findByStatus("IN_PROGRESS"),
                "SOLVED", requestRepository.findByStatus("SOLVED"),
                "SCRAP", requestRepository.findByStatus("SCRAP")
        );
    }

    @Override
    public long getRequestCountForEquipment(String equipmentId) {
        // This allows the "Badge" count on the machine detail page
        return requestRepository.findAll().stream()
                .filter(r -> r.getEquipmentId().equals(equipmentId))
                .count();
    }
}