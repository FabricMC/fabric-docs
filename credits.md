---
title: Credits
description: A sincere THANK YOU to everyone involved in creating the Fabric Documentation!
layout: page
authors:
  - its-miroma
---

<!-- markdownlint-disable-file MD033 -->

<script setup lang="ts">
import { useData } from "vitepress";
import {
  VPTeamMembers,
  VPTeamPage,
  VPTeamPageSection,
  VPTeamPageTitle,
} from "vitepress/theme";
import { computed } from "vue";

import { data as _authors } from "/.vitepress/data/authors.data";
import { data as _committers } from "/.vitepress/data/committers.data";
import { data as _maintainers } from "/.vitepress/data/maintainers.data";
import { data as _translators } from "/.vitepress/data/translators.data";
import { Fabric } from "/.vitepress/types";

const FALLBACK_AVATAR = "/assets/avatater.png";

const data = useData();
const options = computed(
  () => data.theme.value.credits as Fabric.CreditsOptions
);

const authors = _authors.map((author) => ({
  ...author,
  avatar: author.avatar || FALLBACK_AVATAR,
  title:
    author.number === 1
      ? options.value.authors.description.singular
      : options.value.authors.description.plural.replace(
          "%d",
          author.number.toString()
        ),
}));

const committers = _committers.map((committer) => ({
  ...committer,
  avatar: committer.avatar || FALLBACK_AVATAR,
  title:
    committer.number === 1
      ? options.value.committers.description.singular
      : options.value.committers.description.plural.replace(
          "%d",
          committer.number.toString()
        ),
}));

const maintainers = _maintainers;

const translators = _translators.map((translator) => ({
  ...translator,
  avatar: translator.avatar || FALLBACK_AVATAR,
  title:
    translator.number === 1
      ? options.value.translators.description.singular
      : options.value.translators.description.plural.replace(
          "%d",
          translator.number.toString()
        ),
}));
</script>

<VPTeamPage>
  <VPTeamPageTitle>
    <template #title>{{ $frontmatter.title }}</template>
    <template #lead>{{ $frontmatter.description }}</template>
  </VPTeamPageTitle>
  <VPTeamPageSection v-if="maintainers.length">
    <template #title>{{ options!.maintainers.title }}</template>
    <template #members>
      <VPTeamMembers :members="maintainers" />
    </template>
  </VPTeamPageSection>
  <VPTeamPageSection v-if="committers.length">
    <template #title>{{ options!.committers.title }}</template>
    <template #members>
      <VPTeamMembers :members="committers" size="small" />
    </template>
  </VPTeamPageSection>
  <VPTeamPageSection v-if="authors.length">
    <template #title>{{ options!.authors.title }}</template>
    <template #members>
      <VPTeamMembers :members="authors" size="small" />
    </template>
  </VPTeamPageSection>
  <VPTeamPageSection v-if="translators.length">
    <template #title>{{ options!.translators.title }}</template>
    <template #members>
      <VPTeamMembers :members="translators" size="small" />
    </template>
  </VPTeamPageSection>
</VPTeamPage>
