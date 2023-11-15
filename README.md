A API Match_service é desenvolvida para integrar-se à aplicação "App Meu Preço Baixo", fornecendo três funcionalidades principais. Segue abaixo a descrição das funcionalidades bem detalhadas.

OBS: Repositório do app meu preço baixo:  https://github.com/glauberosouza/api-my-low-price

# 1. Evento de Criação de Alerta
   
    Consome um evento de criação de um alerta do "App Meu Preço Baixo" no tópico do kafka.
    Envia um e-mail ao usuário confirmando a criação bem-sucedida do alerta.

# 2. Evento de Novo Produto

    Consome um evento de um novo produto no tópico do kafka.

# 3. Comparação entre o Alerta e o Produto

    Se o preço informado no alerta coincidir com o preço do produto recém-adicionado, envia um e-mail ao usuário informando que o range de preço do produto está dentro do informado no alerta.

## Projeto em andamento sujeito a alterações.
