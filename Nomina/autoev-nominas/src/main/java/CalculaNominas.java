
import Laboral.DatosNoCorrectosException;
import Laboral.Empleado;
import Laboral.Nomina;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CalculaNominas {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        try {

            Empleado emp1 = new Empleado("James Cosling", "3200032G", 'M', 7, 4);
            Empleado emp2 = new Empleado("Ada Lovelace", "32000031R", 'F');
            escribe(emp1, emp2);

            emp1.incrtAnyos();
            emp1.incrtAnyos();
            emp1.incrtAnyos();
            emp2.setCategoria(9);

            escribe(emp1, emp2);

            File filentrada = new File("empleados.txt");
            FileReader fr = new FileReader(filentrada);
            BufferedReader br = new BufferedReader(fr);

            File filentradaActualizada = new File("empleadosActualizados.txt");
            FileWriter fw = new FileWriter(filentradaActualizada);
            BufferedWriter bw = new BufferedWriter(fw);

            File filesalida = new File("salarios.dat");
            FileOutputStream fos = new FileOutputStream(filesalida);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            OutputStreamWriter os = new OutputStreamWriter(fos);

            String linea;
            Empleado emp;
            String[] datosEmp;

            while (br.ready()) {

                linea = br.readLine();
                datosEmp = linea.split(";");
                if (datosEmp.length == 3) {
                    emp = new Empleado(datosEmp[0], datosEmp[1], datosEmp[2].toCharArray()[0]);
                } else {
                    emp = new Empleado(datosEmp[0], datosEmp[1], datosEmp[2].toCharArray()[0], Integer.parseInt(datosEmp[3]), Integer.parseInt(datosEmp[4]));
                }
                escribe2(emp);

                bos.write((emp.dni + ";" + Nomina.sueldo(emp) + '\n').getBytes());
                os.write(emp.dni + ";" + Nomina.sueldo(emp) + '\n');

                if (emp.nombre.equalsIgnoreCase("James Cosling")) {
                    emp.setCategoria(9);
                } else if (emp.nombre.equalsIgnoreCase("Ada Lovealace")) {
                    emp.incrtAnyos();
                }

                bw.write(emp.nombre + ";" + emp.dni + ";" + emp.sexo + ";" + emp.getCategoria() + ";" + emp.anyos + '\n');
            }

            br.close();
            bos.close();
            bw.close();

            filentrada.delete();
            filentradaActualizada.renameTo(filentrada);

        } catch (DatosNoCorrectosException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void escribe(Empleado emp1, Empleado emp2) {

        System.out.println(emp1.imprime() + " con un sueldo de: " + Nomina.sueldo(emp1) + " €.");
        System.out.println(emp2.imprime() + " con un sueldo de: " + Nomina.sueldo(emp2) + " €,");

    }

    private static void escribe2(Empleado emp) {

        System.out.println(emp.imprime() + " con un sueldo de: " + Nomina.sueldo(emp) + " €.");
    }

}
