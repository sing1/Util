# StringUtil
## API
######字符串是否为空

```JAVA
StringUtil.isEmpty(String)
```
###### 将半角的符号转换成全角符号.(即英文字符转中文字符)

```JAVA
StringUtil.changeToFull(String) // 要转换的字符
```
###### 将字符转换为编码为Unicode，格式 为'\u0020'

```JAVA
StringUtil.unicodeEscaped(char) // 待转换的char 字符
```
###### 判断字符串是否全部都为小写

```JAVA
StringUtil.isAllLowerCase(String) // 待判断的字符串
```
###### 判断字符串是否全部大写

```JAVA
StringUtil.isAllUpperCase(String) // 待判断的字符串
```
###### 过滤html标签，包括script、style、html、空格、回车标签

```JAVA
StringUtil.delHTMLTag(String)
```
###### 反转字符串

```JAVA
StringUtil.reverse(String) // 待反转的字符串
```
###### 将某个字符重复N次

```JAVA
StringUtil.repeatChar(char, int) // 需要循环的字符,循环的次数
```
