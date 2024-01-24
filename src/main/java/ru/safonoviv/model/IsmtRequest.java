package ru.safonoviv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IsmtRequest {
    private ParticipantInn description;
    private String doc_id;
    private String doc_status;
    private String doc_type;
    private Boolean importRequest;
    private String owner_inn;
    private String participant_inn;
    private String producer_inn;
    private LocalDate production_date;
    private String production_type;
    private Set<Product> products;
    private LocalDate reg_date;
    private String reg_number;

}
