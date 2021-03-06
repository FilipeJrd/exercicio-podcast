# Métricas de rede

Para as análises de Rede citadas neste documento foi utilizada uma ferramenta de captura de métrica conhecida como Android Profiler.

Este documento está dividido em seções que correspondem as telas e atividades da aplicação.

Só uma Activity da nossa aplicação usa recursos de rede, então só coletamos as métricas para ela.

## Início da atividade da Home Screen

No onCreate() da MainActivity, é feita uma requisição para checar se há novos podcast upados no feed RSS. Com isso uma pequena quantidade de rede é usada num período de 1 segundo. Segue o resultado na imagem abaixo:

![MEM1](https://github.com/FilipeJrd/exercicio-podcast/blob/master/prints/studio64_2017-12-12_19-07-20.png)

## Download de episódios de Podcast

Também feito na MainActivity (após o usuário selecionar um arquivo para) o uso de rede é feito durante o período em que está sendo feito o download e não altera nada em relação ao desempenho do aplicativo. Como podemos ver na imagem abaixo, o uso de rede é periódico e se mantém até o final do download, sendo terminado imediatamente
![MEM2](https://github.com/FilipeJrd/exercicio-podcast/blob/master/prints/studio64_2017-12-12_19-08-20.png)

## Boas práticas relacionadas a rede

Uma das boas práticas que fizemos foi realizar as requisições de rede em uma thread separada (Async Task) para não influenciar em nada a utilização do nosso aplicativo (travar UI, deixar mais lento, etc).

(https://github.com/FilipeJrd/exercicio-podcast/blob/master/Podcast/app/src/main/java/br/ufpe/cin/if710/podcast/ui/adapter/XmlFeedAdapter.java#L100)
