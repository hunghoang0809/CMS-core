package com.example.BaseCMS.module.keyword.repository;

import com.example.BaseCMS.module.keyword.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findByName(String name);

    boolean existsById(Long id);
}
