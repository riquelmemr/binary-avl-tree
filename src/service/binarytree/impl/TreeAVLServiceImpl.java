package service.binarytree.impl;

import model.TreeAVL;
import model.Node;
import service.binarytree.TreeAVLPrintService;
import service.binarytree.TreeAVLRotationService;
import service.binarytree.TreeAVLService;
import service.node.NodeService;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class TreeAVLServiceImpl implements TreeAVLService {

    private final TreeAVL treeAVL = new TreeAVL();

    private final NodeService nodeService;
    private final TreeAVLPrintService treeAVLPrintService;
    private final TreeAVLRotationService treeAVLRotationService;

    public TreeAVLServiceImpl(NodeService nodeService,
                              TreeAVLPrintService treeAVLPrintService,
                              TreeAVLRotationService treeAVLRotationService) {
        this.nodeService = nodeService;
        this.treeAVLPrintService = treeAVLPrintService;
        this.treeAVLRotationService = treeAVLRotationService;
    }

    @Override
    public void insert(int value) {
        treeAVL.setRoot(insert(treeAVL.getRoot(), value));
    }

    @Override
    public boolean search(int value) {
        Node currentNode = treeAVL.getRoot();

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

        treeAVL.setRoot(remove(treeAVL.getRoot(), value));
        return true;
    }

    @Override
    public void print() {
        treeAVLPrintService.print(treeAVL);
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
            System.out.println("Chave " + node.getValue() + " está desbalanceada. Vamos iniciar o balanceamento...");
            System.out.println("Rotação simples à direita realizada!");
            return treeAVLRotationService.rotateRight(node);
        }

        if (balance < -1 && value > node.getRightNode().getValue()) {
            System.out.println("Chave " + node.getValue() + " está desbalanceada. Vamos iniciar o balanceamento...");
            System.out.println("Rotação simples à esquerda realizada!");
            return treeAVLRotationService.rotateLeft(node);
        }

        if (balance > 1 && value > node.getLeftNode().getValue()) {
            System.out.println("Chave " + node.getValue() + " está desbalanceada. Vamos iniciar o balanceamento...");
            System.out.println("Rotação dupla à direita realizada!");
            node.setLeftNode(treeAVLRotationService.rotateLeft(node.getLeftNode()));
            return treeAVLRotationService.rotateRight(node);
        }

        if (balance < -1 && value < node.getRightNode().getValue()) {
            System.out.println("Chave " + node.getValue() + " está desbalanceada. Vamos iniciar o balanceamento...");
            System.out.println("Rotação dupla à esquerda realizada!");
            node.setRightNode(treeAVLRotationService.rotateRight(node.getRightNode()));
            return treeAVLRotationService.rotateLeft(node);
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

        if (balance > 1 && nodeService.getBalance(node.getLeftNode()) >= 0) {
            System.out.println("Chave " + node.getValue() + " está desbalanceada. Vamos iniciar o balanceamento...");
            System.out.println("Rotação simples à direita realizada!");
            return treeAVLRotationService.rotateRight(node);
        }

        if (balance > 1 && nodeService.getBalance(node.getLeftNode()) < 0) {
            System.out.println("Chave " + node.getValue() + " está desbalanceada. Vamos iniciar o balanceamento...");
            System.out.println("Rotação dupla à direita realizada!");
            node.setLeftNode(treeAVLRotationService.rotateLeft(node.getLeftNode()));
            return treeAVLRotationService.rotateRight(node);
        }

        if (balance < -1 && nodeService.getBalance(node.getRightNode()) <= 0) {
            System.out.println("Chave " + node.getValue() + " está desbalanceada. Vamos iniciar o balanceamento...");
            System.out.println("Rotação simples à esquerda realizada!");
            return treeAVLRotationService.rotateLeft(node);
        }

        if (balance < -1 && nodeService.getBalance(node.getRightNode()) > 0) {
            System.out.println("Chave " + node.getValue() + " está desbalanceada. Vamos iniciar o balanceamento...");
            System.out.println("Rotação dupla à esquerda realizada!");
            node.setRightNode(treeAVLRotationService.rotateRight(node.getRightNode()));
            return treeAVLRotationService.rotateLeft(node);
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
