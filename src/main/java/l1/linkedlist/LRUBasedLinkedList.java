package l1.linkedlist;

import java.util.HashMap;
import java.util.Map;

/**
 * 基于单链表实现的LRU缓存
 * @author lfwen
 * @date 2019-12-26 16:58
 */
public class LRUBasedLinkedList<T> {

    private Node<T> headNode = new Node(null, null);

    /**
     * 默认链表容量
     */
    private final static Integer DEFAULT_CAPACITY = 10;

    private int length;

    private int capacity;

    public LRUBasedLinkedList() {
        this(DEFAULT_CAPACITY);
    }

    public LRUBasedLinkedList(int capacity) {
        this.headNode = new Node(null, null);
        this.length = 0;
        this.capacity = capacity;
    }

    public void add(T data) {

        boolean exist = isExist(data);
        if(!exist) {
            if(isFull()) {
                removeLast();
            }
            insertAtFirst(data);
        } else {
            update(data);
        }
    }

    private boolean isFull() {
        return length == capacity;
    }

    private boolean isEmpty() {
        return length == 0;
    }

    public int getLength() {
        return this.length;
    }

    public int getCapacity() {
        return this.capacity;
    }

    /**
     * 判断数据在链表中是否存在
     * @param data
     * @return
     */
    private boolean isExist(T data) {
        if(isEmpty()) {
            return false;
        }

        Node node = headNode.getNext();
        while(node != null) {
            if(data.equals(node.getElement())) {
                return true;
            }
            node = node.getNext();
        }
        return false;
    }

    /**
     * 在队头插入数据
     * @param data
     */
    private void insertAtFirst(T data) {
        Node node = new Node(data);
        node.setNext(headNode.getNext());
        headNode.setNext(node);
        length++;
    }

    /**
     * 删除队尾结点
     * @return
     */
    private T removeLast() {
        if(isEmpty()) return null;

        Node<T> node = headNode;
        while(node.getNext().getNext() != null) {
            node = node.getNext();
        }

        Node<T> tailNode = node.getNext();
        node.setNext(null);
        length--;
        T data = tailNode.getElement();
        release(tailNode);
        return data;
    }

    public void remove(T data) {
        if(isEmpty()) return;
        if(data == null) {
            throw new RuntimeException("null value cache is not supported.");
        }
        Node preNode = headNode;
        Node node = headNode.getNext();
        while(node != null) {
            if(data.equals(node.getElement())) {
                preNode.setNext(node.getNext());
                release(node);
                length--;
                return;
            }
            node = node.getNext();
            preNode = preNode.getNext();
        }
    }


    public void update(T data) {
        remove(data);
        insertAtFirst(data);
    }

    /**
     * 释放结点占用的内存
     * @param node
     */
    private void release(Node<T> node) {
        node.setElement(null);
        node.setNext(null);
        node = null;
    }

    public static void main(String args[]) {
        LRUBasedLinkedList lruBasedLinkedList = new LRUBasedLinkedList();
        lruBasedLinkedList.add(1);
        lruBasedLinkedList.add(2);
        lruBasedLinkedList.add(3);
        lruBasedLinkedList.add(1);
        lruBasedLinkedList.add(4);
        System.out.println(lruBasedLinkedList.getLength());
        System.out.println(new Integer(1).equals(1));

    }

}

class Node<T> {
    private T element;
    private Node<T> next;

    public Node() {}
    public Node(T element) {
        this.element = element;
    }
    public Node(T element, Node<T> next) {
        this.element = element;
        this.next = next;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
