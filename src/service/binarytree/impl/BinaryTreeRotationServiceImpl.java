package service.binarytree.impl;

import model.Node;
import service.binarytree.BinaryTreeRotationService;
import service.node.NodeService;

public class BinaryTreeRotationServiceImpl implements BinaryTreeRotationService {

    private final NodeService nodeService;

    public BinaryTreeRotationServiceImpl(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Override
    public Node rotateLeft(Node node) {
        Node rightNode = node.getRightNode();
        Node t2 = rightNode.getLeftNode();

        rightNode.setLeftNode(node);
        node.setRightNode(t2);

        nodeService.updateHeight(node);
        nodeService.updateHeight(rightNode);

        return rightNode;
    }

    @Override
    public Node rotateRight(Node node) {
        Node leftNode = node.getLeftNode();
        Node t2 = leftNode.getRightNode();

        leftNode.setRightNode(node);
        node.setLeftNode(t2);

        nodeService.updateHeight(node);
        nodeService.updateHeight(leftNode);

        return leftNode;
    }
}
