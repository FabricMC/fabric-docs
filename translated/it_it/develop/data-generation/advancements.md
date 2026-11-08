---
title: Generazione di Progressi
description: Una guida per configurare la generazione di progressi con datagen.
authors:
  - cassiancc
  - CelDaemon
  - MarcusElg
  - MattiDragon
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
---

<!---->

:::info PREREQUISITI

Assicurati di aver prima completato il processo di [configurazione della datagen](./setup).

:::

## Configurazione {#setup}

Anzitutto, dobbiamo creare il nostro fornitore. Crea una classe che estenda `FabricAdvancementProvider` e compilane i metodi di base:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_provider_start

Per completare la configurazione, aggiungi questo fornitore alla tua `DataGeneratorEntrypoint` nel metodo `onInitializeDataGenerator`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_advancements_register

## Struttura dei Progressi {#advancement-structure}

Un progresso è composto di alcune componenti diverse. Assieme ai requisiti, detti "criterio", potrebbe avere:

- Un oggetto `DisplayInfo` che dica al gioco come mostrare il progresso ai giocatori,
- `AdvancementRequirements`, ovvero liste di liste di criteri, che richiedono che almeno un criterio di ogni sotto-lista sia soddisfatto,
- `AdvancementRewards`, che il giocatore riceverà per aver completato il progresso,
- Una `Strategy`, che informa il progresso su come gestire criteri multipli, e
- Un `Advancement` genitore, che organizza la gerarchia che vedi nella schermata "Progressi".

## Progressi Semplici {#simple-advancements}

Ecco un semplice progresso per aver ottenuto un blocco di terra:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_simple_advancement

:::details Output JSON

<<< @/reference/latest/src/main/generated/data/example-mod/advancement/get_dirt.json

:::

## Genitori {#parents}

Per creare o estendere un albero di progressi, è possibile impostare un genitore del nostro progressi. Per fare ciò, chiama `Advancement.Builder#parent(...)` passando il riferimento al progresso genitore.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reference_parent

Se non è disponibile un riferimento diretto al progresso genitore (per esempio se esso fosse un progresso vanilla), si può passare un `Identifier` come segnaposto.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#placeholder_parent

I tuoi progressi dovrebbero ora essere mostrati come albero nel menu apposito.

![Albero dei progressi](/assets/develop/data-generation/advancement_tree.png)

## Criteri multipli {#multiple-criteria}

Per predisporre condizioni più avanzate per un progresso, è possibile chiamare `Advancement.Builder#addCriteria(...)` più volte, con criteri ulteriori.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#multiple_criteria

Affinché questo progresso sia completato, sarà ora necessario che tutti i criteri vengano soddisfatti. Questo è il comportamento predefinito, ma è anche possibile modificarlo scegliendo un'altra strategia.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#requirements_strategy

## Premi {#rewards}

È possibile attribuire premi a determinati progressi, ed essi saranno conferiti al giocatore che li completa. Per fare ciò, chiamiamo `Advancement.Builder#rewards(...)` elencando i premi desiderati.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#experience_reward

Sono disponibili altri tipi di premi, eccone alcuni:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reward_types

## Criteri Personalizzati {#custom-criteria}

::: warning

Anche se la datagen può avvenire lato client, i `Criterion` e i `Predicate` sono nell'insieme di codice main (entrambi i lati), poiché il server ne ha bisogno per innescarli e valutarli.

:::

### Definizioni {#definitions}

