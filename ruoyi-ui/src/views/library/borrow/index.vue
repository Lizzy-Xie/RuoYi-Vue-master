<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="读者序号【外键关联TB_Reader】" prop="rdID">
        <el-input
          v-model="queryParams.rdID"
          placeholder="请输入读者序号【外键关联TB_Reader】"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图书序号【外键关联TB_Book】" prop="bkID">
        <el-input
          v-model="queryParams.bkID"
          placeholder="请输入图书序号【外键关联TB_Book】"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="续借次数(初始0)" prop="ldContinueTimes">
        <el-input
          v-model="queryParams.ldContinueTimes"
          placeholder="请输入续借次数(初始0)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="借书日期" prop="ldDateOut">
        <el-date-picker clearable
          v-model="queryParams.ldDateOut"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择借书日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="应还日期" prop="ldDateRetPlan">
        <el-date-picker clearable
          v-model="queryParams.ldDateRetPlan"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择应还日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="实际还书日期" prop="ldDateRetAct">
        <el-date-picker clearable
          v-model="queryParams.ldDateRetAct"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择实际还书日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="超期天数" prop="ldOverDay">
        <el-input
          v-model="queryParams.ldOverDay"
          placeholder="请输入超期天数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="超期应罚款金额" prop="ldOverMoney">
        <el-input
          v-model="queryParams.ldOverMoney"
          placeholder="请输入超期应罚款金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实际罚款金额" prop="ldPunishMoney">
        <el-input
          v-model="queryParams.ldPunishMoney"
          placeholder="请输入实际罚款金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否还书(0=未还,1=已还)" prop="lsHasReturn">
        <el-input
          v-model="queryParams.lsHasReturn"
          placeholder="请输入是否还书(0=未还,1=已还)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="借书操作员" prop="OperatorBorrow">
        <el-input
          v-model="queryParams.OperatorBorrow"
          placeholder="请输入借书操作员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="还书操作员" prop="OperatorReturn">
        <el-input
          v-model="queryParams.OperatorReturn"
          placeholder="请输入还书操作员"
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
          v-hasPermi="['library:borrow:add']"
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
          v-hasPermi="['library:borrow:edit']"
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
          v-hasPermi="['library:borrow:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['library:borrow:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="borrowList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="借阅顺序号【自增主键】" align="center" prop="BorrowID" />
      <el-table-column label="读者序号【外键关联TB_Reader】" align="center" prop="rdID" />
      <el-table-column label="图书序号【外键关联TB_Book】" align="center" prop="bkID" />
      <el-table-column label="续借次数(初始0)" align="center" prop="ldContinueTimes" />
      <el-table-column label="借书日期" align="center" prop="ldDateOut" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ldDateOut, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="应还日期" align="center" prop="ldDateRetPlan" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ldDateRetPlan, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际还书日期" align="center" prop="ldDateRetAct" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ldDateRetAct, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="超期天数" align="center" prop="ldOverDay" />
      <el-table-column label="超期应罚款金额" align="center" prop="ldOverMoney" />
      <el-table-column label="实际罚款金额" align="center" prop="ldPunishMoney" />
      <el-table-column label="是否还书(0=未还,1=已还)" align="center" prop="lsHasReturn" />
      <el-table-column label="借书操作员" align="center" prop="OperatorBorrow" />
      <el-table-column label="还书操作员" align="center" prop="OperatorReturn" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['library:borrow:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['library:borrow:remove']"
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

    <!-- 添加或修改借阅信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="读者序号【外键关联TB_Reader】" prop="rdID">
          <el-input v-model="form.rdID" placeholder="请输入读者序号【外键关联TB_Reader】" />
        </el-form-item>
        <el-form-item label="图书序号【外键关联TB_Book】" prop="bkID">
          <el-input v-model="form.bkID" placeholder="请输入图书序号【外键关联TB_Book】" />
        </el-form-item>
        <el-form-item label="续借次数(初始0)" prop="ldContinueTimes">
          <el-input v-model="form.ldContinueTimes" placeholder="请输入续借次数(初始0)" />
        </el-form-item>
        <el-form-item label="借书日期" prop="ldDateOut">
          <el-date-picker clearable
            v-model="form.ldDateOut"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择借书日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="应还日期" prop="ldDateRetPlan">
          <el-date-picker clearable
            v-model="form.ldDateRetPlan"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择应还日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实际还书日期" prop="ldDateRetAct">
          <el-date-picker clearable
            v-model="form.ldDateRetAct"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择实际还书日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="超期天数" prop="ldOverDay">
          <el-input v-model="form.ldOverDay" placeholder="请输入超期天数" />
        </el-form-item>
        <el-form-item label="超期应罚款金额" prop="ldOverMoney">
          <el-input v-model="form.ldOverMoney" placeholder="请输入超期应罚款金额" />
        </el-form-item>
        <el-form-item label="实际罚款金额" prop="ldPunishMoney">
          <el-input v-model="form.ldPunishMoney" placeholder="请输入实际罚款金额" />
        </el-form-item>
        <el-form-item label="是否还书(0=未还,1=已还)" prop="lsHasReturn">
          <el-input v-model="form.lsHasReturn" placeholder="请输入是否还书(0=未还,1=已还)" />
        </el-form-item>
        <el-form-item label="借书操作员" prop="OperatorBorrow">
          <el-input v-model="form.OperatorBorrow" placeholder="请输入借书操作员" />
        </el-form-item>
        <el-form-item label="还书操作员" prop="OperatorReturn">
          <el-input v-model="form.OperatorReturn" placeholder="请输入还书操作员" />
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
import { listBorrow, getBorrow, delBorrow, addBorrow, updateBorrow } from "@/api/library/borrow"

export default {
  name: "Borrow",
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
      // 借阅信息表格数据
      borrowList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rdID: null,
        bkID: null,
        ldContinueTimes: null,
        ldDateOut: null,
        ldDateRetPlan: null,
        ldDateRetAct: null,
        ldOverDay: null,
        ldOverMoney: null,
        ldPunishMoney: null,
        lsHasReturn: null,
        OperatorBorrow: null,
        OperatorReturn: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        rdID: [
          { required: true, message: "读者序号【外键关联TB_Reader】不能为空", trigger: "blur" }
        ],
        bkID: [
          { required: true, message: "图书序号【外键关联TB_Book】不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询借阅信息列表 */
    getList() {
      this.loading = true
      listBorrow(this.queryParams).then(response => {
        this.borrowList = response.rows
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
        BorrowID: null,
        rdID: null,
        bkID: null,
        ldContinueTimes: null,
        ldDateOut: null,
        ldDateRetPlan: null,
        ldDateRetAct: null,
        ldOverDay: null,
        ldOverMoney: null,
        ldPunishMoney: null,
        lsHasReturn: null,
        OperatorBorrow: null,
        OperatorReturn: null
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
      this.ids = selection.map(item => item.BorrowID)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加借阅信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const BorrowID = row.BorrowID || this.ids
      getBorrow(BorrowID).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改借阅信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.BorrowID != null) {
            updateBorrow(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addBorrow(this.form).then(response => {
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
      const BorrowIDs = row.BorrowID || this.ids
      this.$modal.confirm('是否确认删除借阅信息编号为"' + BorrowIDs + '"的数据项？').then(function() {
        return delBorrow(BorrowIDs)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('library/borrow/export', {
        ...this.queryParams
      }, `borrow_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
