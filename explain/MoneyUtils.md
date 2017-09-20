# MoneyUtils
## API
###### 人民币转换为大写,格式为：x万x千x百x十x元x角x分
```JAVA
MoneyUtils.number2CNMontray(String) //传入的金额
```
###### 人民币转换为大写,格式为：x万x千x百x十x元x角x分
```JAVA
MoneyUtils.number2CNMontray(BigDecimal)  //传入的金额
```
###### 将人民币转换为会计格式金额(xxxx, xxxx, xxxx.xx),保留两位小数
```JAVA
MoneyUtils.accountantMoney(BigDecimal) //待转换的金额
```
###### 格式化金额，显示为xxx万元，xxx百万,xxx亿
```JAVA
MoneyUtils.getFormatMoney(BigDecimal,int,double)
```
###### 获取会计格式的人民币(格式为:xxxx, xxxx, xxxx.xx)
```JAVA
MoneyUtils.getAccountantMoney(BigDecimal,int,double)
```
###### 将人民币转换为会计格式金额(xxxx, xxxx, xxxx.xx)
```JAVA
MoneyUtils.accountantMoney(BigDecimal,int,double)
```
###### 格式化金额，显示为xxx万元，xxx百万,xxx亿
```JAVA
MoneyUtils.formatMoney(BigDecimal,int,double)
```