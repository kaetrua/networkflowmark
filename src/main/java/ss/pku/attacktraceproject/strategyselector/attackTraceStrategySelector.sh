#! /bin/bash

export TERM=linux   #防止脚本定时任务出错

count=10
ipaddr="123.207.144.96"
#获取网络情况
ping_status=`ping -c $count $ipaddr | awk 'NR==7 {print $4}'`
#统计丢包率
persent=$[count-ping_status]
ping_persent=`awk 'BEGIN{printf "%.0f\n",('$persent'/'$count')*100}'`

choice=""
if [[ ping_persent<3 ]]; then
	choice="网络流水印子策略"

elif [[ ping_persent>=3 ]]; then
	choice="蜜标溯源子策略"


#规则判断
echo " iptables= $ipaddr ---> 发包数:$count --->丢包数: $persent --->  网络丢包率: 
 ${ping_persent}% ---->建议使用策略:$choice" >> /Users/bingoer/log/log.txt
