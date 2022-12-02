package antigo;

public class Thread1 {
    static Pessoa pessoa;
    Thread1(Pessoa _pessoa){
        pessoa = _pessoa;
    }
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(pessoa.esperaEmMs);
        GerenciadorBanheiro.limiteBanheiro.release();
    }
}
