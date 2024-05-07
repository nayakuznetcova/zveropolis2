package ru.skypro.zveropolis.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.skypro.zveropolis.model.Pet;
import ru.skypro.zveropolis.model.TypeOfAnimal;
import ru.skypro.zveropolis.model.Users;
import ru.skypro.zveropolis.repository.PetRepository;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class PetService {
    @Autowired
    private final PetRepository petRepository;


    /**
     * Сохраняет заданную сущность.
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     *
     * @param pet сохраняемая сущность
     */
    public void addPet(Pet pet) {
        petRepository.save(pet);
    }

    /**
     * Позволяет получить информацию о питомце
     *
     * @param id идентификатор питомца
     * @return Optional <Pet>
     */

    public Optional<Pet> getPetById(long id) {
        return petRepository.findById(id);
    }

    /**
     * Позволяет обновить информацию о питомце
     *
     * @param pet сущность питомца
     * @return обновленные данные питомца
     */

    public Pet updatePet(Pet pet) {
        return petRepository.save(pet);
    }

    /**
     * Позволяет удалить питомца из базы данных
     *
     * @param id идентификатор питомца
     */

    public void deletePet(long id) {
        petRepository.deleteById(id);
    }

    /**
     * Позволяет получить список всех питомцев
     *
     * @return список всех питомцев
     */

    public List<Pet> getAll() {
        return petRepository.findAll();
    }

    /**
     * Позволяет получить список питомцев, отданных под опеку или нет, с сортировкой по типу животного
     *
     * @param isAdopted    под опекой ли питомец ({@code true} - да, под опекой, {@code false} -нет, находится в приюте, ждет опеки)
     * @param typeOfAnimal вид животного
     * @return список питомцев
     */

    public List<Pet> getPetsAdopted(boolean isAdopted, TypeOfAnimal typeOfAnimal) {
        return petRepository.findAllByIsAdoptedAndTypeOfAnimal(isAdopted, typeOfAnimal);
    }

    /**
     * Позволяет получить список питомцев, с сортировкой по виду животного
     *
     * @param typeOfAnimal вид животного (определены в {@link TypeOfAnimal}
     * @return список животных определенного вида
     */

    public List<Pet> getListOf(TypeOfAnimal typeOfAnimal) {
        return petRepository.findAllByTypeOfAnimal(typeOfAnimal);
    }

    /**
     * Позволяет взять под опеку питомца
     *
     * @param id идентификатор животного
     * @return обновленная информация о питомце
     */
    public Pet petToAdopt(long id, Users user) {
        Pet pet = petRepository.findById(id).get();
        pet.setAdopted(true);
        pet.setUsers(user);
        return petRepository.save(pet);
    }
}
