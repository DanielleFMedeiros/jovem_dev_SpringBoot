insert into pais(id_pais,nome_pais) values (1,'Brasil');
insert into pais(id_pais,nome_pais) values (2,'Alemanha');
insert into pais(id_pais,nome_pais) values (3,'Argentina');

insert into equipe(id_equipe,nome_equipe ) values (1,'Equipe 1');
insert into equipe(id_equipe,nome_equipe ) values (2,'Equipe 2');

insert into campeonato(id_campeonato,descricao_campeonato, ano_campeonato ) values (1,'Campeonato 1', 2023);
insert into campeonato(id_campeonato,descricao_campeonato, ano_campeonato) values (2,'Campeonato 2', 2021);

insert into pista(id_pista, tamanho_pista, pais_id_pais) values(1, 10, 1);
insert into pista(id_pista, tamanho_pista, pais_id_pais) values(2, 15, 3);
insert into pista(id_pista, tamanho_pista, pais_id_pais) values(3, 13, 2);

insert into piloto(id, name, pais_id_pais, equipe_id_equipe) values(1, 'João', 2, 1);
insert into piloto(id, name, pais_id_pais, equipe_id_equipe) values(2, 'Paulo', 1, 1);
insert into piloto(id, name, pais_id_pais, equipe_id_equipe) values(3, 'Vanessa', 1, 1);

insert into corrida(id, data, pista_id_pista, campeonato_id_campeonato) values(1, '2024-05-11T13:30:00Z', 2, 2);
insert into corrida(id, data, pista_id_pista, campeonato_id_campeonato) values(2, '2023-07-17T15:30:00Z', 1, 1);
insert into corrida(id, data, pista_id_pista, campeonato_id_campeonato) values(3, '2023-10-20T14:00:00Z', 1, 1);