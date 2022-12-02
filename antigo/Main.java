package antigo;
import java.util.Random;

class Main{
    public static void main(String[] args) throws InterruptedException {
        Random gerador = new Random();
        GerenciadorBanheiro gerenciadorBanheiro= new GerenciadorBanheiro();
        while(true){
            Pessoa pessoa = new Pessoa(gerador.nextInt(2), gerador.nextInt(700));
            gerenciadorBanheiro.addPessoa(pessoa);
            Thread.sleep(100);
        }
    }
}