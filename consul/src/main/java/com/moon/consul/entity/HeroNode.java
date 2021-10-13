package com.moon.consul.entity;

import lombok.Data;

/**
 * @ClassName HeroNode
 * @Description: 英雄节点
 * @Author zyl
 * @Date 2021/10/12
 * @Version V1.0
 **/
@Data
public class HeroNode {
    private   Integer  no;//排名
    private  String name;// 姓名
    private  String nickName;// 昵称
    private  HeroNode  next;
    private  HeroNode  pre;

    public  HeroNode(Integer no,String name,String nickName) {
        this.name = name;
        this.nickName = nickName;
        this.no = no;
    }
}
