package com.example.gearguardUser.modules.equipment.application;

import com.example.gearguardUser.modules.equipment.domain.Equipment;
import com.example.gearguardUser.modules.equipment.domain.MaintenanceRequest;

import java.util.List;
import java.util.Map;

public interface EquipmentService {
    Equipment addEquipment(Equipment equipment);
    MaintenanceRequest createBreakdownRequest(MaintenanceRequest request);
    MaintenanceRequest assignTicket(String requestId, String technicianId);
    MaintenanceRequest updateStatus(String requestId, String status);
    Map<String, List<MaintenanceRequest>> getKanbanBoard();
    long getRequestCountForEquipment(String equipmentId);
}