# Framework - Desafio Android

Construa uma aplicação Android que irá sincronizar as listagens da API Rest para o banco local ao iniciar a aplicação. Esta aplicação deve conter os menus:
- POSTAGENS
- ALBUNS
- TO-DOs

Toda a aplicação funcionando com o banco local sincronizado inicialmente. 
- A API a ser utilizada está publicada em https://jsonplaceholder.typicode.com/. 
- Utilizar os guid lines (https://developer.android.com/design/index.html) do Google Material Design para construção das telas.

# A Solução

A aplicação se trata de uma simples listagem de postagens, albuns e tarefas, divididas respectivamente em abas. A aplicação consome *endpoints* fornecida pelo *{JSON} Placeholder* e comporta-se da seguinda maneira:

- Caso o usuário esteja conectado à internet, fazer consumo dos endpoints especificados. Caso contrário, consumir a base de dados local.
- **OBS.:** Para tratamento de erros, adotei uma estratégia de exibir um diálogo em caso de perca de conexão repentina e, em caso de ausência de dados, uma mensagem ao centro da tela informando ao usuário que não possuímos dados no momento, oferecendo a ele a opção de poder solicitar novamente uma chamada ao webservice para verificar se há dados a serem consumidos.

Foi adotada a **[Arquitetura MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)** de acordo com as [recomendações de arquitetura mobile da Google](https://developer.android.com/jetpack/guide) juntamente com a **[Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)**. Além disso, foi adotado no projeto as principais bibliotecas do mundo do [Android Jetpack](https://developer.android.com/jetpack) e utilização de demais ferramentas. Seguem abaixo cada uma com a devida justificativa de adoção:

- **[Retrofit:](https://square.github.io/retrofit/)** um cliente HTTP *type-safety* para Android de fácil utilização que fornece um simples padrão de implementação para transmissão de dados entre aplicação/servidor.
- **[Room:](https://developer.android.com/training/data-storage/room)** um banco de dados que oferece uma camada de abstração sobre o SQLite para permitir acesso fluente ao banco de dados e, ao mesmo tempo, aproveitar toda a capacidade do SQLite de forma offline. É altamente recomendável utilizar o Room por sua forma de tratamento dos dados.
- **[LiveData:](https://developer.android.com/reference/androidx/lifecycle/LiveData?hl=pt-br)** trata-se de uma classe de portador de dados que pode ser observada dentro de um determinado ciclo de vida. Isso significa que um *Observer* pode ser adicionado a um par com um *LifecycleOwner* e esse observador será notificado sobre as modificações dos dados agrupados apenas se o *LifecycleOwner* emparelhado estiver no estado ativo. 
- **[Koin:](https://insert-koin.io/)** uma biblioteca de injeção de dependências de forma inteligente, de fácil configuração e adoção. O Koin roda em tempo de execução, contudo, não apresenta uma performance ruim. Foi levantada a possível adoção do [Dagger](https://dagger.dev/dev-guide/android.html), contudo, sua configuração é um pouco mais massante e requer maior robustez e atenção. Entre isso e outros fatores, o Koin vem se destacando no mercado e isso foi considerado para a sua implantação na nossa arquitetura.

Por fim, falando um pouco sobre o **MVVM (Model-View-ViewModel)**, temos basicamente a divisão em:

- **Model:** a camada que encapsula a lógica de negócios e os dados. Nada mais é do que o modelo de domínio de uma aplicação.
- **View:** tem por responsabilidade definir a aparência ou estrutura que o usuário vê na tela. Se trata de toda a parte visual da aplicação.
- **ViewModel:** sua incumbência é disponibilizar para a camada de *View* uma lógica de apresentação. A *ViewModel* não tem nenhum conhecimento específico sobre a view, apenas implementa propriedades e comandos e notifica a *View* no caso de alterações. Ela permite que os dados sobrevivam às mudanças de configurações, como a rotação de tela, por exemplo.

Considerando estas camadas, podemos observar que é uma divisão que se encaixa diretamente com a **Clean Architecture**, que consiste em um diagrama de camadas, onde cada um dos seus componentes possuem suas próprias responsabilidades e cada uma delas tem conhecimento apenas de camadas mais internas. Isso traz consigo várias vantagens:

- o código é facilmente testável.
- componentes ainda mais desacoplados, a estrutura do pacote é facilmente de se navegar entre eles.
- novas funcionalidades podem ser adicionadas rapidamente pelo time de desenvolvedores.

Cada camada de MVVM usando Clean Architecture no Android e os códigos se dividem em três camadas:

- **Camada de Apresentação (Presentation Layer):** Nesta camada, são incluídas as "Activity"s, "Fragment"s como sendo as "Views", e as "ViewModel"s, devem ser mais simples e intuitivo possível e ainda, não devem ser incluídas regras de negócio nas "Activity"s e "Fragment"s. Uma "View" irá se comunicar com sua respectiva "ViewModel", e assim, a "ViewModel" se comunicará com a camada de domínio para que sejam executadas determinadas ações. Uma "ViewModel" nunca se comunicará diretamente com a camada de dados. Aqui, na estrutura de nosso projeto, temos os diretórios "presentation", que por sua vez, possui os diretórios "ui" (com nossas "View"s e "ViewModel"s) e "di" (com nosso módulo Koin para tratar as injeções de dependência).

- **Camada de Domínio (Domain Layer):** Na camada de domínio, devem conter todos os casos de uso da aplicação. Os casos de uso tem como objetivo serem mediadores entre suas "ViewModel"s e os "Repository"s. Caso for necessário adicionar uma nova funcionalidade, tudo o que deve ser feito é adicionar um novo caso de uso e todo seu respectivo código estará completamente separado e desacoplado dos outros casos de uso. A criação de um novo caso de uso é justamente para evitar que ao adicionar novas funcionalidades, quebrar as preexistentes no código. Podemos observar o diretório "domain" e, neste, o diretório "usecase" com todos os nossos casos de uso.

- **Camada de Dados (Data Layer):** Esta camada possui todos os repositórios que a camada de domínio tem disponíveis para utilização e "DataSource"s, que são responsáveis por decidir qual a fonte em que devem ser recuperados os dados, sendo de uma base de dados local ou servidor remoto. Note o repositório "data". Nele se concentra nossos modelos de dados, modelagem do banco de dados, camada de serviço (que lista todos os nosso endpoints), camada de DAO para acesso aos dados no banco e, a parte dos nossos repositórios.

Esta foi a abordagem adotada na solução. Desde já, grato.

**Autor: Lucas Christopher.**

##### _All honor and all glory, everything comes from him and everything is for him: JESUS!_
