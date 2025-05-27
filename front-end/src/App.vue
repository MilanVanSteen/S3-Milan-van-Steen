<template>
  <NavComponent />

  <div class="page-wrapper">
    <router-view />
  </div>
</template>

<script setup lang="ts">
  import NavComponent from "./components/NavComponent.vue";
</script>

<style>
.page-wrapper {
  margin-left: 3rem;
  margin-right: 3rem;
  padding: 0.5rem;       /* space around content */
  box-sizing: border-box; /* include padding inside width */
}
</style>