# Architecture Components

Para a realizar a conversão do app para o uso dos Architecture Components foi necessario que fossem abordadas duas frentes que vão ser descritas nos tópicos a seguir:

## Acesso ao banco de dados usando Room:

Esse foi o primeiro passo realizado no processo de conversão. O novo padrão arquitetural do android trás consigo um ORM que facilita o acesso ao banco de dados presente no dispositivo.

Para a realização da implementação dessa nova forma de acesso foi necessária a modificação do modelo ItemFeed. Essa modificação consistiu na adição de annotations definindo que esse modelo correspondia a uma entidade com nome de tabela "ItemFeed" e o mapeamento de cada atributo a uma coluna da tabela. Uma vez que essas modificações foram realizadas o sistema passa a entender como converter os resultados de uma query para Objetos desse tipo.

Depois que o sistema entende o que é o objeto ItemFeed precisamos definir as funções que realizaram o acesso e inserção desses dados. Para isso foi criada a classe ItemFeedDao onde definimos funções de crud. No caso da nossa aplicação tinhamos as funções de getItems() que busca todos os items no bancom a função inserItem(item) que adiciona um novo item ao banco, a função updateItem(item) que atualiza um objeto com os novos atributos e a função deleteAllItems() que realiza uma limpeza total no banco.

Uma vez que definimos a interface de inserção de dados no banco criamos a classe PodcastDatabase que é responsável por ser uma instância unica de acesso ao banco, logo ela é implementada como um singleton. Essa classe será utilizada por outras partes do sistema para acessar nosso Dao.


## Utilização de LiveData

LiveData é um novo recurso presente na arquitetura android que permite a observação de dados de forma dinâmica, notificando todos os observadores de mudanças realizadas. Para a implementação desse recurso na aplicação foi desenvolvida uma classe chamada ItemFeedViewModel, essa classe é reponsavel por intermediar essa observação através da sua função getItems() que retorna um objeto LiveData da lista de ItemFeed. Isso é feito através das interfaces de acesso ao banco desenvolvidas na sessão anterior.

Uma vez que todo esse processo foi realizado foram feitas modificações na classe responsável pela tela principal da aplicação de modo que a lista de podcasts estivesse atrelada aos dados do viewModel. Sempre que somos notificados com uma nova lista de items é executada uma limpeza no adapter de items e a inserção dos novos items é feita.

Dessa forma a conversão foi realizada por completo tornando o fluxo de dados do sistema mais confiável e robusto.

