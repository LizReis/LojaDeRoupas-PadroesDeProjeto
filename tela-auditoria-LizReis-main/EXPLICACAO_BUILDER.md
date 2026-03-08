# Builder (Padrao Criacional)

## Problema
O codigo estava criando objetos `Produto` com construtor longo e muitos parametros do mesmo tipo (`String`), por exemplo:

`new Produto(modelo, cor, tamanho, preco, departamento)`

Esse formato deixa o codigo mais dificil de ler e aumenta o risco de troca de ordem dos argumentos.
No fluxo de cadastro, havia risco real de inconsistencia: em um ponto os campos eram enviados como `modelo, tamanho, cor`, enquanto o construtor esperava `modelo, cor, tamanho`.

Entao o problema nao era apenas organizacao de codigo. A criacao por construtor posicional podia gerar dados errados no objeto final.

## Local no codigo
Pacote principal: `br.edu.ifba.saj.fwads.model`  
Classe principal: `Produto.java`

Pontos atualizados de criacao:
- `br.edu.ifba.saj.fwads.controller.CadastroProdutoController`
- `br.edu.ifba.saj.fwads.model.Departamento`

## Como foi aplicado
Criamos uma classe interna estatica `Builder` dentro de `Produto`;
Alteramos a criacao para usar um construtor privado `Produto(Builder builder)`;
Criamos o ponto de entrada `Produto.builder()` para iniciar a montagem do objeto;
Adicionamos metodos fluentes no builder para cada campo:
`modelo(...)`, `tamanho(...)`, `cor(...)`, `preco(...)` e `departamento(...)`;
Concluimos a criacao com `build()`, que retorna o objeto `Produto` pronto;
Refatoramos os pontos de instancia para remover `new Produto(...)`:
- em `CadastroProdutoController`, o produto agora e montado por nome de campo;
- em `Departamento#adicionarProduto`, a criacao tambem foi padronizada com Builder.

Com isso, a construicao ficou explicita e padronizada, sem depender da ordem de parametros no construtor.

## Beneficios
Reduz o risco de erro por ordem de parametros, que era o problema pratico identificado;
Melhora a legibilidade porque cada valor e associado ao seu campo (`.cor(...)`, `.tamanho(...)`, etc.);
Facilita manutencao e evolucao da classe `Produto` quando novos atributos forem adicionados;
Padroniza a forma de criar `Produto` em diferentes camadas do sistema;
Mantem a responsabilidade de construcao concentrada em um unico ponto (o Builder).