Un **criterio** (in inglese _criterion_, plurale _criteria_) è qualcosa che un giocatore può fare (o che succede a un giocatore) e che può essere considerata per quanto riguarda un progresso. Nel gioco ci sono già vari [criteri](https://minecraft.wiki/w/Advancement_definition#List_of_triggers), che si possono trovare nel package `net.minecraft.advancements.criterion`. In genere dovrai aggiungere un nuovo criterio solo se devi implementare una meccanica personalizzata nel gioco.

Le **condizioni** sono valutate dai criteri. Un criterio viene preso in considerazione solo se tutte le condizioni rilevanti sono soddisfatte. Le condizioni di solito si esprimono come predicati.

Un **predicato** è qualcosa che accetta un valore e restituisce un `boolean`. Per esempio, un `Predicate<Item>` potrebbe restituire `true` se l'oggetto è un diamante, mentre un `Predicate<LivingEntity>` potrebbe restituire `true` se l'entità non è ostile ai villici.

### Creare Criteri Personalizzati {#creating-custom-criteria}

Anzitutto ci serve una meccanica da implementare. Informiamo il giocatore riguardo a quale utensile ha usato ogni volta che rompe un blocco.

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_entrypoint

Nota che questo è del codice molto brutto. La `HashMap` non viene memorizzata persistentemente, quindi sarà resettata ad ogni riavvio del gioco. È solo per mostrare i `Criterion`. Avvia il gioco e provalo!

Ora, creiamo il nostro criterio personalizzato, `UseToolCriterion`. Avrà bisogno di una sua classe `Conditions`, quindi creeremo entrambe insieme:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_criterion_base

Wow, questo è un sacco! Analizziamolo poco per volta.

- `UseToolCriterion` è un `SimpleCriterionTrigger`, al quale si possono applicare delle `Conditions`.
- `Conditions` ha un attributo `playerPredicate`. Tutte le `Conditions` dovrebbero avere un predicato del giocatore (tecnicamente un `LootContextPredicate`).
- `Conditions` ha anche un `CODEC`. Questo `Codec` è semplicemente il codec per il suo unico attributo, `playerPredicate`, con istruzioni aggiuntive per convertirlo tra di essi (`xmap`).

::: info

Per saperne di più sui codec, controlla la pagina [Codec](../serialization/codecs).

:::

Ci serve un modo per controllare se le condizioni sono soddisfatte. Aggiungiamo un metodo ausiliare a `Conditions`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_conditions_test

Ora che abbiamo un criterio e le sue condizioni, ci serve un modo per innescarlo. Aggiungi un metodo d'innesco a `UseToolCriterion`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen_advancements_criterion_trigger

Ci siamo quasi! Ora, ci serve un'istanza del nostro criterio con cui lavorare. Mettiamola in una nuova classe, chiamata `ModCriteria`, mediante a un metodo ausiliario che facilita la registrazione.

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_mod_criteria

Per assicurarci che i nostri criteri siano inizializzati al tempo giusto, aggiungi un metodo vuoto `init`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_mod_criteria_init

E chiamalo nell'initializer della tua mod:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_call_init

Infine, dobbiamo innescare il nostro criterio. Aggiungi questo a dove inviamo un messaggio al giocatore nella classe main della mod.

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_trigger_criterion

Il tuo criterio nuovo e luccicante è ora pronto per l'uso! Aggiungiamolo al nostro fornitore:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_custom_criteria_advancement

Esegui l'operazione di datagen di nuovo, e avrai con te un nuovo progresso con cui giocare!

## Condizioni con Parametri {#conditions-with-parameters}

Tutto questo è bello e tutto, ma, e se volessimo solo concedere il progresso dopo aver fatto qualcosa 5 volte? E perché non concederne un altro dopo 10 volte? Per questo dovremo dare alla nostra condizione un parametro. Puoi attenerti a `UseToolCriterion`, o andare avanti qui con un nuovo `ParameterizedUseToolCriterion`. Nella pratica dovresti solo avere quello parametrizzato, ma li terremo entrambi per questo tutorial.

Lavoriamo dal basso verso l'alto. Dovremo verificare se i requisiti sono soddisfatti, quindi modifichiamo il nostro metodo `Conditions#requirementsMet`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_requirements_met

`requiredTimes` non esiste, quindi rendilo un parametro di `Conditions`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_parameter

Ora il nostro codec dà errore. Scriviamo un nuovo codec per le nuove modifiche:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_codec

Andando avanti, dobbiamo ora aggiustare il nostro metodo `trigger`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen_advancements_new_trigger

Se hai creato un nuov criterio, dobbiamo aggiungerlo a `ModCriteria`

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen_advancements_new_mod_criteria

E chiamarlo nella nostra classe principale, proprio dove c'è quello vecchio:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen_advancements_trigger_new_criterion

Aggiungi il progresso al tuo fornitore:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_new_custom_criteria_advancement

Esegui nuovamente la datagen, e hai finalmente finito!

## Risorse condizionali {#resource-conditions}

Per applicare una [condizione per risorse](../resource-conditions) a un progresso generato, racchiudi il consumer in `withConditions` e fornisci le condizioni desiderate. Questo permetterà di generare il progresso al quale sono applicate le condizioni:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen_advancements_conditions
