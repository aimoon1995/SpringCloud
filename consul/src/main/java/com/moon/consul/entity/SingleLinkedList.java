package com.moon.consul.entity;

/**
 * @ClassName SingleLinkedList
 * @Description: 单链表
 * @Author zyl
 * @Date 2021/10/12
 * @Version V1.0
 **/
public class SingleLinkedList {
    //先初始化一个头结点
    HeroNode head = new HeroNode(0,"","");

    //获取头信息
    public HeroNode getHead() {
        return head;
    };

    public void  add(HeroNode node)  {
        //因为头节点不能动，因此我们仍然通过一个辅助指针(变量)来帮助找到添加的位置
        //因为单链表，因为我们找的temp 是位于 添加位置的前一个节点，否则插入不了
        HeroNode temp = head;
        while (true) {
            if(temp.getNext() == null) {//说明temp已经在链表的最后
                break; //
            }
            //如果没有找到最后, 将将temp后移
            temp = temp.getNext();
        }
        //当退出while循环时，temp就指向了链表的最后
        //将最后这个节点的next 指向 新的节点
        temp.setNext(node);
    }


    //按序排列
    public void  addByOrder(HeroNode node) {
      HeroNode temp = head;
      Boolean flag = true;
      while (true) {
          if (temp.getNext() == null) {
              break;
          }
          if (temp.getNext().getNo() > node.getNo()) {
              break;
          }
          if (temp.getNext().getNo() == node.getNo()) {
              throw new RuntimeException("repeat");
          }
          temp = temp.getNext();
      }
        if (temp.getNext() == null) {
            temp.setNext(node);
        }
        if (temp.getNext().getNo() > node.getNo()) {
            HeroNode heroNode = temp.getNext();
            node.setNext(heroNode);
            temp.setNext(node);
        }

    };

    //按序排列
    public void  delete(HeroNode node) {
        HeroNode temp = head;
        Boolean flag = true;
        while (true) {
            if (temp.getNext() == null) {
                break;
            }
            if (temp.getNext().getNo() == node.getNo()) {
                break;
            }
            temp = temp.getNext();
        }
        if (temp.getNext().getNo() > node.getNo()) {
            temp.setNext(temp.getNext().getNext());
        }

    };
}
