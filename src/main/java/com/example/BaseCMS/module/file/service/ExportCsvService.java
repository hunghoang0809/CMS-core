package com.example.BaseCMS.module.file.service;

import com.example.BaseCMS.module.category.model.Category;
import com.example.BaseCMS.module.category.repo.CategoryRepository;
import com.example.BaseCMS.module.file.dto.ProductCsv;
import com.example.BaseCMS.module.keyword.model.Keyword;
import com.example.BaseCMS.module.keyword.repository.KeywordRepository;
import com.example.BaseCMS.module.product.dto.ProductCategoryDto;
import com.example.BaseCMS.module.product.model.CategoryProduct;
import com.example.BaseCMS.module.product.model.Product;
import com.example.BaseCMS.module.product.model.ProductKeyword;
import com.example.BaseCMS.module.product.repo.BrandRepository;
import com.example.BaseCMS.module.product.repo.CategoryProductRepository;
import com.example.BaseCMS.module.product.repo.ProductKeywordRepository;
import com.example.BaseCMS.module.product.repo.ProductRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.CSVWriter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportCsvService {
    private final ModelMapper modelMapper;
    private final ProductKeywordRepository productKeywordRepository;
    private final CategoryProductRepository categoryProductRepository;
    private final BrandRepository brandRepository;
    private final KeywordRepository keywordRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    public ProductCsv convertCsvDto(Product product) {
        ProductCsv productCsv = modelMapper.map(product, ProductCsv.class);
        if (product.getBrandId() != null) {
            productCsv.setBrand(brandRepository.findById(product.getBrandId()).get().getName());
        }
        setCategory(product, productCsv);
        setKeyword(product, productCsv);

        return productCsv;
    }

    private void setKeyword(Product product, ProductCsv productCsv) {
        List<ProductKeyword> keywordProducts = productKeywordRepository.findByProductId(product.getId());
        List<Keyword> keywords = keywordRepository.findAllById(keywordProducts.stream().map(ProductKeyword::getKeywordId).toList());
        List<String> keywordNames = keywords.stream().map(Keyword::getName).toList();
        if (!keywords.isEmpty()) {
            StringBuilder keywordString = new StringBuilder();
            for (String keyword : keywordNames) {
                keywordString.append(keyword).append(", ");
            }
            productCsv.setSeoKeyword(keywordString.substring(0, keywordString.length() - 2));
        }
    }
    private void setCategory(Product product, ProductCsv productCsv) {
        List<CategoryProduct> categoryProducts = categoryProductRepository.findByProductId(product.getId());
        List<Category> categories = categoryRepository.findAllById(categoryProducts.stream().map(CategoryProduct::getCategoryId).toList());
        List<String> categoryNames = categories.stream().map(Category::getName).toList();
        if (!categoryNames.isEmpty()) {
            StringBuilder categoryString = new StringBuilder();
            for (String categoryName : categoryNames) {
                categoryString.append(categoryName).append(", ");
            }
            productCsv.setCategory(categoryString.substring(0, categoryString.length() - 2));
        }
    }

    public void exportProductsCsv(HttpServletResponse response) throws Exception {
        List<Product> products = productRepository.findAll(); // Hoặc service

        List<ProductCsv> csvData = products.stream().map(this::convertCsvDto).toList(); ;

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"products.csv\"");

        StatefulBeanToCsv<ProductCsv> writer = new StatefulBeanToCsvBuilder<ProductCsv>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();

        writer.write(csvData);
    }

}
