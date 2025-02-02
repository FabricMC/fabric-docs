---
title: Parametri dei Comandi
description: Impara come creare comandi con parametri complessi.
---

La maggior parte dei comandi usa i parametri. A volte possono essere opzionali, il che significa che se non viene fornito quel parametri, il comando verrà eseguito comunque. Ogni nodo può avere tipi di parametri multipli, ma ricorda che c'è una possibilità di ambiguità, che dovrebbe essere evitata.

@[code lang=java highlight={3} transcludeWith=:::command_with_arg](@/reference/1.21/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_command_with_arg](@/reference/1.21/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

In questo caso, dopo il testo del comando `/command_with_arg`, dovresti scrivere un intero. Per esempio, se eseguissi `/command_with_arg 3`, otterresti come risposta il messaggio:

> Called /command_with_arg with value = 3

Se scrivessi `/command_with_arg` senza parametri, non sarebbe possibile fare il parsing del comando correttamente.

Dopo di che aggiungiamo un secondo parametro opzionale:

@[code lang=java highlight={3,5} transcludeWith=:::command_with_two_args](@/reference/1.21/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_command_with_two_args](@/reference/1.21/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Ora puoi scrivere uno oppure due interi. Se fornisci un intero, un testo di feedback con un singolo valore verrà stampato. Se fornisci due interi, un testo di feedback con due interi verrà stampato.

Potresti pensare che sia inutile specificare esecuzioni simili due volte. Per cui possiamo creare un metodo che verrà usato in entrambe le esecuzioni.

@[code lang=java highlight={4,6} transcludeWith=:::command_with_common_exec](@/reference/1.21/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java transcludeWith=:::execute_common](@/reference/1.21/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

## Tipi di Parametri Personalizzati {#custom-argument-types}

Se vanilla non fornisce il tipo di parametro che ti serve, puoi creartene uno. Per fare questo, hai bisogno di creare una classe che implementa l'interfaccia `ArgumentType<T>` dove `T` è il tipo del parametro.

Avrai bisogno d'implementare il metodo `parse`, che farà il parsing della stringa in input nel tipo desiderato.

Per esempio, puoi creare un tipo di parametro che fa il parsing di un `BlockPos` da una stringa con il formato seguente: `{x, y, z}`

@[code lang=java transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/command/BlockPosArgumentType.java)

### Registrare i Tipi di Parametri Personalizzati {#registering-custom-argument-types}

:::warning
Avrai bisogno di registrare i tipi di parametri personalizzati sia sul server sia sul client altrimenti il comando non funzionerà!
:::

Puoi registrare il tuo tipo di parametro personalizzato nel metodo `onInitialize` dell'[inizializer della tua mod](./getting-started/project-structure#entrypoints) usando la classe `ArgumentTypeRegistry`:

@[code lang=java transcludeWith=:::register_custom_arg](@/reference/1.21/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

### Usare i Tipi di Parametri Personalizzati {#using-custom-argument-types}

Possiamo usare il nostro tipo di parametro personalizzato in un comando - passando un'istanza di esso nel metodo `.argument` del costruttore del comando.

@[code lang=java highlight={3} transcludeWith=:::custom_arg_command](@/reference/1.21/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)
@[code lang=java highlight={2} transcludeWith=:::execute_custom_arg_command](@/reference/1.21/src/main/java/com/example/docs/command/FabricDocsReferenceCommands.java)

Eseguendo il comando possiamo testare se il tipo di parametro funziona o meno:

![Parametro non valido](/assets/develop/commands/custom-arguments_fail.png)

![Parametro valido](/assets/develop/commands/custom-arguments_valid.png)

![Risultato del Comando](/assets/develop/commands/custom-arguments_result.png)
