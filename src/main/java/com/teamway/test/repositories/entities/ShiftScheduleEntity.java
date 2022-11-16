package com.teamway.test.repositories.entities;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shift_schedule", uniqueConstraints = { @UniqueConstraint(columnNames = { "worker_id", "date" }) })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "worker_id", nullable = false)
    private Long workerId;

    @Column(name = "shift", nullable = false)
    @Enumerated(EnumType.STRING)
    private Shift shift;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}
