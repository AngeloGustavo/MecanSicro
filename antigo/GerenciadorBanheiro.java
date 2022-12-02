package antigo;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class GerenciadorBanheiro{
    static int limitePessoas = 5; 
    static Semaphore limiteBanheiro = new Semaphore(limitePessoas);
    //Semaphore regiaoCritica = new Semaphore(1);

    //.offer() {adicionando elemento}  .poll() {pegando e removendo próximo elemento}
    //Queue<Pessoa> banheiro;

    void addPessoa(Pessoa pessoa) throws InterruptedException{
        //regiaoCritica.acquire();

        limiteBanheiro.acquire();
        System.out.println("Entrou um "+pessoa.genero+" e vai usar por "+pessoa.esperaEmMs+" milisegundos.");
        Thread thread1 = new Thread((Runnable) new Thread1(pessoa));
        thread1.start();
        thread1.join();
        //usarBanheiro(pessoa);

        //banheiro.offer(pessoa);
        //regiaoCritica.release();
    }

    void usarBanheiro(Pessoa pessoa) throws InterruptedException{
        Thread.sleep(pessoa.esperaEmMs);
        limiteBanheiro.release();
    }

    void printRelatorio(){
        System.out.println();
    }
}
