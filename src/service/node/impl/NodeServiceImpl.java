package service.node.impl;

import model.Node;
import service.node.NodeService;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class NodeServiceImpl implements NodeService {

    @Override
    public boolean containsLeftChild(Node node) {
        return nonNull(node.getLeftNode());
    }

    @Override
    public boolean containsRightChild(Node node) {
        return nonNull(node.getRightNode());
    }

    @Override
    public boolean notContainsChildren(Node node) {
        return isNull(node.getLeftNode()) && isNull(node.getRightNode());
    }

    @Override
    public void updateHeight(Node node) {
        node.setHeight(1 + Math.max(getHeight(node.getLeftNode()), getHeight(node.getRightNode())));
    }

    @Override
    public int getHeight(Node node) {
        return isNull(node) ? 0 : node.getHeight();
    }

    @Override
    public int getBalance(Node node) {
        if (isNull(node)) {
            return 0;
        }

        node.setBalancingFactor(getHeight(node.getLeftNode()) - getHeight(node.getRightNode()));
        return node.getBalancingFactor();
    }
}
