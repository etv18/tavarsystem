package com.tavarlabs.tavarsystem.dtos.individual;

import com.tavarlabs.tavarsystem.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndividualDto {
    private String firstName;
    private String lastName;
    private DocumentType documentType;
    private String documentNumber;
    private String email;
    private String telephone;
    private String address;
    private String userId;
}
