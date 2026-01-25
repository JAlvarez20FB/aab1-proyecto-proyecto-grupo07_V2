
import java.io.File;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

public class SistemaCRUD_V2 {
    static Scanner tcl = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("--- SISTEMA DE CONSUMO Y RECOMENDACION DE DATOS ---");
        System.out.print("Ingrese numero de usuarios a registrar: ");
        int numUser = tcl.nextInt();
        if (numUser <= 0) {
            System.out.println("Numero ingresado invalido");
            return;
        }
        String ingresoNomb[][] = new String[numUser][2];
        double datosIngreso[][] = new double[numUser][7];
        generarIndiceNombre(ingresoNomb);
        generarEntrada(datosIngreso);
        persistirResultados("entradaDatos.csv", ingresoNomb, datosIngreso);
    }

    static void generarIndiceNombre(String indNomb[][]) {
        tcl.nextLine(); // limpiar buffer
        for (int i = 0; i < indNomb.length; i++) {
            indNomb[i][0] = String.valueOf(i + 1);
            System.out.print("Ingrese el nombre y apellido del usuario Nro " + (i + 1) + ": ");
            indNomb[i][1] = tcl.nextLine();
        }
    }

    static void generarEntrada(double gbUsados[][]) {
        Random ale = new Random();
        for (int i = 0; i < gbUsados.length; i++) {

            System.out.println("\n---- USUARIO Nro " + (i + 1) + " ----");
            
            System.out.print("Ingrese el numero de dias a evaluar: ");
            gbUsados [i][0] = tcl.nextInt();

            System.out.print("\n--CLASES--\nMB usados en ZOOM: ");
            double mbZoom = 100 + ale.nextInt(2901);
            System.out.print("MB usados en CANVAS: ");
            double mbCanvas = 100 + ale.nextInt(2901);
            gbUsados[i][1] = (mbZoom + mbCanvas) / 1024;

            System.out.print("\n--REDES SOCIALES--\nMB usados en MESSENGER: ");
            double mbMsger = 100 + ale.nextInt(2901);
            System.out.print("MB usados en WHATSAPP: ");
            double mbWhats = 100 + ale.nextInt(2901);
            gbUsados[i][2] = (mbMsger + mbWhats) / 1024;

            System.out.print("\n--VIDEOS--\nMB usados en TIKTOK: ");
            double mbTikTok = 200 + ale.nextInt(3801);
            System.out.print("MB usados en YOUTUBE: ");
            double mbYoutube = 200 + ale.nextInt(3801);
            gbUsados[i][3] = (mbTikTok + mbYoutube) / 1024;

            System.out.print("\n--JUEGOS--\nMB usados en CODM: ");
            double mbCodm = 300 + ale.nextInt(4701);
            System.out.print("MB usados en FREE FIRE: ");
            double mbFreeFire = 300 + ale.nextInt(4701);
            gbUsados[i][4] = (mbCodm + mbFreeFire) / 1024;

            System.out.print("\n--TAREAS--\nMB usados en CHATGPT: ");
            double mbGpt = 50 + ale.nextInt(1951);
            System.out.print("MB usados en GOOGLE: ");
            double mbGoogle = 100 + ale.nextInt(2001);
            gbUsados[i][5] = (mbGpt + mbGoogle) / 1024;

            System.out.println("""
                    Indique su plan de datos:
                    1. PLAN PEQUEÑO (30 GB)
                    2. PLAN MEDIANO (60 GB)
                    3. PLAN GRANDE (80 GB)
                    """);

            int plan = tcl.nextInt();
            gbUsados[i][6] = generarPlanDatos(plan);
        }
    }

    static int generarPlanDatos(int planUsado) {
        switch (planUsado) {
            case 1:
                return 30;
            case 2:
                return 60;
            case 3:
                return 80;
            default:
                System.out.println("Plan invalido, se asigna 0 GB");
                return 0;
        }
    }

    public static void persistirResultados(String archivoSalida,
                                           String ingresoNomb[][],
                                           double datosIngreso[][]) {
        try {
            Formatter fout = new Formatter(new File(archivoSalida));
            fout.format("Nro;NOMBRE;DIAS;CLASES (GB);REDES (GB);VIDEOS (GB);JUEGOS (GB);TAREAS (GB);PLAN USADO (GB)%n");
            for (int i = 0; i < datosIngreso.length; i++) {
                for (int j = 0; j < ingresoNomb[i].length; j++) {
                    fout.format("%s;", ingresoNomb[i][j]);
                }
                for (int j = 0; j < datosIngreso[i].length; j++) {
                    fout.format("%.2f;", datosIngreso[i][j]);
                }
                fout.format("%n");
            }
            fout.close();
            System.out.println("Archivo generado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al generar el archivo.");
        }
    }
}

