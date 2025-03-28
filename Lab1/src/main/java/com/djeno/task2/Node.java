package com.djeno.task2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {
    private int key;
    private Node left, right;

    public Node(int key) {
        this.key = key;
    }
}