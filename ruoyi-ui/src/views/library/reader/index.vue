<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="读者姓名" prop="rdName">
        <el-input
          v-model="queryParams.rdName"
          placeholder="请输入读者姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位名称" prop="rdDept">
        <el-input
          v-model="queryParams.rdDept"
          placeholder="请输入单位名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="电话号码" prop="rdPhone">
        <el-input
          v-model="queryParams.rdPhone"
          placeholder="请输入电话号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="电子邮箱" prop="rdEmail">
        <el-input
          v-model="queryParams.rdEmail"
          placeholder="请输入电子邮箱"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="办证日期" prop="rdDateReg">
        <el-date-picker clearable
          v-model="queryParams.rdDateReg"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择办证日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="已借书数量" prop="rdBorrowQty">
        <el-input
          v-model="queryParams.rdBorrowQty"
          placeholder="请输入已借书数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="读者密码(初始123)" prop="rdPwd">
        <el-input
          v-model="queryParams.rdPwd"
          placeholder="请输入读者密码(初始123)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="管理角色(0=读者,1=借书证管理,2=图书管理,4=借阅管理,8=系统管理)" prop="rdAdminRoles">
        <el-input
          v-model="queryParams.rdAdminRoles"
          placeholder="请输入管理角色(0=读者,1=借书证管理,2=图书管理,4=借阅管理,8=系统管理)"
          clearable
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
          v-hasPermi="['library:reader:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['library:reader:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['library:reader:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['library:reader:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="readerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="读者编号/借书证号【主键】" align="center" prop="rdID" />
      <el-table-column label="读者姓名" align="center" prop="rdName" />
      <el-table-column label="性别(男/女)" align="center" prop="rdSex" />
      <el-table-column label="读者类别【外键关联TB_ReaderType】" align="center" prop="rdType" />
      <el-table-column label="单位名称" align="center" prop="rdDept" />
      <el-table-column label="电话号码" align="center" prop="rdPhone" />
      <el-table-column label="电子邮箱" align="center" prop="rdEmail" />
      <el-table-column label="办证日期" align="center" prop="rdDateReg" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.rdDateReg, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="读者照片" align="center" prop="rdPhoto" />
      <el-table-column label="证件状态(有效/挂失/注销)" align="center" prop="rdStatus" />
      <el-table-column label="已借书数量" align="center" prop="rdBorrowQty" />
      <el-table-column label="读者密码(初始123)" align="center" prop="rdPwd" />
      <el-table-column label="管理角色(0=读者,1=借书证管理,2=图书管理,4=借阅管理,8=系统管理)" align="center" prop="rdAdminRoles" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['library:reader:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['library:reader:remove']"
          >删除</el-button>
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

    <!-- 添加或修改读者信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="读者姓名" prop="rdName">
          <el-input v-model="form.rdName" placeholder="请输入读者姓名" />
        </el-form-item>
        <el-form-item label="单位名称" prop="rdDept">
          <el-input v-model="form.rdDept" placeholder="请输入单位名称" />
        </el-form-item>
        <el-form-item label="电话号码" prop="rdPhone">
          <el-input v-model="form.rdPhone" placeholder="请输入电话号码" />
        </el-form-item>
        <el-form-item label="电子邮箱" prop="rdEmail">
          <el-input v-model="form.rdEmail" placeholder="请输入电子邮箱" />
        </el-form-item>
        <el-form-item label="办证日期" prop="rdDateReg">
          <el-date-picker clearable
            v-model="form.rdDateReg"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择办证日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="已借书数量" prop="rdBorrowQty">
          <el-input v-model="form.rdBorrowQty" placeholder="请输入已借书数量" />
        </el-form-item>
        <el-form-item label="读者密码(初始123)" prop="rdPwd">
          <el-input v-model="form.rdPwd" placeholder="请输入读者密码(初始123)" />
        </el-form-item>
        <el-form-item label="管理角色(0=读者,1=借书证管理,2=图书管理,4=借阅管理,8=系统管理)" prop="rdAdminRoles">
          <el-input v-model="form.rdAdminRoles" placeholder="请输入管理角色(0=读者,1=借书证管理,2=图书管理,4=借阅管理,8=系统管理)" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listReader, getReader, delReader, addReader, updateReader } from "@/api/library/reader"

export default {
  name: "Reader",
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
      // 读者信息表格数据
      readerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rdName: null,
        rdSex: null,
        rdType: null,
        rdDept: null,
        rdPhone: null,
        rdEmail: null,
        rdDateReg: null,
        rdPhoto: null,
        rdStatus: null,
        rdBorrowQty: null,
        rdPwd: null,
        rdAdminRoles: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        rdName: [
          { required: true, message: "读者姓名不能为空", trigger: "blur" }
        ],
        rdType: [
          { required: true, message: "读者类别【外键关联TB_ReaderType】不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询读者信息列表 */
    getList() {
      this.loading = true
      listReader(this.queryParams).then(response => {
        this.readerList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        rdID: null,
        rdName: null,
        rdSex: null,
        rdType: null,
        rdDept: null,
        rdPhone: null,
        rdEmail: null,
        rdDateReg: null,
        rdPhoto: null,
        rdStatus: null,
        rdBorrowQty: null,
        rdPwd: null,
        rdAdminRoles: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.rdID)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加读者信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const rdID = row.rdID || this.ids
      getReader(rdID).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改读者信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.rdID != null) {
            updateReader(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addReader(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const rdIDs = row.rdID || this.ids
      this.$modal.confirm('是否确认删除读者信息编号为"' + rdIDs + '"的数据项？').then(function() {
        return delReader(rdIDs)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('library/reader/export', {
        ...this.queryParams
      }, `reader_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
