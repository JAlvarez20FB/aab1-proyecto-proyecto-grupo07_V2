
import java.io.File;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

public class SistemaCRUD_random_v2 {
    static Random ale = new Random();    
    static Scanner tcl = new Scanner(System.in);

    public static void main(String[] args) {
        int numUser = 1+ ale.nextInt(21); //numero de usarios
        String nombres[][] = new String[numUser][2]; // matriz de indice y nombre
        double datosIngreso[][] = new double[numUser][7]; // matriz de datos de ingreso
        generarNombres(nombres);
        generarEntrada(datosIngreso);
        persistirResultados("entradaDatos.csv", nombres, datosIngreso);
    }
    static void generarNombres(String nombres[][]) {
        String nombresBase[] = {
            "Ana", "Luis", "Maria", "Carlos", "Sofia",
            "Pedro", "Lucia", "Juan", "Andrea", "Miguel"
        };
        String apellidosBase[] = {
            "Torres", "Lopez", "Perez", "Ruiz", "Vega",
            "Mendoza", "Castro", "Ramos", "Ortiz", "Silva"
        };
        for (int i = 0; i < nombres.length; i++) {
            nombres[i][0] = String.valueOf(i + 1);
            nombres[i][1] =
                nombresBase[i % nombresBase.length] + " " +
                apellidosBase[i % apellidosBase.length];
        }
    }
    static void generarEntrada(double gbUsados[][]) {
        for (int i = 0; i < gbUsados.length; i++) {
            int dias = 1+ ale.nextInt(21); //dias a evaluar
            gbUsados [i][0] = dias;
            //se ingresa mb consumidos por el usuario en cada una de las categorias
            //CLASES
            double mbZoom = 100 + ale.nextInt(2901); //
            double mbCanvas = 100 + ale.nextInt(2901);
            double clases = (mbZoom + mbCanvas) / 1024; // se transforma de mb a gb
            gbUsados[i][1] = clases;
            //REDES SOCIALES
            double mbMsger = 100 + ale.nextInt(2901);
            double mbWhats = 100 + ale.nextInt(2901);
            double redes = (mbMsger + mbWhats) / 1024;
            gbUsados[i][2] = redes;
            //VIDEOS
            double mbTikTok = 200 + ale.nextInt(3801);
            double mbYoutube = 200 + ale.nextInt(3801);
            double videos = (mbTikTok + mbYoutube) / 1024;
            gbUsados[i][3] = videos;
            //JUEGOS
            double mbCodm = 300 + ale.nextInt(4701);
            double mbFreeFire = 300 + ale.nextInt(4701);
            double juegos = (mbCodm + mbFreeFire) / 1024;
            gbUsados[i][4] = juegos;
            //TAREAS
            double mbGpt = 50 + ale.nextInt(1951);
            double mbGoogle = 100 + ale.nextInt(2001);
            double tareas = (mbGpt + mbGoogle) / 1024;
            gbUsados[i][5] = tareas;
            //plan de datos
            int plan = ale.nextInt(3);
            gbUsados[i][6] = generarPlanDatos(plan);
        }
    }

    static int generarPlanDatos(int planUsado) {
    int plandatos[] = {30, 60, 80};
    return plandatos[planUsado];
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