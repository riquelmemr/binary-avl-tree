package service.node;

import model.Node;

public interface NodeService {

    boolean containsLeftChild(Node node);

    boolean containsRightChild(Node node);

    boolean notContainsChildren(Node node);

    void updateHeight(Node node);

    int getHeight(Node node);

    int getBalance(Node node);

}
