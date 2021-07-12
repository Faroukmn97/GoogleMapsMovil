/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     10/7/2021 16:22:16                           */
/*==============================================================*/


drop index TBLMARCADOR_PK;

drop table TBLMARCADOR;

/*==============================================================*/
/* Table: TBLMARCADOR                                           */
/*==============================================================*/
create table TBLMARCADOR (
   ID_MARCADOR          SERIAL               not null,
   FACULTAD             VARCHAR(100)         not null,
   DECANO               VARCHAR(100)         not null,
   UBICACION            VARCHAR(100)         not null,
   LATITUDE             VARCHAR(100)         not null,
   LONGITUDE            VARCHAR(100)         not null,
   URL                  TEXT                 not null,
   constraint PK_TBLMARCADOR primary key (ID_MARCADOR)
);

/*==============================================================*/
/* Index: TBLMARCADOR_PK                                        */
/*==============================================================*/
create unique index TBLMARCADOR_PK on TBLMARCADOR (
ID_MARCADOR
);

