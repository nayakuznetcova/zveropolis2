package ru.skypro.zveropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.zveropolis.model.Pet;
import ru.skypro.zveropolis.model.Report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByPet(Pet pet);

    List<Report> findAllByIsChecked(boolean isChecked);
}
