<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备" prop="deviceName">
        <el-input
          v-model="queryParams.deviceName"
          placeholder="请输入设备名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="编号" prop="deviceNo">
        <el-input
          v-model="queryParams.deviceNo"
          placeholder="请输入设备编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['device:fault:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['device:fault:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="faultList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="设备" align="center" prop="deviceName" />
      <el-table-column label="编号" align="center" prop="deviceNo" />
      <el-table-column label="节点" align="center" prop="nodeId" />
      <el-table-column label="报警" align="center" prop="alarmInfo" />
      <el-table-column label="时间" align="center" prop="faultTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.faultTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="组别" align="center" prop="groupName" />
      <el-table-column label="类别" align="center" prop="deviceType" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['device:fault:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['device:fault:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
          >查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listFault, getFault, delFault, addFault, updateFault } from "@/api/device/fault";

export default {
  name: "Fault",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 故障表格数据
      faultList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deviceName: undefined,
        deviceNo: undefined
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询故障列表 */
    getList() {
      this.loading = true;
      listFault(this.queryParams).then(response => {
        this.faultList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.faultId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push({ path: '/device/fault/add' })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.$router.push({ path: '/device/fault/edit', query: { faultId: row.faultId }})
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.$router.push({ path: '/device/fault/detail', query: { faultId: row.faultId }})
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const faultIds = row.faultId || this.ids;
      this.$modal.confirm('是否确认删除故障编号为"' + faultIds + '"的数据项？').then(function() {
        return delFault(faultIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script> 