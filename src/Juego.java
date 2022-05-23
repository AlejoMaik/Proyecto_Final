import java.util.*;

public class Juego {
    static Random random = new Random();
    static Scanner sc = new Scanner(System.in);
    private static final Bicho[][] bichos = new Bicho[3][3];

    Juego() {
        int aleatorio = random.nextInt(9) + 1;
        int veces = (int) Math.ceil((double) aleatorio / 3);
        for (int i = 0; i < veces; i++) {
            for (int j = 0; j < 3; j++) {
                if ((i * 3) + j == aleatorio) break;
                int aleatorio2 = random.nextInt(3) + 1;
                switch (aleatorio2) {
                    case 1 -> bichos[i][j] = new BichoNormal();
                    case 2 -> bichos[i][j] = new BichoAlien();
                    case 3 -> bichos[i][j] = new BichoEspacial();
                }
            }
        }

        imprimirTablero();

        do {
            System.out.println("Presione 1 para disparar una bala");
            System.out.println("Presione 2 para activar una bomba atómica");
            System.out.println("Presione 3 para activar un bicho mutante");
            System.out.println("Presione 4 para intercambiar posiciones");
            System.out.println("Presione 5 para que el bicho con menos salud pase a ser de tipo espacial");
            System.out.println("Presione 6 para matar todos los bichos de una fila aleatoria");
            System.out.println("Presione 7 para ver el promedio de salud de los bichos vivos");
            System.out.println("Presione 8 para ver una frase inspiradora de la abuela");
            System.out.print(":");

            int numero = sc.nextInt();
            switch (numero) {
                case 1 -> dispararBala();
                case 2 -> activarBomba();
                case 3 -> bichoMutante();
                case 4 -> intercambioPosiciones();
                case 5 -> conversionSangre();
                case 6 -> bombaFila();
                case 7 -> promedioSalud();
                case 8 -> fraseAbuela();
            }
        } while (hayBichos());

        System.out.println("Has ganado!!!!!!!");
    }

    public static void imprimirTablero() {
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.println("-------------------");
            for (int j = 0; j < 3; j++) {
                if (bichos[i][j] == null || bichos[i][j].getSalud() == 0) {
                    System.out.print("|     ");
                } else {
                    System.out.print("|" + bichos[i][j].getIniciales() + "-" + bichos[i][j].getSalud());
                }
            }
            System.out.println("|");
        }
        System.out.println("-------------------\n");
    }

    public static void dispararBala() {
        System.out.print("Fila: ");
        int fila = sc.nextInt();
        System.out.print("Columna: ");
        int columna = sc.nextInt();
        bichos[fila - 1][columna - 1].setSalud(bichos[fila - 1][columna - 1].getSalud() - 5);
        imprimirTablero();
    }

    public static void activarBomba() {
        int fila, columna;
        fila = random.nextInt(3);
        columna = random.nextInt(3);
        while (bichos[fila][columna] == null || bichos[fila][columna].getSalud() == 0) {
            fila = random.nextInt(3);
            columna = random.nextInt(3);
        }
        bichos[fila][columna].setSalud(0);
        imprimirTablero();
    }

    public static int[] bichoMenosSalud() {
        Bicho menosSalud = bichos[0][0];
        int f = 0, c = 0;
        out:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (bichos[i][j] != null && bichos[i][j].getSalud() != 0) {
                    menosSalud = bichos[i][j];
                    f = i;
                    c = j;
                    break out;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (bichos[i][j] != null && bichos[i][j].getSalud() > 0 && bichos[i][j].getSalud() < menosSalud.getSalud()) {
                    menosSalud = bichos[i][j];
                    f = i;
                    c = j;
                }
            }
        }

        return new int[]{f, c};
    }

    public static void bichoMutante() {
        bichos[bichoMenosSalud()[0]][bichoMenosSalud()[1]].setSalud(bichos[bichoMenosSalud()[0]][bichoMenosSalud()[1]].getSalud() * 2);
        imprimirTablero();
    }

    public static void intercambioPosiciones() {
        int f1 = random.nextInt(3);
        int c1 = random.nextInt(3);
        int f2 = random.nextInt(3);
        int c2 = random.nextInt(3);

        while (bichos[f1][c1] == null || bichos[f1][c1].getSalud() == 0 || (f1 == f2 && c1 == c2)) {
            f1 = random.nextInt(3);
            c1 = random.nextInt(3);
        }

        final Bicho temp = bichos[f1][c1];
        bichos[f1][c1] = bichos[f2][c2];
        bichos[f2][c2] = temp;
        System.out.println("Se ha intercambiado el bicho de la posición ("+(f1+1)+"."+(c1+1)+") con ("+(f2+1)+"."+(c2+1)+")");
        imprimirTablero();
    }

    public static void conversionSangre() {
        int f = bichoMenosSalud()[0];
        int c = bichoMenosSalud()[1];
        int salud = bichos[f][c].getSalud();
        bichos[f][c] = new BichoEspacial();
        bichos[f][c].setSalud(salud);
        imprimirTablero();
    }

    public static void bombaFila() {
        int fila = random.nextInt(3);
        for (int i = 0; i < 3; i++) {
            if (bichos[fila][i] != null) {
                bichos[fila][i].setSalud(0);
            }
        }
        imprimirTablero();
    }

    public static void promedioSalud() {
        int suma = 0;
        int contador = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (bichos[i][j] != null && bichos[i][j].getSalud() > 0) {
                    suma += bichos[i][j].getSalud();
                    contador++;
                }
            }
        }
        System.out.println("El promedio de salud de los bichos vivos es " + (double) suma / contador);
    }

    public static void fraseAbuela() {
        System.out.println("MUERTO POR ZUNGA!!! hahahaha");
    }

    public static boolean hayBichos() {
        boolean hay = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (bichos[i][j] != null && bichos[i][j].getSalud() > 0) {
                    hay = true;
                    break;
                }
            }
        }
        return hay;
    }
}