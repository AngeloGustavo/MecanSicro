package novo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Banheiro {
    AtomicInteger ocupacaoBanheiro;
    int capacity;
    Semaphore noOfEntriesSemaphore;
    Semaphore currentGenderSemaphore = new Semaphore(1);
    volatile Pessoa currentGender;

    public Banheiro(int capacity) {
        this.capacity = capacity;
        this.noOfEntriesSemaphore = new Semaphore(capacity);
        this.ocupacaoBanheiro =  new AtomicInteger(0);
    }

    public void occupy(Pessoa personSex) throws InterruptedException {
        acquireGenderSemaphore(personSex);
        useBathroomAndExit(personSex);
        releaseGenderSemaphore(personSex);
    }

    private void releaseGenderSemaphore(Pessoa personSex) {
        if(ocupacaoBanheiro.get()==0){
            currentGenderSemaphore.release();
            currentGender = null;
            System.out.println(personSex.genero+ Thread.currentThread().getId()+" liberando semaforo de genero." +"-----------------");
        }
    }

    private void useBathroomAndExit(Pessoa personSex) throws InterruptedException {
        noOfEntriesSemaphore.acquire();
        ocupacaoBanheiro.incrementAndGet();
        System.out.println(personSex.genero + " " + Thread.currentThread().getId()+" entrou. (Capacidade atual: "+ocupacaoBanheiro.get()+")");
        Thread.sleep(100);
        noOfEntriesSemaphore.release();
        ocupacaoBanheiro.decrementAndGet();
        System.out.println(personSex.genero + " " + Thread.currentThread().getId()+" saiu. (Capacidade atual: "+ ocupacaoBanheiro.get()+")");
    }

    private void acquireGenderSemaphore(Pessoa personSex) throws InterruptedException {
        if(ocupacaoBanheiro.get() == 0){
            currentGenderSemaphore.acquire();
            currentGender = personSex;
        }
        if(!personSex.genero.equals(currentGender.genero)){
            currentGenderSemaphore.acquire();
            currentGender = personSex;
        }
    }

    public static void main(String[] args){
        Banheiro banheiro = new Banheiro(5);

        new Thread(new ThreadPessoa(banheiro, Pessoa.HOMEM)).start();
        new Thread(new ThreadPessoa(banheiro, Pessoa.MULHER)).start();
        new Thread(new ThreadPessoa(banheiro, Pessoa.HOMEM)).start();
        /*
        Random gerador = new Random();
        Banheiro banheiro = new Banheiro(5);
        while(true){
            int genero = gerador.nextInt(2);
            if(genero == 0)
                new Thread(new ThreadPessoa(banheiro, Pessoa.HOMEM)).start();
            else    
                new Thread(new ThreadPessoa(banheiro, Pessoa.MULHER)).start();
            Thread.sleep(1000);
        }
        */
    }
}


