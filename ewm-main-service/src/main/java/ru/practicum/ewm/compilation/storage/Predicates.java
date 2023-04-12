package ru.practicum.ewm.compilation.storage;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import ru.practicum.ewm.compilation.model.Compilation;

public class Predicates {

    public static Predicate hasPinned(Root<Compilation> root, CriteriaBuilder cb, Boolean pinned) {
        return cb.equal(root.get("pinned"), pinned);
    }
}
