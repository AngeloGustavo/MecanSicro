package novo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Banheiro {
    public static void main(String[] args){
        Banheiro banheiro = new Banheiro(5);
        new Thread(new ThreadPessoa(banheiro, Pessoa.HOMEM)).start();
        new Thread(new ThreadPessoa(banheiro, Pessoa.MULHER)).start();
        new Thread(new ThreadPessoa(banheiro, Pessoa.HOMEM)).start();
        new Thread(new ThreadPessoa(banheiro, Pessoa.MULHER)).start();
        new Thread(new ThreadPessoa(banheiro, Pessoa.HOMEM)).start();
    }

    int tamanhoBanheiro;
    AtomicInteger ocupacaoBanheiro;
    volatile Pessoa generoNoBanheiro;
    Semaphore semaforoDeEntrada;
    Semaphore semaforoDeGenero = new Semaphore(1);
    static Semaphore semaforoMutex = new Semaphore(1);

    public Banheiro(int tamanhoBanheiro) {
        this.tamanhoBanheiro = tamanhoBanheiro;
        this.semaforoDeEntrada = new Semaphore(tamanhoBanheiro);
        this.ocupacaoBanheiro =  new AtomicInteger(0);
    }

    public void ocuparBanheiro(Pessoa pessoa) throws InterruptedException {
        ocuparSemaforoDeGenero(pessoa);
        usarBanheiro(pessoa);
        liberarSemaforoDeGenero(pessoa);
    }

    private void liberarSemaforoDeGenero(Pessoa pessoa) {
        if(ocupacaoBanheiro.get()==0){
            semaforoDeGenero.release();
            generoNoBanheiro = null;
            System.out.println(pessoa.genero + "[" + Thread.currentThread().getId()+pessoa.id + "] liberando semaforo de genero." +"-----------------");
        }
    }

    private void usarBanheiro(Pessoa pessoa) throws InterruptedException {
        semaforoDeEntrada.acquire();
        ocupacaoBanheiro.incrementAndGet();
        System.out.println(pessoa.genero + "[" + Thread.currentThread().getId()+pessoa.id +"] entrou. (Capacidade atual: "+(tamanhoBanheiro-ocupacaoBanheiro.get())+")");
        Thread.sleep(1000);
        semaforoDeEntrada.release();
        ocupacaoBanheiro.decrementAndGet();
        System.out.println(pessoa.genero + "[" + Thread.currentThread().getId()+pessoa.id + "] saiu. (Capacidade atual: "+(tamanhoBanheiro-ocupacaoBanheiro.get())+")");
    }

    private void ocuparSemaforoDeGenero(Pessoa pessoa) throws InterruptedException {
        if(ocupacaoBanheiro.get() == 0){
            semaforoDeGenero.acquire();
            generoNoBanheiro = pessoa;
        }
        if(!pessoa.genero.equals(generoNoBanheiro.genero)){
            semaforoDeGenero.acquire();
            generoNoBanheiro = pessoa;
        }
    }
}