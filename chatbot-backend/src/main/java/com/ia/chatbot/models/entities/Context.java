package com.ia.chatbot.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@SuperBuilder
@Table(name="t_context")
@AllArgsConstructor
@NoArgsConstructor
public class Context {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="pdf_name")
    private String pdfName;

    @Column(name="description")
    private String description;

    @Column(name="pdf")
    private byte[] pdf;

    @Column(name="creation_date")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name="update_date")
    @UpdateTimestamp
    private LocalDateTime updateDate;
}
