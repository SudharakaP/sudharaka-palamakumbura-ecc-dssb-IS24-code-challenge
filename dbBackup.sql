-- H2 2.1.214; 
SET DB_CLOSE_DELAY -1;         
;              
CREATE USER IF NOT EXISTS "CODECHALLENGE" SALT '2c65e041c373e933' HASH '85a12c06dd38157f2faf065dbc8a0b62f0625baacba6b66d86e6f806289560de' ADMIN;               
CREATE SEQUENCE "PUBLIC"."SEQUENCE_GENERATOR" START WITH 1050 INCREMENT BY 50; 
CREATE CACHED TABLE "PUBLIC"."DATABASECHANGELOGLOCK"(
    "ID" INTEGER NOT NULL,
    "LOCKED" BOOLEAN NOT NULL,
    "LOCKGRANTED" TIMESTAMP,
    "LOCKEDBY" CHARACTER VARYING(255)
);          
ALTER TABLE "PUBLIC"."DATABASECHANGELOGLOCK" ADD CONSTRAINT "PUBLIC"."PK_DATABASECHANGELOGLOCK" PRIMARY KEY("ID");             
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.DATABASECHANGELOGLOCK;    
INSERT INTO "PUBLIC"."DATABASECHANGELOGLOCK" VALUES
(1, FALSE, NULL, NULL);    
CREATE CACHED TABLE "PUBLIC"."DATABASECHANGELOG"(
    "ID" CHARACTER VARYING(255) NOT NULL,
    "AUTHOR" CHARACTER VARYING(255) NOT NULL,
    "FILENAME" CHARACTER VARYING(255) NOT NULL,
    "DATEEXECUTED" TIMESTAMP NOT NULL,
    "ORDEREXECUTED" INTEGER NOT NULL,
    "EXECTYPE" CHARACTER VARYING(10) NOT NULL,
    "MD5SUM" CHARACTER VARYING(35),
    "DESCRIPTION" CHARACTER VARYING(255),
    "COMMENTS" CHARACTER VARYING(255),
    "TAG" CHARACTER VARYING(255),
    "LIQUIBASE" CHARACTER VARYING(20),
    "CONTEXTS" CHARACTER VARYING(255),
    "LABELS" CHARACTER VARYING(255),
    "DEPLOYMENT_ID" CHARACTER VARYING(10)
);   
-- 12 +/- SELECT COUNT(*) FROM PUBLIC.DATABASECHANGELOG;       
INSERT INTO "PUBLIC"."DATABASECHANGELOG" VALUES
('00000000000000', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', TIMESTAMP '2023-10-09 17:48:43.759671', 1, 'EXECUTED', '8:b8c27d9dc8db18b5de87cdb8c38a416b', 'createSequence sequenceName=sequence_generator', '', NULL, '4.15.0', NULL, NULL, '6898923656'),
('00000000000001', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', TIMESTAMP '2023-10-09 17:48:43.809927', 2, 'EXECUTED', '8:9452e8659f5e195517b11ac91ff810a8', 'createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...', '', NULL, '4.15.0', NULL, NULL, '6898923656'),
('20231010004813-1', 'jhipster', 'config/liquibase/changelog/20231010004813_added_entity_Product.xml', TIMESTAMP '2023-10-09 17:48:43.814166', 3, 'EXECUTED', '8:002653d5d25eae141f677126ae4b83bc', 'createTable tableName=product', '', NULL, '4.15.0', NULL, NULL, '6898923656'),
('20231010004813-1-relations', 'jhipster', 'config/liquibase/changelog/20231010004813_added_entity_Product.xml', TIMESTAMP '2023-10-09 17:48:43.817196', 4, 'EXECUTED', '8:7982d9b4f7992364e70ef9d500970e28', 'createTable tableName=rel_product__developer; addPrimaryKey tableName=rel_product__developer', '', NULL, '4.15.0', NULL, NULL, '6898923656'),
('20231010004813-1-data', 'jhipster', 'config/liquibase/changelog/20231010004813_added_entity_Product.xml', TIMESTAMP '2023-10-09 17:48:43.835492', 5, 'EXECUTED', '8:d4c28862f9e2f06daf4ae0d7f0bb621d', 'loadData tableName=product', '', NULL, '4.15.0', 'faker', NULL, '6898923656'),
('20231010004814-1', 'jhipster', 'config/liquibase/changelog/20231010004814_added_entity_Developer.xml', TIMESTAMP '2023-10-09 17:48:43.837091', 6, 'EXECUTED', '8:7d566eadb0d05a19641589642fd10f58', 'createTable tableName=developer', '', NULL, '4.15.0', NULL, NULL, '6898923656'),
('20231010004814-1-data', 'jhipster', 'config/liquibase/changelog/20231010004814_added_entity_Developer.xml', TIMESTAMP '2023-10-09 17:48:43.839717', 7, 'EXECUTED', '8:a1e1ebd5df3f05a236a7263d928e8742', 'loadData tableName=developer', '', NULL, '4.15.0', 'faker', NULL, '6898923656'),
('20231010004815-1', 'jhipster', 'config/liquibase/changelog/20231010004815_added_entity_ScrumMaster.xml', TIMESTAMP '2023-10-09 17:48:43.841489', 8, 'EXECUTED', '8:484cad215c56bc425a7e57eba54c537a', 'createTable tableName=scrum_master', '', NULL, '4.15.0', NULL, NULL, '6898923656'),
('20231010004815-1-data', 'jhipster', 'config/liquibase/changelog/20231010004815_added_entity_ScrumMaster.xml', TIMESTAMP '2023-10-09 17:48:43.843873', 9, 'EXECUTED', '8:3c557838e5e8aba30984eb34d969aea2', 'loadData tableName=scrum_master', '', NULL, '4.15.0', 'faker', NULL, '6898923656'),
('20231010004816-1', 'jhipster', 'config/liquibase/changelog/20231010004816_added_entity_ProductOwner.xml', TIMESTAMP '2023-10-09 17:48:43.845219', 10, 'EXECUTED', '8:dce18f1c6f9c90d7a3e4e5c8841056f4', 'createTable tableName=product_owner', '', NULL, '4.15.0', NULL, NULL, '6898923656'),
('20231010004816-1-data', 'jhipster', 'config/liquibase/changelog/20231010004816_added_entity_ProductOwner.xml', TIMESTAMP '2023-10-09 17:48:43.847627', 11, 'EXECUTED', '8:749e3f5e46f4c10da9c141c19653622b', 'loadData tableName=product_owner', '', NULL, '4.15.0', 'faker', NULL, '6898923656'),
('20231010004813-2', 'jhipster', 'config/liquibase/changelog/20231010004813_added_entity_constraints_Product.xml', TIMESTAMP '2023-10-09 17:48:43.853582', 12, 'EXECUTED', '8:536939b2d54f6f1a4f39cde0d7632dae', 'addForeignKeyConstraint baseTableName=rel_product__developer, constraintName=fk_rel_product__developer__product_id, referencedTableName=product; addForeignKeyConstraint baseTableName=rel_product__developer, constraintName=fk_rel_product__developer...', '', NULL, '4.15.0', NULL, NULL, '6898923656');         
CREATE CACHED TABLE "PUBLIC"."JHI_USER"(
    "ID" BIGINT NOT NULL,
    "LOGIN" CHARACTER VARYING(50) NOT NULL,
    "PASSWORD_HASH" CHARACTER VARYING(60) NOT NULL,
    "FIRST_NAME" CHARACTER VARYING(50),
    "LAST_NAME" CHARACTER VARYING(50),
    "EMAIL" CHARACTER VARYING(191),
    "IMAGE_URL" CHARACTER VARYING(256),
    "ACTIVATED" BOOLEAN NOT NULL,
    "LANG_KEY" CHARACTER VARYING(10),
    "ACTIVATION_KEY" CHARACTER VARYING(20),
    "RESET_KEY" CHARACTER VARYING(20),
    "CREATED_BY" CHARACTER VARYING(50) NOT NULL,
    "CREATED_DATE" TIMESTAMP DEFAULT NULL,
    "RESET_DATE" TIMESTAMP,
    "LAST_MODIFIED_BY" CHARACTER VARYING(50),
    "LAST_MODIFIED_DATE" TIMESTAMP
);           
ALTER TABLE "PUBLIC"."JHI_USER" ADD CONSTRAINT "PUBLIC"."PK_JHI_USER" PRIMARY KEY("ID");       
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.JHI_USER; 
INSERT INTO "PUBLIC"."JHI_USER" VALUES
(1, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', '', TRUE, 'en', NULL, NULL, 'system', NULL, NULL, 'system', NULL),
(2, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', '', TRUE, 'en', NULL, NULL, 'system', NULL, NULL, 'system', NULL);   
CREATE CACHED TABLE "PUBLIC"."JHI_AUTHORITY"(
    "NAME" CHARACTER VARYING(50) NOT NULL
);     
ALTER TABLE "PUBLIC"."JHI_AUTHORITY" ADD CONSTRAINT "PUBLIC"."PK_JHI_AUTHORITY" PRIMARY KEY("NAME");           
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.JHI_AUTHORITY;            
INSERT INTO "PUBLIC"."JHI_AUTHORITY" VALUES
('ROLE_ADMIN'),
('ROLE_USER');     
CREATE CACHED TABLE "PUBLIC"."JHI_USER_AUTHORITY"(
    "USER_ID" BIGINT NOT NULL,
    "AUTHORITY_NAME" CHARACTER VARYING(50) NOT NULL
);       
ALTER TABLE "PUBLIC"."JHI_USER_AUTHORITY" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_E" PRIMARY KEY("USER_ID", "AUTHORITY_NAME");     
-- 3 +/- SELECT COUNT(*) FROM PUBLIC.JHI_USER_AUTHORITY;       
INSERT INTO "PUBLIC"."JHI_USER_AUTHORITY" VALUES
(1, 'ROLE_ADMIN'),
(1, 'ROLE_USER'),
(2, 'ROLE_USER');        
CREATE CACHED TABLE "PUBLIC"."PRODUCT"(
    "ID" BIGINT NOT NULL,
    "PRODUCT_NAME" CHARACTER VARYING(255),
    "START_DATE" DATE,
    "METHODOLOGY" CHARACTER VARYING(255),
    "LOCATION" CHARACTER VARYING(255),
    "SCRUM_MASTER_ID" BIGINT,
    "PRODUCT_OWNER_ID" BIGINT
);            
ALTER TABLE "PUBLIC"."PRODUCT" ADD CONSTRAINT "PUBLIC"."PK_PRODUCT" PRIMARY KEY("ID");         
-- 40 +/- SELECT COUNT(*) FROM PUBLIC.PRODUCT; 
INSERT INTO "PUBLIC"."PRODUCT" VALUES
(1, 'learningcurator', DATE '2019-02-11', 'AGILE', 'https://github.com/bcgov/learningcurator', 8, 7),
(2, 'climR-pnw', DATE '2023-12-03', 'AGILE', 'https://github.com/bcgov/climR-pnw', 9, 7),
(3, 'PIMS', DATE '2022-03-06', 'WATERFALL', 'https://github.com/bcgov/PIMS', 6, 6),
(4, 'MoH-SAT', DATE '2021-10-20', 'AGILE', 'https://github.com/bcgov/MoH-SAT', 7, 9),
(5, 'family-law-act-app', DATE '2021-06-16', 'AGILE', 'https://github.com/bcgov/family-law-act-app', 7, 10),
(6, 'nr-spar', DATE '2016-07-14', 'WATERFALL', 'https://github.com/bcgov/nr-spar', 7, 3),
(7, 'nr-containers', DATE '2022-07-16', 'AGILE', 'https://github.com/bcgov/nr-containers', 8, 6),
(8, 'wps', DATE '2019-09-15', 'WATERFALL', 'https://github.com/bcgov/wps', 5, 8),
(9, 'ppr', DATE '2018-01-17', 'AGILE', 'https://github.com/bcgov/ppr', 7, 3),
(10, 'nr-spar-data-sync', DATE '2018-08-12', 'WATERFALL', 'https://github.com/bcgov/nr-spar-data-sync', 7, 7),
(11, 'nr-forest-client', DATE '2021-04-20', 'AGILE', 'https://github.com/bcgov/nr-forest-client', 8, 5),
(12, 'NotifyBC', DATE '2018-02-16', 'AGILE', 'https://github.com/bcgov/NotifyBC', 8, 10),
(13, 'nr-renovate', DATE '2016-07-16', 'WATERFALL', 'https://github.com/bcgov/nr-renovate', 10, 5),
(14, 'met-guide', DATE '2018-07-05', 'AGILE', 'https://github.com/bcgov/met-guide', 6, 9),
(15, 'healthgateway', DATE '2023-12-14', 'AGILE', 'https://github.com/bcgov/healthgateway', 8, 10),
(16, 'ECC-IOSAS', DATE '2019-11-10', 'WATERFALL', 'https://github.com/bcgov/ECC-IOSAS', NULL, NULL),
(17, 'nr-results-exam', DATE '2023-08-11', 'AGILE', 'https://github.com/bcgov/nr-results-exam', 8, 10),
(18, 'nr-forests-access-management', DATE '2022-01-04', 'WATERFALL', 'https://github.com/bcgov/nr-forests-access-management', 9, 9),
(19, 'skill_value', DATE '2022-05-17', 'WATERFALL', 'https://github.com/bcgov/skill_value', 10, 8),
(20, 'nr-rfc-reanalysis', DATE '2020-02-10', 'WATERFALL', 'https://github.com/bcgov/nr-rfc-reanalysis', 8, 7),
(21, 'air-zone-reports', DATE '2023-10-17', 'AGILE', 'https://github.com/bcgov/air-zone-reports', 10, 9),
(22, 'MOH-ALR', DATE '2018-03-21', 'AGILE', 'https://github.com/bcgov/MOH-ALR', NULL, NULL),
(23, 'nr-theme', DATE '2022-11-19', 'WATERFALL', 'https://github.com/bcgov/nr-theme', 9, 6),
(24, 'CONN-CCBC-portal', DATE '2023-04-21', 'WATERFALL', 'https://github.com/bcgov/CONN-CCBC-portal', 9, 6),
(25, 'quickstart-openshift-backends', DATE '2015-07-28', 'AGILE', 'https://github.com/bcgov/quickstart-openshift-backends', 10, 6),
(26, 'nr-rfc-grib-copy', DATE '2018-01-14', 'AGILE', 'https://github.com/bcgov/nr-rfc-grib-copy', 6, 7),
(27, 'envair', DATE '2017-05-17', 'WATERFALL', 'https://github.com/bcgov/envair', 8, 4),
(28, 'nr-epd-digital-services', DATE '2016-04-01', 'AGILE', 'https://github.com/bcgov/nr-epd-digital-services', 7, 6),
(29, 'jag-file-submission', DATE '2023-08-10', 'WATERFALL', 'https://github.com/bcgov/jag-file-submission', 7, 7),
(30, 'jag-community-corrections-case-management', DATE '2021-09-02', 'AGILE', 'https://github.com/bcgov/jag-community-corrections-case-management', 4, 4),
(31, 'developer-portal', DATE '2018-08-14', 'WATERFALL', 'https://github.com/bcgov/developer-portal', 5, 9),
(32, 'jag-adobe-common-service', DATE '2023-03-10', 'AGILE', 'https://github.com/bcgov/jag-adobe-common-service', 8, 7),
(33, 'jag-jobscheduler', DATE '2015-02-04', 'WATERFALL', 'https://github.com/bcgov/jag-jobscheduler', 6, 2),
(34, 'onRouteBCSpecification', DATE '2023-07-26', 'AGILE', 'https://github.com/bcgov/onRouteBCSpecification', 5, 7),
(35, 'quickstart-openshift', DATE '2021-09-21', 'WATERFALL', 'https://github.com/bcgov/quickstart-openshift', 7, 8),
(36, 'bc-emli-pin-mgmt-fe', DATE '2015-04-09', 'WATERFALL', 'https://github.com/bcgov/bc-emli-pin-mgmt-fe', 7, 7),
(37, 'GDX-Analytics-Looker-Google-MyBusiness', DATE '2019-06-25', 'AGILE', 'https://github.com/bcgov/GDX-Analytics-Looker-Google-MyBusiness', 6, 7),
(38, 'GDX-Analytics-Looker-theq_sdpr_block', DATE '2022-09-08', 'WATERFALL', 'https://github.com/bcgov/GDX-Analytics-Looker-theq_sdpr_block', 8, 3);       
INSERT INTO "PUBLIC"."PRODUCT" VALUES
(39, 'GDX-Analytics-Looker-Maintenance', DATE '2015-01-05', 'AGILE', 'https://github.com/bcgov/GDX-Analytics-Looker-Maintenance', 7, 6),
(40, 'GDX-Analytics-Looker-DES-Reporting', DATE '2023-01-22', 'WATERFALL', 'https://github.com/bcgov/GDX-Analytics-Looker-DES-Reporting', 8, 8);
CREATE CACHED TABLE "PUBLIC"."REL_PRODUCT__DEVELOPER"(
    "DEVELOPER_ID" BIGINT NOT NULL,
    "PRODUCT_ID" BIGINT NOT NULL
); 
ALTER TABLE "PUBLIC"."REL_PRODUCT__DEVELOPER" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_C" PRIMARY KEY("PRODUCT_ID", "DEVELOPER_ID");
-- 87 +/- SELECT COUNT(*) FROM PUBLIC.REL_PRODUCT__DEVELOPER;  
INSERT INTO "PUBLIC"."REL_PRODUCT__DEVELOPER" VALUES
(1, 1),
(3, 1),
(1, 2),
(4, 2),
(1, 3),
(4, 3),
(2, 4),
(3, 4),
(1, 5),
(3, 5),
(7, 5),
(8, 5),
(9, 5),
(2, 6),
(3, 6),
(9, 6),
(1, 7),
(2, 7),
(3, 7),
(2, 8),
(3, 8),
(4, 8),
(2, 9),
(3, 9),
(4, 9),
(2, 10),
(8, 10),
(9, 10),
(2, 11),
(4, 11),
(1, 12),
(3, 12),
(7, 13),
(8, 13),
(10, 13),
(1, 14),
(2, 15),
(3, 15),
(7, 17),
(1, 18),
(2, 18),
(3, 18),
(10, 18),
(2, 19),
(3, 19),
(6, 19),
(7, 20),
(8, 20),
(10, 20),
(2, 21),
(4, 21),
(1, 23),
(3, 23),
(2, 24),
(3, 24),
(2, 25),
(4, 25),
(2, 40),
(8, 40),
(7, 39),
(8, 39),
(1, 38),
(9, 38),
(2, 26),
(8, 26),
(3, 27),
(8, 27),
(2, 28),
(8, 28),
(5, 29),
(7, 29),
(1, 30),
(9, 30),
(2, 31),
(8, 31),
(4, 32),
(6, 32),
(8, 33),
(9, 33),
(1, 34),
(2, 34),
(1, 35),
(3, 35),
(7, 36),
(8, 36),
(2, 37),
(4, 37);  
CREATE CACHED TABLE "PUBLIC"."DEVELOPER"(
    "ID" BIGINT NOT NULL,
    "NAME" CHARACTER VARYING(255)
);       
ALTER TABLE "PUBLIC"."DEVELOPER" ADD CONSTRAINT "PUBLIC"."PK_DEVELOPER" PRIMARY KEY("ID");     
-- 10 +/- SELECT COUNT(*) FROM PUBLIC.DEVELOPER;               
INSERT INTO "PUBLIC"."DEVELOPER" VALUES
(1, 'Sudharaka Palamakumbura'),
(2, 'John Lee'),
(3, 'Mathew Hayden'),
(4, 'Charles Mark'),
(5, 'Brandon Benson'),
(6, 'Kate Ireland'),
(7, 'Nabil Sena'),
(8, 'Peter Smith'),
(9, 'Samantha Jones'),
(10, 'Craig Smith');             
CREATE CACHED TABLE "PUBLIC"."SCRUM_MASTER"(
    "ID" BIGINT NOT NULL,
    "NAME" CHARACTER VARYING(255)
);    
ALTER TABLE "PUBLIC"."SCRUM_MASTER" ADD CONSTRAINT "PUBLIC"."PK_SCRUM_MASTER" PRIMARY KEY("ID");               
-- 10 +/- SELECT COUNT(*) FROM PUBLIC.SCRUM_MASTER;            
INSERT INTO "PUBLIC"."SCRUM_MASTER" VALUES
(1, 'Mike Butt'),
(2, 'Kathryn Lee'),
(3, 'Pine Pandlewalts'),
(4, 'Craig Walter'),
(5, 'Stephen Johnson'),
(6, 'Ian Smith'),
(7, 'Quentin Tarantino'),
(8, 'Zoe Ball'),
(9, 'Harry Potter'),
(10, 'Lord Voldemort');               
CREATE CACHED TABLE "PUBLIC"."PRODUCT_OWNER"(
    "ID" BIGINT NOT NULL,
    "NAME" CHARACTER VARYING(255)
);   
ALTER TABLE "PUBLIC"."PRODUCT_OWNER" ADD CONSTRAINT "PUBLIC"."PK_PRODUCT_OWNER" PRIMARY KEY("ID");             
-- 10 +/- SELECT COUNT(*) FROM PUBLIC.PRODUCT_OWNER;           
INSERT INTO "PUBLIC"."PRODUCT_OWNER" VALUES
(1, 'Bonn Hyde'),
(2, 'Lazarus Marks'),
(3, 'Kundalee Sundra'),
(4, 'Bertie Blythe'),
(5, 'Dorothy Dandridge'),
(6, 'Evelyn Preer'),
(7, 'Yvonne De Carlo'),
(8, 'Nina Mae McKinney'),
(9, 'Katherine Dunham'),
(10, 'Ursula Thiess');             
ALTER TABLE "PUBLIC"."JHI_USER" ADD CONSTRAINT "PUBLIC"."UX_USER_LOGIN" UNIQUE("LOGIN");       
ALTER TABLE "PUBLIC"."JHI_USER" ADD CONSTRAINT "PUBLIC"."UX_USER_EMAIL" UNIQUE("EMAIL");       
ALTER TABLE "PUBLIC"."PRODUCT" ADD CONSTRAINT "PUBLIC"."FK_PRODUCT__PRODUCT_OWNER_ID" FOREIGN KEY("PRODUCT_OWNER_ID") REFERENCES "PUBLIC"."PRODUCT_OWNER"("ID") NOCHECK;       
ALTER TABLE "PUBLIC"."JHI_USER_AUTHORITY" ADD CONSTRAINT "PUBLIC"."FK_USER_ID" FOREIGN KEY("USER_ID") REFERENCES "PUBLIC"."JHI_USER"("ID") NOCHECK;            
ALTER TABLE "PUBLIC"."REL_PRODUCT__DEVELOPER" ADD CONSTRAINT "PUBLIC"."FK_REL_PRODUCT__DEVELOPER__DEVELOPER_ID" FOREIGN KEY("DEVELOPER_ID") REFERENCES "PUBLIC"."DEVELOPER"("ID") NOCHECK;     
ALTER TABLE "PUBLIC"."REL_PRODUCT__DEVELOPER" ADD CONSTRAINT "PUBLIC"."FK_REL_PRODUCT__DEVELOPER__PRODUCT_ID" FOREIGN KEY("PRODUCT_ID") REFERENCES "PUBLIC"."PRODUCT"("ID") NOCHECK;           
ALTER TABLE "PUBLIC"."PRODUCT" ADD CONSTRAINT "PUBLIC"."FK_PRODUCT__SCRUM_MASTER_ID" FOREIGN KEY("SCRUM_MASTER_ID") REFERENCES "PUBLIC"."SCRUM_MASTER"("ID") NOCHECK;          
ALTER TABLE "PUBLIC"."JHI_USER_AUTHORITY" ADD CONSTRAINT "PUBLIC"."FK_AUTHORITY_NAME" FOREIGN KEY("AUTHORITY_NAME") REFERENCES "PUBLIC"."JHI_AUTHORITY"("NAME") NOCHECK;       
