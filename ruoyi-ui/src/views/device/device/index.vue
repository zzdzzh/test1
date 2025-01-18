<template>
    <div class="app-container">
      <el-row :gutter="20">
        <el-col :span="24">
          <div class="head-container">
            <el-button type="primary" @click="handleAdd">新增</el-button>
            <el-button type="info" @click="handleGroup">分组</el-button>
            <el-button type="warning" @click="handleStrategy">策略</el-button>
            <el-input
              v-model="searchQuery"
              placeholder="搜索..."
              clearable
              @keyup.enter.native="handleQuery"
              style="width: 240px; margin-left: 20px;"
            />
          </div>
          <el-table :data="deviceList" style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" />
            <el-table-column prop="name" label="名称" />
            <el-table-column prop="code" label="编号" />
            <el-table-column prop="status" label="状态" />
            <el-table-column prop="createTime" label="时间" />
            <el-table-column prop="groupId" label="组别" />
            <el-table-column prop="category" label="类别" />
            <el-table-column prop="terminal" label="终端" />
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
                <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <pagination
            v-show="total > 0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />
        </el-col>
      </el-row>
    </div>
  </template>
  
  <script>
  import { listDevices, addDevice, updateDevice, deleteDevice } from '@/api/device/device'; // 引入API
  
  export default {
    data() {
      return {
        searchQuery: '',
        deviceList: [],
        total: 0,
        queryParams: {
          pageNum: 1,
          pageSize: 10,
        },
      };
    },
    methods: {
      handleAdd() {
        // 新增设备逻辑
        this.$router.push({ path: '/device/add' });
      },
      handleGroup() {
        // 分组逻辑
      },
      handleStrategy() {
        // 策略逻辑
      },
      handleQuery() {
        // 搜索逻辑
        this.getList();
      },
      handleEdit(row) {
        // 编辑设备逻辑
        this.$router.push({ path: `/device/edit/${row.id}` });
      },
      handleDelete(row) {
        this.$confirm('确认删除该设备吗?', '提示', {
          type: 'warning'
        }).then(() => {
          deleteDevice(row.id).then(() => {
            this.$message.success('删除成功');
            this.getList();
          });
        }).catch(() => {});
      },
      handleSelectionChange(selection) {
        // 选中项变化逻辑
      },
      getList() {
        listDevices(this.queryParams).then(response => {
          this.deviceList = response.rows;
          this.total = response.total;
        });
      },
    },
    created() {
      this.getList();
    },
  };
  </script>
  
  <style scoped>
  .head-container {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
  }
  </style>