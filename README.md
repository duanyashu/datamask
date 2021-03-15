# datamask
    这是一个脱敏工具，通过添加注解实现灵活的数据脱敏
 
# 使用文档

### DesensitiveApi 实体字段注解 
    @DesensitiveInfo(DesensitiveType.EMAIL)
    指定要脱敏的字段类型（名称，手机，座机，地址，身份证号，邮箱，银行卡）
    除了指定格式，也可以自定义脱敏格式
    @DesensitiveInfo(retainLeft = 3,retainRight = 3,padStr = "*",separator = "@")
    retainLeft 左边显示个数 
    retainRight 右边显示个数
    padStr 替换的字符
    separator 分隔符 （eg:邮箱的“@”，座机的“-”）
### DesensitiveApi 接口类，方法注解 
    指定要脱敏的类或者接口