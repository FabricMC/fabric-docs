---
title: Translation Guidelines
description: General guidelines regarding the localization of the Fabric Docs via Crowdin.
authors:
  - IMB11
  - its-miroma
resources:
  https://crowdin.com/project/fabricmc: Fabric Project on Crowdin
editLink: false
---

<!-- markdownlint-configure-file { MD033: { allowed_elements: [Icon, script, style, VPLink] } } -->

<script setup lang="ts">
import { Icon } from "@iconify/vue";
import { useData } from "vitepress";
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";
import { computed } from "vue";

const data = useData();

const href = computed(() =>
  data.localeIndex.value === "root"
    ? Object.keys(data.frontmatter.value).at(0)
    : data.theme.value.editLink!.pattern!.replace(":path", data.page.value.filePath)
);
const text = computed(() => data.theme.value.editLink!.text!.replace("GitHub", "Crowdin"));
</script>

Thank you for your interest in translating the Fabric Docs to your language!

::: warning IMPORTANT

The Fabric Team does not generally accept localization contributions except through Crowdin.

Any other contribution, including changes to a page, should be applied to the English version first.

:::

## Correcting a Mistake {#correcting-a-mistake}

<!-- TODO: localize screenshots -->

Let's say you speak Italian, and while reading a page, you came across a spelling mistake:

![A paragraph in Italian with a spelling mistake: "legera" should be "leggera"](/assets/contributing/lang-spelling-mistake.png)

To fix it, you should visit the Crowdin project. Click the Edit button at the bottom of the page:

<VPLink :href no-icon>
  <Icon icon="lucide:edit" />
  {{ text }}
</VPLink>

::: tip

You may have to sign up for a Crowdin account if you don't have one yet.

:::

Then, select the file you want to modify:

![Opening the file on Crowdin](/assets/contributing/crowdin-open-file.png)

On the Preview tab you'll see what the page currently looks like, and you can find the string you want to correct there:

![Preview of the file with an error on Crowdin](/assets/contributing/crowdin-preview-with-error.png)

Suggest the correction on the right side, then save your suggestion:

![Correction submitted on Crowdin](/assets/contributing/crowdin-corrected-suggestion.png)

Now, you have to wait. Translations are pulled once a week, on Saturday at 7:37 UTC. When that happens, you'll see a new PR opened on GitHub containing changes from the week, including your correction. Here's an [example of a localization PR](https://github.com/FabricMC/fabric-docs/pull/546):

![Translation PR on GitHub](/assets/contributing/github-l10n-pr.png)

Finally, when someone from the Docs Team approves the PR and merges it, you'll see your correction live on the website.

## Translating a New Page {#translating-a-new-page}

To translate an entire page anew, you should follow a similar process: open the [Crowdin project](https://crowdin.com/project/fabricmc), and select your language:

![Selecting the language Italian on Crowdin](/assets/contributing/crowdin-select-lang.png)

Find the file you want to translate, suggest translations for **all its sentences**, and wait for them to be merged to the main site.

## Frequently Asked Questions {#faq}

- **Q: Can I translate on GitHub?**

  A: No, translations through GitHub will not be accepted.

- **Q: Can I translate only parts of a file?**

  A: You can translate as much or as little as you like. However, note that Crowdin will only sync a page once all of its strings are translated.

- **Q: Can I translate using LLMs or other forms of AI?**

  A: No, automated translations are unreliable, and we value the human touch more!

- **Q: Can I translate code blocks?**

  A: No, code blocks and snippets are excluded from translation.

- **Q: Should I translate class or field names?**

  A: No, please use the English names. You might want to explain the meaning at their first occurrence.

- **Q: What about code comments?**

  A: Unfortunately, comments cannot be localized at this time.

- **Q: Which files should be prioritized?**

  A: Follow the priority indicators on Crowdin. In particular, you'll need to translate the home page for the language to appear on the website.

- **Q: I cannot find my language on Crowdin.**

  A: Please inform us with a message on the **#docs** channel on the [Discord server](https://discord.fabricmc.net/).

<style>
.vp-doc > div > .VPLink {
  display: flex;
  align-items: center;
  font-size: 14px;
  text-decoration: none;
  width: fit-content;
  margin-left: auto;
  margin-right: auto;

  svg {
    margin-right: 8px;
  }
}
</style>
