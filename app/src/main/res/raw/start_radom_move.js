<script language="javascript">
var timer;
function startTimer()
{
    //设置定时器，定时执行randomMoveButton（）函数
    timer = setInterval("randomMoveButton()",1000);
}
function randomMoveButton(){
    var x = Math.round(Math.random() * 300);
    var y = Math.round(100 + Math.random() * 400);
    //调用接口中的方法
    window.demo.move(x,y);
}
startTimer();
</script>