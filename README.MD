Exemplo de implementa��o de endpoint rest usando JaxRS.

Nesse hello world, foi implementado um crud com os seguintes endpoints:

http://localhost:8080/restJaxRsExemplo/crudFinal/consultarTodos (GET)
http://localhost:8080/restJaxRsExemplo/crudFinal/consultarId/{Id} (GET)
http://localhost:8080/restJaxRsExemplo/crudFinal/cadastrar (POST)
http://localhost:8080/restJaxRsExemplo/crudFinal/editar (PUT)
http://localhost:8080/restJaxRsExemplo/crudFinal/excluir/{Id} (DELETE)

O json utilizado nas requisi��es POST E PUT, bem como resposta nas consultas (GET), 
tem o formato exemplificado:

{
	"id": 1,
	"uri": "restJaxRsExemplo/editar/1",
	"nome": "nome",
	"sobrenome": "sobrenome"
}

A parte de persist�ncia foi desenvolvida usando banco Oracle, acessado por interm�dio do Hibernate/JPA.

No pacote controller, existem 3 implementa��es distintas, no intuito de documentar o passo a passo realizado ao longo do aprendizado.
A classe final (exemplo a ser considerado) � "ApiTesteCrudFinal", que usa o padr�o MVC.