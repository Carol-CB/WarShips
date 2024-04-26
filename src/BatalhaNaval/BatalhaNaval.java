package BatalhaNaval;

import java.util.Random;
import java.util.Scanner;

public class BatalhaNaval {

    public static char[][] geradorDeMatriz() {
        char[][] matriz = new char[11][11];
        matriz[0] = new char[]{'*', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        for (int i = 1; i < matriz.length; i++) {
            matriz[i][0] = (char) ('0' + (i - 1));
            for (int j = 1; j < matriz[i].length; j++) {
                matriz[i][j] = ' ';
            }
        }
        return matriz;
    }

    public static boolean checkBomba(int[] bomba) {
        return bomba.length == 2 && bomba[0] >= 1 && bomba[0] <= 10 && bomba[1] >= 1 && bomba[1] <= 10;
    }

    public static boolean confereOP(char opcao) {
        return opcao == '1' || opcao == '2';
    }

    public static void mostrarMatrizDeJogo(char[][] matriz) {
        for (char[] linha : matriz) {
            for (char celula : linha) {
                System.out.printf("| %c ", celula);
            }
            System.out.println("|\n! - ! - ! - ! - ! - ! - ! - ! - ! - ! - ! - !");
        }
    }

    public static int[] refatorando(String bomba) {
        return checkBomba(new int[]{bomba.charAt(0) - 'a' + 1, bomba.charAt(1) - '0' + 1}) ? new int[]{bomba.charAt(0) - 'a' + 1, bomba.charAt(1) - '0' + 1} : new int[]{-1, -1};
    }

    public static boolean conferirdirecao(int tamanho, char[][] matriz, int[] bomba, boolean horizontalOUvertical) {
        if (!checkBomba(bomba)) return false;
        int pontaBarco = horizontalOUvertical ? bomba[1] + tamanho - 1 : bomba[0] + tamanho - 1;
        if (pontaBarco > 10) return false;
        for (int i = horizontalOUvertical ? bomba[1] : bomba[0]; i <= pontaBarco; i++) {
            if (horizontalOUvertical && matriz[i][bomba[0]] == 'B' || !horizontalOUvertical && matriz[bomba[1]][i] == 'B') {
                return false;
            }
        }
        return true;
    }

    public static char[][] colocaBarco(int tamanho, char[][] matriz, int[] bomba, boolean horizontalOUvertical) {
        int pontaBarco = horizontalOUvertical ? bomba[1] + tamanho - 1 : bomba[0] + tamanho - 1;
        for (int i = horizontalOUvertical ? bomba[1] : bomba[0]; i <= pontaBarco; i++) {
            matriz[horizontalOUvertical ? i : bomba[1]][horizontalOUvertical ? bomba[0] : i] = 'B';
        }
        return matriz;
    }

    public static int[] gerador() {
        Random aleatorizar = new Random();
        int[] bomba = new int[2];
        do {
            bomba[0] = aleatorizar.nextInt(10) + 1;
            bomba[1] = aleatorizar.nextInt(10) + 1;
        } while (!checkBomba(bomba));
        return bomba;
    }

    public static int[] lancarBomba(char[][] matriz) {
        return gerador();
    }

    public static boolean direcao(Scanner ler) {
        char orientacao = 0;
        boolean direcaoBool = false;
        System.out.print("\n\uD83C\uDFF4\u200D☠\uFE0F\uD83E\uDD9C: Estou posicionando seu navio, marujo! Pra que lado eu viro mesmo? Marujo, o pirata quer saber como deve posicionar seu barco? Vertical[1] ou Horizontal[2]: ");
        do {
            orientacao = ler.next().charAt(0);
        } while (!confereOP(orientacao));
        direcaoBool = orientacao == '1';
        return direcaoBool;
    }

    public static char[][] naviosAutomaticos(char[][] matriz, int num, int tamBarco) {
        Random aleatorizar = new Random();
        int contBarcos = 0;

        while (contBarcos < num) {
            int[] bomba = gerador();
            boolean orientacao = aleatorizar.nextBoolean();
            if (conferirdirecao(tamBarco, matriz, bomba, orientacao)) {
                matriz = colocaBarco(tamBarco, matriz, bomba, orientacao);
                contBarcos++;
            }
        }

        return matriz;
    }

    public static char alocacao(Scanner ler) {
        char autoSN = ' ';
        System.out.println("\n\uD83C\uDFF4\u200D☠\uFE0F\uD83E\uDD9C: Arr! Preciso lhe perguntar chefia. Eu escolho onde colocar essas belezinhas ou o senhor manda? 'Vá enfrente'[1] ou 'Eu comando'[2]: ");
        do {
            autoSN = ler.next().charAt(0);
        } while (!confereOP(autoSN));
        return autoSN;
    }

    public static char[][] posicionaBarco(int tamBarco, char[][] matriz, Scanner ler) {
        int[] bomba = new int[2];
        boolean orientacao = false;
        boolean proximo = false;
        do {
            switch (tamBarco) {
                case 1 -> System.out.print("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Onde coloco esse submarino (ocupa uma posição)? ");
                case 2 -> System.out.print("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Onde coloco esse barco de remo (ocupa duas posições)? ");
                case 3 -> System.out.print("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Onde coloco esse barco (ocupa três posições)? ");
                case 4 -> System.out.print("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Aha! Onde coloco esse Navio Pirata (ocupa quatro posições)? ");
            }
            String pos = ler.next();
            bomba = refatorando(pos);
            proximo = conferirdirecao(tamBarco, matriz, bomba, orientacao);
            if (!proximo) {
                System.out.print("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Argh! Um pirata bobo você, não? O barco não cabe aqui, chefe! Onde coloco? ");
                continue;
            }
            if (tamBarco != 1)
                orientacao = direcao(ler);
            proximo = conferirdirecao(tamBarco, matriz, bomba, orientacao);
            if (!proximo)
                System.out.print("Este barco não cabe ai, chefe! ");
            else
                matriz = colocaBarco(tamBarco, matriz, bomba, orientacao);
        } while (!proximo);
        return matriz;
    }

    public static char[][] manual(char[][] matriz, Scanner ler) {
        mostrarMatrizDeJogo(matriz);
        matriz = posicionaBarco(4, matriz, ler);

        for (int i = 0; i < 2; i++) {
            mostrarMatrizDeJogo(matriz);
            matriz = posicionaBarco(3, matriz, ler);
        }
        for (int i = 0; i < 3; i++) {
            mostrarMatrizDeJogo(matriz);
            matriz = posicionaBarco(2, matriz, ler);
        }
        for (int i = 0; i < 4; i++) {
            mostrarMatrizDeJogo(matriz);
            matriz = posicionaBarco(1, matriz, ler);
        }
        return matriz;
    }

    public static char[][] auto(char[][] matriz) {
        matriz = naviosAutomaticos(matriz, 1, 4);
        matriz = naviosAutomaticos(matriz, 2, 3);
        matriz = naviosAutomaticos(matriz, 3, 2);
        matriz = naviosAutomaticos(matriz, 4, 1);
        return matriz;
    }

    public static int condicaoDaBomba(int[] bomba, char[][] matriz) {
        return switch (matriz[bomba[1]][bomba[0]]) {
            case 'B' -> 1;
            case 'X', 'O' -> 2;
            default -> 3;
        };
    }

    public static char[][] lancarBomba(int[] bomba, char[][] matriz, int condicaoDaBomba) {
        if (condicaoDaBomba == 1) {
            matriz[bomba[1]][bomba[0]] = 'X';
        } else if (condicaoDaBomba == 3) {
            matriz[bomba[1]][bomba[0]] = 'O';
        }
        return matriz;
    }

    public static boolean contar(char[][] matriz) {
        int tiros = 0;
        for (char[] linha : matriz) {
            for (char coluna : linha) {
                if (coluna == 'X')
                    tiros++;
            }
        }
        return tiros == 20;
    }

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        char autoSN = ' ';
        char alocacao = ' ';
        boolean vez = true;
        boolean fim = false;
        int[] bomba = new int[2];
        int bombaRes = 0;
        String ganhador = "";
        boolean proximo = false;
        char[][] matriz1 = geradorDeMatriz();
        char[][] ataque1 = geradorDeMatriz();
        char[][] matriz2 = geradorDeMatriz();
        char[][] ataque2 = geradorDeMatriz();

        System.out.println("\n\uD83C\uDFF4\u200D☠\uFE0F\uD83E\uDD9C: YoHo Marujo! Bem vindo a Batalha Naval! Eu serei seu pirata guia no dia de hoje, como vamos fazer isso? ");
        System.out.println("\n   ⋆༺\uD80C\uDDA9☠\uFE0E\uFE0E\uD80C\uDDAA༻⋆⋆༺\uD80C\uDDA9☠\uFE0E\uFE0E\uD80C\uDDAA༻⋆⋆༺\uD80C\uDDA9☠\uFE0E\uFE0E\uD80C\uDDAA༻⋆ ");
        System.out.println("| Marujo x PirateSoftware(robô) [1] |");
        System.out.println("| Marujo x Marujo               [2] |");
        System.out.println("   ⋆༺\uD80C\uDDA9☠\uFE0E\uFE0E\uD80C\uDDAA༻⋆⋆༺\uD80C\uDDA9☠\uFE0E\uFE0E\uD80C\uDDAA༻⋆⋆༺\uD80C\uDDA9☠\uFE0E\uFE0E\uD80C\uDDAA༻⋆ ");
        do {
            autoSN = ler.next().charAt(0);
            proximo = confereOP(autoSN);
        } while (!proximo);

        if (autoSN == '1') {
            matriz2 = auto(matriz2);
            alocacao = alocacao(ler);
            if (alocacao == '1') {
                matriz1 = auto(matriz1);
            }
            if (alocacao == '2') {
                matriz1 = manual(matriz1, ler);
            }
        }

        if (autoSN == '2') {
            alocacao = alocacao(ler);
            if (alocacao == '1') {
                matriz1 = auto(matriz1);
            }
            if (alocacao == '2') {
                matriz1 = manual(matriz1, ler);
            }
            alocacao = alocacao(ler);
            if (alocacao == '1') {
                matriz2 = auto(matriz2);
            }
            if (alocacao == '2') {
                matriz2 = manual(matriz2, ler);
            }
        }

        do {
            if (vez) {
                System.out.println("\nVez do primeiro Marujo:  ");
                do {
                    mostrarMatrizDeJogo(ataque2);
                    System.out.print("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Posição da bomba, Marujo! [ex; a1]: ");
                    do {
                        String pos = ler.next();
                        bomba = refatorando(pos);
                        proximo = checkBomba(bomba);
                        if (!proximo)
                            System.out.print("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Nay! De novo: ");
                    } while (!proximo);

                    bombaRes = condicaoDaBomba(bomba, matriz2);
                    ataque2 = lancarBomba(bomba, ataque2, bombaRes);
                    matriz2 = lancarBomba(bomba, matriz2, bombaRes);

                    switch (bombaRes) {
                        case 1 -> {
                            System.out.println("\n\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Capitão, acertamos um barco!");
                            vez = true;
                        }
                        case 2 -> {
                            System.out.println("\n\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Arr! Não te vareia, abobado! Você ja jogou uma bomba ai! ");
                            vez = true;
                        }
                        case 3 -> {
                            System.out.println("\n\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Espero que essa bomba na água não mate nenhum peixe. Aha! ");
                            vez = false;
                        }
                    }

                    fim = contar(ataque2);
                    if (fim)
                        ganhador = "Parabéns Marujo 1";
                } while (vez && !fim);
            } else {
                System.out.println("\nVez do segundo Marujo: ");
                do {
                    if (autoSN == '1') {
                        bomba = lancarBomba(matriz1);
                    }

                    if (autoSN == '2') {
                        mostrarMatrizDeJogo(ataque1);
                        System.out.print("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Posição da bomba, Marujo! [ex; a1]: ");
                        do {
                            String pos = ler.next();
                            bomba = refatorando(pos);
                            proximo = checkBomba(bomba);

                            if (!proximo)
                                System.out.print("Nay, de novo: ");
                        } while (!proximo);
                    }

                    bombaRes = condicaoDaBomba(bomba, matriz1);
                    ataque1 = lancarBomba(bomba, ataque1, bombaRes);
                    matriz1 = lancarBomba(bomba, matriz1, bombaRes);

                    if (autoSN == '2')
                        mostrarMatrizDeJogo(ataque1);

                    switch (bombaRes) {
                        case 1 -> {
                            System.out.println("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Capitão, acertamos um barco!\n");
                            vez = false;
                        }
                        case 2 -> {
                            System.out.println("\n\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Arr! Não te vareia, abobado! Você ja jogou uma bomba ai! ");
                            vez = false;
                        }
                        case 3 -> {
                            System.out.println("\n\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Espero que essa bomba na água não mate nenhum peixe. Aha! ");
                            vez = true;
                        }
                    }

                    fim = contar(ataque1);
                    if (fim)
                        ganhador = "Parabéns Marujo 2";
                } while (!vez && !fim);
            }
        } while (!fim);

        System.out.println("\n\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: " + ganhador + "!!");

        if (autoSN == '1')
            mostrarMatrizDeJogo(matriz2);

        if (autoSN == '2')
            mostrarMatrizDeJogo(matriz1);
    }
}
