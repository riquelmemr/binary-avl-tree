package service.binarytree.impl;

import model.BinaryTree;
import model.Node;
import service.binarytree.BinaryTreePrintService;
import service.binarytree.BinaryTreeRotationService;
import service.binarytree.BinaryTreeService;
import service.node.NodeService;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class BinaryTreeServiceImpl implements BinaryTreeService {

    private final BinaryTree binaryTree = new BinaryTree();

    private final NodeService nodeService;
    private final BinaryTreePrintService binaryTreePrintService;
    private final BinaryTreeRotationService binaryTreeRotationService;

    public BinaryTreeServiceImpl(NodeService nodeService,
                                 BinaryTreePrintService binaryTreePrintService,
                                 BinaryTreeRotationService binaryTreeRotationService) {
        this.nodeService = nodeService;
        this.binaryTreePrintService = binaryTreePrintService;
        this.binaryTreeRotationService = binaryTreeRotationService;
    }

    @Override
    public void insert(int value) {
        binaryTree.setRoot(insert(binaryTree.getRoot(), value));
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
        if (!search(value)) return false;

        binaryTree.setRoot(remove(binaryTree.getRoot(), value));
        return true;
    }

    @Override
    public void print() {
        binaryTreePrintService.print(binaryTree);
    }

    private Node insert(Node node, int value) {
        if (isNull(node)) {
            return new Node(value);
        }

        if (value < node.getValue()) {
            node.setLeftNode(insert(node.getLeftNode(), value));
        } else if (value > node.getValue()) {
            node.setRightNode(insert(node.getRightNode(), value));
        } else {
            return node;
        }

        nodeService.updateHeight(node);

        int balance = nodeService.getBalance(node);

        if (balance > 1 && value < node.getLeftNode().getValue()) {
            return binaryTreeRotationService.rotateRight(node);
        }

        if (balance < -1 && value > node.getRightNode().getValue()) {
            return binaryTreeRotationService.rotateLeft(node);
        }

        if (balance > 1 && value > node.getLeftNode().getValue()) {
            node.setLeftNode(binaryTreeRotationService.rotateLeft(node.getLeftNode()));
            return binaryTreeRotationService.rotateRight(node);
        }

        if (balance < -1 && value < node.getRightNode().getValue()) {
            node.setRightNode(binaryTreeRotationService.rotateRight(node.getRightNode()));
            return binaryTreeRotationService.rotateLeft(node);
        }

        return node;
    }

    private Node remove(Node node, int value) {
        if (isNull(node)) {
            return null;
        }

        if (value < node.getValue()) {
            node.setLeftNode(remove(node.getLeftNode(), value));
        } else if (value > node.getValue()) {
            node.setRightNode(remove(node.getRightNode(), value));
        } else {
            if (nodeService.notContainsChildren(node)) {
                return nonNull(node.getLeftNode()) ? node.getLeftNode() : node.getRightNode();
            }

            Node substitute = findMin(node.getRightNode());
            node.setValue(substitute.getValue());
            node.setRightNode(remove(node.getRightNode(), substitute.getValue()));
        }

        nodeService.updateHeight(node);

        int balance = nodeService.getBalance(node);

        // LL
        if (balance > 1 && nodeService.getBalance(node.getLeftNode()) >= 0) {
            return binaryTreeRotationService.rotateRight(node);
        }

        // LR
        if (balance > 1 && nodeService.getBalance(node.getLeftNode()) < 0) {
            node.setLeftNode(binaryTreeRotationService.rotateLeft(node.getLeftNode()));
            return binaryTreeRotationService.rotateRight(node);
        }

        // RR
        if (balance < -1 && nodeService.getBalance(node.getRightNode()) <= 0) {
            return binaryTreeRotationService.rotateLeft(node);
        }

        // LL
        if (balance < -1 && nodeService.getBalance(node.getRightNode()) > 0) {
            node.setRightNode(binaryTreeRotationService.rotateRight(node.getRightNode()));
            return binaryTreeRotationService.rotateLeft(node);
        }

        return node;
    }

    private Node findMin(Node node) {
        while (nonNull(node.getLeftNode())) {
            node = node.getLeftNode();
        }

        return node;
    }
}
