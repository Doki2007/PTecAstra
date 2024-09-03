package dev.jamirgarro.astra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

import java.time.LocalDate;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio.")
    private String title;

    @NotBlank(message = "El autor es obligatorio.")
    private String author;

    @ISBN(message = "El ISBN debe ser único y válido.")
    @Column(unique = true, nullable = false)
    private String isbn;

    @PastOrPresent(message = "La fecha de publicación no puede ser futura.")
    private LocalDate publishedDate;

    @Enumerated(EnumType.STRING)
    private Status status;

}
