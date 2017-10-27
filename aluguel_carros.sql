drop database aluguel_veiculos;
create database aluguel_veiculos;
use aluguel_veiculos;

create table Clientes(
	id int not null auto_increment,
    nome varchar(65) not null,
    cpf varchar (14) not null,
    email varchar(65),
    telefone varchar(13), 
    celular varchar(14) not null,
    alugou boolean not null,
    primary key(id)
);

create table Carros(
	id_carro int not null auto_increment,
    nome varchar(65) not null,
    combustivel varchar(10) not null,
	potencia int not null,
    placa varchar(8) not null,
    cor varchar(10) not null,
    disponivel boolean not null,
    primary key(id_carro)
);

create table Aluguel(
	id_aluguel int not null auto_increment,
    id_cliente int,
    id_carro int,
    primary key(id_aluguel),
    foreign key(id_cliente) references Clientes(id),
    foreign key(id_carro) references Carros(id_carro)
	);
    
select * from Aluguel;