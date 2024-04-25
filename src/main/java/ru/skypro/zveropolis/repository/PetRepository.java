package ru.skypro.zveropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.zveropolis.model.Pet;
import ru.skypro.zveropolis.model.TypeOfAnimal;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {


    List<Pet> findAllByIsAdoptedAndTypeOfAnimal(boolean isAdopted, TypeOfAnimal typeOfAnimal);
    List<Pet> findAllByTypeOfAnimal(TypeOfAnimal typeOfAnimal);
}
