import data.CommandData;
import exception.InvalidCommandException;
import exception.InvalidNumberException;
import service.binarytree.TreeAVLCommandService;
import service.binarytree.TreeAVLPrintService;
import service.binarytree.TreeAVLRotationService;
import service.binarytree.TreeAVLService;
import service.binarytree.impl.TreeAVLCommandServiceImpl;
import service.binarytree.impl.TreeAVLPrintServiceImpl;
import service.binarytree.impl.TreeAVLRotationServiceImpl;
import service.binarytree.impl.TreeAVLServiceImpl;
import service.node.NodeService;
import service.node.impl.NodeServiceImpl;
import utils.Keyboard;

import static utils.StringUtils.notEquals;

public class Main {

    private static final String STOP_WORLD = "stop";

    // Riquelme Maia Rodrigues e Guilherme Anacleto
    public static void main(String[] args) {
        System.out.println("Construa sua árvore agora! Utilize os comandos abaixo para interagir com ela:\n");

        System.out.println("1. search {value}");
        System.out.println("2. insert {value}");
        System.out.println("3. remove {value}");
        System.out.println("4. stop");

        NodeService nodeService = new NodeServiceImpl();
        TreeAVLPrintService treePrintService = new TreeAVLPrintServiceImpl();
        TreeAVLRotationService treeRotationService = new TreeAVLRotationServiceImpl(nodeService);
        TreeAVLService treeService = new TreeAVLServiceImpl(nodeService, treePrintService, treeRotationService);
        TreeAVLCommandService treeCommandService = new TreeAVLCommandServiceImpl(treeService);

        while (true) {
            String input = Keyboard.readString("\nDigite um comando:");

            if (notEquals(input, STOP_WORLD)) {
                try {
                    CommandData data = treeCommandService.splitCommand(input);
                    treeCommandService.executeCommand(data);
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