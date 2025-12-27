package com.example.gearguardUser.modules.equipment.interfaces.web;

import com.example.gearguardUser.modules.equipment.application.EquipmentService;
import com.example.gearguardUser.modules.equipment.domain.Equipment;
import com.example.gearguardUser.modules.equipment.domain.MaintenanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping
    public ResponseEntity<Equipment> create(@RequestBody Equipment equipment) {
        return ResponseEntity.ok(equipmentService.addEquipment(equipment));
    }

    @PostMapping("/breakdown")
    public ResponseEntity<MaintenanceRequest> breakdown(@RequestBody MaintenanceRequest request) {
        return ResponseEntity.ok(equipmentService.createBreakdownRequest(request));
    }

    @PatchMapping("/{requestId}/assign")
    public ResponseEntity<MaintenanceRequest> assign(@PathVariable String requestId, @RequestParam String techId) {
        return ResponseEntity.ok(equipmentService.assignTicket(requestId, techId));
    }

    @PatchMapping("/{requestId}/status")
    public ResponseEntity<MaintenanceRequest> status(@PathVariable String requestId, @RequestParam String status) {
        return ResponseEntity.ok(equipmentService.updateStatus(requestId, status));
    }

    @GetMapping("/kanban")
    public ResponseEntity<Map<String, List<MaintenanceRequest>>> getKanban() {
        return ResponseEntity.ok(equipmentService.getKanbanBoard());
    }
}