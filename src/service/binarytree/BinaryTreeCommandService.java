package service.binarytree;

import data.CommandData;

public interface BinaryTreeCommandService {

    void executeCommand(CommandData commandData);

    CommandData splitCommand(String input);

}
