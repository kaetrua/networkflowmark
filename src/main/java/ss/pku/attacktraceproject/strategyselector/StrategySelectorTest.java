package ss.pku.attacktraceproject.strategyselector;

public class StrategySelectorTest {
    public static void main(String[] args) {

        int selector=TraceStrategySelector.startSelector(StrategySelectorConstants.iptables);
        if (selector==0){
            System.out.println("网络稳定性好，推荐使用网络流水印溯源子策略");
        }else if (selector==1){
            System.out.println("网络稳定性差，推荐使用蜜标溯源子策略");
        }else {
            System.out.println("本次稳定性测试失败。");
        }
    }
}
