# PracticalTest--Autoflex

API REST para gerenciamento de produtos e matérias primas com relacionamento entre elas. Desenvolvida com Spring Boot.

Tecnologias utilizadas nesse projeto:
Spring Boot, Hibernate / JPA, MYSql, Maven, Swagger, Validation, Mockito, JUnit, HTML, CSS, REACT

Para rodar o projeto você precisa:
- Ter instalado o JDK 24, NODE.JS.
- Baixe o projeto e abra o back-end. Na camada main/resources, abra o application.properties e altere o username e password para seu login e senha MYSQL, altere o hibernate para Create para criar as tabelas no seu banco de dados. Feito isso, basta rodar o projeto na classe main, que irá ser hospedado na porta 8080. http://localhost:8080
- Com o back-end em execução, abra o projeto front-end, abra o seu Git Bash, dê: "cd autoflex" para entrar na pasta do projeto, e rode "npm install" e em seguida "npm run dev".
- Após isso, o projeto estará pronto para uso. 
 

Funcionalidades:
- Cadastro de produtos
- Listagem de produtos
- Procura dos detalhes do produto por ID
- Atualização de produtos por ID
- Remoção de produtos por ID
- Cadastro de matérias primas
- Listagem de matérias primas
- Procura dos detalhes da matéria prima por ID
- Atualização de matérias primas por ID
- Remoção de matérias primas por ID
- Cadastro de relacionamento entre produtos e matérias primas e registro de quantidade necessária para fazer o produto
- Listagem de produtos feitos por uma matéria prima específica
- Listagem de matérias primas necessárias para um produto

## Todo o projeto está documentado com SWAGGER:
`http://localhost:8080/swagger-ui/index.html#/`

## Principais Requisições

### Cadastro de Produtos

`/product/register`.

JSON:
```json
{
	"name": "Chair",
	"value": 200
}
```
______________________________________
### Atualização de Produtos

`/product/updateProduct/{id}`

JSON:
```json
{
	"name": "BigChair",
	"value": 300
}
```
______________________________________
### Busca de Produtos por ID

`/product/findById/{id}`.

______________________________________
### Busca de todos os Produtos

`/product/listProducts`.


______________________________________
### Remoção de Produtos por ID

`/product/delete/{id}`.

______________________________________
### Cadastro de Matérias Primas

`/rawMaterial/register`.

JSON:
```json
{
	"name": "Wood",
	"stock": 500
}
```
______________________________________
### Atualização de Matérias Primas

`/rawMaterial/updateRawMaterial/{id}`.


JSON:
```json
{
	"name": "Wood",
	"stock": 750
}
```
______________________________________
### Buscar de Matérias Primas por ID

`/rawMaterial/findById/{id}`.

______________________________________
### Busca de todas as Matérias Primas

`/rawMaterial/listRawMaterials`

--------------------------------------
### Remoção de Matérias Primas por ID

`/rawMaterial/delete/{id}`

______________________________________
### Cadastro de Relacionamento entre Matérias Primas e Produtos com quantidades necessárias para fabricação do produto

`/rawMaterialProduct/product/{idProduct}/rawMaterial/{idRawMaterial}`.

JSON:
```json
{
	"quantity": 1
}
```
______________________________________
### Busca de todos os produtos feitos por determinada matéria prima, retornando também a quantidade possível para fabricar e simulação de valor total (quantidade possível x valor unitário)

`rawMaterialProduct/productAvailable/{idRawMaterial}`.

______________________________________
### Busca de todas as matérias primas necessárias e quantidade para fabricar determinado produto.

`/rawMaterialProduct/necessaryRawMaterial/{idProduct}`.


