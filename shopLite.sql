
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'USER')) DEFAULT 'USER'
);

CREATE TABLE products(
	id SERIAL primary KEY ,
	product_name VARCHAR(100) not null,
	price INTEGER NOT NULL, 
	imageUrl VARCHAR ( 100),
    stock INTEGER NOT NULL
);

-- Usuarios
insert into users (name, email, password, role) values ('Ismael Liquez', 'admin@gmail.com','admin123', 'ADMIN')
insert into users (name, email, passwor d, role) values ('Alejandro Liquez', 'user@gmail.com', 'user123', 'USER')

-- Productos
insert into products(name,price,imageUrl, stock) values ('Teclado mecánico', 100, '/imagenes/teclado.png', 100);
insert into products(name,price,imageUrl, stock) values ('Mouse inalámbrico',29.90,'/imagenes/mouse.png', 100);
insert into products(name,price,imageUrl, stock) values ('Monitor 24',139.00,'/imagenes/monitor.png', 100);
insert into products(name,price,imageUrl, stock) values ('Headset',49.90,'/imagenes/headset.png', 100);
insert into products(name,price,imageUrl, stock) values ('Webcam',39.50,'/imagenes/webcam.png', 100);






