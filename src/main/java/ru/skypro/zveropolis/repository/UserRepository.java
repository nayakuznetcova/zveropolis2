package ru.skypro.zveropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.zveropolis.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
}
