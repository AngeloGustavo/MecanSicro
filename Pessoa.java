public class Pessoa {
    String genero;
    int esperaEmMs;

    Pessoa(int _genero, int _esperaEmMs){
        if(_genero == 0)
            genero = "Homem";
        else
            genero = "Mulher";

        esperaEmMs = _esperaEmMs;
    }

    void printInfo(){
        System.out.println(genero + " por " + esperaEmMs + " milisegundos");
    }

    @Override
    public String toString() {
        return genero+esperaEmMs;
    }
}