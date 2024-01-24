package ru.safonoviv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String certificate_document;
    private LocalDate certificate_document_date;
    private String certificate_document_number;
    private String owner_inn;
    private String producer_inn;
    private LocalDate production_date;
    private String tnved_code;
    private String uit_code;
    private String uitu_code;

}
