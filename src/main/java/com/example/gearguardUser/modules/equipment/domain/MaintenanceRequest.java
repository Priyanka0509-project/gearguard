package com.example.gearguardUser.modules.equipment.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "maintenance_requests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRequest {
    @Id
    private String id;
    private String equipmentId;
    private String subject;
    private String description;
    private String deviceType;
    private String status;          // NEW, IN_PROGRESS, SOLVED, SCRAP
    private String assignedManagerId;
    private String assignedTechnicianId;
    private LocalDateTime createdAt;
}