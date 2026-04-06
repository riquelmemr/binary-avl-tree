package service.binarytree;

import data.CommandData;

public interface TreeAVLCommandService {

    void executeCommand(CommandData commandData);

    CommandData splitCommand(String input);

}
