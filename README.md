**该控件是一个带有展开和缩放功能的TextView**

先看下效果图吧:
![simple.gif](https://github.com/1002326270xc/MoreTextView/blob/master/photo/demo.gif)

效果图做得不太好啊，大家到真机上面测试demo吧。

## 思路:
其实这个地方就是一个动画，实时地去改变textview的高度，来达到一个渐变的效果。这里的特色:
- 能动态判断是否需要去显示更多的图标，如果用户的textView本来需要的行数大于自己定义的最大行数的话，此时才会有更多的功能。我这里定义的最大行数是3行，所以下面的textView会有更多的图标。而上面没有。
- 能实现初始状态是否是展开的效果，这里可以通过xml布局文件去配置。

## 使用:

  <pre><code>
  <declare-styleable name="MoreTextStyle">    
    <attr name="mytextSize" format="dimension" />    
    <attr name="mytextColor" format="color" />    
    <attr name="maxLine" format="integer" />    
    <attr name="text" format="string|reference" />    
    <attr name="expand" format="boolean" />
  </declare-styleable>
  </code></pre>

**mytextSize:** 显示的文字大小。

**mytextColor:** 显示的文字的颜色。

**maxLine:** 最大行数的规定。

**text:** 显示的文本，这里建议不要通过xml文件设置文本的文字，通过代码去设置。

**expand:** 初始状态是否是展开的，true的话，默认就是展开，反之缩进。

**代码中要调用的方法:**
  <pre><code>
  MoreTextView text1 = ((MoreTextView)findViewById(R.id.more));
  text1.setText(getResources().getString(R.string.text));
  text1.refreshText();
  </code></pre>

就这么简单，赶快使用到你的项目中吧。

## 关于我:

   - email:a1002326270@163.com
   - 简书:http://www.jianshu.com/users/7b186b7247c1/latest_articles
