## 搭建 hexo + github 个人博客

[github地址](https://github.com/hexojs/hexo)  

平时我们偶尔看到一些有用的东西会做点笔记，但是每次写的东西都会到处放，不好找，所以才想着自己搭建一个人博客网站，所以就考虑搭建一下自己的博客，hexo是一款基于Node.js的静态博客框架,这篇教程是针对与Mac的，同时可以同步git上，不提供绑定域名的方法，只针对小型的记录笔记。
### 准备工作  
1、安装Node  
- 用来生成静态页面的　[下载地址](https://nodejs.org/en/download/)

2、安装Git  
- 把本地的hexo内容提交到github上去　[下载地址](https://git-scm.com/downloads)  
 
3、申请GitHub账号  
- 用来做博客的远程创库　[申请地址](https://github.com/join)  

准备工作比较简单，有问题可以自己百度。  
### 安装Hexo
首先我们在将要安装的地方创建一个文件夹，并通过终端进入该目录，我这里命名为 hexo：

```XML
cd /Users/Sing/Documents/Workspace/hexo
```
然后执行以下命令安装：

```XML
npm install -g hexo
```
安装过程...
![](http://upload-images.jianshu.io/upload_images/7115680-73573599d3ee6f93.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
安装完成后输入以下命令检查是否成功：

```XML
hexo
```
如果出现以下情况即为成功：

```XML
Sing-2:~ Sing$ hexo
Usage: hexo <command>

Commands:
  help     Get help on a command.
  init     Create a new Hexo folder.
  version  Display version information.

Global Options:
  --config  Specify config file instead of using _config.yml
  --cwd     Specify the CWD
  --debug   Display all verbose messages in the terminal
  --draft   Display draft posts
  --safe    Disable all plugins and scripts
  --silent  Hide output on console

For more help, you can use 'hexo help [command]' for the detailed information
or you can check the docs: http://hexo.io/docs/
Sing-2:~ Sing$ 
```
成功之后在终端进入刚刚创建的文件夹执行以下命令初始化建立网站所需要的所有文件：

```XML
hexo init
```

```XML
Sing-2:~ Sing$ cd /Users/Sing/Documents/hexo
Sing-2:hexo1 Sing$ hexo init
... // 此处略掉
```
到此本地的博客已经建立好了，我们输入以下命令查看以下：

```XML
hexo generate
hexo server
```
执行窗口：

```XML
Sing-2:hexo1 Sing$ hexo generate
hexo serverINFO  Start processing
INFO  Files loaded in 200 ms
INFO  Generated: index.html
...
INFO  Generated: css/fonts/fontawesome-webfont.svg
INFO  28 files generated in 623 ms
Sing-2:hexo1 Sing$ hexo server
INFO  Start processing
INFO  Hexo is running at http://localhost:4000/. Press Ctrl+C to stop.
```
可以看到成功启动了服务，并且在浏览器输入 http://localhost:4000/ 查看，按Ctrl+C停止服务。
![](http://upload-images.jianshu.io/upload_images/7115680-0db55c17e3355955.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
如果执行 hexo server 提示找不到该指令的话需要安装 server 并重新执行命令即可（Hexo 3.0 后 server 被单独出来）

```XML
npm install hexo -server --save 
```
### 搭建 gighub 个人博客
前面已经说到了注册 github 账号，注册登录之后在右上角点击 “+” 选择 New repository
![](http://upload-images.jianshu.io/upload_images/7115680-b620c8ec1839fd02.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
此时你已经可以通过 https://账户名.github.io 来访问你的博客了，然后我们将本地的hexo和你的github博客同步起来。
### 生成SSH密钥
在终端执行命令按提示走(记住输入的密码)：
```XML
ssh-keygen -t rsa -C "你的邮箱地址"
```

![](http://upload-images.jianshu.io/upload_images/7115680-8f2f41a4e84b5277.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
此时我们在.ssh文件夹下，得到两个文件 id\_rsa 和 id\_rsa.pub,然后我们需要将它添加到 github 上面。
### 添加 SSH 密钥到 GitHub 上
![](http://upload-images.jianshu.io/upload_images/7115680-e295b7de843a4e0a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
点击 SSH keys 右侧的 New SSH key ，在Title随便填，Key中将 刚刚得到的文件id_rsa.pub的全部内容粘贴进去，完事保存。
### 关联 hexo 到 github
之后我们进入 hexo 的安装目录，找到 _config.yml 文件打开，在最底下的代码处修改：

```XML
deploy:
  type: git    # 这里是 git 并非 github
  repo: https://github.com/Sing1/Sing1.github.io.git  # 这里写刚刚新建的github的地址，也就是博客的源文件地址
  branch: master
```
保存后关闭，然后我们在终端定位到 hexo 的目录执行如下命令：

```JAVA
hexo clean
hexo generate
hexo deploy
```
完事之后，刷新你的博客即可看到和本地一样的效果了，此时已经完成了 hexo+github 的搭建，接下来我们说一下目录结构的介绍和基本配置。
### hexo 目录结构介绍

```XML
|-- _config.yml
|-- db.json
|-- node_modules
|-- package.json
|-- scaffolds
|-- source
   |-- _posts
|-- themes
```
这里我们只看一下 \_config.yml、source 和 themes。  
- 1、_config.yml是全局配置文件，网站的名称、副标题、描述、作者、语言、主题、部署等等参数都在这里配置，稍后会做较为详细的介绍。  
- 2、source 是固定目录，有子目录 _posts，新建的博文(markdown文件)都放在这里，会被编译成html文件，放到 public （此文件现在应该没有，因为你还没有编译过）文件夹下。  
- 3、themes 网站主题目录，该目录下，每一个子目录就是一个主题，稍后我们会配置一个第三方主题。

##### _config.yml 
采用YAML语法格式，具体语法可以自己学习，我们看下主要参数：

```XML
# Hexo Configuration
## Docs: https://hexo.io/docs/configuration.html
## Source: https://github.com/hexojs/hexo/

# Site
title: Sing  #网站标题
subtitle: 笔记   #网站副标题
description: #网站描述
author: Sing  #作者
language: zh-CN   #语言
timezone: # 网站时区，默认使用您电脑的时区。

# URL
## If your site is put in a subdirectory, set url as 'http://yoursite.com/child' and root as '/child/'
url: #你的站点Url
root: /   #站点的根目录
permalink: :year/:month/:day/:title/  #文章的 永久链接 格式
permalink_defaults:   #永久链接中各部分的默认值

# Directory
source_dir: source  #资源文件夹，这个文件夹用来存放内容
public_dir: public  #公共文件夹，这个文件夹用于存放生成的站点文件。
tag_dir: tags   # 标签文件夹
archive_dir: archives  #归档文件夹
category_dir: categories    #分类文件夹
code_dir: downloads/code    #Include code 文件夹
i18n_dir: :lang   #国际化（i18n）文件夹
skip_render:  #跳过指定文件的渲染，您可使用 glob 表达式来匹配路径。

# Writing
new_post_name: :title.md # 新文章的文件名称
default_layout: post  # 预设布局
titlecase: false # 把标题转换为 title case
external_link: true # 在新标签中打开链接
filename_case: 0 #把文件名称转换为 (1) 小写或 (2) 大写
render_drafts: false #是否显示草稿
post_asset_folder: false #是否启动 Asset 文件夹
relative_link: false #把链接改为与根目录的相对位址    
future: true #显示未来的文章
highlight: #内容中代码块的设置 
  enable: true
  line_number: true
  auto_detect: true
  tab_replace:

# Home page setting
# path: Root path for your blogs index page. (default = '')
# per_page: Posts displayed per page. (0 = disable pagination)
# order_by: Posts order. (Order by date descending by default)
index_generator:
  path: ''
  per_page: 10  # 首页每页10条数据
  order_by: -date # 时间倒序排列

# Category & Tag
default_category: uncategorized
category_map:   #分类别名
tag_map:   #标签别名

# Date / Time format
## Hexo uses Moment.js to parse and display date
## You can customize the date format as defined in
## http://momentjs.com/docs/#/displaying/format/
date_format: YYYY-MM-DD #日期格式
time_format: HH:mm:ss  #时间格式

# Pagination
## Set per_page to 0 to disable pagination
per_page: 10   #分页数量
pagination_dir: page

# Extensions
## Plugins: https://hexo.io/plugins/
## Themes: https://hexo.io/themes/
theme: landscape  #主题名称


# Deployment
## Docs: https://hexo.io/docs/deployment.html
deploy:  # 前面介绍过了
  type: git
  repo: https://github.com/Sing1/Sing1.github.io.git
  branch: master

```
### hexo 常用语法

```XML
hexo version                 # 查看Hexo的版本
hexo help                    # 查看帮助
hexo new "postName"          # 新建文章
hexo new page "pageName"     # 新建页面
hexo clean                   # 清空缓存(主要是public目录，需要重新生成)
hexo generate                # 生成静态页面至public目录
hexo server                  # 开启预览访问服务
hexo deploy                  # 将.deploy目录部署到GitHub
```
其中部分命令可以简写：  

```ＸＭＬ
hexo generate    -->　　hexo g  
hexo server      -->　　hexo s  
hexo deploy      -->　　hexo d   
```
建议每次上传的时候，都调用 clean 清空一下，然后再 generate 生成一次，再同步或者启动服务。

### 修改网站主题
hexo 的主题有很多，[主题地址](https://hexo.io/themes)，可以在这里选择自己喜欢的主题，然后下载下来。  
- 将解压文件夹放在 hexo 目录的 themes 文件夹下  
- 在\_config.yml文件里面将theme字段修改为下载的主题文件夹的名字。  
  
这样重新启动一下就可以换成喜欢的主题了，我这边使用的主题是 [Maupassant](https://www.haomwei.com/technology/maupassant-hexo.html)，简单而且在移动端也适配相对不错(当然，我的好多md文件还没有迁移上去)，如果可以的话自己写一个主题也行！  
![](http://upload-images.jianshu.io/upload_images/7115680-fe168260a4fbea59.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
