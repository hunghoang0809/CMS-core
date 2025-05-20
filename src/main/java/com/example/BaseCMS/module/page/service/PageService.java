package com.example.BaseCMS.module.page.service;

import com.example.BaseCMS.exc.GenericErrorException;
import com.example.BaseCMS.module.category.model.Category;
import com.example.BaseCMS.module.category.repo.CategoryRepository;
import com.example.BaseCMS.module.keyword.model.Keyword;
import com.example.BaseCMS.module.keyword.repository.KeywordRepository;
import com.example.BaseCMS.module.page.PageEnum;
import com.example.BaseCMS.module.page.dto.PageCategoryDto;
import com.example.BaseCMS.module.page.dto.PageDto;
import com.example.BaseCMS.module.page.dto.PageKeywordDto;
import com.example.BaseCMS.module.page.model.Page;
import com.example.BaseCMS.module.page.model.PageCategory;
import com.example.BaseCMS.module.page.model.PageKeyword;
import com.example.BaseCMS.module.page.repo.PageCategoryRepository;
import com.example.BaseCMS.module.page.repo.PageKeywordRepository;
import com.example.BaseCMS.module.page.repo.PageRepository;
import com.example.BaseCMS.module.page.rq.PageRequest;
import com.example.BaseCMS.module.user.model.User;
import com.example.BaseCMS.module.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PageService {
    private final PageRepository pageRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PageCategoryRepository pageCategoryRepository;
    private final KeywordRepository keywordRepository;
    private final PageKeywordRepository pageKeywordRepository;

    @Transactional
    public Page createPage(PageRequest pageRq, Long userId) {
        if (pageRepository.existsBySlug(pageRq.getSlug())) {
            throw new GenericErrorException("Slug đã tồn tại", HttpStatus.BAD_REQUEST);
        }
        Page page = Page.builder()
                .title(pageRq.getTitle())
                .content(pageRq.getContent())
                .status(pageRq.getStatus())
                .authorId(userId)
                .imageUrl(pageRq.getImgUrl())
                .shortDescription(pageRq.getShortDescription())
                .slug(pageRq.getSlug())
                .seoTitle(pageRq.getSeoTitle())
                .seoDescription(pageRq.getSeoDescription())
                .build();

        pageRepository.save(page);
        if (pageRq.getCategoryId() != null && !pageRq.getCategoryId().isEmpty()) {
            createCategoryPage(pageRq, page);
        }
        if (pageRq.getKeywordId() != null && !pageRq.getKeywordId().isEmpty()) {
            createPageKeyword(pageRq, page);
        }
        return page;
    }

    @Transactional
    protected void createCategoryPage(PageRequest pageRq, Page page) {
            for (Long categoryId : pageRq.getCategoryId()) {
                Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new GenericErrorException("Không tìm thấy danh mục với id " + categoryId, HttpStatus.NOT_FOUND));
                PageCategory pageCategory = PageCategory.builder()
                        .categoryId(category.getId())
                        .pageId(page.getId())
                        .build();
                pageCategoryRepository.save(pageCategory);
            }
    }

    @Transactional
    protected void createPageKeyword(PageRequest pageRequest, Page page) {
        for (Long keywordId : pageRequest.getKeywordId()) {
             Keyword keyword = keywordRepository.findById(keywordId).orElseThrow(() -> new GenericErrorException("Không tìm thấy từ khóa với id " + keywordId, HttpStatus.NOT_FOUND));
             PageKeyword productKeyword = PageKeyword.builder()
                     .keywordId(keyword.getId())
                     .pageId(page.getId())
                     .build();
             pageKeywordRepository.save(productKeyword);
        }

    }

    public PageDto getPageById(Long id) {
        Page page = pageRepository.findById(id).orElseThrow(()->new GenericErrorException("Không tìm thấy trang với id " + id, HttpStatus.NOT_FOUND));
        return convertToDto(page);
    }

    private PageDto convertToDto(Page page) {
        PageDto pageDto = modelMapper.map(page, PageDto.class);
        User user = userRepository.findById(page.getAuthorId()).orElse(null);
        List<PageCategory> pageCategory = pageCategoryRepository.findByPageId(page.getId());
        List<PageKeyword> pageKeyword = pageKeywordRepository.findAllByPageId(page.getId());
        List<Category> categories = categoryRepository.findAllById(pageCategory.stream().map(PageCategory::getCategoryId).toList());
        List<Keyword> keywords = keywordRepository.findAllById(pageKeyword.stream().map(PageKeyword::getKeywordId).toList());
        if(user!= null){
            pageDto.setAuthorName(user.getUsername());
        }
        if(!pageCategory.isEmpty()){
            setCategories(pageDto, categories);
        }
        if(!pageKeyword.isEmpty()){
            setKeywords(pageDto, keywords);
        }
        return pageDto;
    }

    private void setKeywords(PageDto pageDto, List<Keyword> keywords)
    {
        pageDto.setKeywords(keywords.stream().map(keyword -> modelMapper.map(keyword, PageKeywordDto.class)).toList());

    }

    private void setCategories(PageDto pageDto, List<Category> categories) {
        pageDto.setCategories(categories.stream().map(category -> modelMapper.map(category,PageCategoryDto.class)).toList());
    }

    public org.springframework.data.domain.Page<PageDto> getAllPage(org.springframework.data.domain.Pageable pageable) {
        org.springframework.data.domain.Page<Page> pages = pageRepository.findAll(pageable);
        return pages.map(this::convertToDto);
    }

    public org.springframework.data.domain.Page<PageDto> getAllPublishedPage(org.springframework.data.domain.Pageable pageable) {
        org.springframework.data.domain.Page<Page> pages = pageRepository.findAllByStatus(PageEnum.PUBLISHED, pageable);
        return pages.map(this::convertToDto);
    }

    @Transactional
    public void updatePage(Long id, PageRequest pageRq){
        Page page = pageRepository.findById(id).orElseThrow(()->new GenericErrorException("Không tìm thấy trang với id " + id, HttpStatus.NOT_FOUND));
        page.setTitle(pageRq.getTitle());
        page.setContent(pageRq.getContent());
        page.setImageUrl(pageRq.getImgUrl());
        page.setShortDescription(pageRq.getShortDescription());
        page.setSlug(pageRq.getSlug());
        page.setSeoTitle(pageRq.getSeoTitle());
        page.setSeoDescription(pageRq.getSeoDescription());
        page.setStatus(pageRq.getStatus());
        pageRepository.save(page);
        updateCategory(pageRq, page);
        updateKeyword(pageRq, page);
    }

    @Transactional
    protected void updateCategory(PageRequest pageRq, Page page) {
        if (pageRq.getCategoryId() != null && !pageRq.getCategoryId().isEmpty()) {
            pageCategoryRepository.deleteAllByPageId(page.getId());
            createCategoryPage(pageRq, page);
        }
    }
    @Transactional
    protected void updateKeyword(PageRequest pageRq, Page page) {
        if (pageRq.getKeywordId() != null && !pageRq.getKeywordId().isEmpty()) {
            pageKeywordRepository.deleteAllByPageId(page.getId());
            createPageKeyword(pageRq, page);
        }
    }

    @Transactional
    public void deletePage(long id){
        Page page = pageRepository.findById(id).orElseThrow(()->new GenericErrorException("Không tìm thấy trang với id " + id, HttpStatus.NOT_FOUND));
        pageRepository.delete(page);
    }
    public PageDto getPageBySlug(String slug) {
        Page page = pageRepository.findBySlug(slug).orElseThrow(()->new GenericErrorException("Không tìm thấy trang với slug " + slug, HttpStatus.NOT_FOUND));
        return convertToDto(page);
    }
}
