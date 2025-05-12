package com.example.BaseCMS.module.page.service;

import com.example.BaseCMS.exc.GenericErrorException;
import com.example.BaseCMS.module.category.model.Category;
import com.example.BaseCMS.module.category.repo.CategoryRepository;
import com.example.BaseCMS.module.page.dto.PageDto;
import com.example.BaseCMS.module.page.model.Page;
import com.example.BaseCMS.module.page.repo.PageRepository;
import com.example.BaseCMS.module.page.rq.PageRequest;
import com.example.BaseCMS.module.user.model.User;
import com.example.BaseCMS.module.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageService {
    private final PageRepository pageRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public Page createPage(PageRequest pageRq, Long userId) {
        Page page = Page.builder()
                .title(pageRq.getTitle())
                .content(pageRq.getContent())
                .status(pageRq.getStatus())
                .authorId(userId)
                .categoryId(pageRq.getCategoryId())
                .imageUrl(pageRq.getImgUrl())
                .shortDescription(pageRq.getShortDescription())
                .slug(pageRq.getSlug())
                .build();

        pageRepository.save(page);
        return page;
    }

    public PageDto getPageById(Long id) {
        Page page = pageRepository.findById(id).orElseThrow(()->new GenericErrorException("Không tìm thấy trang với id " + id, HttpStatus.NOT_FOUND));
        PageDto pageDto = convertToDto(page);
        return pageDto;
    }

    private PageDto convertToDto(Page page) {
        PageDto pageDto = modelMapper.map(page, PageDto.class);
        User user = userRepository.findById(page.getAuthorId()).orElse(null);
        if(user!= null){
            pageDto.setAuthorName(user.getUsername());
        }
        if(page.getCategoryId() != null){
            Category category = categoryRepository.findById(page.getCategoryId()).orElse(null);
            if(category != null){
                pageDto.setCategoryName(category.getName());
            }
        }
        return pageDto;
    }

    public org.springframework.data.domain.Page<PageDto> getAllPage(org.springframework.data.domain.Pageable pageable) {
        org.springframework.data.domain.Page<Page> pages = pageRepository.findAll(pageable);
        return pages.map(this::convertToDto);
    }

    public void updatePage(long id, PageRequest pageRq){
        Page page = pageRepository.findById(id).orElseThrow(()->new GenericErrorException("Không tìm thấy trang với id " + id, HttpStatus.NOT_FOUND));
        page.setTitle(pageRq.getTitle());
        page.setContent(pageRq.getContent());
        page.setStatus(pageRq.getStatus());
    }

    public void deletePage(long id){
        Page page = pageRepository.findById(id).orElseThrow(()->new GenericErrorException("Không tìm thấy trang với id " + id, HttpStatus.NOT_FOUND));
        pageRepository.delete(page);
    }
    public Page getPageBySlug(String slug) {
        return pageRepository.findBySlug(slug).orElseThrow(()->new GenericErrorException("Không tìm thấy trang với slug " + slug, HttpStatus.NOT_FOUND));
    }
}
