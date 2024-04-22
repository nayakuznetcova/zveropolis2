package ru.skypro.zveropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.zveropolis.model.Shelter;
import ru.skypro.zveropolis.model.TypeOfAnimal;


@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Shelter findFirstByTypeOfAnimal(TypeOfAnimal typeOfAnimal);
}
