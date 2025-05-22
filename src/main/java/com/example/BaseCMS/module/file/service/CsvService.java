package com.example.BaseCMS.module.file.service;

import com.example.BaseCMS.module.category.model.Category;
import com.example.BaseCMS.module.category.repo.CategoryRepository;
import com.example.BaseCMS.module.file.dto.ProductCsv;
import com.example.BaseCMS.module.keyword.model.Keyword;
import com.example.BaseCMS.module.keyword.repository.KeywordRepository;
import com.example.BaseCMS.module.product.model.CategoryProduct;
import com.example.BaseCMS.module.product.model.Product;
import com.example.BaseCMS.module.product.model.ProductKeyword;
import com.example.BaseCMS.module.product.repo.BrandRepository;
import com.example.BaseCMS.module.product.repo.CategoryProductRepository;
import com.example.BaseCMS.module.product.repo.ProductKeywordRepository;
import com.example.BaseCMS.module.product.repo.ProductRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.CSVWriter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.text.Normalizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvService {
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
            productCsv.setKeyword(keywordString.substring(0, keywordString.length() - 2));
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

            List<Product> products = productRepository.findAll();

            List<ProductCsv> csvData = products.stream().map(this::convertCsvDto).toList(); ;

        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"products.csv\"");
        response.setCharacterEncoding("UTF-8");

        ServletOutputStream outputStream = response.getOutputStream();
        // Ghi BOM UTF-8
        outputStream.write(0xEF);
        outputStream.write(0xBB);
        outputStream.write(0xBF);

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

        StatefulBeanToCsv<ProductCsv> beanToCsv = new StatefulBeanToCsvBuilder<ProductCsv>(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(';')
                .build();
        beanToCsv.write(csvData);
        writer.flush();


    }

    @Transactional
    public void importProductCsv(MultipartFile file) throws Exception {
        List<Product> productList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())){

            for (CSVRecord record : csvParser) {
                String slug = toSlug(record.get("Tên"));
                if (productRepository.existsBySlug(slug)) {
                    continue;
                }
                Product product = new Product();
                product.setName(record.get("Tên"));
                product.setPrice(parseLong(record.get("Giá bán thường")));
                product.setDiscountPrice(parseLong(record.get("Giá khuyến mãi")));
                product.setDescription(record.get("Mô tả"));
                product.setShortDescription(record.get("Mô tả ngắn"));
                product.setImageUrl(record.get("Hình ảnh"));
                product.setSlug(slug);
                processAndAssignCategories(record.get("Danh mục"), product.getId());
                productList.add(product);
            }
        }
        productRepository.saveAll(productList);

    }


    public void processAndAssignCategories(String categoryPath, Long productId) {
        String[] parts = categoryPath.split(">");
        long parentId = 0;
        List<Category> categories = new ArrayList<>();

        for (String raw : parts) {
            String name = raw.trim();


            long finalParentId = parentId;
            Category category = categoryRepository
                    .findByNameAndParentId(name, parentId)
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(name);
                        newCategory.setSlug(toSlug(name));
                        newCategory.setParentId(finalParentId);
                        return categoryRepository.save(newCategory);
                    });

            categories.add(category);
            parentId = category.getId();
        }

        // Gán tất cả category (cha + con) cho product
        for (Category category : categories) {
            if (!categoryProductRepository.existsByCategoryIdAndProductId(category.getId(), productId)) {
                CategoryProduct cp = new CategoryProduct();
                cp.setCategoryId(category.getId());
                cp.setProductId(productId);
                categoryProductRepository.save(cp);
            }
        }
    }


    private Long parseLong(String value) {
        try {
            return value == null || value.trim().isEmpty() ? null : Long.parseLong(value.replaceAll("[^\\d]", ""));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String toSlug(String input) {
        if (input == null) return null;
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("^-|-$", "");
        return normalized;
    }

}
