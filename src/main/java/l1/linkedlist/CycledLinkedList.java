package l1.linkedlist;

import java.util.ArrayList;
import java.util.List;

/**
 * 循环单链表，模拟解决约瑟夫环问题
 *
 * @author lfwen
 * @date 2019-12-26 19:50
 */
public class CycledLinkedList<T> {

    /**
     * 头结点
     */
    private Node<T> head;

    /**
     * 链表长度
     */
    private int length;

    /**
     * 创建空链表
     */
    public CycledLinkedList() {
        head = new Node(null, null);
        length = 0;
    }


    /**
     * 为循环单链表新增元素，每次都新增为队头元素
     * 当只有一个元素时，该元素结点的next指向自身
     *           |-------|
     *           V       |
     * head --> first -->|
     *
     * 两个元素时，链表如下：
     *            |-----------------|
     *            V                 |
     * head --> first --> second -->|
     *
     * @param data
     */
    public void add(T data) {
        Node<T> node = new Node<>(data);

        if (length == 0) {
            node.setNext(node);
        } else {
            Node<T> firstNode = head.getNext();
            // 找到链表末尾结点
            Node<T> ptr = firstNode.getNext();
            while (!ptr.getNext().getElement().equals(firstNode.getElement())) {
                ptr = ptr.getNext();
            }
            Node<T> lastNode = ptr;
            node.setNext(head.getNext());
            lastNode.setNext(node);
        }

        head.setNext(node);
        length++;
    }

    public Node<T> getHead() {
        return this.head;
    }

    public int getLength() {
        return length;
    }

    public static void main(String args[]) {
        CycledLinkedList<Integer> linkedList = new CycledLinkedList<>();
        int m = 100; // 环大小
        int n = 2;   // 环模数
        List<Integer> dataList = new ArrayList<>();
        initData(dataList, m);
        // 这里单链表的数据不能重复，否则判断队尾等逻辑会有问题
        createCycle(linkedList, dataList);
        int r = josephus(linkedList, n);
        System.out.println("alive: " + r);
    }

    /**
     * 初始化整形数据List
     * @param dataList
     * @param m
     */
    static void initData(List<Integer> dataList, int m) {
        for (int i = m; i >= 1; i--) {
            dataList.add(i);
        }
    }

    /**
     * 用List数据创建循环单链表
     * @param linkedList
     * @param dataList
     * @param <T>
     */
    static <T> void createCycle(CycledLinkedList<T> linkedList, List<T> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            linkedList.add(dataList.get(i));
        }
    }

    /**
     * 模拟约瑟夫环问题求解
     * @param linkedList    循环单链表
     * @param n             约瑟夫环的模数
     * @param <T>
     * @return
     */
    static <T> T josephus(CycledLinkedList<T> linkedList, int n) {
        Node<T> head = linkedList.getHead();
        int m = linkedList.getLength();
        int start = 1;
        int index = start;
        Node<T> preNode = head;
        Node<T> node = head.getNext();
        while (linkedList.getLength() > 1) {
            if (index == n) {
                System.out.println("kill: " + node.getElement());
                preNode.setNext(node.getNext());
                linkedList.length--;
                // 当前被kill的结点为头结点，则头结点顺移
                if(node.getElement().equals(head.getNext().getElement())) {
                    head.setNext(node.getNext());
                }

                node = node.getNext();
                index = 1;
                continue;
            }
            preNode = preNode.getNext();
            node = node.getNext();
            index++;
        }
        return head.getNext().getElement();
    }


}
