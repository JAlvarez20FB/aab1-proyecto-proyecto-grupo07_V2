import java.util.Scanner;
public class SistemaCalculoRandom {


    static Scanner tcl = new Scanner(System.in);
    static final int MAX = 30;

    static String[] nombres = new String[MAX];
    static double[] consumoMensual = new double[MAX];
    static int contador = 0;

    public static void main(String[] args) {

        int n;
        System.out.print("Ingrese el número de estudiantes (1-30): ");
        n = tcl.nextInt();

        if (n <= 0 || n > MAX) {
            System.out.println("Cantidad inválida.");
            return;
        }

        for (int i = 0; i < n; i++) {
            registrarEstudiante();
        }

        mostrarReporte();
    }

    static void registrarEstudiante() {
        System.out.print("\nNombre del estudiante: ");
        nombres[contador] = tcl.next();

        double clases = leerConsumo("Clases (Zoom + Canvas)");
        double redes = leerConsumo("Redes Sociales");
        double videos = leerConsumo("Videos");
        double juegos = leerConsumo("Juegos");
        double tareas = leerConsumo("Tareas");

        double totalGB = clases + redes + videos + juegos + tareas;

        System.out.print("Ingrese días evaluados (1-30): ");
        int dias = tcl.nextInt();
        while (dias <= 0 || dias > 30) {
            System.out.print("Días inválidos. Reingrese: ");
            dias = tcl.nextInt();
        }

        double promedioDiario = totalGB / dias;
        double mensual = promedioDiario * 30;

        consumoMensual[contador] = mensual;

        String plan = recomendarPlan(mensual);
        String sugerencia = generarSugerencia(clases, redes, videos, juegos, tareas);

        System.out.println("Plan recomendado: " + plan);
        System.out.println("Sugerencia: " + sugerencia);

        contador++;
    }

    static double leerConsumo(String actividad) {
        double valor;
        do {
            System.out.print("Consumo en GB para " + actividad + ": ");
            valor = tcl.nextDouble();
        } while (valor < 0);
        return valor;
    }

    static String recomendarPlan(double consumo) {
        if (consumo <= 30) return "Plan Pequeño (30 GB)";
        if (consumo <= 60) return "Plan Mediano (60 GB)";
        return "Plan Grande (80 GB)";
    }

    static String generarSugerencia(double c, double r, double v, double j, double t) {
        if (v > 20) return "Reducir calidad de videos.";
        if (j > (c + t)) return "Demasiado tiempo en juegos.";
        if (c + t > (r + j)) return "Uso académico adecuado.";
        return "Buen equilibrio de consumo.";
    }

    static void mostrarReporte() {
        System.out.println("\n====== REPORTE FINAL ======");
        System.out.println("N°\tNombre\tConsumo Mensual (GB)\tVariación");

        for (int i = 0; i < contador; i++) {
            String variacion = "N/A";
            if (i > 0) {
                if (consumoMensual[i] > consumoMensual[i - 1])
                    variacion = "Aumento";
                else if (consumoMensual[i] < consumoMensual[i - 1])
                    variacion = "Disminución";
                else
                    variacion = "Estable";
            }

            System.out.println((i + 1) + "\t" + nombres[i] + "\t\t" +
                    String.format("%.2f", consumoMensual[i]) + "\t\t" + variacion);
        }
    }
}
/*
run:
------------SISTEMA DE CONSUMO Y RECOMENDACION DE USO DE DATOS-------------
ingrese el numero de jovenes a calcular: 
5
=========== REPORTE FINAL ===========
|N�|	|NOMBRE|		|PROMEDIO DIARIO|	|PROMEDIO MENSUAL|	|PLAN RECOMENDADO|	|SUGERENCIAS|
1	User_1		0,4 GB			12,1 GB		PLAN PEQUENIO: 30 GB		Consumo estable. Buen manejo de datos.
2	User_2		0,7 GB			19,8 GB		PLAN PEQUENIO: 30 GB		Consumo estable. Buen manejo de datos.
3	User_3		5,9 GB			178,2 GB		PLAN GRANDE: 80 GB		Mas tiempo en juegos que en tareas. Reorganice horarios.
4	User_4		1,3 GB			37,9 GB		PLAN MEDIANO: 60 GB		Consumo estable. Buen manejo de datos.
5	User_5		1,0 GB			29,8 GB		PLAN PEQUENIO: 30 GB		Mas tiempo en juegos que en tareas. Reorganice horarios.
*/

