package ru.practicum.ewm.compilation.storage;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.compilation.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long>,
        JpaSpecificationExecutor<Compilation> {

    @Query("select c " +
            "from Compilation c " +
            "LEFT JOIN FETCH c.events " +
            "where c.id = :compId")
    Optional<Compilation> findByIdFetch(@Param("compId") Long compId);
}
