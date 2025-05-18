package com.example.BaseCMS.module.keyword.service;

import com.example.BaseCMS.exc.GenericErrorException;
import com.example.BaseCMS.module.keyword.model.Keyword;
import com.example.BaseCMS.module.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    @Transactional
    public Keyword create(String name) {
        Keyword keyword = Keyword.builder()
                .name(name)
                .build();

        return keywordRepository.save(keyword);
    }

    public Page<Keyword> getAll(Pageable pageable) {
        return keywordRepository.findAll(pageable);
    }

    public Keyword getById(Long id) {
        return keywordRepository.findById(id).orElseThrow(() -> new GenericErrorException(
                "Không tìm thấy từ khoá với id " + id,
                HttpStatus.NOT_FOUND
        ));
    }

    @Transactional
    public void update(Long id, String name) {
        Keyword keyword = getById(id);
        keyword.setName(name);
        keywordRepository.save(keyword);
    }

    @Transactional
    public void delete(Long id) {
        Keyword keyword = getById(id);
        keywordRepository.delete(keyword);
    }
}
