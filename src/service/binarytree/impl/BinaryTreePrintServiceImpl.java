package service.binarytree.impl;

import model.BinaryTree;
import model.Node;
import service.binarytree.BinaryTreePrintService;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class BinaryTreePrintServiceImpl implements BinaryTreePrintService {

    private static final String IN_ORDER = "Em-ordem: ";
    private static final String PRE_ORDER = "Pré-ordem: ";
    private static final String POS_ORDER = "Pós-ordem: ";

    @Override
    public void print(BinaryTree binaryTree) {
        inOrder(binaryTree.getRoot());
        preOrder(binaryTree.getRoot());
        posOrder(binaryTree.getRoot());
    }

    private void inOrder(Node node) {
        List<Integer> list = new ArrayList<>();
        inOrderInternal(node, list);
        System.out.println(IN_ORDER + list);
    }

    private void preOrder(Node node) {
        List<Integer> preOrderList = new ArrayList<>();
        preOrderInternal(node, preOrderList);
        System.out.println(PRE_ORDER + preOrderList);
    }

    private void posOrder(Node node) {
        List<Integer> posOrderList = new ArrayList<>();
        posOrderInternal(node, posOrderList);
        System.out.println(POS_ORDER + posOrderList);
    }

    private void inOrderInternal(Node node, List<Integer> list) {
        if (nonNull(node)) {
            inOrderInternal(node.getLeftNode(), list);
            list.add(node.getValue());
            inOrderInternal(node.getRightNode(), list);
        }
    }

    private void posOrderInternal(Node node, List<Integer> list) {
        if (nonNull(node)) {
            posOrderInternal(node.getLeftNode(), list);
            posOrderInternal(node.getRightNode(), list);
            list.add(node.getValue());
        }
    }

    private void preOrderInternal(Node node, List<Integer> list) {
        if (nonNull(node)) {
            list.add(node.getValue());
            preOrderInternal(node.getLeftNode(), list);
            preOrderInternal(node.getRightNode(), list);
        }
    }
}
