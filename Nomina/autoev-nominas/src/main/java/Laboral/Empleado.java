
package Laboral;

public class Empleado extends Persona{
    
    private int categoria=1;
    public  int anyos=0;
    
    public Empleado(String nombre, String dni, char sexo) {
        super(nombre, dni, sexo);
         this.categoria = 1;

        this.anyos = 0;
    
    }

    public Empleado( String nombre, String dni, char sexo, int categoria, int anyos) throws DatosNoCorrectosException{
		super(nombre, dni, sexo);
		this.setCategoria(categoria);
		if (anyos>=0)
			this.anyos = anyos;
		else throw new DatosNoCorrectosException();
	}

   public void setCategoria(int categoria) throws DatosNoCorrectosException {
		if (categoria<1 || categoria>10) {
			throw new DatosNoCorrectosException();
		}else {
			this.categoria = categoria;
		}
	}

    public int getCategoria() {
        return categoria;
    }
    
    
    public void incrtAnyos(){
        
        this.anyos++;
        
        
    }
    

    
    @Override
    public String imprime(){
        return "Empleado de: " + super.imprime() +", categoria:" + categoria + ", años: " + anyos  ;
    }
}
