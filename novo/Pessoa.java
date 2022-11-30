package novo;

enum Pessoa {
    HOMEM("Homem"), MULHER("Mulher");
    String genero;

    Pessoa(String genero) {
        this.genero = genero;
    }
}