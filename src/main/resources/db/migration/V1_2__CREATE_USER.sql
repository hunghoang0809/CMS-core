CREATE TABLE user (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            username VARCHAR(255) UNIQUE,
                            password VARCHAR(255),
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          deleted_at DATETIME
);

insert into user (username, password) values ('admin', '$2a$12$2OG70cSqUEVMi5c.yTtcHeWaH.6nj70qoc1y1GZTvgPGmZdRbTx86');
