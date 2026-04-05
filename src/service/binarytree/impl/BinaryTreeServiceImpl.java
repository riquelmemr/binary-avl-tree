package service.binarytree.impl;

import model.BinaryTree;
import model.Node;
import service.binarytree.BinaryTreePrintService;
import service.binarytree.BinaryTreeService;
import service.node.NodeService;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class BinaryTreeServiceImpl implements BinaryTreeService {

    private final BinaryTree binaryTree = new BinaryTree();

    private final NodeService nodeService;
    private final BinaryTreePrintService binaryTreePrintService;

    public BinaryTreeServiceImpl(NodeService nodeService, BinaryTreePrintService binaryTreePrintService) {
        this.nodeService = nodeService;
        this.binaryTreePrintService = binaryTreePrintService;
    }

    @Override
    public void insert(int value) {
        Node newNode = new Node(value);

        if (isEmpty()) {
            binaryTree.setRoot(newNode);
            return;
        }

        Node currentNode = binaryTree.getRoot();

        while (true) {
            if (newNode.getValue() < currentNode.getValue()) {
                if (nodeService.containsLeftChild(currentNode)) {
                    currentNode = currentNode.getLeftNode();
                } else {
                    currentNode.setLeftNode(newNode);
                    break;
                }
            } else {
                if (nodeService.containsRightChild(currentNode)) {
                    currentNode = currentNode.getRightNode();
                } else {
                    currentNode.setRightNode(newNode);
                    break;
                }
            }
        }
    }

    @Override
    public boolean search(int value) {
        Node currentNode = binaryTree.getRoot();

        while (nonNull(currentNode)) {
            if (currentNode.getValue() == value) {
                return true;
            }

            if (value < currentNode.getValue()) {
                currentNode = currentNode.getLeftNode();
            }

            if (value > currentNode.getValue()) {
                currentNode = currentNode.getRightNode();
            }
        }

        return false;
    }

    @Override
    public boolean remove(int value) {
        Node currentNode = binaryTree.getRoot();
        Node currentParentNode = null;

        while (nonNull(currentNode)) {
            if (currentNode.getValue() == value) {
                break;
            } else if (value < currentNode.getValue()) {
                currentParentNode = currentNode;
                currentNode = currentNode.getLeftNode();
            } else {
                currentParentNode = currentNode;
                currentNode = currentNode.getRightNode();
            }
        }

        if (nonNull(currentNode)) {
            if (nodeService.containsRightChild(currentNode)) {
                Node substituteNode = currentNode.getRightNode();
                Node substituteParentNode = currentNode;

                while (nonNull(substituteNode.getLeftNode())) {
                    substituteParentNode = substituteNode;
                    substituteNode = substituteNode.getLeftNode();
                }

                setSubstitute(currentNode, currentParentNode, substituteNode, substituteParentNode);
            } else if (nodeService.containsLeftChild(currentNode)) {
                Node substituteNode = currentNode.getLeftNode();
                Node substituteParentNode = currentNode;

                while (nonNull(substituteNode.getRightNode())) {
                    substituteParentNode = substituteNode;
                    substituteNode = substituteNode.getRightNode();
                }

                setSubstitute(currentNode, currentParentNode, substituteNode, substituteParentNode);
            } else if (nodeService.notContainsChildren(currentNode)) {
                if (nonNull(currentParentNode)) {
                    if (currentNode.getValue() > currentParentNode.getValue()) {
                        currentParentNode.setRightNode(null);
                    } else {
                        currentParentNode.setLeftNode(null);
                    }
                } else {
                    binaryTree.setRoot(null);
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public void print() {
        binaryTreePrintService.print(binaryTree);
    }

    private boolean isEmpty() {
        return isNull(binaryTree.getRoot());
    }

    private void setSubstitute(Node currentNode, Node currentParentNode, Node substituteNode, Node substituteParentNode) {
        if (nonNull(currentParentNode)) {
            if (currentNode.getValue() < currentParentNode.getValue()) {
                currentParentNode.setLeftNode(substituteNode);
            } else {
                currentParentNode.setRightNode(substituteNode);
            }
        } else {
            binaryTree.setRoot(substituteNode);
        }

        if (substituteNode.getValue() < substituteParentNode.getValue()) {
            System.out.println("substitute " + substituteNode.getValue() + " is letter than sub parent node" + substituteParentNode.getValue());
            substituteParentNode.setLeftNode(null);
        } else {
            System.out.println("substitute " + substituteNode.getValue() + " is better than sub parent node" + substituteParentNode.getValue());
            substituteParentNode.setRightNode(null);
        }
    }
}
