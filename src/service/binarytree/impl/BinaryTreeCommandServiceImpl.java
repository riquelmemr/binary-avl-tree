package service.binarytree.impl;

import data.CommandData;
import exception.InvalidCommandException;
import exception.InvalidNumberException;
import service.binarytree.BinaryTreeCommandService;
import service.binarytree.BinaryTreeService;

public class BinaryTreeCommandServiceImpl implements BinaryTreeCommandService {

    private final BinaryTreeService binaryTreeService;

    public BinaryTreeCommandServiceImpl(BinaryTreeService binaryTreeService) {
        this.binaryTreeService = binaryTreeService;
    }

    @Override
    public void executeCommand(CommandData commandData) {
        switch (commandData.getCommand()) {
            case "search":
                boolean found = binaryTreeService.search(commandData.getValue());
                System.out.println(found ? "Valor encontrado!" : "Valor não encontrado.");
                return;

            case "insert":
                binaryTreeService.insert(commandData.getValue());
                System.out.println("Valor inserido com sucesso!");
                binaryTreeService.print();
                return;

            case "remove":
                boolean removed = binaryTreeService.remove(commandData.getValue());
                System.out.println(removed ? "Valor removido!" : "Valor não encontrado");
                binaryTreeService.print();
                return;

            default:
                System.out.println("Comando desconhecido!");
        }
    }

    @Override
    public CommandData splitCommand(String input) {
        String[] parts = input.split(" ");

        if (parts.length != 2) {
            throw new InvalidCommandException();
        }

        String command = parts[0].toLowerCase();

        int value;

        try {
            value = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new InvalidNumberException();
        }

        CommandData commandData = new CommandData();
        commandData.setCommand(command);
        commandData.setValue(value);

        return commandData;
    }
}
