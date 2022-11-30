import java.util.Queue;
import java.util.concurrent.Semaphore;

public class GerenciadorBanheiro {
    final int limitePessoas = 5; 
    Semaphore limiteBanheiro = new Semaphore(limitePessoas);
    Semaphore regiaoCritica = new Semaphore(1);

    //.offer() {adicionando elemento}  .poll() {pegando e removendo próximo elemento}
    Queue<Pessoa> banheiro;

    void addPessoa(Pessoa pessoa) throws InterruptedException{
        regiaoCritica.acquire();

        limiteBanheiro.acquire();
        banheiro.offer(pessoa);
        

        regiaoCritica.release();
    }

    void printRelatorio(){
        System.out.println();
    }
}
