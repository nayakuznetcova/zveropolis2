package ru.skypro.zveropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.zveropolis.model.Pet;
import ru.skypro.zveropolis.model.TypeOfAnimal;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findById(long id);

    List<Pet> findAllByIsAdoptedAndTypeOfAnimal(boolean isAdopted, TypeOfAnimal typeOfAnimal);

    List<Pet> findAllByTypeOfAnimal(TypeOfAnimal typeOfAnimal);

    Pet findByUsersChatId(long chatId);

}
