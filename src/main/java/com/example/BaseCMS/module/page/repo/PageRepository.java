package com.example.BaseCMS.module.page.repo;

import com.example.BaseCMS.module.page.PageEnum;
import com.example.BaseCMS.module.page.model.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PageRepository extends JpaRepository<Page, Long> {
    Optional<Page> findBySlug(String slug);

    org.springframework.data.domain.Page<Page> findAllByStatus(PageEnum status, Pageable pageable);


}
