package service.node;

import model.Node;

public interface NodeService {

    boolean containsLeftChild(Node node);

    boolean containsRightChild(Node node);

    boolean notContainsChildren(Node node);

}
