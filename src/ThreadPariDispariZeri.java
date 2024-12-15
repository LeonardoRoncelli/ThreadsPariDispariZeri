import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
public class ThreadPariDispariZeri extends Thread{
    //dichiarazione degli attributi della classe
    int conta;
    String nome;
    int num=0;
    //creazione dei semafori
    static Semaphore semaforoPari=new Semaphore(1);
    static Semaphore semaforoDispari=new Semaphore(0);
    static Semaphore semaforoZeri=new Semaphore(0);
    public ThreadPariDispariZeri(String nome){ //costruttore della classe
        this.nome=nome;
        if(nome.equalsIgnoreCase("pari")){ //in questo if viene assegnato il primo valore alla variabile conta, tutto in base al contenuto della variabile nome
            conta=0;
        }else{
            conta=1;
        }
    }
    public void stampaNumPari(){
        try{
            semaforoPari.acquire(); //viene diminuito il numero di permessi di semaforoPari
            System.out.println(nome+": "+conta); //stampa del numero, ovvero la variabile conta
            conta=conta+2; //viene aggiornata la variabile conta, viene sommato 2 dato che siamo nella stampa relativa ai numeri pari e la variabile conta per i numeri pari è inizializzata a 0, grazie all'if nel costruttore
            semaforoDispari.release(); //viene aumentato il numero dei permessi di semaforoDispari dato che è terminata la stampa del numero pari
            TimeUnit.SECONDS.sleep(1); //attesa di un secondo per rendere l'output più leggibile
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void stampaNumDispari(){
        try{
            semaforoDispari.acquire(); //viene diminuito il numero di permessi di semaforoDispari
            System.out.println(nome+": "+conta); //stampa del numero, ovvero la variabile conta
            conta=conta+2; //viene aggiornata la variabile conta, viene sommato 2 dato che siamo nella stampa relativa ai numeri pari e la variabile conta per i numeri pari è inizializzata a 0, grazie all'if nel costruttore
            semaforoZeri.release(); //viene aumentato il numero dei permessi di semaforoPari dato che è terminata la stampa del numero dispari
            TimeUnit.SECONDS.sleep(1); //attesa di un secondo per rendere l'output più leggibile
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void stampaZeri(){
        try {
            semaforoZeri.acquire();
            System.out.println(nome+": "+num);
            semaforoPari.release();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void run(){
        while(true){//ciclo che stampa numeri all'infinito
            if(nome.equalsIgnoreCase("pari")){ //se il nome è uguale a "pari" verra chiamato il metodo stampaNumPari altrimenti stampaNumDispari
                stampaNumPari();
            }else if(nome.equalsIgnoreCase("dispari")){
                stampaNumDispari();
            }else{
                stampaZeri();
                System.out.println(" ");
            }
        }
    }
    public static void main(String[]args){
        ThreadPariDispariZeri t1=new ThreadPariDispariZeri("Pari"); //istanza dell'oggetto a cui viene assegnato il nome "Pari"
        ThreadPariDispariZeri t2=new ThreadPariDispariZeri("Dispari"); //istanza dell'oggetto a cui viene assegnarto il nome "Dispari"
        ThreadPariDispariZeri t0=new ThreadPariDispariZeri("Zeri");
        t1.start(); //il thread t1 richiama il metodo run
        t2.start();// il thread t2 richiama il metodo run
        t0.start();
    }
}