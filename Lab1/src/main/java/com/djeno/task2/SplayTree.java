package com.djeno.task2;

import lombok.Getter;

@Getter
public class SplayTree {
    private Node root;

    public void insert(int key) {
        root = insert(root, key);
        root = splay(root, key);
    }

    private Node insert(Node node, int key) {
        if (node == null) return new Node(key);
        if (key < node.getKey()) node.setLeft(insert(node.getLeft(), key));
        else if (key > node.getKey()) node.setRight(insert(node.getRight(), key));
        return node;
    }

    public Node search(int key) {
        root = splay(root, key);
        if (root == null || root.getKey() != key) return null;
        return root;
    }

    public void delete(int key) {
        if (root == null) return;
        root = splay(root, key);
        if (root.getKey() != key) return;
        if (root.getLeft() == null) root = root.getRight();
        else {
            Node rightSubtree = root.getRight();
            root = splay(root.getLeft(), key);
            root.setRight(rightSubtree);
        }
    }

    public Node splay(Node node, int key) {
        if (node == null || node.getKey() == key) return node;
        if (key < node.getKey()) {
            if (node.getLeft() == null) return node;
            if (key < node.getLeft().getKey()) {
                node.getLeft().setLeft(splay(node.getLeft().getLeft(), key));
                node = rotateRight(node);
            } else if (key > node.getLeft().getKey()) {
                node.getLeft().setRight(splay(node.getLeft().getRight(), key));
                if (node.getLeft().getRight() != null) node.setLeft(rotateLeft(node.getLeft()));
            }
            return node.getLeft() == null ? node : rotateRight(node);
        } else {
            if (node.getRight() == null) return node;
            if (key < node.getRight().getKey()) {
                node.getRight().setLeft(splay(node.getRight().getLeft(), key));
                if (node.getRight().getLeft() != null) node.setRight(rotateRight(node.getRight()));
            } else if (key > node.getRight().getKey()) {
                node.getRight().setRight(splay(node.getRight().getRight(), key));
                node = rotateLeft(node);
            }
            return node.getRight() == null ? node : rotateLeft(node);
        }
    }

    public Node rotateRight(Node node) {
        Node newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);
        return newRoot;
    }

    public Node rotateLeft(Node node) {
        Node newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);
        return newRoot;
    }
}