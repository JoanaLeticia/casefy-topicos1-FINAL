-- Administrador
insert into administrador (id, nome, login, senha, cpf, matricula, perfil) 
values(102687, 'Victor Alves', 'victor@unitins.br', 'yEaSZv1mx2Hf11tomtEAY3HUG2hrQS2ACE17U1PeCoA7PFIhHARbDredPke5UTKwvMVA+jod2rMVKSoDzm8p3Q==', '753.369.654-98', 159, 2);
insert into administrador (id, nome, login, senha, cpf, matricula, perfil) 
values(226873, 'Aline barros', 'alineb@unitins.br', 'yEaSZv1mx2Hf11tomtEAY3HUG2hrQS2ACE17U1PeCoA7PFIhHARbDredPke5UTKwvMVA+jod2rMVKSoDzm8p3Q==', '023.396.147-08', 111, 2);
insert into administrador (id, nome, login, senha, cpf, matricula, perfil) 
values(385473, 'Gloria Groover', 'gg@unitins.br', 'yEaSZv1mx2Hf11tomtEAY3HUG2hrQS2ACE17U1PeCoA7PFIhHARbDredPke5UTKwvMVA+jod2rMVKSoDzm8p3Q==', '063.369.688-05', 257, 2);

-- Telefones
insert into telefone (id, codArea, numero) values(12, '63', '98478-3692');
insert into telefone (id, codArea, numero) values(22, '62', '99999-9688');
insert into telefone (id, codArea, numero) values(32, '63', '99928-9652');
insert into telefone (id, codArea, numero) values(42, '63', '98147-9522');
insert into telefone (id, codArea, numero) values(52, '62', '94856-9959');
insert into telefone (id, codArea, numero) values(62, '61', '98477-9772');
insert into telefone (id, codArea, numero) values(72, '99', '99978-9699');

-- Estados
insert into estado (id, nome, sigla) values(12, 'Tocantins', 'TO');
insert into estado (id, nome, sigla) values(22, 'Maranhão', 'MA');
insert into estado (id, nome, sigla) values(32, 'Pará', 'PA');
insert into estado (id, nome, sigla) values(42, 'Goiás', 'GO');

-- Cidades
insert into cidade (id, nome, id_estado) values(84, 'Palmas', 12);
insert into cidade (id, nome, id_estado) values(94, 'Carolina', 22);
insert into cidade (id, nome, id_estado) values(74, 'Belém', 32);
insert into cidade (id, nome, id_estado) values(54, 'Goiania', 42);

-- Endereços
insert into endereco (id, logradouro, numero, complemento, bairro, cep, cidade_id)
values(13, 'Alameda 18', '88', 'Próximo ao mercado', '108 Norte', '74985-888', 84); 
insert into endereco (id, logradouro, numero, complemento, bairro, cep, cidade_id)
values(23, 'Alameda 4', '12', 'Próximo a praça', '106 Sul', '74985-333', 94);
insert into endereco (id, logradouro, numero, complemento, bairro, cep, cidade_id)
values(43, 'Alameda 10', '58', 'Próximo ao mercado', '604 Norte', '70084-885', 74);
insert into endereco (id, logradouro, numero, complemento, bairro, cep, cidade_id)
values(53, 'Alameda 12', '85', 'Próximo a escola', '303 Norte', '72585-858', 54);
insert into endereco (id, logradouro, numero, complemento, bairro, cep, cidade_id)
values(63, 'Alameda 7', '42', 'Portão Azul', '406 Norte', '74585-558', 54);
insert into endereco (id, logradouro, numero, complemento, bairro, cep, cidade_id)
values(73, 'Alameda 8', '38', 'Murro verde e Portão preto', '504 Norte', '74854-666', 74);
insert into endereco (id, logradouro, numero, complemento, bairro, cep, cidade_id)
values(83, 'Alameda 3', '37', 'Próximo ao mercado BIG', '502 Norte', '74885-698', 74);

