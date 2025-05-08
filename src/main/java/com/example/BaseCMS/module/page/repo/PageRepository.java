package com.example.BaseCMS.module.page.repo;

import com.example.BaseCMS.module.page.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, Long> {

}
