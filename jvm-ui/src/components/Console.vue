<template>
  <div class="console">
    <div>
      <div class="log" v-for="(item,index) in logArray">{{item}}</div>
    </div>
  </div>
</template>

<script>
  import taskApi from "../ajax/taskApi";

  export default {
    name: "Console",
    data() {
      return {
        parm: {},
        logArray: []
      }
    },
    created() {
      this.parm = this.$route.query;
      this.query();
    },
    methods:{
      query(){
       taskApi.getConsoleLog(this.parm,result => {
         if (result && result.length > 0) {
           let offset = 0;
           for (let i = 0; i < result.length; i++) {
             if (result[i].log) {
               let split = result[i].log.split("\n");
               this.logArray= this.logArray.concat(split);
               offset = result[i].offset
             }
           }
           this.parm.offset = offset;
           if (result.length >= 20) {
             this.query();
           }
         }
       })
      }
    }
  }
</script>

<style scoped>
  .log {
    text-align: left;
  }

</style>
