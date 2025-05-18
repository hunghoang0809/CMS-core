package com.example.BaseCMS.module.page.repo;

import com.example.BaseCMS.module.page.model.PageKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageKeywordRepository extends JpaRepository<PageKeyword, Long> {

    void deleteAllByPageId(Long pageId);
    List<PageKeyword> findAllByPageId(Long pageId);

}
