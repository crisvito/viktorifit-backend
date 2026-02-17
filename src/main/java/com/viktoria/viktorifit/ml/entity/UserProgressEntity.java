package com.viktoria.viktorifit.ml.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user_progress")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProgressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    private LocalDate startDate; // Penting untuk hitung "Sekarang Minggu Keberapa"

    // Gunakan LONGTEXT atau JSON. 
    // Kita simpan SELURUH isi "roadmap" (array 12 minggu) di sini.
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String roadmapData; 

    private Double targetWeight;
}
