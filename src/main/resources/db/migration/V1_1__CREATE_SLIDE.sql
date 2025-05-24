CREATE TABLE slide (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          image_url VARCHAR(500),
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          deleted_at DATETIME
);
