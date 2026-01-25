import java.util.Scanner;
/*
Proyecto-06 ABP: "Sistema para Calcular y Recomendar Consumo de Internet Mensual"
Muchos estudiantes utilizan aviones de datos móviles para clases, redes sociales
y entretenimiento, sin saber si su consumo es adecuado o si están gastando de más.
Producto final esperado
Registro de megabytes o gigabytes usados ​​por actividad (clases, redes sociales
videos, juegos, tareas).
Cálculo de consumo total diario y mensual.
Recomendación de plan de datos (pequeño, mediano o grande).
Sugerencias para optimizar el uso.
Almacenamiento de consumo histórico, análisis de promedios y detección de 
aumento o disminución del gasto.
@Autor : Jonathan Alvarez
version
*/
public class SistemaCalcularyRecomendarConsumoInternetMensual {
    public static void main(String[] args) {
        Scanner tcl = new Scanner(System.in);
        double MB_clase, MB_Zoom, MB_Canvas;
        double MB_redes, MB_M, MB_Whats;
        double MB_videos, MB_TT, MB_YT;
        double MB_juegos, MB_CR, MB_FF;
        double MB_tarea, MB_google, MB_chatgpt;
        int Dias;
        double MB_suma, GB_suma, P_diario, P_mensual;
        String nombre_u = "User_";
        int lim, cont = 1;
        String plan, sugerencia = "", reporte = "";
        System.out.println("------------SISTEMA DE CONSUMO Y RECOMENDACION DE USO DE DATOS-------------");
        System.out.println("ingresse el numero de estudiantes a registrar: ");
        lim = tcl.nextInt();
        if (lim <=0){
            System.out.println("numero ingresado invalido");
            return;
        }else{
        while (lim > 0) {
            System.out.println("ingrese el nombre: ");
            nombre_u = tcl.next();
            System.out.println("Ingrese la cantidad de MB usados: ");
            System.out.println("--CLASES-- ");
            System.out.print("Zoom (GB): ");
            MB_Zoom = tcl.nextDouble();
            System.out.print("Canvas (GB): ");
            MB_Canvas = tcl.nextDouble();
            MB_clase = MB_Zoom + MB_Canvas;
            System.out.println("--REDES SOCIALES--");
            System.out.print("Messenger (GB): ");
            MB_M = tcl.nextDouble();
            System.out.print("WhatsApp (GB): ");
            MB_Whats = tcl.nextDouble();
            MB_redes = MB_M + MB_Whats;
            System.out.println("--VIDEOS-- ");
            System.out.print("TikTok (GB): ");
            MB_TT = tcl.nextDouble();
            System.out.print("YouTube (GB): ");
            MB_YT = tcl.nextDouble();
            MB_videos = MB_TT + MB_YT;
            System.out.println("--JUEGOS--");
            System.out.print("Clash Royale (GB): ");
            MB_CR = tcl.nextDouble();
            System.out.print("Free Fire (GB): ");
            MB_FF = tcl.nextDouble();
            MB_juegos = MB_CR + MB_FF;
            System.out.println("--TAREAS--");
            System.out.print("ChatGPT (GB): ");
            MB_chatgpt = tcl.nextDouble();
            System.out.print("Google (GB): ");
            MB_google = tcl.nextDouble();
            MB_tarea = MB_chatgpt + MB_google;
            MB_suma = MB_clase + MB_redes + MB_videos + MB_juegos + MB_tarea;
            GB_suma = MB_suma /1024;
            System.out.print("Ingrese el numero de dias a evaluar: ");
            Dias = tcl.nextInt();
            P_diario = GB_suma / Dias;
            P_mensual = P_diario *30;
            if (P_mensual <= 30){
                plan = " PLAN PEQUENIO: 30 GIGAS";
            }else if (P_mensual <= 60){
                plan = "PLAN MEDIANO 60 GIGAS";
            }else { 
                plan = "PLAN GRANDE: 80 GIGAS";
            }if (MB_videos >= 25) {
                sugerencia = "Consumo excesivo en videos. Reduzca la calidad.";
            } else if (MB_juegos >= 50) {
                sugerencia = "Consumo alto en videojuegos. Reduzca FPS.";
            } else if ((MB_tarea + MB_clase) < MB_juegos) {
                sugerencia = "Mas tiempo en juegos que en tareas. Reorganice horarios.";
            } else if (MB_chatgpt > (MB_clase + MB_google)) {
                sugerencia = "Uso muy alto de ChatGPT. Evite dependencia.";
            } else {
                sugerencia = "Consumo estable. Buen manejo de datos.";
            }
            reporte += cont + "\t" + nombre_u + "\t\t" +
                    String.format("%.1f", P_diario) + " GB\t\t\t" +
                    String.format("%.1f", P_mensual) + " GB\t\t" +
                    plan + "\t\t"+ sugerencia + "\n";
            cont++;
            lim--;
        }
        }
        System.out.println("\n=========== REPORTE FINAL ===========");
        System.out.println("|N°|\t|NOMBRE|\t\t|PROMEDIO DIARIO|\t|PROMEDIO MENSUAL|\t|PLAN RECOMENDADO|\t|SUGERENCIAS|");
        System.out.println(reporte);
    } 
    
}
/*
run:
------------SISTEMA DE CONSUMO Y RECOMENDACION DE USO DE DATOS-------------
ingresse el numero de estudiantes a registrar: 
1
ingrese el nombre: 
juan
Ingrese la cantidad de MB usados: 
--CLASES-- 
Zoom (GB): 50
Canvas (GB): 20
--REDES SOCIALES--
Messenger (GB): 150
WhatsApp (GB): 200
--VIDEOS-- 
TikTok (GB): 600
YouTube (GB): 800
--JUEGOS--
Clash Royale (GB): 300
Free Fire (GB): 250
--TAREAS--
ChatGPT (GB): 180
Google (GB): 65
Ingrese el numero de dias a evaluar: 2

=========== REPORTE FINAL ===========
|N�|	|NOMBRE|		|PROMEDIO DIARIO|	|PROMEDIO MENSUAL|	|PLAN RECOMENDADO|	|SUGERENCIAS|
1	juan		             1,3 GB			39,2 GB		PLAN MEDIANO 60 GIGAS		Consumo excesivo en videos. Reduzca la calidad.
*/