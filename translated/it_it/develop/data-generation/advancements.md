---
title: Generazione di Progressi
description: Una guida per configurare la generazione di progressi con datagen.
authors:
  - MattiDragon
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

:::info PREREQUISITI
Assicurati di aver prima completato il processo di [configurazione della datagen](./setup).
:::

## Configurazione {#setup}

Anzitutto, dobbiamo creare il nostro fornitore. Crea una classe che `extends FabricAdvancementProvider` e compilane i metodi di base:

@[code lang=java transcludeWith=:::datagen-advancements:provider-start](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceAdvancementProvider.java)

Per completare la configurazione, aggiungi questo fornitore alla tua `DataGeneratorEntrypoint` nel metodo `onInitializeDataGenerator`.

@[code lang=java transclude={26-26}](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

## Struttura dei Progressi {#advancement-structure}

Un progresso è composto di alcune componenti diverse. Assieme ai requisiti, detti "criterio", potrebbe avere:

- Un `AdvancementDisplay` che dice al gioco come mostrare il progresso ai giocatori,
- `AdvancementRequirements`, ovvero liste di liste di criteri, che richiedono che almeno un criterio di ogni sotto-lista sia soddisfatto,
- `AdvancementRewards`, che il giocatore riceverà per aver completato il progresso,
- Un `CriterionMerger`, che informa il progresso su come gestire criteri multipli, e
- Un `Advancement` genitore, che organizza la gerarchia che vedi nella schermata "Progressi".

## Progressi Semplici {#simple-advancements}

Ecco un semplice progresso per aver ottenuto un blocco di terra:

@[code lang=java transcludeWith=:::datagen-advancements:simple-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceAdvancementProvider.java)

:::warning
Nel costruire le voci del tuo progresso, ricorda che la funzione accetta l'`Identifier` del progresso nel formato `String`!
:::

:::details Output JSON
@[code lang=json](@/reference/latest/src/main/generated/data/fabric-docs-reference/advancement/get_dirt.json)
:::

## Un Altro Esempio Ancora {#one-more-example}

Solo per capirne il funzionamento, aggiungiamo un altro progresso. Faremo pratica con l'aggiunta di ricompense, l'uso di criterio multiplo, e l'assegnazione di genitori:

@[code lang=java transcludeWith=:::datagen-advancements:second-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceAdvancementProvider.java)

## Criteri Personalizzati {#custom-criteria}

:::warning
Anche se la datagen può avvenire lato client, i `Criterion` e i `Predicate` sono nell'insieme di codice main (entrambi i lati), poiché il server ne ha bisogno per innescarli e valutarli.
:::

### Definizioni {#definitions}

Un **criterio** (in inglese _criterion_, plurale _criteria_) è qualcosa che un giocatore può fare (o che succede a un giocatore) e che può essere considerata per quanto riguarda un progresso. Nel gioco ci sono già vari [criteri](https://minecraft.wiki/w/Advancement_definition#List_of_triggers), che si possono trovare nel package `net.minecraft.advancement.criterion`. In genere dovrai aggiungere un nuovo criterio solo se devi implementare una meccanica personalizzata nel gioco.

Le **condizioni** sono valutate dai criteri. Un criterio viene preso in considerazione solo se tutte le condizioni rilevanti sono soddisfatte. Le condizioni di solito si esprimono come predicati.

Un **predicato** è qualcosa che prende un valore e restituisce un `boolean`. Per esempio, un `Predicate<Item>` potrebbe restituire `true` se l'oggetto è un diamante, mentre un `Predicate<LivingEntity>` potrebbe restituire `true` se l'entità non è ostile ai villici.

### Creare Criteri Personalizzati {#creating-custom-criteria}

Anzitutto ci serve una meccanica da implementare. Informiamo il giocatore riguardo a quale utensile ha usato ogni volta che rompe un blocco.

@[code lang=java transcludeWith=:::datagen-advancements:entrypoint](@/reference/latest/src/main/java/com/example/docs/advancement/FabricDocsReferenceDatagenAdvancement.java)

Nota che questo è del codice molto brutto. La `HashMap` non viene memorizzata persistentemente, quindi sarà resettata ad ogni riavvio del gioco. È solo per mostrare i `Criterion`. Avvia il gioco e provalo!

Ora, creiamo il nostro criterio personalizzato, `UseToolCriterion`. Avrà bisogno di una sua classe `Conditions`, quindi creeremo entrambe insieme:

@[code lang=java transcludeWith=:::datagen-advancements:criterion-base](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Wow, questo è un sacco! Analizziamolo poco per volta.

- `UseToolCriterion` è un `AbstractCriterion`, al quale si possono applicare delle `Conditions`.
- `Conditions` ha un attributo `playerPredicate`. Tutte le `Conditions` dovrebbero avere un predicato del giocatore (tecnicamente un `LootContextPredicate`).
- `Conditions` ha anche un `CODEC`. Questo `Codec` è semplicemente il codec per il suo unico attributo, `playerPredicate`, con istruzioni aggiuntive per convertirlo tra di essi (`xmap`).

:::info
Per saperne di più sui codec, controlla la pagina [Codec](../codecs).
:::

Ci serve un modo per controllare se le condizioni sono soddisfatte. Aggiungiamo un metodo ausiliare a `Conditions`:

@[code lang=java transcludeWith=:::datagen-advancements:conditions-test](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Ora che abbiamo un criterio e le sue condizioni, ci serve un modo per innescarlo. Aggiungi un metodo d'innesco a `UseToolCriterion`:

@[code lang=java transcludeWith=:::datagen-advancements:criterion-trigger](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Ci siamo quasi! Ora, ci serve un'istanza del nostro criterio con cui lavorare. Mettiamola in una nuova classe, chiamata `ModCriteria`.

@[code lang=java transcludeWith=:::datagen-advancements:mod-criteria](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

Per assicurarci che i nostri criteri siano inizializzati al tempo giusto, aggiungi un metodo vuoto `init`:

@[code lang=java transcludeWith=:::datagen-advancements:mod-criteria-init](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

E chiamalo nell'initializer della tua mod:

@[code lang=java transcludeWith=:::datagen-advancements:call-init](@/reference/latest/src/main/java/com/example/docs/advancement/FabricDocsReferenceDatagenAdvancement.java)

Infine, dobbiamo innescare i nostri criteri. Aggiungi questo a dove inviamo un messaggio al giocatore nella classe main della mod.

@[code lang=java transcludeWith=:::datagen-advancements:trigger-criterion](@/reference/latest/src/main/java/com/example/docs/advancement/FabricDocsReferenceDatagenAdvancement.java)

Il tuo criterio nuovo e luccicante è ora pronto per l'uso! Aggiungiamolo al nostro fornitore:

@[code lang=java transcludeWith=:::datagen-advancements:custom-criteria-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceAdvancementProvider.java)

Esegui l'operazione di datagen di nuovo, e avrai con te un nuovo progresso con cui giocare!

## Condizioni con Parametri {#conditions-with-parameters}

Tutto questo è bello e tutto, ma, e se volessimo solo concedere il progresso dopo aver fatto qualcosa 5 volte? E perché non concederne un altro dopo 10 volte? Per questo dovremo dare alla nostra condizione un parametro. Puoi attenerti a `UseToolCriterion`, o andare avanti qui con un nuovo `ParameterizedUseToolCriterion`. Nella pratica dovresti solo avere quello parametrizzato, ma li terremo entrambi per questo tutorial.

Lavoriamo dal basso verso l'alto. Dovremo verificare se i requisiti sono soddisfatti, quindi modifichiamo il nostro metodo `Conditions#requirementsMet`:

@[code lang=java transcludeWith=:::datagen-advancements:new-requirements-met](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

`requiredTimes` non esiste, quindi rendilo un parametro di `Conditions`:

@[code lang=java transcludeWith=:::datagen-advancements:new-parameter](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

Ora il nostro codec dà errore. Scriviamo un nuovo codec per le nuove modifiche:

@[code lang=java transcludeWith=:::datagen-advancements:new-codec](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

Andando avanti, dobbiamo ora aggiustare il nostro metodo `trigger`:

@[code lang=java transcludeWith=:::datagen-advancements:new-trigger](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

Se hai creato un nuov criterio, dobbiamo aggiungerlo a `ModCriteria`

@[code lang=java transcludeWith=:::datagen-advancements:new-mod-criteria](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

E chiamarlo nella nostra classe principale, proprio dove c'è quello vecchio:

@[code lang=java transcludeWith=:::datagen-advancements:trigger-new-criterion](@/reference/latest/src/main/java/com/example/docs/advancement/FabricDocsReferenceDatagenAdvancement.java)

Aggiungi il progresso al tuo fornitore:

@[code lang=java transcludeWith=:::datagen-advancements:new-custom-criteria-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceAdvancementProvider.java)

Esegui nuovamente la datagen, e hai finalmente finito!
