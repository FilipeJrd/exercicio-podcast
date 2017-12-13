# Métricas de bateria

As métricas de consumo de energia/ uso de bateria foram medidas com base nas outras métricas coletadas neste projeto. Sabemos que para deixar os apps "power efficient" temos que gerenciar os principais recursos que drenam a bateria do aparelho, como requisições de rede, tela ligada, recursos de som, gps, IO, etc.

## Gasto de bateria nas activities

A activity que gasta mais bateria na nossa aplicação é a MainActivity, pois lá que são feitas todas as requisições de rede, de leitura e escrita no banco de dados e de reprodução dos podcasts. Isso está diretamente ligado ao uso de rede, atividade de tela e escrita e leitura em disco. 

## Boas práticas do projeto relacionadas a bateria.

Como boa prática no projeto, nós permitimos que o usuário continue escutando seu podcast mesmo com a tela desligada (o que econonimza bastante a bateria). Além do que as requisições de checagem de atualização de podcast podem ser feitas manualmente pelo usuário ou automaticamente em grandes intervalos de tempo  (o que previne várias requisições) o tempo todo, assim, evitando o gasto desnecessário de bateria.
