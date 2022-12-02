class ThreadPessoa implements Runnable {
    public Banheiro banheiro;
    public Pessoa pessoa;
    
    public ThreadPessoa(Banheiro banheiro, Pessoa pessoa){
        this.banheiro = banheiro;
        this.pessoa = pessoa;
    }

    @Override
    public void run() {
        try {
            for(int i=0;i<50;i++) {
                pessoa.setId(i);
                banheiro.ocuparBanheiro(pessoa);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}