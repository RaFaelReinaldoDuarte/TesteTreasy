Este teste foi desenvolvido por
Rafael Reinaldo Duarte
rafaelreinaldoduarte@gmail.com
47 99738 9025

WEb services Desenvolvidos conforme teste solicitado
1- Conexão com banco de dados : ARQUIVO hibernate.properties dentro de src 
2- Gerar banco de dados e dados teste: 
Pacote FRAMEWORK.CONEXAO arquivo GeraTabelas.java (apenas executar) após execução, aparecerá no console
******** INSERIDO ********

desenvolvimento utilizando Tomcat9, java8, hibernate annotation,rest json


Exemplo de requisição

INSERIR um registro
POST
http://localhost:8080/TestTreasyWS/node
{"code":"inserindo POST","description":"Filho do 1","parentId":4,"detail":"Filho do 4"}

ATUALIZAR um registro
PUT
http://localhost:8080/TestTreasyWS/node
{"cd":5,"code":"atualizando","description":"Filho do 1","parentId":4,"detail":"Filho do 4"}

Listar Todos os registros
GET
http://localhost:8080/TestTreasyWS/node

Listar Todos os filhos do ID
GET
http://localhost:8080/TestTreasyWS/node/1