-- Clientes
insert into cliente (id, nome, login, senha, cpf, dataNascimento, perfil) 
values(19, 'Victor Alves', 'alves@unitins.br', 'yEaSZv1mx2Hf11tomtEAY3HUG2hrQS2ACE17U1PeCoA7PFIhHARbDredPke5UTKwvMVA+jod2rMVKSoDzm8p3Q==', '753.001.654-98', '2000-05-04', 1);
insert into cliente (id, nome, login, senha, cpf, dataNascimento, perfil) 
values(29, 'Bruno barros', 'bbruno@unitins.br', 'yEaSZv1mx2Hf11tomtEAY3HUG2hrQS2ACE17U1PeCoA7PFIhHARbDredPke5UTKwvMVA+jod2rMVKSoDzm8p3Q==', '023.396.158-08', '1995-03-01', 1);
insert into cliente (id, nome, login, senha, cpf, dataNascimento, perfil) 
values(39, 'Dany Bananinha', 'bananas@unitins.br', 'yEaSZv1mx2Hf11tomtEAY3HUG2hrQS2ACE17U1PeCoA7PFIhHARbDredPke5UTKwvMVA+jod2rMVKSoDzm8p3Q==', '522.369.528-05', '1997-01-04', 1);
insert into cliente (id, nome, login, senha, cpf, dataNascimento, perfil) 
values(59, 'Eliana do SBT', 'eliana@unitins.br', 'yEaSZv1mx2Hf11tomtEAY3HUG2hrQS2ACE17U1PeCoA7PFIhHARbDredPke5UTKwvMVA+jod2rMVKSoDzm8p3Q==', '001.311.102-15', '1998-02-02', 1);
insert into cliente (id, nome, login, senha, cpf, dataNascimento, perfil) 
values(49, 'Ana Vitoria', 'vitoriadaana@unitins.br', 'yEaSZv1mx2Hf11tomtEAY3HUG2hrQS2ACE17U1PeCoA7PFIhHARbDredPke5UTKwvMVA+jod2rMVKSoDzm8p3Q==', '099.389.963-11', '1998-06-06', 1);
insert into cliente (id, nome, login, senha, cpf, dataNascimento, perfil) 
values(69, 'Ana ', 'vitoriadaana@unitins.br', 'yEaSZv1mx2Hf11tomtEAY3HUG2hrQS2ACE17U1PeCoA7PFIhHARbDredPke5UTKwvMVA+jod2rMVKSoDzm8p3Q==', '099.389.963-11', '1998-06-06', 1);
insert into cliente (id, nome, login, senha, cpf, dataNascimento, perfil) 
values(79, 'Vitoria', 'vitoriadaana@unitins.br', 'yEaSZv1mx2Hf11tomtEAY3HUG2hrQS2ACE17U1PeCoA7PFIhHARbDredPke5UTKwvMVA+jod2rMVKSoDzm8p3Q==', '099.389.963-11', '1998-06-06', 1);

-- Clientes -> telefones
insert into cliente_telefone (id_cliente, id_telefone) values(19, 12);
insert into cliente_telefone (id_cliente, id_telefone) values(29, 22);
insert into cliente_telefone (id_cliente, id_telefone) values(29, 32);
insert into cliente_telefone (id_cliente, id_telefone) values(39, 42);
insert into cliente_telefone (id_cliente, id_telefone) values(49, 52);
insert into cliente_telefone (id_cliente, id_telefone) values(59, 62);
insert into cliente_telefone (id_cliente, id_telefone) values(59, 72);

-- Clientes -> endereços
insert into cliente_endereco (id_cliente, id_endereco) values(79, 83);
insert into cliente_endereco (id_cliente, id_endereco) values(79, 73);
insert into cliente_endereco (id_cliente, id_endereco) values(19, 13);
insert into cliente_endereco (id_cliente, id_endereco) values(29, 23);
insert into cliente_endereco (id_cliente, id_endereco) values(49, 43);
insert into cliente_endereco (id_cliente, id_endereco) values(59, 53);
insert into cliente_endereco (id_cliente, id_endereco) values(69, 63);

-- Pix
insert into pix (id, chavePix) values(35, 'carlos@gmail.com');
insert into pix (id, chavePix) values(45, '9998563254');

