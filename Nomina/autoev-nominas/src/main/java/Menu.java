
import Laboral.DatosNoCorrectosException;
import Laboral.Empleado;
import Laboral.EmpleadosFich;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;


public class Menu {
    
    public static void main(String[] args) throws SQLException, IOException, FileNotFoundException, DatosNoCorrectosException {
    
        try (Scanner scnum = new Scanner(System.in);
				Scanner sc = new Scanner(System.in)) {
			boolean salir = false;		
			
			
			String dni;
					
			while (!salir) {
			
				System.out.println("1. Ver lista de empleados");
				System.out.println("2. Ver salario de un empleado");
				System.out.println("3. Menu de edicion de datos");
				System.out.println("4. Recalcular y actualizar sueldo del empleado");
				System.out.println("5. Recalcular y actualizar sueldos de todos los empleados");
				System.out.println("6. Realizar copia de seguridad en fichero");
				System.out.println("7. Dar de alta de nuevos empleados");
				System.out.println("8. Dar de baja empleado");
				System.out.println("9. Cargar backup-empleados.txt a la base de datos");
				System.out.println("0. Salir ");
				String menuprincipal = sc.nextLine();
			
				switch (menuprincipal) {
				case "1": 
					for(Empleado emp : EmpleadosFich.obtieneEmpleado()) {
						System.out.println(emp.imprime());
					}
					break;
				
				case "2":
					System.out.println("Introduce el DNI del empleado para ver su sueldo");
					dni = sc.nextLine();
					int sueldo=EmpleadosFich.getSueldo(dni);
					
					if( sueldo > 0) {
						System.out.println("El sueldo del empleado con DNI: "+dni+" es de "+sueldo+"€.");
					}else{
						System.out.println("El empleado con ese"+dni+ " no existe");
					}
					break;
					
				case "3":
					System.out.println("Introduce el DNI del empleado a editar");
					dni = sc.nextLine().toUpperCase();
					
					if(EmpleadosFich.emplExist(dni)) {
						System.out.println("Que campos deseas editar? \n"
								+ "1. Nombre \n"
								+ "2. Sexo \n"
								+ "3. Categoria \n"
								+ "4. Anyos \n"
								+ "5. Todos los campos \n"
								+ "Introduce cualquier otra tecla para salir.");
						String opcioneditar = sc.nextLine();
						
						Empleado emp = EmpleadosFich.getEmpleado(dni);
						
						switch (opcioneditar) {
						case "1":
							System.out.println("Actualiza el nombre");
							emp.nombre = sc.nextLine();
							EmpleadosFich.updateEmpleado(emp);
							break;
						case "2":
							System.out.println("Actualzia el sexo del empleado F/M");
							emp.sexo = sc.nextLine().charAt(0);
							EmpleadosFich.updateEmpleado(emp);
							break;
						case "3":
							System.out.println("Actualiza la categoria");
							try {
								emp.setCategoria(scnum.nextInt());
								EmpleadosFich.updateEmpleado(emp);
							}catch (DatosNoCorrectosException ex) {
								ex.printStackTrace();
							}
							break;
						case "4":
							System.out.println("Actualiza los años");
							emp.anyos = scnum.nextInt();
							EmpleadosFich.updateEmpleado(emp);
							break;
						case "5":
							System.out.println("Actualiza el nombre");
							emp.nombre = sc.nextLine();
							System.out.println("Actualzia el sexo del empleado F/M");
							emp.sexo = sc.nextLine().charAt(0);
							System.out.println("Actualiza la categoria");
							try {
								emp.setCategoria(scnum.nextInt());
							}catch (DatosNoCorrectosException ex) {
								ex.printStackTrace();
							}						
							System.out.println("Actualiza los años");
							emp.anyos = scnum.nextInt();
							EmpleadosFich.updateEmpleado(emp);
							break;
						default:
							System.out.println("OK");
							break;
						}
						
					}
					
					break;
					
				case "4":
					
					System.out.println("Introduce el DNI del empleado del que deseas recalcular su nómina");
					dni = sc.nextLine();
					
					if(EmpleadosFich.emplExist(dni)) {
						EmpleadosFich.updateNomina(dni);
						System.out.println("En proceso");
					}else {
						System.out.println("Error: empleado no encontado");
					}
									
					break;
					
				case "5":
					
					for(Empleado emp : EmpleadosFich.obtieneEmpleado()) {
						EmpleadosFich.updateNomina(emp.dni);
					}
					System.out.println("En proceso");
					
					break;
					
				case "6":
					
					for(Empleado emp : EmpleadosFich.obtieneEmpleado()) {
						EmpleadosFich.backup(emp);
					}
					System.out.println("Finalizado");
					
					break;
					
				case "7":
					System.out.println("¿Alta manual o usando el fichero empleadosNuevos.txt? ");
					System.out.println("1. Manual");
					System.out.println("2. Fichero");
					String submenualta = sc.nextLine();
					String nombre, dniemp;
					char sexo;
					int categoria, anyos;
					
					switch (submenualta) {
					case "1":
						System.out.println("Nombre del empleado: ");
						nombre = sc.nextLine();
						System.out.println("DNI del empleado: ");
						dniemp = sc.nextLine().trim().toUpperCase();
						System.out.println("Introduce el Sexo del empelado F/M");
						sexo = sc.nextLine().charAt(0);
						System.out.println("Introduce la categoria del empleado");
						categoria=scnum.nextInt();
						System.out.println("Introduce los anyos trabajados del empleado");
						anyos = scnum.nextInt();
						EmpleadosFich.altaEmpleado(nombre, dniemp, sexo, categoria, anyos);
						System.out.println("Consulta Enviada");				
						break;
					
					case "2":
						System.out.println("Introduce el nombre del fichero con la información de los empleados");
						EmpleadosFich.altaEmpleado(sc.nextLine());
						System.out.println("Consulta Enviada");
						break;

					default:
						System.out.println("Operación cancelada");
						break;
					}
					break;
					
				case "8":
					
					System.out.println("Introduce el DNI del empleado que deseas dar de baja");
					dni = sc.nextLine();
					EmpleadosFich.deleteEmpleado(dni);
					System.out.println("Consulta enviada");
					break;
					
				case "9":
					
					EmpleadosFich.altaEmpleado("backupEmp.txt");
					System.out.println("Backup finalizado");
					break;
					
				case "0":
					
					System.out.println("Adios");
					salir=true;
					break;
				}
			
			}
		}
			
		
	}
        
    }
    

