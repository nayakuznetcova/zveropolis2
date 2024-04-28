package ru.skypro.zveropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.zveropolis.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
