package Lab05.F;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author vladislav.trofimov@emc.com
 */
public class quack {

    private static final String INPUT_FILE_NAME = "quack.in";
    private static final String OUTPUT_FILE_NAME = "quack.out";

    private static ArrayList<String> commandQueue = new ArrayList<>(100_000);
    private static LinkedList<Long> virtualMachineQueue = new LinkedList<>();
    private static HashMap<String, Integer> labelIndex = new HashMap<>(100_000);
    private static HashMap<Character, Long> registers = new HashMap<>(26);
    private static int commandIndex = 0;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        out = new PrintWriter(OUTPUT_FILE_NAME);

        while (in.ready()) {

            String command = in.readLine();

            if (command.charAt(0) == ':') {
                processAddLabel(command.substring(1), commandQueue.size());
            }

            commandQueue.add(command);
        }

        if (!commandQueue.isEmpty()) {

            while (commandIndex != -1) {
                processCommand(commandQueue.get(commandIndex));
            }

        }

        in.close();
        out.close();
    }

    private static void goToNextCommand() {
        commandIndex = ((commandIndex + 1) == commandQueue.size()) ? -1 : commandIndex + 1;
    }

    private static void processAddCommand() {
        long x = virtualMachineQueue.removeFirst();
        long y = virtualMachineQueue.removeFirst();
        virtualMachineQueue.addLast((x + y) % 65536);
    }

    private static void processSubCommand() {
        long x = virtualMachineQueue.removeFirst();
        long y = virtualMachineQueue.removeFirst();
        long res = (x - y) % 65536;

        if (res < 0) {
            res += 65536;
        }

        virtualMachineQueue.addLast(res);
    }

    private static void processMulCommand() {
        long x = virtualMachineQueue.removeFirst();
        long y = virtualMachineQueue.removeFirst();
        virtualMachineQueue.addLast((x * y) % 65536);
    }

    private static void processDivCommand() {
        long x = virtualMachineQueue.removeFirst();
        long y = virtualMachineQueue.removeFirst();
        virtualMachineQueue.addLast((y == 0) ? 0 : x / y);
    }

    private static void processModCommand() {
        long x = virtualMachineQueue.removeFirst();
        long y = virtualMachineQueue.removeFirst();
        virtualMachineQueue.addLast((y == 0) ? 0 : x % y);
    }

    private static void processPutToRegister(char register) {
        registers.put(register, virtualMachineQueue.removeFirst());
    }

    private static void processGetFromRegister(char register) {
        virtualMachineQueue.addLast(registers.get(register));
    }

    private static void processPrintFromQueue() {
        out.println(virtualMachineQueue.removeFirst());
    }

    private static void processPrintFromRegister(char register) {
        out.println(registers.get(register));
    }

    private static void processPrintCharFromQueue() {
        out.print((char) (virtualMachineQueue.removeFirst() % 256));
    }

    private static void processPrintCharFromRegister(char register) {
        out.print((char) (registers.get(register) % 256));
    }

    private static void processAddLabel(String label, int commandIndex) {
        labelIndex.put(label, commandIndex);
    }

    private static void processGoToLabel(String label) {
        commandIndex = labelIndex.get(label);
    }

    private static void processGoToLabelIfZero(char register, String label) {
        if (registers.get(register) == 0) {
            processGoToLabel(label);
        }
    }

    private static void processGoToLabelIfEquals(char register1, char register2, String label) {
        if (registers.get(register1).longValue() == registers.get(register2).longValue()) {
            processGoToLabel(label);
        }
    }

    private static void processGoToLabelIfGreater(char register1, char register2, String label) {
        if (registers.get(register1) > registers.get(register2)) {
            processGoToLabel(label);
        }
    }

    private static void processQuit() {
        commandIndex = -1;
    }

    private static void processPutNumber(long value) {
        virtualMachineQueue.addLast(value);
    }

    private static void processCommand(String commandLine) {

        char command = commandLine.charAt(0);

        switch (command) {

            case '+':
                processAddCommand();
                goToNextCommand();
                break;
            case '-':
                processSubCommand();
                goToNextCommand();
                break;
            case '*':
                processMulCommand();
                goToNextCommand();
                break;
            case '/':
                processDivCommand();
                goToNextCommand();
                break;
            case '%':
                processModCommand();
                goToNextCommand();
                break;
            case '>':
                processPutToRegister(commandLine.charAt(1));
                goToNextCommand();
                break;
            case '<':
                processGetFromRegister(commandLine.charAt(1));
                goToNextCommand();
                break;
            case 'P':
                switch (commandLine.length()) {
                    case 1:
                        processPrintFromQueue();
                        goToNextCommand();
                        break;
                    case 2:
                        processPrintFromRegister(commandLine.charAt(1));
                        goToNextCommand();
                        break;
                }
                break;
            case 'C':
                switch (commandLine.length()) {
                    case 1:
                        processPrintCharFromQueue();
                        goToNextCommand();
                        break;
                    case 2:
                        processPrintCharFromRegister(commandLine.charAt(1));
                        goToNextCommand();
                        break;
                }
                break;
            case ':':
                goToNextCommand();
                break;
            case 'J':
                processGoToLabel(commandLine.substring(1));
                goToNextCommand();
                break;
            case 'Z':
                processGoToLabelIfZero(commandLine.charAt(1), commandLine.substring(2));
                goToNextCommand();
                break;
            case 'E':
                processGoToLabelIfEquals(commandLine.charAt(1), commandLine.charAt(2), commandLine.substring(3));
                goToNextCommand();
                break;
            case 'G':
                processGoToLabelIfGreater(commandLine.charAt(1), commandLine.charAt(2), commandLine.substring(3));
                goToNextCommand();
                break;
            case 'Q':
                processQuit();
                break;
            default:
                processPutNumber(Long.parseLong(commandLine));
                goToNextCommand();
                break;

        }
    }

}
