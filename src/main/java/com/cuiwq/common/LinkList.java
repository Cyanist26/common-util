package com.cuiwq.common;


import java.util.Optional;

/**
 * 链表实现
 *
 * @author cuiwq
 * @date 2020-08-11 星期二
 */
public class LinkList<D> {
    
    private Node<D> head;
    
    private Node<D> tail;
    
    private int size;
    
    public LinkList() {
        head = null;
        tail = null;
        size = 0;
    }
    
    public void add(D e) {
        if(tail == null) {
            /* 此时链表中还没有数据，该元素作为头节点 */
            tail = head = new Node<>(e);
            size++;
            return;
        }
        Node<D> n = new Node<>(e);
        n.prev = tail;
        tail.next = n;
        tail = n;
        size++;
    }
    
    public void add(D e, int index) {
        if(index == size) {
            /* 插入到最后 */
            add(e);
        }
        Node<D> n = getNode(index);
        if(n == null) {
            throw new ArrayIndexOutOfBoundsException("index not exist");
        }
        Node<D> newN = new Node<>(e);
        Node<D> prev = n.prev;
        if(prev != null) {
            prev.next = newN;
            newN.prev = prev;
        }
        newN.next = n;
        n.prev = newN;
        size++;
    }
    
    public D get(int index) {
        return Optional.ofNullable(getNode(index)).map(n -> n.data).orElse(null);
    }
    
    private Node<D> getNode(int index) {
        if(index < 0 || index >= size) {
            return null;
        }
        int mid = size >> 1;
        if(index <= mid) {
            Node<D> n = head;
            int i = 0;
            while(i < index) {
                n = n.next;
                i++;
            }
            return n;
        } else {
            Node<D> n = tail;
            int i = size - 1;
            while(i > index) {
                n = n.prev;
                i--;
            }
            return n;
        }
    }
    
    public D delete(int index) {
        Node<D> n = getNode(index);
        if(n == null) {
            return null;
        }
        Node<D> prev = n.prev;
        Node<D> next = n.next;
        size--;
        if(prev == null) {
            /* 删除的是头节点 */
            head = next;
            if(head != null) {
                head.prev = null;
            }
            n.next = null;
            return n.data;
        } else if(next == null) {
            /* 删除的是尾节点 */
            tail = prev;
            tail.next = null;
            n.prev = null;
            return n.data;
        } else {
            prev.next = next;
            next.prev = prev;
            n.next = null;
            n.prev = null;
            return n.data;
        }
    }
    
    public int size() {
        return size;
    }
    
    public void selfReverse() {
        Node<D> first = head;
        Node<D> prev = null;
        Node<D> n = head;
        while(n != null) {
            Node<D> next = n.next;
            n.next = prev;
            prev = n;
            n = next;
        }
        tail = first;
        head = prev;
    }
    
    public LinkList<D> reverse() {
        LinkList<D> re = new LinkList<>();
        reverse(re, head);
        return re;
    }
    
    private void reverse(LinkList<D> re, Node<D> n) {
        if(n == null) {
            return;
        }
        reverse(re, n.next);
        re.add(n.data);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<D> n = head;
        while(n != null) {
            sb.append(n.data).append(", ");
            n = n.next;
        }
        sb.delete(sb.length() - 2, sb.length()).append("]");
        return sb.toString();
    }
    
    private static class Node<D> {
        
        Node<D> next;
        
        Node<D> prev;
        
        D data;
    
        public Node(D data) {
            this.data = data;
        }
    
        @Override
        public String toString() {
            return "Node{" + "data=" + data + '}';
        }
    
    }
    
}