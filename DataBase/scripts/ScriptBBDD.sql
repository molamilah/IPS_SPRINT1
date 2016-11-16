DROP TABLE IF EXISTS APUNTADOS_ACTIVIDADES;
DROP TABLE IF EXISTS NO_PRESENTADOS;
DROP TABLE IF EXISTS ACTIVIDADES;
DROP TABLE IF EXISTS PAGO;
DROP TABLE IF EXISTS MENSUALIDADES;
DROP TABLE IF EXISTS RESERVA;
DROP TABLE IF EXISTS SALA;
DROP TABLE IF EXISTS USUARIO;


CREATE TABLE USUARIO(
    ID_USUARIO INTEGER IDENTITY PRIMARY KEY,
    DNI VARCHAR(9) NOT NULL UNIQUE,
    NOMBRE VARCHAR(20) NOT NULL,
    APELLIDOS VARCHAR(40) NOT NULL,
    DIRECCION VARCHAR(40) NOT NULL,
    EMAIL VARCHAR(40),
    CIUDAD VARCHAR(20) NOT NULL,
    PASSWORD VARCHAR(20) NOT NULL,
    CUOTA DECIMAL(6,3) NOT NULL,
    BAJA BOOLEAN NOT NULL,
    FECHA_BAJA TIMESTAMP
);

CREATE TABLE SALA(
    ID_SALA INTEGER IDENTITY PRIMARY KEY,
    DESCRIPCION VARCHAR(200) UNIQUE,
    PRECIO DECIMAL(6,3) NOT NULL
);

CREATE TABLE RESERVA(
	ID_RESERVA INTEGER IDENTITY PRIMARY KEY,
	HORA_INICIO TIMESTAMP NOT NULL,
	HORA_FIN TIMESTAMP NOT NULL,
	ID_USUARIO INTEGER NOT NULL,
	ID_SALA INTEGER NOT NULL,
	HORA_ENTRADA TIMESTAMP,
	HORA_SALIDA TIMESTAMP,
	ESTADO VARCHAR(15),
	CONSTRAINT FK_RESERVA_USUARIO FOREIGN KEY(ID_USUARIO) REFERENCES USUARIO(ID_USUARIO),
	CONSTRAINT FK_RESERVA_SALA FOREIGN KEY(ID_SALA) REFERENCES SALA(ID_SALA)
);

CREATE TABLE PAGO (
    ID_PAGO INTEGER IDENTITY PRIMARY KEY,
    ID_RESERVA INTEGER NOT NULL,
    PRECIO DECIMAL(6,3) NOT NULL,
    CONTADO BOOLEAN NOT NULL,
    PAGADO BOOLEAN NOT NULL,
	EN_CUOTA BOOLEAN DEFAULT FALSE,
    CONSTRAINT FK_RESERVA_PAGO FOREIGN KEY (ID_RESERVA) REFERENCES RESERVA(ID_RESERVA),
);

CREATE TABLE MENSUALIDADES (
	ID_USUARIO INTEGER,
	MES INTEGER,
	ANNO INTEGER,
	CUOTA DOUBLE,
	CONSTRAINT PK_ID_USUARIO_MES_ANNO PRIMARY KEY (ID_USUARIO, MES, ANNO),
	CONSTRAINT FK_ID_USUARIO FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID_USUARIO)
);

CREATE TABLE ACTIVIDADES(
	ID_ACTIVIDAD INTEGER IDENTITY PRIMARY KEY,
	NOMBRE VARCHAR(40) NOT NULL,
	DESCRIPCION VARCHAR(200),
	ID_SALA INTEGER NOT NULL,
	NUMERO_PLAZAS INTEGER NOT NULL,
	CONSTRAINT FK_ID_SALA FOREIGN KEY (ID_SALA) REFERENCES SALA(ID_SALA)
);

CREATE TABLE APUNTADOS_ACTIVIDADES(
	ID_USUARIO INTEGER NOT NULL,
	ID_ACTIVIDAD INTEGER NOT NULL,
	CONSTRAINT FK_APUNTADOS_ACTIVIDADES_USUARIO FOREIGN KEY(ID_USUARIO) REFERENCES USUARIO(ID_USUARIO),
	CONSTRAINT FK_APUNTADOS_ACTIVIDADES_ACTIVIDAD FOREIGN KEY(ID_ACTIVIDAD) REFERENCES ACTIVIDADES(ID_ACTIVIDAD)
);

CREATE TABLE NO_PRESENTADOS(
	ID_USUARIO INTEGER NOT NULL,
	ID_ACTIVIDAD INTEGER NOT NULL,
	CONSTRAINT FK_NO_PRESENTADOS_USUARIO FOREIGN KEY(ID_USUARIO) REFERENCES USUARIO(ID_USUARIO),
	CONSTRAINT FK_NO_PRESENTADOS_ACTIVIDAD FOREIGN KEY(ID_ACTIVIDAD) REFERENCES ACTIVIDADES(ID_ACTIVIDAD),
);