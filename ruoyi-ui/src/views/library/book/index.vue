<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="图书编号/条码号(书号)" prop="bkCode">
        <el-input
          v-model="queryParams.bkCode"
          placeholder="请输入图书编号/条码号(书号)"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="书名" prop="bkName">
        <el-input
          v-model="queryParams.bkName"
          placeholder="请输入书名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者" prop="bkAuthor">
        <el-input
          v-model="queryParams.bkAuthor"
          placeholder="请输入作者"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版社" prop="bkPress">
        <el-input
          v-model="queryParams.bkPress"
          placeholder="请输入出版社"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版日期" prop="bkDatePress">
        <el-date-picker clearable
          v-model="queryParams.bkDatePress"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择出版日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="ISBN书号" prop="bkISBN">
        <el-input
          v-model="queryParams.bkISBN"
          placeholder="请输入ISBN书号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- 优化：分类号改为下拉选择 -->
      <el-form-item label="分类号" prop="bkCatalog">
        <el-select v-model="queryParams.bkCatalog" placeholder="请选择分类号" clearable>
          <el-option v-for="item in catalogList" :key="item.catalogCode" :label="item.catalogCode + '-' + item.catalogName" :value="item.catalogCode"></el-option>
        </el-select>
      </el-form-item>
      <!-- 优化：语言改为下拉选择 -->
      <el-form-item label="语言" prop="bkLanguage">
        <el-select v-model="queryParams.bkLanguage" placeholder="请选择语言" clearable>
          <el-option label="中文" value="0"></el-option>
          <el-option label="英文" value="1"></el-option>
          <el-option label="日文" value="2"></el-option>
          <el-option label="俄文" value="3"></el-option>
          <el-option label="德文" value="4"></el-option>
          <el-option label="法文" value="5"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="页数" prop="bkPages">
        <el-input
          v-model="queryParams.bkPages"
          placeholder="请输入页数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="价格" prop="bkPrice">
        <el-input
          v-model="queryParams.bkPrice"
          placeholder="请输入价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入馆日期" prop="bkDateIn">
        <el-date-picker clearable
          v-model="queryParams.bkDateIn"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择入馆日期">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="图书状态" prop="bkStatus">
        <el-select 
          v-model="queryParams.bkStatus" 
          placeholder="请选择图书状态" 
          clearable
          @keyup.enter.native="handleQuery"
        >
          <el-option label="在馆" value="在馆" />
          <el-option label="借出" value="借出" />
          <el-option label="遗失" value="遗失" />
          <el-option label="变卖" value="变卖" />
          <el-option label="销毁" value="销毁" />
        </el-select>
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
          v-hasPermi="['library:book:add']"
        >新增</el-button>
      </el-col>
      <!-- 新增：批量入库按钮 -->
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="handleBatchInsert"
          v-hasPermi="['library:book:add']"
        >批量入库</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['library:book:edit']"
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
          v-hasPermi="['library:book:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['library:book:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bookList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="图书序号" align="center" prop="bkID" />
      <el-table-column label="图书编号/条码号(书号)" align="center" prop="bkCode" />
      <el-table-column label="书名" align="center" prop="bkName" />
      <el-table-column label="作者" align="center" prop="bkAuthor" />
      <el-table-column label="出版社" align="center" prop="bkPress" />
      <el-table-column label="出版日期" align="center" prop="bkDatePress" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.bkDatePress, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="ISBN书号" align="center" prop="bkISBN" />
      <el-table-column label="分类号(如TP316-21/123)" align="center" prop="bkCatalog" />
      <el-table-column label="语言" align="center" prop="bkLanguage" />
      <el-table-column label="页数" align="center" prop="bkPages" />
      <el-table-column label="价格" align="center" prop="bkPrice" />
      <el-table-column label="入馆日期" align="center" prop="bkDateIn" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.bkDateIn, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="内容简介" align="center" prop="bkBrief" />
      <el-table-column label="图书封面" align="center" prop="bkCover" />
      <!-- 优化：图书状态标签化显示 -->
      <el-table-column label="图书状态" align="center" prop="bkStatus">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.bkStatus === '在馆'" type="success">在馆</el-tag>
          <el-tag v-else-if="scope.row.bkStatus === '借出'" type="warning">借出</el-tag>
          <el-tag v-else-if="scope.row.bkStatus === '遗失'" type="danger">遗失</el-tag>
          <el-tag v-else-if="scope.row.bkStatus === '变卖'" type="info">变卖</el-tag>
          <el-tag v-else type="gray">销毁</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['library:book:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['library:book:remove']"
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

    <!-- 添加或修改图书信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="图书编号/条码号(书号)" prop="bkCode">
          <el-input v-model="form.bkCode" placeholder="请输入图书编号/条码号(书号)" />
        </el-form-item>
        <el-form-item label="书名" prop="bkName">
          <el-input v-model="form.bkName" placeholder="请输入书名" />
        </el-form-item>
        <el-form-item label="作者" prop="bkAuthor">
          <el-input v-model="form.bkAuthor" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="出版社" prop="bkPress">
          <el-input v-model="form.bkPress" placeholder="请输入出版社" />
        </el-form-item>
        <el-form-item label="出版日期" prop="bkDatePress">
          <el-date-picker clearable
            v-model="form.bkDatePress"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择出版日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="ISBN书号" prop="bkISBN">
          <el-input v-model="form.bkISBN" placeholder="请输入ISBN书号" />
        </el-form-item>
        <!-- 优化：分类号改为下拉选择 -->
        <el-form-item label="分类号(如TP316-21/123)" prop="bkCatalog">
          <el-select v-model="form.bkCatalog" placeholder="请选择分类号" required>
            <el-option v-for="item in catalogList" :key="item.catalogCode" :label="item.catalogCode + '-' + item.catalogName" :value="item.catalogCode"></el-option>
          </el-select>
        </el-form-item>
        <!-- 优化：语言改为下拉选择 -->
        <el-form-item label="语言(0=中文,1=英文,2=日文)" prop="bkLanguage">
          <el-select v-model="form.bkLanguage" placeholder="请选择语言" required>
            <el-option label="中文" value="0"></el-option>
            <el-option label="英文" value="1"></el-option>
            <el-option label="日文" value="2"></el-option>
            <el-option label="俄文" value="3"></el-option>
            <el-option label="德文" value="4"></el-option>
            <el-option label="法文" value="5"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="页数" prop="bkPages">
          <el-input v-model="form.bkPages" placeholder="请输入页数" />
        </el-form-item>
        <el-form-item label="价格" prop="bkPrice">
          <el-input v-model="form.bkPrice" placeholder="请输入价格" />
        </el-form-item>
        <el-form-item label="入馆日期" prop="bkDateIn">
          <el-date-picker clearable
            v-model="form.bkDateIn"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择入馆日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="内容简介" prop="bkBrief">
          <el-input v-model="form.bkBrief" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <!-- 新增：图书状态下拉选择 -->
        <el-form-item label="图书状态" prop="bkStatus">
          <el-select v-model="form.bkStatus" placeholder="请选择图书状态" required>
            <el-option label="在馆" value="在馆" />
            <el-option label="借出" value="借出" />
            <el-option label="遗失" value="遗失" />
            <el-option label="变卖" value="变卖" />
            <el-option label="销毁" value="销毁" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 新增：批量入库弹窗 -->
    <el-dialog title="批量入库" :visible.sync="batchOpen" width="500px" append-to-body>
      <el-form ref="batchForm" :model="batchForm" :rules="batchRules" label-width="80px">
        <el-form-item label="起始书号" prop="bkCode">
          <el-input v-model="batchForm.bkCode" placeholder="请输入起始书号（如10000）" />
        </el-form-item>
        <el-form-item label="入库数量" prop="batchNum">
          <!-- 优化：添加number修饰符，确保值为数字类型 -->
          <el-input v-model.number="batchForm.batchNum" type="number" min="1" max="100" placeholder="请输入数量（1-100）" />
        </el-form-item>
        <el-form-item label="书名" prop="bkName">
          <el-input v-model="batchForm.bkName" placeholder="请输入书名" />
        </el-form-item>
        <el-form-item label="作者" prop="bkAuthor">
          <el-input v-model="batchForm.bkAuthor" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="分类号" prop="bkCatalog">
          <el-select v-model="batchForm.bkCatalog" placeholder="请选择分类号" required>
            <el-option v-for="item in catalogList" :key="item.catalogCode" :label="item.catalogCode + '-' + item.catalogName" :value="item.catalogCode"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出版社" prop="bkPress">
          <el-input v-model="batchForm.bkPress" placeholder="请输入出版社" />
        </el-form-item>
        <el-form-item label="价格" prop="bkPrice">
          <el-input v-model.number="batchForm.bkPrice" type="number" step="0.1" placeholder="请输入价格" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBatch">确 定</el-button>
        <el-button @click="cancelBatch">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { 
  listBook, getBook, delBook, addBook, updateBook,
  getBookCatalog, batchInsertBook, updateBatchStatus 
} from "@/api/library/book"

export default {
  name: "Book",
  data() {
    return {
      // 原有数据保留不变
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      bookList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bkCode: null,
        bkName: null,
        bkAuthor: null,
        bkPress: null,
        bkDatePress: null,
        bkISBN: null,
        bkCatalog: null,
        bkLanguage: null,
        bkPages: null,
        bkPrice: null,
        bkDateIn: null,
        bkBrief: null,
        bkCover: null,
        bkStatus: null
      },
      form: {},
      rules: {
        bkCode: [
          { required: true, message: "图书编号/条码号(书号)不能为空", trigger: "blur" }
        ],
        bkName: [
          { required: true, message: "书名不能为空", trigger: "blur" }
        ],
        // 新增校验规则
        bkCatalog: [
          { required: true, message: "分类号不能为空", trigger: "blur" }
        ],
        bkStatus: [
          { required: true, message: "图书状态不能为空", trigger: "blur" }
        ]
      },
      // 新增数据
      catalogList: [], // 分类目录列表
      batchOpen: false, // 批量入库弹窗开关
      batchForm: { // 批量入库表单
        bkCode: "",
        batchNum: 1, // 默认值为数字类型
        bkName: "",
        bkAuthor: "",
        bkPress: "",
        bkCatalog: "",
        bkPrice: 0,
        bkLanguage: "0",
        bkDateIn: new Date()
      },
      batchRules: { // 批量入库校验规则
        bkCode: [
          { required: true, message: "起始书号不能为空", trigger: "blur" }
        ],
        batchNum: [
          { required: true, message: "入库数量不能为空", trigger: "blur" },
          // 核心修改：自定义校验，兼容数字/字符串类型
          { 
            validator: (rule, value, callback) => {
              // 空值已由required校验，此处只需判断数值范围
              const num = Number(value);
              if (isNaN(num)) {
                callback(new Error("请输入有效的数字"));
              } else if (num < 1 || num > 100) {
                callback(new Error("数量范围1-100"));
              } else {
                callback();
              }
            },
            trigger: "blur"
          }
        ],
        bkName: [
          { required: true, message: "书名不能为空", trigger: "blur" }
        ],
        bkCatalog: [
          { required: true, message: "分类号不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getCatalogList() // 加载分类目录
  },
  methods: {
    /** 查询图书信息列表 */
    getList() {
      this.loading = true
      listBook(this.queryParams).then(response => {
        this.bookList = response.rows
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
        bkID: null,
        bkCode: null,
        bkName: null,
        bkAuthor: null,
        bkPress: null,
        bkDatePress: null,
        bkISBN: null,
        bkCatalog: null,
        bkLanguage: "0", // 默认中文
        bkPages: null,
        bkPrice: null,
        bkDateIn: new Date(), // 默认当前日期
        bkBrief: null,
        bkCover: null,
        bkStatus: "在馆" // 默认在馆
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
      this.ids = selection.map(item => item.bkID)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加图书信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const bkID = row.bkID || this.ids
      getBook(bkID).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改图书信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.bkID != null) {
            updateBook(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addBook(this.form).then(response => {
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
      const bkIDs = row.bkID || this.ids
      this.$modal.confirm('是否确认删除图书信息编号为"' + bkIDs + '"的数据项？').then(function() {
        return delBook(bkIDs)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('library/book/export', {
        ...this.queryParams
      }, `book_${new Date().getTime()}.xlsx`)
    },

    // ========== 新增方法 ==========
    /** 获取图书分类目录 */
    getCatalogList() {
      getBookCatalog().then(response => {
        this.catalogList = response.data || [] // 兼容后端返回空的情况
      }).catch(err => {
        console.error("获取分类目录失败：", err)
        this.$modal.msgError("获取分类目录失败，请刷新页面重试")
      })
    },
    /** 打开批量入库弹窗 */
    handleBatchInsert() {
      this.batchForm = {
        bkCode: "",
        batchNum: 1, // 默认值为数字
        bkName: "",
        bkAuthor: "",
        bkPress: "",
        bkCatalog: "",
        bkPrice: 0,
        bkLanguage: "0",
        bkDateIn: new Date()
      }
      this.batchOpen = true
    },
    /** 提交批量入库 */
    submitBatch() {
      this.$refs["batchForm"].validate(valid => {
        if (valid) {
          // 确保batchNum为数字类型
          const batchNum = Number(this.batchForm.batchNum)
          batchInsertBook(this.batchForm, batchNum).then(response => {
            this.$modal.msgSuccess(`批量入库成功，共新增${response.data || batchNum}本图书`)
            this.batchOpen = false
            this.getList()
          }).catch(err => {
            console.error("批量入库失败：", err)
            this.$modal.msgError("批量入库失败，请检查输入或联系管理员")
          })
        }
      })
    },
    /** 取消批量入库 */
    cancelBatch() {
      this.batchOpen = false
    },
    /** 批量更新图书状态（可选：供批量借书/还书调用） */
    updateBookStatus(bkIDs, status) {
      updateBatchStatus(bkIDs, status).then(response => {
        this.$modal.msgSuccess("状态更新成功")
        this.getList()
      }).catch(err => {
        console.error("批量更新状态失败：", err)
        this.$modal.msgError("批量更新状态失败，请重试")
      })
    }
  }
}
</script>