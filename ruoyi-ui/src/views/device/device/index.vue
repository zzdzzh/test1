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

      <!-- 添加设备对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="名称" prop="name">
                <el-input v-model="form.name" placeholder="请输入设备名称" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="密码" prop="secret">
                <el-input v-model="form.secret" placeholder="请输入密码" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="SN" prop="sn">
                <el-input v-model="form.sn" placeholder="请输入SN" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="编号" prop="code">
                <el-input v-model="form.code" placeholder="请输入IMEI" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="卡号" prop="iccid">
                <el-input v-model="form.iccid" placeholder="请输入ICCID" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="ParentSN" prop="parentSn">
                <el-input v-model="form.parentSn" placeholder="请输入ParentSN" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="使用模板">
                <el-checkbox v-model="form.useTemplate"></el-checkbox>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="类别" prop="category">
                <el-select v-model="form.category" placeholder="请选择类别" style="width: 100%">
                  <el-option label="类别1" value="1" />
                  <el-option label="类别2" value="2" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="组别" prop="groupId">
                <el-select v-model="form.groupId" placeholder="请选择组别" style="width: 100%">
                  <el-option label="组别1" value="1" />
                  <el-option label="组别2" value="2" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="策略" prop="strategy">
                <el-select v-model="form.strategy" placeholder="请选择策略" style="width: 100%">
                  <el-option label="策略1" value="1" />
                  <el-option label="策略2" value="2" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="状态" prop="status">
                <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
                  <el-option label="禁用" value="禁用" />
                  <el-option label="启用" value="启用" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="添加数量" prop="addCount">
                <el-input-number v-model="form.addCount" :min="1" :max="100" style="width: 100%" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="备注" prop="remark">
                <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">关 闭</el-button>
        </div>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import { listDevices, addDevice, updateDevice, deleteDevice, getDevice } from '@/api/device/device'; // 引入API
  
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
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 是否编辑模式
        isEdit: false,
        // 表单参数
        form: {
          id: undefined,
          name: undefined,
          secret: undefined,
          sn: undefined,
          code: undefined,
          iccid: undefined,
          parentSn: "0",
          useTemplate: false,
          category: undefined,
          groupId: undefined,
          strategy: undefined,
          status: "启用",
          addCount: 1,
          remark: undefined
        },
        // 表单校验
        rules: {
          name: [
            { required: true, message: "设备名称不能为空", trigger: "blur" }
          ],
          secret: [
            { required: true, message: "密码不能为空", trigger: "blur" }
          ],
          sn: [
            { required: true, message: "SN不能为空", trigger: "blur" }
          ]
        }
      };
    },
    methods: {
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加设备";
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
        this.reset();
        this.isEdit = true;
        this.open = true;
        this.title = "编辑设备";
        getDevice(row.id).then(response => {
          this.form = response.data;
        });
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
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: undefined,
          name: undefined,
          secret: undefined,
          sn: undefined,
          code: undefined,
          iccid: undefined,
          parentSn: "0",
          useTemplate: false,
          category: undefined,
          groupId: undefined,
          strategy: undefined,
          status: "启用",
          addCount: 1,
          remark: undefined
        };
        this.resetForm("form");
        this.isEdit = false;
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.isEdit) {
              updateDevice(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addDevice(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
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
  .dialog-footer {
    text-align: center;
  }
  </style>