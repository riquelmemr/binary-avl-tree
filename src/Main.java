import data.CommandData;
import exception.InvalidCommandException;
import exception.InvalidNumberException;
import service.binarytree.BinaryTreeCommandService;
import service.binarytree.BinaryTreePrintService;
import service.binarytree.BinaryTreeRotationService;
import service.binarytree.BinaryTreeService;
import service.binarytree.impl.BinaryTreeCommandServiceImpl;
import service.binarytree.impl.BinaryTreePrintServiceImpl;
import service.binarytree.impl.BinaryTreeRotationServiceImpl;
import service.binarytree.impl.BinaryTreeServiceImpl;
import service.node.NodeService;
import service.node.impl.NodeServiceImpl;
import utils.Keyboard;

import static utils.StringUtils.notEquals;

public class Main {

    private static final String STOP_WORLD = "stop";

    public static void main(String[] args) {
        System.out.println("Construa sua árvore agora! Utilize os comandos abaixo para interagir com ela:\n");

        System.out.println("1. search {value}");
        System.out.println("2. insert {value}");
        System.out.println("3. remove {value}");
        System.out.println("4. stop");

        NodeService nodeService = new NodeServiceImpl();
        BinaryTreePrintService binaryTreePrintService = new BinaryTreePrintServiceImpl();
        BinaryTreeRotationService binaryTreeRotationService = new BinaryTreeRotationServiceImpl(nodeService);
        BinaryTreeService binaryTreeService = new BinaryTreeServiceImpl(nodeService, binaryTreePrintService, binaryTreeRotationService);
        BinaryTreeCommandService binaryTreeCommandService = new BinaryTreeCommandServiceImpl(binaryTreeService);

        while (true) {
            String input = Keyboard.readString("\nDigite um comando:");

            if (notEquals(input, STOP_WORLD)) {
                try {
                    CommandData data = binaryTreeCommandService.splitCommand(input);
                    binaryTreeCommandService.executeCommand(data);
                } catch (InvalidCommandException ex) {
                    System.out.println("Comando inválido! Use o formato: comando valor");
                } catch (InvalidNumberException ex) {
                    System.out.println("Valor inválido! Digite um número inteiro.");
                }
            } else {
                break;
            }
        }
    }
}