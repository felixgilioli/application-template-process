## Objetivo

O objetivo desse sistema é permitir a criação de repositórios git que sirvam como template para outros projetos, que por exemplo, já venham com uma estrutura padrão configurada, dependências, etc.

## Templates

Os templates podem conter variáveis, seja no nome dos arquivos e diretórios ou dentro de cada arquivo. Também é possível fazer condicionais, loops, etc.

## Gerando Projetos

Para gerar um projeto a partir de um template, basta rodar a aplicação localmente e acessar o endereço http://localhost:8080. Nesta tela você deve informar a URL do seu repositório git, ao clicar em "Next", você será direcionado para uma tela onde deve informar as variáveis utilizadas no template(as variáveis são informadas no arquivo template-definition.yaml). Após informada as variáveis, basta clicar em "Download" e será baixado um zip do projeto.

Também é possível fazer o download através de APIs REST.

## Contribuições

Sinta-se a vontade para contribuir com o projeto através de sugestões, documentações, issues e Pull Requests.
