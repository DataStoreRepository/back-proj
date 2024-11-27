package com.core.dto;

import com.core.entity.ServiceOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrderDTO {

    private Long id;
    private Long userId;
    private String userName; // Novo campo para o nome do usuário
    private Long serviceProviderId;
    private Long offeredServiceId;
    private String offeredServiceName; // Novo campo para o nome do serviço
    private ServiceOrderStatus status;
    private Integer rating;

}