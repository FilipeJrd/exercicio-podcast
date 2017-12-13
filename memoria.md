# Métricas de Memória

Para as análises de memória citadas nesse documento foram utilizadas algumas ferramentas de captura de métricas como o Android Profiler para medir o consumo de memória e o Canary Leak para a detecção de Memory Leaks.

Este documento está dividido em seções que correspondem as telas e atividades da aplicação.

## Memory Leaks na aplicação

Com o auxílio do Canary Leak, achamos um memory leak na nossa aplicação. Ele ocorria na MainActivity e era causado por causa de um Receiver que não era desregistrado nunca. Era um leak de 9.5KB que foi resolvido com o seguinte trecho de código.

-- inserir imagem aqui -- 

As imagens do memory leak capturadas pelo leak canary são as seguintes:

-- inserir imagem aqui -- 

-- inserir imagem aqui --

## Início da atividade de Home Screen

O uso de memória entre o sistema no seu estado idle e o início do uso do app sobe de 25MB para 35MB (uma diferença de 10MB) e se permanece constante durante mesmo após o aplicativo fazer as consultas a rede a ao banco de dados.

-- imagem aqui --

## Reprodução de episódios

Durante a reprodução de episódios, a memória cresce pouco, aumentando cerca de 300KB de memória durante a reprodução e se mantendo estável até sua interrupção.

-- imagem aqui

## Download dos episódios

Durante o download dos episódios a memória varia entre uma faixa de 35 a 38MB e permanece constante até a finalização do download. Esta variação é representada na imagem abaixo:

-- imagem aqui

## Outras Activities

Nas outras activities como a DetailActivity e a SettingsActivity a memória varia para 1MB a mais durante sua inicializações e se mantém constante até seu encerramento.

-- imagem aqui --  