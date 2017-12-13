# Testes

Para validar o correto funcionamento da aplicação foram realizados testes unitários, de integração e de UI.
Durante esse processo foi percebida a necessidade de realizar alterações em certas partes do código de modo que cada pedaço se tornasse mais facilmente testado.

OBS: Todos os testes foram feitos antes da adição do Room e do LiveData, infelizmente por questões de tempo não foi possível atualizar os testes, mas os testes do content provider continuam funcionando mesmo após o update (por que deixamos algumas partes vivas só para mostrar o funcionamento dos testes, mesmo que não elas não estejam mais sendo usadas).

A descrição do processo segue a seguir.

## ItemFeedTest:

Esse caso de teste é bem simples, basicamente realiza o teste da class ItemFeed validando se o seu construtor e getters funcionam de maneira adequada.

Para a realização desse teste não foi necessária nenhuma refatoração no código.

## XMLFeedParserTest:

Caso de teste responsável por validar a classe XMLFeedParser que é reponsável pela leitura do arquivo xml correspondente ao RSS contendo os podcast.

Para a realização desses testes decidi que não haveria necessidade de utilizar um simulador. Como essa classe originalmente utiliza objetos que dependiam do ambiente Android para sua execução foi necessária a extração desses objetos de dentro da classe para que seu comportamento pudesse ser mockado.

Uma vez realizada a extração do objeto XmlPullParser, que depois da extração é recebido como parâmetro, foi utilizada a biblioteca mockito para criação de um mock desse objeto configurando qual o comportamento a ser simulado ao longo do teste. Isso permitiu que esse teste fosse executado sem depender do ambiente Android, tornando o mais facil de testar.

## PodcastContentProviderTest:

Caso de teste responsável por validar a utilização da classe PodcastContentProvider. Essa classe é a "interface" de comunicação com o banco de dados, é através dela que a aplicação tem acesso as informações.

Para a realização dos testes relativos a essa classe é necessário o uso do ambiente Android. Isso ocorre visto que o acesso as funções da mesma não é feita de maneira direta, sendo sempre intermediada por um ContentResolver que será responsável por acessar o ContentProvider adequado de acordo com a operação realizada. Além disso essa classe faz uso direto do banco de dados SQLite presente no ambiente android, o que mais uma vez "amarra" a classe a esse ambiente.

Dito isso, os testes são sempre executados em um emulador. Embora não tenham ocorrido modificações nó código relativo ao PodcastContentProvider alguns cuidados foram tomados durante o desenvolvimento desses testes, isso foi realizado seguindo o seguinte [artigo](https://developer.android.com/training/testing/integration-testing/content-provider-testing.html) presente na documentação do android. Segundo o artigo para a realização dos testes de maneira adequada nossa classe de testes deve extender a classe ProviderTestCase2 para que tenhamos acesso a um MockContendResolver que será capaz de expor as funcionalidades presenter no ContentProvider a ser testado. Nesse caso foi realizado um teste que limpa o banco, insere um grupo de dados especificados na classe e em seguida realiza uma query para que confirmar a inserção dos mesmo.

## RSSDownloaderTest

Caso de teste responsável por validar o funcionamento do download de um arquivo RSS e seu correto armazenamento no banco. Inicialmente a ideia era fazer um teste do serviço responsável pelo download do RSS porém para facilitar o processo foi realizada uma refatoração com o intuito de extrair toda a lógica de download e armazenamento dos dados RSS no banco para uma nova classe, dessa forma caso a operação seja bem sucedida em retornando um boolean true.

Uma vez que a extração foi realizada foi desenvolvido um caso de teste para essa nova classe, onde é passado a url de um arquivo RSS e é requisitado  seu download, por se tratar de um teste de integração todo o comportamento é testado fazendo com que de fato seja necessário o download, parsing e armazenamento dos dados. Por fim se o resultado da operação for true o teste é considerado bem sucedido.


# Testes UI

## openDetailsForFirst_Test

Caso de teste responsável por validar a tela de detalhes para os primeiros 4 podcasts presentes na lista, uma vez que esse teste é executado cada tela de detalhes é aberta por vez verificando se os valores são corretamente preenchidos.

## checkFirstFourItems_Test

Caso de teste responsável por validar se os quatro primeiros elementos do FeedRSS estão com os valores corretamente preenchidos, checando valores como title e date.

