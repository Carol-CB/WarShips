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
        if (bomba.length != 2) {
            return false; //false se não houver exatamente dois elementos
        } else {
            int coluna = bomba[0];
            int linha = bomba[1];
            return coluna >= 1 && coluna <= 10 && linha >= 1 && linha <= 10; //verdadeiro se as coordenadas estiverem dentro dos limites
        }
    }

    public static boolean confereOP(char opcao) {
        if (opcao == '1' || opcao == '2') {
            return true;
        } else {
            System.out.print("\n\uD83C\uDFF4\u200D☠\uFE0F\uD83E\uDD9C:OHO, Marujo! Está me testando, espertinho? " +
                    "Digite uma opção válida desta vez [1/2]: ");
            return false;
        }
    }

    public static void mostrarMatrizDeJogo(char[][] matriz) {
        for (char[] linha : matriz) {
            for (int j = 0; j < linha.length; j++) {
                System.out.printf("| %c ", linha[j]);
            }
            System.out.print("|");
            System.out.println("\n! - ! - ! - ! - ! - ! - ! - ! - ! - ! - ! - !");
        }
    }

    //converter a entrada da bomba em coordenadas legíveis para o jogo
    public static int[] refatorando(String bomba) {
        if (bomba.length() != 2)
            bomba = "x";
        return new int[]{bomba.charAt(0) - 'a' + 1, bomba.charAt(1) - '0' + 1};
    }

    public static boolean conferirdirecao(int tamanho, char[][] matriz, int[] bomba, boolean horizontalOUvertical) {
        //verifica se as coordenadas da bomba estão dentro dos limites da matriz
        if (bomba[0] < 1 || bomba[0] > 10 || bomba[1] < 1 || bomba[1] > 10) {
            return false;
        }
        int pontaBarco = horizontalOUvertical ? bomba[1] + tamanho - 1 : bomba[0] + tamanho - 1;
        if (pontaBarco > 10) {
            return false;
        }
        for (int i = horizontalOUvertical ? bomba[1] : bomba[0]; i <= pontaBarco; i++) {
            if (horizontalOUvertical) {
                if (matriz[i][bomba[0]] == 'B') {
                    return false;
                }
            } else {
                if (matriz[bomba[1]][i] == 'B') {
                    return false;
                }
            }
        }
        return true;
    }

    public static char[][] colocaBarco(int tamanho, char[][] matriz, int[] bomba, boolean horizontalOUvertical) {
        int pontaBarco = horizontalOUvertical ? bomba[1] + tamanho - 1 : bomba[0] + tamanho - 1;
        for (int i = horizontalOUvertical ? bomba[1] : bomba[0]; i <= pontaBarco; i++) {
            if (horizontalOUvertical) {
                matriz[i][bomba[0]] = 'B';
            } else {
                matriz[bomba[1]][i] = 'B';
            }
        }
        return matriz;
    }

    // gerar coordenadas aleatórias dentro dos limites da matriz
    public static int[] gerador() {
        Random aleatorizar = new Random();
        int[] bomba = new int[2];
        do {
            bomba[0] = aleatorizar.nextInt(10) + 1;
            bomba[1] = aleatorizar.nextInt(10) + 1;
        } while (!checkBomba(bomba));
        return bomba;
    }

    //lançar uma bomba em uma posição aleatória
    public static int[] lancarBomba(char[][] matriz) {
        Random aleatorizar = new Random();
        int[] bomba = new int[2];
        do {
            bomba[0] = aleatorizar.nextInt(10) + 1;
            bomba[1] = aleatorizar.nextInt(10) + 1;
        } while (matriz[bomba[1]][bomba[0]] == 'X' || matriz[bomba[1]][bomba[0]] == 'A' || !checkBomba(bomba));
        return bomba;
    }

    public static boolean direcao() {
        Scanner ler = new Scanner(System.in);
        boolean proximo = false;
        char orientacao = 0;
        boolean direcaoBool = false;

        System.out.print("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C:Estou posicionando seu navio, marujo! Pra que lado eu viro mesmo?");
        System.out.print("Marujo, o pirata quer saber como deve posicionar seu barco? Vertical[1] ou Horizontal[2]: ");
        do {
            orientacao = ler.next().charAt(0);
            proximo = confereOP(orientacao);
        } while (!proximo);
        if (orientacao == '1') {
            direcaoBool = true;
        }
        return direcaoBool;
    }

    //posicionar automaticamente os navios na matriz
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

    public static char alocacao() {
        Scanner ler = new Scanner(System.in);
        boolean proximo = false;
        char autoSN = ' ';

        System.out.println("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C:Arr! Preciso lhe perguntar chefia. Eu escolho onde " +
                "colocar essas belezinhas ou o senhor manda? ");
        System.out.print("'Vá enfrente'[1] ou 'Eu comando'[2]: ");
        do {
            autoSN = ler.next().charAt(0);
            proximo = confereOP(autoSN);
        } while (!proximo);
        return autoSN;
    }

    public static char[][] posicionaBarco(int tamBarco, char[][] matriz) {
        Scanner ler = new Scanner(System.in);
        int[] bomba = new int[2];
        boolean orientacao = false;
        boolean proximo = false;
        do {
            switch (tamBarco) {
                case 1 ->
                        System.out.print("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Onde coloco esse submarino (ocupa uma posição)? ");
                case 2 ->
                        System.out.print("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Onde coloco esse barco de remo (ocupa duas posições)? ");
                case 3 ->
                        System.out.print("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Onde coloco esse barco (ocupa três posições)? ");
                case 4 ->
                        System.out.print("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Aha! Onde coloco esse Navio Pirata (ocupa " +
                                "quatro posições)? ");
            }
            String pos = ler.next();
            bomba = refatorando(pos);
            proximo = conferirdirecao(tamBarco, matriz, bomba, orientacao);
            if (!proximo) {
                System.out.print("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Argh! Um pirata bobo você, não? O barco não cabe" +
                        " aqui, chefe! Onde coloco? ");
                continue;
            }
            if (tamBarco != 1)
                orientacao = direcao();
            proximo = conferirdirecao(tamBarco, matriz, bomba, orientacao);
            if (!proximo)
                System.out.print("Este barco não cabe ai, chefe! ");
            else
                matriz = colocaBarco(tamBarco, matriz, bomba, orientacao);
        } while (!proximo);
        return matriz;
    }

    //posicionar os barcos manualmente
    public static char[][] manual(char[][] matriz) {
        mostrarMatrizDeJogo(matriz);
        matriz = posicionaBarco(4, matriz);

        for (int i = 0; i < 2; i++) {
            mostrarMatrizDeJogo(matriz);
            matriz = posicionaBarco(3, matriz);
        }
        for (int i = 0; i < 3; i++) {
            mostrarMatrizDeJogo(matriz);
            matriz = posicionaBarco(2, matriz);
        }
        for (int i = 0; i < 4; i++) {
            mostrarMatrizDeJogo(matriz);
            matriz = posicionaBarco(1, matriz);
        }
        return matriz;
    }

    //posicionar automaticamente os barcos
    public static char[][] auto(char[][] matriz) {
        matriz = naviosAutomaticos(matriz, 1, 4);
        matriz = naviosAutomaticos(matriz, 2, 3);
        matriz = naviosAutomaticos(matriz, 3, 2);
        matriz = naviosAutomaticos(matriz, 4, 1);
        return matriz;
    }

    public static int condicaoDaBomba(int[] bomba, char[][] matriz) {
        return switch (matriz[bomba[1]][bomba[0]]) {
            case 'B' -> 1;     //retorna 1 se atingir um barco
            case 'X', 'O' -> 2; //retorna 2 se a posição já foi atingida anteriormente
            default -> 3;      //retorna 3 se a bomba caiu na água
        };
    }

    public static char[][] lancarBomba(int[] bomba, char[][] matriz, int condicaoDaBomba) {
        if (condicaoDaBomba == 1) {
            matriz[bomba[1]][bomba[0]] = 'X'; //a posição do barco atingido
        } else if (condicaoDaBomba == 3) {
            matriz[bomba[1]][bomba[0]] = 'O'; //a posição da água
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
        return tiros == 20; //verdadeiro se todos os navios forem atingidos
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
        boolean continua = false;
        boolean proximo = false;
        char[][] matriz1 = geradorDeMatriz();
        char[][] ataque1 = geradorDeMatriz();
        char[][] matriz2 = geradorDeMatriz();
        char[][] ataque2 = geradorDeMatriz();

        System.out.println("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Isto vai ser uma briga justa! Preparem-se, pois " +
                "estamos prestes a iniciar a Batalha Naval! ");

        System.out.println("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Antes de iniciarmos a Batalha Naval, uma questão: ");
        System.out.print("Quem decide a localização dos barcos? (Você ou o Pirata?)\n1. Você\n2. O Pirata\nResposta: ");

        do {
            alocacao = ler.next().charAt(0);
            proximo = confereOP(alocacao);
        } while (!proximo);

        if (alocacao == '1') {
            matriz1 = manual(matriz1);
            matriz2 = auto(matriz2);
        } else {
            matriz1 = auto(matriz1);
            matriz2 = manual(matriz2);
        }

        while (!fim) {
            System.out.println("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Iniciando ataque!");
            System.out.println("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: Tá na hora de atirar no navio, Marujo! ");
            if (vez) {
                mostrarMatrizDeJogo(ataque1);
                System.out.print("Informe a posição da bomba [exemplo: A1]: ");
                do {
                    String pos = ler.next();
                    bomba = refatorando(pos);
                    bombaRes = condicaoDaBomba(bomba, matriz2);
                    continua = bombaRes == 2 || bombaRes == 3;
                    if (continua) {
                        System.out.print("Já atiramos aqui, chefe! Onde é o próximo ponto? ");
                    }
                } while (continua);
                ataque1 = lancarBomba(bomba, matriz2, bombaRes);
                fim = contar(matriz2);
                vez = false;
            } else {
                mostrarMatrizDeJogo(ataque2);
                bomba = lancarBomba(matriz1);
                bombaRes = condicaoDaBomba(bomba, matriz1);
                ataque2 = lancarBomba(bomba, matriz1, bombaRes);
                fim = contar(matriz1);
                vez = true;
            }
        }

        if (vez)
            ganhador = "Pirata";
        else
            ganhador = "Marujo";

        System.out.println("\n\uD83C\uDFF4\u200D☠️\uFE0F\uD83E\uDD9C: A batalha terminou! O grande vencedor é: " + ganhador + "! Parabéns!");
    }
}
