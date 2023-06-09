package ru.practicum.ewm.user.storage;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByIdIn(List<Long> userId, Pageable pageable);
}
