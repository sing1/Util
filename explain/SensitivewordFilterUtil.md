#SensitivewordFilterUtil

##API 
######构造函数，敏感词库地址或list
```JAVA
public SensitivewordFilterUtil(String) // localFilePath
public SensitivewordFilterUtil(List<String>)
```
######判断文字是否包含敏感字符(包含返回true，否则false)
```JAVA
public boolean isContaintSensitiveWord(String,int) // 文字，匹配规则:1 最小匹配、2 最大匹配
```
######获取文字中的敏感词
```JAVA
public Set<String> getSensitiveWord(String, int) // 文字，匹配规则:1 最小匹配、2 最大匹配
```
######替换敏感字字符
```JAVA
public String replaceSensitiveWord(String,int,String) // 文字,匹配规则:1 最小匹配、2 最大匹配,替换字符:默认*
```
######获取替换字符串
```JAVA
private String getReplaceChars(String,int) // replaceChar,length
```
######检查文字中是否包含敏感字符(如果存在，则返回敏感词字符的长度，不存在返回0)
```JAVA
public int CheckSensitiveWord(String txt,int beginIndex,int matchType){
```