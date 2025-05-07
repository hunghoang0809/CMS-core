package com.example.BaseCMS.module.product.service;

import com.example.BaseCMS.exc.GenericErrorException;
import com.example.BaseCMS.module.product.dto.ProductDto;
import com.example.BaseCMS.module.product.model.CategoryProduct;
import com.example.BaseCMS.module.product.model.Product;
import com.example.BaseCMS.module.product.repo.CategoryProductRepository;
import com.example.BaseCMS.module.product.repo.ProductRepository;
import com.example.BaseCMS.module.product.rq.CreateProductRq;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService  {
    private final ProductRepository productRepository;
    private final CategoryProductRepository categoryProductRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void create(CreateProductRq rq) {

        Product product = Product.builder()
                .name(rq.getName())
                .price(rq.getPrice())
                .description(rq.getDescription())
                .shortDescription(rq.getShortDescription())
                .imageUrl(rq.getImageUrl())
                .discountPrice(rq.getDiscountPrice())
                .build();
        productRepository.save(product);

        if(!rq.getCategoryId().isEmpty()) {
            createCategoryProduct(rq, product);
        }
    }

    @Transactional
    public void createCategoryProduct(CreateProductRq rq, Product product) {
        for (Long categoryId : rq.getCategoryId()) {
            if(categoryProductRepository.existsByCategoryId(categoryId)) {
                CategoryProduct categoryProduct = CategoryProduct.builder()
                        .productId(product.getId())
                        .categoryId(categoryId)
                        .build();
                categoryProductRepository.save(categoryProduct);
            }else {
                throw new GenericErrorException("Danh mục với id " + categoryId + " chưa được tạo", HttpStatus.BAD_REQUEST);
            }
        }
    }


    public void update(CreateProductRq rq) {
        Product product = Product.builder()
                .name(rq.getName())
                .price(rq.getPrice())
                .description(rq.getDescription())
                .shortDescription(rq.getShortDescription())
                .imageUrl(rq.getImageUrl())
                .discountPrice(rq.getDiscountPrice())
                .build();
        productRepository.save(product);
    }

    public Page<ProductDto> list(Pageable pageable) {
        Page<Product> products = productRepository.findAllProduct(pageable);
        return products.map(product -> modelMapper.map(product, ProductDto.class));
    }


    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }



}
