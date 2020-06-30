<template>
  <div id="app" v-title data-title="OvO">
    <div id="particles">
      <vue-particles
              color="#ABD2EA"
              :particleOpacity="0.7"
              :particlesNumber="50"
              shapeType="circle"
              :particleSize="5"
              linesColor="#C1E7FF"
              :linesWidth="2"
              :lineLinked="true"
              :lineOpacity="0.4"
              :linesDistance="150"
              :moveSpeed="2"
              :hoverEffect="true"
              hoverMode="grab"
              :clickEffect="true"
              clickMode="push"
              class="lizi"
              style="position: absolute;width: 100%;height: 100%;z-index: 0"
      ></vue-particles>
    </div>
    <transition name="fade-transform" mode="out-in">
      <router-view/>
    </transition>
  </div>
</template>
<script>
export default {
  components: {
    
  }
}
</script>
<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  width: 100%;
  padding: 20px 100px 144px;
  background: #F5FBFF;
  /*background: #f0f2f5 url('assets/background.png') repeat 100%;*/
  min-height: 800px
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}

/* fade-transform */
.fade-transform-leave-active,
.fade-transform-enter-active {
  transition: all .5s;
}

.fade-transform-enter {
  opacity: 0;
  transform: translateX(-30px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style>