-- Boleto Bancario
insert into boletoBancario (id, banco, numeroBoleto, dataVencimento) 
values(11, 'Bradesco', '36985214789', '2026-05-03');
insert into boletoBancario (id, banco, numeroBoleto, dataVencimento) 
values(21, 'Next', '036985523', '2028-05-04');
insert into boletoBancario (id, banco, numeroBoleto, dataVencimento) 
values(31, 'Nubank', '951555887', '2024-02-05');

-- Cartão de Crédito
insert into cartaoCredito (id, bandeira, numeroCartao, codigoSeguranca, dataVencimento) 
values(100, 'Visa', '111225589', '123', '2024-03-08');
insert into cartaoCredito (id, bandeira, numeroCartao, codigoSeguranca, dataVencimento) 
values(111, 'Alguma', '111225589', '444', '2024-03-08');
insert into cartaoCredito (id, bandeira, numeroCartao, codigoSeguranca, dataVencimento) 
values(122, 'Visa', '111225589', '569', '2024-03-08');

-- Pagamentos
insert into pagamento (id, tipo, valor) 
values(81, 'Avista', 12.5);
insert into pagamento (id, tipo, valor) 
values(71, 'Aprazo', 125.59);
insert into pagamento (id, tipo, valor) 
values(61, 'Avista', 125);
insert into pagamento (id, tipo, valor) 
values(51, 'Aprazo', 155);
insert into pagamento (id, tipo, valor) 
values(41, 'Avista', 189.5);
insert into pagamento (id, tipo, valor) 
values(31, 'Avista', 125);
insert into pagamento (id, tipo, valor) 
values(21, 'Aprazo', 155);
insert into pagamento (id, tipo, valor) 
values(11, 'Avista', 189.5);

-- Pagamentos -> Boleto Bacario
insert into pagamento_boletobancario(id_boletoBancario, id_pagamento)
values(11, 81);
insert into pagamento_boletobancario(id_boletoBancario, id_pagamento)
values(21, 71);
insert into pagamento_boletobancario(id_boletoBancario, id_pagamento)
values(31, 61);

-- Pagamentos -> Cartão de Crédito
insert into pagamento_cartaocredito(id_cartaocredito, id_pagamento)
values(111, 51);
insert into pagamento_cartaocredito(id_cartaocredito, id_pagamento)
values(100, 41);
insert into pagamento_cartaocredito(id_cartaocredito, id_pagamento)
values(122, 31);

-- Pagamentos -> Pix
insert into pagamento_pix(id_pix, id_pagamento)
values(35, 21);
insert into pagamento_pix(id_pix, id_pagamento)
values(45, 11);

-- Modelos

insert into modelo (id, nome, marca) values(7, 'Iphone Xr', 'Apple');
insert into modelo (id, nome, marca) values(5, 'A45', 'Samsung');


-- Capinhas

insert into capinha (id, nome, descricao, valor, quantEstoque, modelo_id, cor)
values(13, 'Capinha de Glitter Rosa', 'Uma capinha de celular', 25.0, 30, 7, 'AZUL');
insert into capinha (id, nome, descricao, valor, quantEstoque, modelo_id, cor)
values(23, 'Capinha de titânio cinza', 'Uma capinha de celular', 45.0, 5, 5, 'AZUL');

-- Pedidos
insert into pedido (id_cliente, id_pagamento, dataHora, valorTotal) 
values(19, 81, '2023-12-01', 12.5);

insert into pedido (id_cliente, id_pagamento, dataHora, valorTotal) 
values(29, 71, '2023-12-01', 125.59);

insert into pedido (id_cliente, id_pagamento, dataHora, valorTotal) 
values(39, 61, '2023-12-02', 125);

-- Itens do Pedido
insert into itemvenda (quantidade, valor, id_capinha, id_pedido) 
values(2, 25.0, 13, 1);

insert into itemvenda (quantidade, valor, id_capinha, id_pedido) 
values(1, 45.0, 23, 2);

insert into itemvenda (quantidade, valor, id_capinha, id_pedido) 
values(3, 25.0, 13, 3);
