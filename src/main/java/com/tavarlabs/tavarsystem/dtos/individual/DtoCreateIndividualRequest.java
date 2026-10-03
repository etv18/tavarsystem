package com.tavarlabs.tavarsystem.dtos.individual;

import com.tavarlabs.tavarsystem.enums.DocumentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DtoCreateIndividualRequest {

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    private String documentNumber;
    private String email;
    private String telephone;
    private String address;
}
