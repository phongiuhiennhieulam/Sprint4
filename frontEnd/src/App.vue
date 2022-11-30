<template>
  <div id="app">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
    <TheHeader v-if="loggedIn" />
    <the-content>
      <router-view />
    </the-content>
  </div>
</template>
<script>


export default {
  name: "App",
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },

}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: hidden;
  background: linear-gradient(90deg, #eddaca 0%, rgba(255, 255, 255, 0) 100%), #ebd5e0;

}
</style>
