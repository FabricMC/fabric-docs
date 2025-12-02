<script setup lang="ts">
import { useData } from "vitepress";
import { computed } from "vue";

import { Fabric } from "../../types";

const data = useData();

const options = computed(() => (data.theme.value.version as Fabric.VersionOptions).reminder);

const version = computed(() => {
  const split = data.page.value.filePath.split("/");
  if (split[0] === "versions") return split[1];
  return data.theme.value.nav[3].props.versioningPlugin.latestVersion as string;
});

const latest = computed(() => {
  const split = options.value.latestVersion.split("%s");
  return [split[0], version.value, split.slice(1).join("%s")];
});

const old = computed(() => {
  var versionSplit = version.value.split(".");
  if (versionSplit[1] <= 21 && versionSplit[2] <= 10) {
    const split = options.value.oldVersionYarn.split("%s");
    return [split[0], version.value, split.slice(1).join("%s")];
  } else {
    const split = options.value.oldVersionMojang.split("%s");
    return [split[0], version.value, split.slice(1).join("%s")];
  }
});
</script>

<template>
  <div v-if="data.page.value.filePath.startsWith('versions/')" class="old">
    <span>
      <svg
        version="1.1"
        id="Capa_1"
        width="25%"
        xmlns="http://www.w3.org/2000/svg"
        xmlns:xlink="http://www.w3.org/1999/xlink"
        viewBox="0 0 478.125 478.125"
        xml:space="preserve"
      >
        <g>
          <g>
            <g>
              <circle stroke-width="7px" cx="239.904" cy="314.721" r="35.878" />
              <path
                stroke-width="7px"
                d="M256.657,127.525h-31.9c-10.557,0-19.125,8.645-19.125,19.125v101.975c0,10.48,8.645,19.125,19.125,19.125h31.9
              c10.48,0,19.125-8.645,19.125-19.125V146.65C275.782,136.17,267.138,127.525,256.657,127.525z"
              />
              <path
                stroke-width="7px"
                d="M239.062,0C106.947,0,0,106.947,0,239.062s106.947,239.062,239.062,239.062c132.115,0,239.062-106.947,239.062-239.062
              S371.178,0,239.062,0z M239.292,409.734c-94.171,0-170.595-76.348-170.595-170.596c0-94.248,76.347-170.595,170.595-170.595
              s170.595,76.347,170.595,170.595C409.887,333.387,333.464,409.734,239.292,409.734z"
              />
            </g>
          </g>
        </g>
      </svg>
    </span>
    <p>
      {{ old[0] }}<b>{{ old[1] }}</b
      >{{ old[2] }}
    </p>
  </div>
  <div v-else class="latest">
    <span>
      <svg
        version="1.1"
        id="Capa_1"
        xmlns="http://www.w3.org/2000/svg"
        xmlns:xlink="http://www.w3.org/1999/xlink"
        width="800px"
        height="800px"
        viewBox="0 0 469.184 469.185"
        xml:space="preserve"
      >
        <g>
          <path
            d="M462.5,96.193l-21.726-21.726c-8.951-8.95-23.562-8.95-32.59,0L180.368,302.361l-119.34-119.34
            c-8.95-8.951-23.562-8.951-32.589,0L6.712,204.747c-8.95,8.951-8.95,23.562,0,32.589L163.997,394.62
            c4.514,4.514,10.327,6.809,16.218,6.809s11.781-2.295,16.219-6.809L462.27,128.783C471.45,119.68,471.45,105.145,462.5,96.193z"
          />
        </g>
      </svg>
    </span>
    <p>
      {{ latest[0] }}<b>{{ latest[1] }}</b
      >{{ latest[2] }}
    </p>
  </div>
</template>

<style scoped>
div {
  align-items: center;
  border-radius: 8px;
  display: flex;
  flex-direction: row;
  gap: 16px;
  margin-top: 16px;
  padding: 16px;
  white-space: pre-wrap;
}

span {
  width: 48px;

  svg {
    display: block;
    height: 100%;
    width: 100%;
  }
}

.latest {
  background-color: var(--vp-custom-block-tip-bg);
  color: var(--vp-custom-block-tip-text);

  svg {
    fill: var(--vp-c-tip-1);
  }
}

.old {
  background-color: var(--vp-custom-block-warning-bg);
  color: var(--vp-custom-block-warning-text);

  svg {
    fill: var(--vp-c-warning-1);
  }
}

@media (min-width: 1280px) {
  .content-container > div {
    display: none;
  }

  span {
    height: 20%;
    width: 20%;
  }
}
</style>
