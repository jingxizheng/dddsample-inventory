# dddsample-inventory

## 领域层(domain)

### 实体
- 唯一的身份标识
- 可变性(mutability)
- eg. 物理库存[PhysicalStore](src/main/java/com/linesum/inventory/domain/model/store/PhysicalStore.java)

### 值对象
- 不变性(类修饰为final，实例初始化后属性状态不可变更)
- 概念整体(如标表示钱的值对象中，价格price和货币currency作为概念整体)
- 度量
- 可替换性
- 无副作用(值对象的方法应该是无副作用的，无副作用方法不会修改属性本身，而是通过计算返回新的对象)
- eg. 仓库信息[WarehouseInfo](src/main/java/com/linesum/inventory/domain/model/store/WarehouseInfo.java)

### 领域服务
- 满足领域服务的特性
1. 执行一个显著的业务操作过程
2. 对领域对象进行转换
3. 以多个领域对象作为输入进行计算，结果产生一个值对象
- 在领域服务中可以操作资源库
- eg. 移仓服务[TransferService](src/main/java/com/linesum/inventory/domain/service/TransferService.java)

### 注意
- 实际编码过程中，尽量做到不违背领域建模的思想，但为了满足设计可以适当违背，但尽量注解详细

## 基础设施层(infrastructure)

### mybatis & mapper & pagehelper
- 通用Mapper指定的PO类，主键生成策略不支持`@GeneratedValue(strategy = GenerationType.AUTO)`形式
- `mapper.mappers=com.linesum.inventory.infrastructure.BaseMapper` 自定义通用Mapper，作为顶层Mapper
- 具体使用详见[GoodsMapperTest.java](src/test/java/com/linesum/inventory/infrastructure/persistence/mapper/GoodsMapperTest.java)
