-- Generado por Oracle SQL Developer Data Modeler 19.4.0.350.1424
--   en:        2020-05-27 17:58:41 COT
--   sitio:      Oracle Database 11g
--   tipo:      Oracle Database 11g



CREATE TABLE estudiante (
    id_estudiante     VARCHAR2(11) NOT NULL,
    nombre            VARCHAR2(50) NOT NULL,
    apellido          VARCHAR2(50) NOT NULL,
    fecha_nacimiento  VARCHAR2(10) NOT NULL
);

ALTER TABLE estudiante ADD CONSTRAINT estudiante_pk PRIMARY KEY ( id_estudiante );

CREATE TABLE estudiante_tutor (
    tutor_id_tutor            VARCHAR2(11) NOT NULL,
    estudiante_id_estudiante  VARCHAR2(11) NOT NULL
);

ALTER TABLE estudiante_tutor ADD CONSTRAINT relation_1_pk PRIMARY KEY ( tutor_id_tutor,
                                                                        estudiante_id_estudiante );

CREATE TABLE factura (
    num_factura                VARCHAR2(20) NOT NULL,
    fecha                      VARCHAR2(10) NOT NULL,
    prefactura_num_prefactura  VARCHAR2(20) NOT NULL,
    num_prefactura             VARCHAR2(20) NOT NULL,
    tutor_id_tutor             VARCHAR2(11) NOT NULL
);

CREATE UNIQUE INDEX factura__idx ON
    factura (
        prefactura_num_prefactura
    ASC );

ALTER TABLE factura ADD CONSTRAINT factura_pk PRIMARY KEY ( num_factura,
                                                            num_prefactura );

CREATE TABLE factura_servicios (
    factura_num_factura       VARCHAR2(20) NOT NULL,
    factura_num_prefactura    VARCHAR2(20) NOT NULL,
    servicio_codigo_servicio  VARCHAR2(11) NOT NULL
);

ALTER TABLE factura_servicios
    ADD CONSTRAINT factura_servicios_pk PRIMARY KEY ( factura_num_factura,
                                                      factura_num_prefactura,
                                                      servicio_codigo_servicio );

CREATE TABLE prefactura (
    num_prefactura            VARCHAR2(20) NOT NULL,
    fecha_inicio              VARCHAR2(10) NOT NULL,
    fecha_fin                 VARCHAR2(10) NOT NULL,
    estado                    VARCHAR2(20) NOT NULL,
    descuento                 FLOAT NOT NULL,
    iva                       FLOAT NOT NULL,
    total                     FLOAT NOT NULL,
    vendedor_nit              VARCHAR2(10) NOT NULL,
    estudiante_id_estudiante  VARCHAR2(11) NOT NULL
);

ALTER TABLE prefactura ADD CONSTRAINT prefactura_pk PRIMARY KEY ( num_prefactura );

CREATE TABLE prefactura_servicio (
    prefactura_num_prefactura  VARCHAR2(20) NOT NULL,
    servicio_codigo_servicio   VARCHAR2(11) NOT NULL
);

ALTER TABLE prefactura_servicio ADD CONSTRAINT prefactura_servicio_pk PRIMARY KEY ( prefactura_num_prefactura,
                                                                                    servicio_codigo_servicio );

CREATE TABLE servicio (
    codigo_servicio  VARCHAR2(11) NOT NULL,
    nombre           VARCHAR2(20) NOT NULL,
    precio           FLOAT NOT NULL
);

ALTER TABLE servicio ADD CONSTRAINT servicio_pk PRIMARY KEY ( codigo_servicio );

CREATE TABLE tutor (
    id_tutor    VARCHAR2(11) NOT NULL,
    nombre      VARCHAR2(50) NOT NULL,
    apellido    VARCHAR2(50) NOT NULL,
    telefono    VARCHAR2(10),
    celular     VARCHAR2(11) NOT NULL,
    parentesco  VARCHAR2(400) NOT NULL,
    direccion   VARCHAR2(400) NOT NULL
);

ALTER TABLE tutor ADD CONSTRAINT tutor_pk PRIMARY KEY ( id_tutor );

CREATE TABLE vendedor (
    nit        VARCHAR2(10) NOT NULL,
    nombre     VARCHAR2(50) NOT NULL,
    telefono   VARCHAR2(10) NOT NULL,
    direccion  VARCHAR2(400) NOT NULL
);

ALTER TABLE vendedor ADD CONSTRAINT vendedor_pk PRIMARY KEY ( nit );

ALTER TABLE factura
    ADD CONSTRAINT factura_prefactura_fk FOREIGN KEY ( prefactura_num_prefactura )
        REFERENCES prefactura ( num_prefactura );

ALTER TABLE factura_servicios
    ADD CONSTRAINT factura_servicios_fk FOREIGN KEY ( factura_num_factura,
                                                      factura_num_prefactura )
        REFERENCES factura ( num_factura,
                             num_prefactura );

ALTER TABLE factura_servicios
    ADD CONSTRAINT factura_servicios_fkv1 FOREIGN KEY ( servicio_codigo_servicio )
        REFERENCES servicio ( codigo_servicio );

ALTER TABLE factura
    ADD CONSTRAINT factura_tutor_fk FOREIGN KEY ( tutor_id_tutor )
        REFERENCES tutor ( id_tutor );

ALTER TABLE prefactura
    ADD CONSTRAINT prefactura_estudiante_fk FOREIGN KEY ( estudiante_id_estudiante )
        REFERENCES estudiante ( id_estudiante );

ALTER TABLE prefactura_servicio
    ADD CONSTRAINT prefactura_servicio_fk FOREIGN KEY ( prefactura_num_prefactura )
        REFERENCES prefactura ( num_prefactura );

ALTER TABLE prefactura_servicio
    ADD CONSTRAINT prefactura_servicio_fkv1 FOREIGN KEY ( servicio_codigo_servicio )
        REFERENCES servicio ( codigo_servicio );

ALTER TABLE prefactura
    ADD CONSTRAINT prefactura_vendedor_fk FOREIGN KEY ( vendedor_nit )
        REFERENCES vendedor ( nit );

ALTER TABLE estudiante_tutor
    ADD CONSTRAINT relation_1_estudiante_fk FOREIGN KEY ( estudiante_id_estudiante )
        REFERENCES estudiante ( id_estudiante );

ALTER TABLE estudiante_tutor
    ADD CONSTRAINT relation_1_tutor_fk FOREIGN KEY ( tutor_id_tutor )
        REFERENCES tutor ( id_tutor );



-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                             9
-- CREATE INDEX                             1
-- ALTER TABLE                             19
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
