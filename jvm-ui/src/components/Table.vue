<template>
  <div class="table">
    <el-table
      :data="tableData"
      style="width: 100%"
      empty-text="no data"
    >
      <el-table-column
        prop="name"
        label="name"
        width="180">
      </el-table-column>
      <el-table-column
        prop="updateTime"
        label="updateTime">
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="prev, pager, next"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  import taskApi from "../ajax/taskApi";

  export default {
    name: "Table",
    data() {
      return {
        tableData: [],
        total: 0
      }
    },
    created() {
      this.query();
    },
    methods: {
      query() {
        taskApi.pageQuery({}, data => {
          this.tableData = data.rows;
          this.total = data.total;
        })
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
      }
    }
  }
</script>

<style scoped>

</style>
