package com.example.BaseCMS.module.product.service;

import com.example.BaseCMS.exc.GenericErrorException;
import com.example.BaseCMS.module.product.model.Brand;
import com.example.BaseCMS.module.product.repo.BrandRepository;
import com.example.BaseCMS.module.product.rq.CreateBrandRq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public boolean existsBySlug(String slug) {
        return brandRepository.existsBySlug(slug);
    }

    public Brand createBrand(CreateBrandRq brand) {
        Brand newBrand = Brand.builder()
                .name(brand.getName())
                .slug(brand.getSlug())
                .build();
        return brandRepository.save(newBrand);
    }
    public Brand findBySlug(String slug) {
        return brandRepository.findBySlug(slug);
    }

    public Brand findById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new GenericErrorException("Thương hiệu không tồn tại", HttpStatus.NOT_FOUND));
    }

    public Brand updateBrand(Long id, CreateBrandRq rq) {
        Brand brand = findById(id);
        brand.setName(rq.getName());
        brand.setSlug(rq.getSlug());
        return brandRepository.save(brand);
    }

    public void deleteBrand(Long id) {
        Brand brand = findById(id);
        brandRepository.delete(brand);
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Page<Brand> getAllBrands(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }
}
