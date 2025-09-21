<script setup lang="ts">
import { useData, useRoute } from "vitepress";
import { computed } from "vue";

const data = useData();
const route = useRoute();
const LATEST = "1.21.8";

const path = computed(() => route.path);
const text = computed(() => data.theme.value.version.reminder as string);

const version = computed(() => {
  const mcRegex = /^\d+\.\d+(\.\d+)*$/;
  const localeRegex = /^[a-z]{2}_[a-z]{2}$/;

  const segments = path.value.split("/");
  const firstSegment = segments[1] ?? "";
  const secondSegment = segments[2] ?? "";

  if (mcRegex.test(firstSegment)) {
    return firstSegment;
  } else if (localeRegex.test(firstSegment) && mcRegex.test(secondSegment)) {
    return secondSegment;
  } else {
    return LATEST;
  }
});
</script>

<template>
  <br class="before-version-reminder" />
  <div :class="`version-reminder ${version !== LATEST ? 'old-version' : ''}`">
    <div class="icon">
      <svg
        v-if="version !== LATEST"
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
      <svg
        v-else
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
    </div>
    <div class="mobile-wrapper">
      <p>{{ text }}</p>
      <p class="version">
        <strong>{{ version }}</strong>
      </p>
    </div>
  </div>
  <br class="after-version-reminder" />
</template>

<style scoped>
.version-reminder {
  border: 1px solid transparent;
  border-radius: 8px;
  padding: 16px;
  border-color: var(--vp-custom-block-tip-border);
  color: var(--vp-custom-block-tip-text);
  background-color: var(--vp-custom-block-tip-bg);
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 16px;
}

.old-version {
  border-color: var(--vp-custom-block-warning-border);
  color: var(--vp-custom-block-warning-text);
  background-color: var(--vp-custom-block-warning-bg);
}

.old-version > .icon > svg {
  fill: var(--vp-c-warning-1) !important;
}

.icon {
  width: 20%;
  height: 20%;
}

svg {
  display: block;
  width: 100%;
  height: 100%;
  fill: var(--vp-c-tip-1);
}

p {
  margin-top: 0;
  font-size: small;
}

p.larger {
  font-size: larger;
}

p.version {
  font-size: larger;
}

@media (min-width: 1280px) {
  .content-container > .version-reminder,
  .content-container > .after-version-reminder,
  .before-version-reminder {
    display: none;
  }
}

@media (max-width: 1279px) {
  .icon {
    width: 28px;
    height: 28px;
  }

  p.version {
    font-size: small;
  }

  .mobile-wrapper {
    display: flex;
    flex-direction: row;
    gap: 8px;
  }
}
</style>
