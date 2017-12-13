# Métricas de CPU

Para as análises de CPU citadas neste documento, foram utilizadas algumas ferramentas de captura de métricas como Android Profiler e Android Dev Metrics.

Este documento está dividido em seções que correspondem as telas e atividades da aplicação.

## Início da atividade da Home Screen

Na home screen, o uso significativo da CPU ocorre em um curto período de tempo que corresponde a execução da consulta no banco de dados para a população do list view correspondente a lista de podcasts e da chamada de rede feita para a atualização e download de novos elementos para esta lista. O uso da cpu nestes dois casos é na faixa de 35% para realização destas operações em um intervalo de tempo de 3.5 segundos.

![CPU1](https://github.com/FilipeJrd/exercicio-podcast/blob/master/prints/studio64_2017-12-12_18-17-55.png)

## Download de episódios

Durante o download de episódios, a CPU fica em uso constante com um uso na faixa de 15 - 39% durante um período de (1min e 30seg*). 

*Este período é varíavel, pois depende da velocidade de internet do usuário.

![CPU2](https://github.com/FilipeJrd/exercicio-podcast/blob/master/prints/studio64_2017-12-13_13-32-45.png)

## Reprodução de episódios

Quando se trata da reprodução dos episódios do podcast a CPU só tem um pico no primeiro momento de reprodução do episódio (no momento entre quando o usuário aperta o play e o primeiro segundo de áudio). Este pico é de 10% de uso de CPU. Após este curto intervalo, o app volta a usar cerca de 1% de CPU, o qual é o valor de CPU do app no estado idle.

![CPU3](https://github.com/FilipeJrd/exercicio-podcast/blob/master/prints/studio64_2017-12-13_13-36-07.png)

## Outras Activities

O uso de CPU nas outras Activities (Settings Activity e Detail Activity) é o mesmo e só é "significativo" no momento de sua inicialização que é de 10% de uso. Após isso ele fica no estado idle  com o uso de 1% da CPU.

![CPU4](https://github.com/FilipeJrd/exercicio-podcast/blob/master/prints/studio64_2017-12-13_13-48-31.png)
