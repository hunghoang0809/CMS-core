package com.example.BaseCMS.module.product.service;

import com.example.BaseCMS.exc.GenericErrorException;
import com.example.BaseCMS.module.category.model.Category;
import com.example.BaseCMS.module.category.repo.CategoryRepository;
import com.example.BaseCMS.module.product.dto.ListProductDto;
import com.example.BaseCMS.module.product.dto.ProductDto;
import com.example.BaseCMS.module.product.model.Brand;
import com.example.BaseCMS.module.product.model.CategoryProduct;
import com.example.BaseCMS.module.product.model.Product;
import com.example.BaseCMS.module.product.repo.BrandRepository;
import com.example.BaseCMS.module.product.repo.CategoryProductRepository;
import com.example.BaseCMS.module.product.repo.ProductRepository;
import com.example.BaseCMS.module.product.rq.CreateProductRq;
import com.example.BaseCMS.module.product.rq.ProductFilterRq;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService  {
    private final ProductRepository productRepository;
    private final CategoryProductRepository categoryProductRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final BrandRepository brandRepository;


    @Transactional
    public void create(CreateProductRq rq) {

        Product product = Product.builder()
                .name(rq.getName())
                .price(rq.getPrice())
                .description(rq.getDescription())
                .shortDescription(rq.getShortDescription())
                .imageUrl(rq.getImageUrl())
                .discountPrice(rq.getDiscountPrice())
                .slug(rq.getSlug())
                .build();
        productRepository.save(product);

        if( rq.getCategoryId() != null&&!rq.getCategoryId().isEmpty() ) {
            createCategoryProduct(rq, product);
        }
    }

    @Transactional
    public void createCategoryProduct(CreateProductRq rq, Product product) {


        for (Long categoryId : rq.getCategoryId()) {

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new GenericErrorException("Danh mục với id " + categoryId + " chưa được tạo", HttpStatus.BAD_REQUEST));


            CategoryProduct categoryProduct = CategoryProduct.builder()
                    .productId(product.getId())
                    .categoryId(categoryId)
                    .build();
            categoryProductRepository.save(categoryProduct);


            if (category.getParentId() != 0) {
                boolean existsParent = categoryProductRepository.existsCategoryProductByCategoryIdAndProductId(category.getParentId(),product.getId());
                if (!existsParent) {
                    CategoryProduct parentCategoryProduct = CategoryProduct.builder()
                            .productId(product.getId())
                            .categoryId(category.getParentId())
                            .build();
                    categoryProductRepository.save(parentCategoryProduct);
                }
            }
        }
    }


    public void update(long id, CreateProductRq rq) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new GenericErrorException("Không tìm thấy sản phẩm với id " + id, HttpStatus.BAD_REQUEST));
        product.setName(rq.getName());
        product.setPrice(rq.getPrice());
        product.setShortDescription(rq.getShortDescription());
        product.setImageUrl(rq.getImageUrl());
        product.setDiscountPrice(rq.getDiscountPrice());
       product.setDescription(rq.getDescription());
        productRepository.save(product);
    }

    public Page<ProductDto> list(Pageable pageable, Long categoryId) {
        Page<Product> products = productRepository.findAllProduct(pageable, categoryId);
        return products.map(product -> modelMapper.map(product, ProductDto.class));
    }


    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Page<ProductDto> getAllProduct(Pageable pageable, String categorySlug, String brandSlug) {
        Long categoryId = null;
        Long brandId = null;
        if (categorySlug != null) {
            Category category = categoryRepository.findBySlug(categorySlug).orElseThrow(() -> new GenericErrorException("Không tìm thấy danh mục với slug " + categorySlug, HttpStatus.NOT_FOUND));
            categoryId = category.getId();
        }
        if (brandSlug != null) {
            Brand brand = brandRepository.getBySlug(brandSlug).orElseThrow(() -> new GenericErrorException("Không tìm thấy thương hiệu với slug " + brandSlug, HttpStatus.NOT_FOUND));
            brandId = brand.getId();

        }
        Page<Product> products = productRepository.findProduct(pageable, categoryId , brandId);
        return products.map(this::convertToDto);
    }

    public ProductDto convertToDto (Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<CategoryProduct> categoryProducts = categoryProductRepository.findByProductId(product.getId());
        if (categoryProducts != null && !categoryProducts.isEmpty()) {
            productDto.setCategoryIds(categoryProducts.stream()
                    .map(CategoryProduct::getCategoryId)
                    .toList());
            productDto.setCategoryNames(categoryProducts.stream()
                    .map(categoryProduct -> {
                        Category category = categoryRepository.findById(categoryProduct.getCategoryId()).orElse(null);
                        return category != null ? category.getName() : null;
                    })
                    .toList());
        }
        return productDto;
    }

    public Product getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug);
        if (product == null) {
            throw new GenericErrorException("Không tìm thấy sản phẩm với slug " + slug, HttpStatus.NOT_FOUND);
        }
        return product;
    }

    public List<ListProductDto> getAll(Pageable pageable) {
        List<Category> categories = categoryRepository.findAllParentCategory();
        List<ListProductDto> listProductDtos = categories.stream()
                .map(category -> {
                    Page<Product> products = productRepository.findProductByCategoryId(category.getId(), pageable);
                    return new ListProductDto(products, category.getName(), category.getId());
                })
                .toList();
        return listProductDtos;
    }



}
