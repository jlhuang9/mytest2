import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Chart from '@/components/Chart'
import Table from '@/components/Table'
import TaskTable from '@/components/TaskTable'
import Console from '@/components/Console'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/Home',
      name: 'Home',
      component: Home
    },
    {
      path: '/Chart',
      name: 'Chart',
      component: Chart
    },
    {
      path: '/Table',
      name: 'Table',
      component: Table
    },
    {
      path: '/TaskTable',
      name: 'TaskTable',
      component: TaskTable
    },
    {
      path: '/Console',
      name: 'Console',
      component: Console
    }
  ]
})
