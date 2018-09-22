1. ### java JDK 1.8 or later 安装

> 下载jdk
>
> http://download.oracle.com/otn-pub/java/jdk/8u171-b11/512cd62ec5174c3487ac17c61aaa89e8/jdk-8u171-macosx-x64.dmg?AuthParam=1530876706_d18a7e62de36aec5bb13a37cadceeb9f
>
> 
>
> 配置环境变量：
>
> `$vim ~/.bash_profile `
>
> 添加内容：
>
> ```
> JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/Contents/Home
> PATH=$JAVA_HOME/bin:$PATH:.
> CLASSPATH=$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar:.
> export JAVA_HOME
> export PATH
> export CLASSPATH
> ```
>
> 保存退出：
>
> 激活：
>
> `$source ~/.bash_profile ` 
>
> 验证：
>
> `$java -version`
>
> 输出结果如下即jdk安装成功。
>
>  ```
> java version "1.8.0_171"
> Java(TM) SE Runtime Environment (build 1.8.0_171-b11)
> Java HotSpot(TM) 64-Bit Server VM (build 25.171-b11, mixed mode)
>  ```
>
> 参考：https://blog.csdn.net/u010669261/article/details/70768965
>
> 

2. ### maven配置

> 参考：https://www.cnblogs.com/vitasyuan/p/7395601.html?utm_source=debugrun&utm_medium=referral



3. ### 部署Metanome

项目位置 ：https://github.com/HPI-Information-Systems/Metanome  (命令行中已经有对应操作)

> 项目部署命令：
>
> ```
> $ git clone https://github.com/HPI-Information-Systems/Metanome.git
> $cd Metanome		(切换到Metanome文件目录下)
> $ mvn clean install
> $ mvn -f deployment/pom.xml package
> ```
>
> 
>
> 找到 `deployment/target/deployment-1.1-SNAPSHOT-package_with_tomcat.zip`并解压；
>
> ![deployment](/Users/ysh_ce/Desktop/deployment.jpg)
>
> 并切换到解压文件目录下：
>
> ```
> $ cd deployment/target
> $ unzip -d ployment deployment-1.1-SNAPSHOT-package_with_tomcat.zip
> $cd ployment
> ```
>
> 运行 `run.sh` or `run.bat`(Windows Systems),打开 <http://localhost:8080/>
>
> web版Metanome系统即可使用。
>
> **需要验证的算法和需要处理的数据**只需将相应的算法jar包（第4步中产生相应的算法）和数据分别拷贝到当前目录下的`/backend/WEB-INF/classes/algorithms`和`/backend/WEB-INF/classes/inputData`
>
> 刷新网页。选择添加算法和数据即可。（具体如何使用参考附件中的**详细使用步骤介绍**说明文档）



4. ### 部署metanome-algorithms

   项目所在位置：https://github.com/HPI-Information-Systems/metanome-algorithms

   ```
   $ git clone https://github.com/HPI-Information-Systems/metanome-algorithms.git

   $ cd metanome-algorithms 		(切换到文件目录下)

   $ MAVEN_OPTS="-Xmx1g -Xms20m -Xss10m" mvn clean install 
   ```

   完事后运行`./collect.sh`——**目的是**将生成的所有算法jar包集中复制到\_COLLECTION_文件中，待以后方便使用。

   ​

5. ### 部署metadata-ms

   项目所在位置：https://github.com/sekruse/metanome-cli

```
$ git clone https://github.com/sekruse/metanome-cli.git
$ cd metadata-ms
$ cd mdms
```

**<u>由于原项目存在问题，需要进行文档替换：</u>**

将mdms/mdms-metanome/src/main/java/de/hpi/isg/mdms/metanome/文件下的**MetacrateResultReceiver.java**替换附件中的**MetacrateResultReceiver.java**。

```
mvn instal
```

**这一步是metanome-cli部署不可缺少的一步。**

6. ###部署metanome-cli

项目所在位置：https://github.com/sekruse/metanome-cli

```
$ git clone https://github.com/sekruse/metanome-cli.git
$ cd metanome-cli
```

<u>**由于原项目存在问题，需要进行文档替换**：</u>

> 1. metanome-cli当前目录下的**pom.xml**替换成附件中的pom.xml
> 2. 将文件夹/src/main/java/de/metanome/cli/中的**DiscardingResultReceiver.java**替换附件中的**DiscardingResultReceiver.java**
> 3. 将文件夹/src/main/java/de/metanome/cli/中的**App.java**替换附件中的**App.java**

```
mvn package -Pdistro
```



















1. ### 命令行运行：

将第5步中/target/metanome-cli-1.1.1-SNAPSHOT.jar拷贝一个文件夹中：如~/Desktop/data_profiling/run_data/中

将所需的算法同样拷贝到此文件夹中。(以BINDER算法为例)

