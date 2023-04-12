package ru.practicum.ewm.event.storage;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.event.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>,
        JpaSpecificationExecutor<Event> {

    @Query("select e " +
            "from Event e " +
            "JOIN FETCH e.category " +
            "JOIN FETCH e.initiator " +
            "where e.initiator.id = :userId")
    List<Event> findAllWithCategoryAndInitiator(@Param("userId") Long userId, Pageable pageable);

    @Query("select e " +
            "from Event e " +
            "JOIN FETCH e.category " +
            "JOIN FETCH e.initiator " +
            "where e.id = :eventId " +
            "and e.initiator.id = :userId")
    Optional<Event> findWithCategoryAndInitiator(@Param("userId") Long userId,
            @Param("eventId") Long eventId);

    @Query("select e " +
            "from Event e " +
            "JOIN FETCH e.category " +
            "JOIN FETCH e.initiator " +
            "where e.id = :eventId")
    Optional<Event> findWithCategoryAndInitiator(@Param("eventId") Long eventId);

    @Query("select e " +
            "from Event e " +
            "JOIN FETCH e.category " +
            "JOIN FETCH e.initiator " +
            "where e.id in :eventIds")
    Set<Event> findAllByIdIn(@Param("eventIds") Set<Long> eventIds);

    Long countAllByCategory_Id(Long categoryId);
}
