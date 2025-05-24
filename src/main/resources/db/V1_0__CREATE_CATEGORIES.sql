CREATE TABLE category (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255),
                          slug VARCHAR(255),
                          description TEXT,
                          image_url VARCHAR(500),
                          parent_id BIGINT,
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          deleted_at DATETIME,
                          INDEX idx_category_slug (slug),
                          INDEX idx_category_parent_id (parent_id)
);

CREATE TABLE keyword (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255),
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                         updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         deleted_at DATETIME,
                         INDEX idx_keyword_name (name)
);

CREATE TABLE brand (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255),
                       slug VARCHAR(255),
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       deleted_at DATETIME,
                       INDEX idx_brand_slug (slug)
);

CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255),
                         price BIGINT,
                         discount_price BIGINT,
                         description TEXT,
                         short_description TEXT,
                         image_url VARCHAR(500),
                         slug VARCHAR(255),
                         brand_id BIGINT,
                         seo_title VARCHAR(255),
                         seo_description VARCHAR(500),
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                         updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         deleted_at DATETIME,
                         INDEX idx_product_slug (slug),
                         INDEX idx_product_brand_id (brand_id),
                         INDEX idx_product_name (name)
);

CREATE TABLE product_keyword (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 product_id BIGINT,
                                 keyword_id BIGINT,
                                 created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                 updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 deleted_at DATETIME,
                                 FOREIGN KEY (product_id) REFERENCES product(id),
                                 FOREIGN KEY (keyword_id) REFERENCES keyword(id),
                                 INDEX idx_pk_product_id (product_id),
                                 INDEX idx_pk_keyword_id (keyword_id)
);

CREATE TABLE category_product (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  category_id BIGINT,
                                  product_id BIGINT,
                                  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  deleted_at DATETIME,
                                  FOREIGN KEY (category_id) REFERENCES category(id),
                                  FOREIGN KEY (product_id) REFERENCES product(id),
                                  INDEX idx_cp_category_id (category_id),
                                  INDEX idx_cp_product_id (product_id)
);

CREATE TABLE page (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255),
                      content TEXT,
                      author_id BIGINT,
                      status INT, -- Enum ordinal
                      image_url VARCHAR(500),
                      slug VARCHAR(255),
                      short_description TEXT,
                      seo_title VARCHAR(255),
                      seo_description VARCHAR(500),
                      created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                      updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      deleted_at DATETIME,
                      INDEX idx_page_slug (slug),
                      INDEX idx_page_author_id (author_id)
);

CREATE TABLE page_category (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               page_id BIGINT,
                               category_id BIGINT,
                               created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                               updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               deleted_at DATETIME,
                               FOREIGN KEY (page_id) REFERENCES page(id),
                               FOREIGN KEY (category_id) REFERENCES category(id),
                               INDEX idx_pc_page_id (page_id),
                               INDEX idx_pc_category_id (category_id)
);

CREATE TABLE page_keyword (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              page_id BIGINT,
                              keyword_id BIGINT,
                              created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                              updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              deleted_at DATETIME,
                              FOREIGN KEY (page_id) REFERENCES page(id),
                              FOREIGN KEY (keyword_id) REFERENCES keyword(id),
                              INDEX idx_pk_page_id (page_id),
                              INDEX idx_pk_keyword_id (keyword_id)
);
