INSERT INTO tb_user (nome, email, senha, tipo_do_usuario)
VALUES ('nomeadmin', 'admin@gmail.com', '$2a$10$/v7td4/mgxCOwUJDKwmzsO29T9MzrAGz6B/jrMvB.ZQ2U35hLTg5W', 'admin');

INSERT INTO tb_authority (authority) VALUES ('ADMIN');
INSERT INTO tb_authority (authority) VALUES ('PACIENTE');
INSERT INTO tb_authority (authority) VALUES ('MEDICO');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);

--INSERT INTO tb_user (nome, email, senha, tipo_do_usuario)
--VALUES ('nomepaciente', 'paciente@gmail.com', '$2a$10$/v7td4/mgxCOwUJDKwmzsO29T9MzrAGz6B/jrMvB.ZQ2U35hLTg5W', 'paciente');
--
--INSERT INTO tb_user (nome, email, senha, tipo_do_usuario)
--VALUES ('nomemedico', 'medico@gmail.com', '$2a$10$/v7td4/mgxCOwUJDKwmzsO29T9MzrAGz6B/jrMvB.ZQ2U35hLTg5W', 'medico');

--INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
--INSERT INTO user_authority (user_id, authority_id) VALUES (3, 3);

--INSERT INTO paciente (nome, data_nascimento, cpf, id_usuario)
--VALUES ('murilo magalhaes', NOW(), '987654321', 2);
--
--INSERT INTO especialidade (nome, descricao) VALUES ('clinico geral', 'faz tudo');
--INSERT INTO medico (nome_pessoa, nome_profissional, crm, data_inscricao, id_usuario)
--VALUES ('murilo pacheco', 'dr. pacheco', 123456789, NOW(), 3);
