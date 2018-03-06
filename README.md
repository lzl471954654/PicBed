# PicBed

PicBed 是一款用于将GitHub仓库当做图床，可以将当前截屏、或已存在的图像文件、一键上传至GitHub，并生成原始URL，和MarkDown图片链接

## 使用方法
1. 下载jar包
2. 编写配置文件
>
配置文件格式如下
username=github用户名
token=github账户密码、或者github开发者设置中生成的Token
repo=存放图片的仓库名称
format= jpg/png/bmp/gif (默认png为图片生成的格式)


3. 配置好配置文件后，设置系统全局快捷键 命令为 (假设PicBed.jar 存放在 /home/user/tools 下)
>
java -jar /home/user/tools/PicBed.jar

4. 复制一图片文件，对某个文件cltr+c，或者直接截屏
5. 按下预先设置好的快捷键
6. 等待片刻，则会弹出生成的URL

![](https://raw.githubusercontent.com/lzl471954654/drawIO/master/1520328877487.png)
