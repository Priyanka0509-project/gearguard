package com.example.gearguardUser.modules.equipment.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "equipments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
    @Id
    private String id;
    private String name;
    private String modelName;
    private String serialNumber;
    private LocalDate purchaseDate;
    private LocalDate lastMaintenanceDate; // Initialize this with purchaseDate initially
    private int regularMaintenanceDays;
    private String deviceType;           // ELECTRICAL, MECHANICAL
    private boolean isUsable = true;
}