package com.example.BaseCMS.module.page.repo;

import com.example.BaseCMS.module.page.model.PageCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PageCategoryRepository extends JpaRepository<PageCategory, Long> {
    boolean existsByPageId(Long pageId);

    List<PageCategory> findByPageId(Long pageId);
}
