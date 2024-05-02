package ru.skypro.zveropolis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.zveropolis.model.Users;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query(value = """
select * from users
where is_volunteer  = true;
""" ,nativeQuery = true)
    List<Users> getUsersByVolunteerIsTrue();
}
