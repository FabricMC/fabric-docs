---
title: Sugestões de Comando
description: Aprenda como sugerir valores de argumentos de comando aos usuários.
authors:
  - IMB11
---

O Minecraft possui um poderoso sistema de sugestões de comando usado em muitos lugares, como no comando `/give`. Esse sistema permite que você sugira valores para os argumentos de comando ao usuário, quele pode então selecionar — é uma ótima maneira de tornar seus comandos mais amigáveis e ergonômicos.

## Provedores de Sugestões {#suggestion-providers}

Um `SuggestionProvider`é usado para criar uma lista de sugestões que será enviada ao cliente. Um provedor de sugestões é uma interface funcional que recebe um `CommandContext`e um `SuggestionBuilder`, e retorna algumas `Suggestions`. O `SuggestionProvider` retorna um `CompletableFuture` já que as sugestões podem não estar disponíveis imediatamente.

## Usando Provedores de Sugestões {#using-suggestion-providers}

Para usar um provedor de sugestões, você precisa chamar o método `suggests`no builder do argumento. Este método recebe um `SuggestionProvider` e retorna o builder do argumento modificado com o provedor de sugestões anexado.

@[code java highlight={4} transcludeWith=:::command_with_suggestions](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code java transcludeWith=:::execute_command_with_suggestions](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

## Provedores de Sugestões Integrados {#built-in-suggestion-providers}

Existem alguns provedores de sugestões integrados que você pode usar:

| Provedor de Sugestão                      | Descrição                                                               |
| ----------------------------------------- | ----------------------------------------------------------------------- |
| `SuggestionProviders.SUMMONABLE_ENTITIES` | Sugere todas as entidades que podem ser invocadas.      |
| `SuggestionProviders.AVAILABLE_SOUNDS`    | Sugere todos os sons que podem ser tocados.             |
| `LootCommand.SUGGESTION_PROVIDER`         | Sugere todas as tabelas de itens que estão disponíveis. |
| `SuggestionProviders.ALL_BIOMES`          | Sugere todos os biomas que estão disponíveis.           |

## Criando um Provedor de Sugestões Personalizado {#creating-a-custom-suggestion-provider}

Se um provedor integrado não atender às suas necessidades, você pode criar seu próprio provedor de sugestões. Para fazer isso, é necessário criar uma classe que implemente a interface `SuggestionProvider` e sobrescreva o método `getSuggestions`.

Neste exemplo, criaremos um provedor de sugestões que sugere todos os nomes de usuário dos jogadores no servidor.

@[code java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/command/PlayerSuggestionProvider.java)

Para usar esse provedor de sugestões, você simplesmente passaria uma instância dele para o método `.suggests`no builder do argumento.

@[code java highlight={4} transcludeWith=:::command_with_custom_suggestions](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

@[code java transcludeWith=:::execute_command_with_custom_suggestions](@/reference/latest/src/main/java/com/example/docs/command/ExampleModCommands.java)

Obviamente, os provedores de sugestões podem ser mais complexos, pois também podem ler o contexto do comando para fornecer sugestões baseadas no estado do comando — como os argumentos que já foram fornecidos.

Isso poderia acontecer na forma de ler o inventário do jogador e sugerir itens, ou entidades que estão perto desse jogador.