![data_profiling1](/Users/ysh_ce/Desktop/cli/data_profiling1.jpg)

```
cd Desktop/data_profiling/run_data/

java -cp metanome-cli-1.1.1-SNAPSHOT.jar:BINDER-1.1-SNAPSHOT.jar de.metanome.cli.App --algorithm de.metanome.algorithms.binder.BINDERFile  --file-key  csv  --files /Desktop/123/0307.csv -o
```

整体格式为：

```
java  -cp metanome-cli-1.1.1-SNAPSHOT.jar:*.jar de.metanome.cli.App --algorithm *  --file-key  *  --files load:my-files-list -o 
```



其中：`java -cp metanome-cli-1.1.1-SNAPSHOT.jar:BINDER-1.1-SNAPSHOT.jar de.metanome.cli.App —algorithm`中只需要对应修改jar名，其他不变，

- `--algorithm *`中的*需要利用解压包从BINDER-1.1-SNAPSHOT.jar中/META-INF/MANIFEST.MF中Algorithm-Bootstrap-Class:**de.metanome.algorithms.binder.BINDERFile**（不同的jar包都有相应的/META-INF/MANIFEST.MF）

  提取**de.metanome.algorithms.binder.BINDERFile**

- `--file-key——(不太理解是干什么的)，但是也是从源码中能够找到对应的参数值，BINDER这个算法有如下参数：INPUT_FILES, INPUT_ROW_LIMIT, TEMP_FOLDER_PATH, CLEAN_TEMP, DETECT_NARY, MAX_NARY_LEVEL, FILTER_KEY_FOREIGNKEYS, NUM_BUCKETS_PER_COLUMN, MEMORY_CHECK_FREQUENCY, MAX_MEMORY_USAGE_PERCENTAGE，可以供选择。我认为的是对应的web界面中各个参数的变量，如下。

  ![web参数](/Users/ysh_ce/Desktop/cli/web参数.jpg)

- `--files` 指定需要处理的文件，参考别人的issues，格式为 `--files load：文件路径列表 `，如

  `--files: my-files-list.txt`

  my-files-list.txt 文件中内容如下（每行一个文档路径）：

   /Users/ysh_ce/Desktop/123/0320.csv
  /Users/ysh_ce/Desktop/123/0307.csv

- -o 输出结果，，默认为file文件

举例：(以BINDER算法为例)

```
java -cp metanome-cli-1.1.1-SNAPSHOT.jar:BINDER-1.1-SNAPSHOT.jar  de.metanome.cli.App --algorithm de.metanome.algorithms.binder.BINDERFile --algorithm-config INPUT_ROW_LIMIT:-1 TEMP_FOLDER_PATH:BINDER CLEAN_TEMP:true DETECT_NARY:false MAX_NARY_LEVEL:-1 FILTER_KEY_FOREIGNKEYS:false NUM_BUCKETS_PER_COLUMN:10 MEMORY_CHECK_FREQUENCY:100 MAX_MEMORY_USAGE_PERCENTAGE:60 --file-key INPUT_FILES  --files load:my-file-list.txt 
```



——在文青师兄的帮助下，只将这些GitHub部署没出错，但是仍存在最主要的问题，就是metanome-cli实现命令行的项目存在问题：

——**目前的问题是**：**命令行执行后没有结果出现**，没有结果出现。经过几天的摸索，加上缺少java的认知，没有准确定位到具体问题在哪。大概定位到几个问题上：

(1)出现的问题，不应该在算法上，因为在web界面上调用算法是有结果的，但是在metanome-cli调用时没有结果，应该metanome-cli对算法的接口上;

(2)算法—>metanome-cli的输出结果接口上，现在定位到metanome-cli/src/main/java/de/metanome/cli/App.java文件中。其中第120行左右是对输出的一种处理，resultReceiver是对应的结果，但是没内容。根据web和命令行两者执行相同的算法，处理相同的数据，BINER算法打印的Print结果存在差异，所以我想应该是menatome-cli向算法输出的接口存在问题。

**web执行中BINER对应代码输出结果**

![命令行输出](/Users/ysh_ce/Desktop/cli/命令行输出.jpg)

**命令行执行中BINER对应代码输出结果**

![web输出](/Users/ysh_ce/Desktop/cli/web输出.jpg)

上面输出内容是从相同的算法程序中输出的结果，存在差异。如何metanome-cli给算法程序的数据是正确的，则这一部分应该相同。**我认为问题应该在metanome-cli读数据到给算法BINDER传输的过程中。**

(3)**metanome-cli—>算法的端口**，最有可能是问题的所在。我也请教了一些做java的同学，同学说这个没有一定的java基础和对框架的了解，一时半会解决不了这个问题，需要精通java的人来解决这个问题。如果这样毫无头绪的去试探，肯定会耽误项目的进程。