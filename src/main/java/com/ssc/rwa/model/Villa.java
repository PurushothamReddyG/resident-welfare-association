package com.ssc.rwa.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "villas")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Villa {

    @Id
    @NotBlank(message = "Villa number is mandatory")
    private String villaNumber;

    @NotBlank(message = "Road number is required")
    private String roadNumber;

    @NotBlank(message = "Owner name is required")
    private String ownerName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String ownerEmail;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
    private String contactNumber;

    @NotNull(message = "Occupancy status is required")
    private Boolean isOccupied;
}
