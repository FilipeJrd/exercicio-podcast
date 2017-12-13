# Métricas de Memória

Para as análises de memória citadas nesse documento foram utilizadas algumas ferramentas de captura de métricas como o Android Profiler para medir o consumo de memória e o Canary Leak para a detecção de Memory Leaks.

Este documento está dividido em seções que correspondem as telas e atividades da aplicação.

## Memory Leaks na aplicação

Com o auxílio do Canary Leak, achamos um memory leak na nossa aplicação. Ele ocorria na MainActivity e era causado por causa de um Receiver que não era desregistrado nunca. Era um leak de 6.9KB que foi resolvido com o seguinte trecho de código.

![MEM1](https://github.com/FilipeJrd/exercicio-podcast/blob/master/prints/studio64_2017-12-13_13-59-10.png)

As imagens do memory leak capturadas pelo leak canary são as seguintes:

![MEM2](https://github.com/FilipeJrd/exercicio-podcast/blob/master/prints/firefox_2017-12-12_20-05-58.png)

![MEM3](https://github.com/FilipeJrd/exercicio-podcast/blob/master/prints/qemu-system-i386_2017-12-12_20-06-14.png)

## Início da atividade de Home Screen

O uso de memória entre o sistema no seu estado idle e o início do uso do app sobe de 25MB para 35MB (uma diferença de 10MB) e se permanece constante durante mesmo após o aplicativo fazer as consultas a rede a ao banco de dados.

![MEM4](https://github.com/FilipeJrd/exercicio-podcast/blob/master/prints/ApplicationFrameHost_2017-12-13_14-16-06.png)

## Reprodução de episódios

Durante a reprodução de episódios, a memória cresce pouco, aumentando cerca de 300KB de memória durante a reprodução e se mantendo estável até sua interrupção.

![MEM5](https://github.com/FilipeJrd/exercicio-podcast/blob/master/prints/studio64_2017-12-13_14-19-30.png)

## Download dos episódios

Durante o download dos episódios a memória varia entre uma faixa de 35 a 38MB e permanece constante até a finalização do download. Esta variação é representada na imagem abaixo:

![MEM6](https://github.com/FilipeJrd/exercicio-podcast/blob/master/prints/studio64_2017-12-13_14-29-58.png)

## Outras Activities

Nas outras activities como a DetailActivity e a SettingsActivity a memória varia para 1MB a mais durante sua inicializações e se mantém constante até seu encerramento.

![MEM7](https://github.com/FilipeJrd/exercicio-podcast/blob/master/prints/studio64_2017-12-13_14-43-17.png)
