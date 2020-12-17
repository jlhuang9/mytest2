<template>
  <div id="app">
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect"
             :router="true"
             background-color="#384252"
             text-color="#fff"
             active-text-color="#ffd04b">
      <el-menu-item index="Home">home</el-menu-item>
      <el-menu-item index="Table">workspace</el-menu-item>
      <el-menu-item index="TaskTable">task</el-menu-item>
      <el-menu-item index="Chart">chart</el-menu-item>
    </el-menu>
    <router-view/>
  </div>
</template>

<script>

export default {
  name: 'App',
  data() {
    return {
      activeIndex: '1',
      activeIndex2: '1'
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
