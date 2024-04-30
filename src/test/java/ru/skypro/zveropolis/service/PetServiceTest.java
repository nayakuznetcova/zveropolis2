package ru.skypro.zveropolis.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.zveropolis.model.Pet;
import ru.skypro.zveropolis.model.TypeOfAnimal;
import ru.skypro.zveropolis.model.Users;
import ru.skypro.zveropolis.repository.PetRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {
    @InjectMocks
    PetService out;

    @Mock
    PetRepository repository;

    Pet PET_1 = new Pet();
    Pet PET_2 = new Pet();
    List<Pet> ALL_PETS_LIST = List.of(PET_1, PET_2);
    List<Pet> ADOPTED_PETS = List.of(PET_1);

    List<Pet> CATS = List.of(PET_1);

    Users USER_1 = new Users();

    @BeforeEach
    void setUp() {
        PET_1.setId(1L);
        PET_1.setName("GOLUM");
        PET_1.setAdopted(true);
        PET_1.setUsers(USER_1);
        PET_1.setTypeOfAnimal(TypeOfAnimal.CAT);
        PET_2.setId(2L);
        PET_2.setName("SMEAGOL");
        USER_1.setUsername("vasia");
    }

    @Test
    void addPetCorrect() {
        Mockito.when(repository.save(PET_1)).thenReturn(PET_1);
        out.addPet(PET_1);
        Mockito.verify(repository, Mockito.times(1)).save(PET_1);
    }

    @Test
    void getPetByIdCorrect() {
        long id = 1L;
        Mockito.when(repository.findById(id)).thenReturn(Optional.ofNullable(PET_1));
        out.getPetById(id);
        Mockito.verify(repository, Mockito.times(1)).findById(id);
        assertEquals(Optional.of(PET_1), out.getPetById(id));
    }

    @Test
    void updatePetCorrect() {
        Mockito.when(repository.save(PET_1)).thenReturn(PET_1);
        out.updatePet(PET_1);
        Mockito.verify(repository, Mockito.times(1)).save(PET_1);
        assertEquals(PET_1, out.updatePet(PET_1));
    }

    @Test
    void deletePetCorrect() {
        long id = 1L;
        Mockito.doNothing().when(repository).deleteById(id);
        out.deletePet(id);
        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void getAllCorrect() {
        Mockito.when(repository.findAll()).thenReturn(ALL_PETS_LIST);
        out.getAll();
        Mockito.verify(repository, Mockito.times(1)).findAll();
        assertEquals(ALL_PETS_LIST, out.getAll());
    }

    @Test
    void getPetsAdoptedCorrect() {
        Mockito.when(repository.findAllByIsAdoptedAndTypeOfAnimal(true, TypeOfAnimal.CAT)).thenReturn(ADOPTED_PETS);
        out.getPetsAdopted(true, TypeOfAnimal.CAT);
        Mockito.verify(repository, Mockito.times(1)).findAllByIsAdoptedAndTypeOfAnimal(true, TypeOfAnimal.CAT);
        assertEquals(ADOPTED_PETS, out.getPetsAdopted(true, TypeOfAnimal.CAT));
    }

    @Test
    void getListOfCorrect() {
        Mockito.when(repository.findAllByTypeOfAnimal(TypeOfAnimal.CAT)).thenReturn(CATS);
        out.getListOf(TypeOfAnimal.CAT);
        Mockito.verify(repository, Mockito.times(1)).findAllByTypeOfAnimal(TypeOfAnimal.CAT);
        assertEquals(CATS, out.getListOf(TypeOfAnimal.CAT));
    }

    @Test
    void adoptPetCorrect() {
        Mockito.when(repository.save(any(Pet.class))).thenReturn(PET_1);
        out.petToAdopt(PET_1, USER_1);
        Mockito.verify(repository, Mockito.times(1)).save(PET_1);
        assertEquals(PET_1,out.petToAdopt(PET_1,USER_1));
    }
}