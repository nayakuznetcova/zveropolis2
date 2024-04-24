package ru.skypro.zveropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.zveropolis.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
