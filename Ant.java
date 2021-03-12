import java.awt.Color;
import java.util.Random;
import javax.swing.JPanel;

public class Ant extends Thread {
    //coordenadas da formiga
    private int antX;
    private int antY;

    //representa a direção do movimento da formiga com um numero entre 1 e 8
    private int direction;
    
    //representação visual do formigueiro
    private JPanel[][] anthill;
    //representação lógica do formigueiro
    private Ant[][] matrix;
    

    public Ant(JPanel[][] anthill) {
        //recebe o formigueiro construido na classe principal
        this.anthill = anthill;
        
        //inserindo formiga
        boolean insert = false;
        while (!insert) {
            //gera coordenada aleatória
            antX = new Random().nextInt(anthill.length);
            antY = new Random().nextInt(anthill[0].length);
            //verifica na formigueiro visual se já existe uma formiga nessa posição
            if (!anthill[antX][antY].getBackground().equals(Color.BLACK))
                //caso não haja insert se torna true e encerra o loop
                insert = true;
        }
        //insere a formiga no formigueiro visual
        anthill[antX][antY].setBackground(Color.BLACK);
        
        //gera uma direção aleatória
        direction = new Random().nextInt(8) + 1;
    }

    public void setMatrix(Ant[][] matrix) {
        //recebe o formigueiro lógico criado na classe principal
        this.matrix = matrix;
        //insere a formiga no formigueiro lógico
        this.matrix[antX][antY] = this;
    }

    public void run() {
        //a formiga aguarda 100 milisegundo e depois caminha, repete isso infinitamente
        while (true) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                walk();
        }
    }

    //função de colisão de duas formigas
    private void collision(Ant a2) {
        //gera um tempo de espera aleátorio entre 1000 e 3000 milisegundos
        int stunTime = new Random().nextInt(2000) + 1000;
        //coloca a formiga2 em espera depois coloca a formiga principal
        a2.stun(stunTime);
        this.stun(stunTime);
    }

    private void stun(int stunTime) {
        //muda a direção da formiga depois da colisão
        direction = new Random().nextInt(8) + 1;
        //inicia a espera
        try {
            sleep(stunTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //faz a representação lógica e visual do movimento da formiga
    private void realMove(){
        //pinta a nova posição da formiga
        anthill[antX][antY].setBackground(Color.BLACK);
        //muda a posição lógica da formiga
        matrix[antX][antY] = this;
    }

    //apaga o rastro do movimento da formiga
    private void eraseTrail(){
        //pinta a posição anterior da formiga
        anthill[antX][antY].setBackground(Color.lightGray);
        //apaga a posição lógica anterior da formiga
        matrix[antX][antY] = null;
    }

    private int nextStep(int nextX, int nextY){
        //verfica de a formiga não está na borda do formigueiro, caso ela esteja na borda muda a direção;
        if(nextX >= anthill.length || nextY >= anthill[0].length ||
            nextX < 0 || nextY < 0){
                direction  = new Random().nextInt(8) + 1;
                return -1;
            }
            //verifica se exite uma formiga na posição lógica para qual ela quer andar;
            synchronized(matrix){
                if(matrix[nextX][nextY] != null){
                    return 1;
                }
            }

        return 0;
    }

    public void walk(){
        int nextStep = 0;
        int nextX = antX;
        int nextY = antY;

        switch(direction){
            //Leste
            case 1:
                nextX++;
                nextStep = nextStep(nextX,nextY);
                break;
            //Oeste
            case 2:
                nextX--;
                nextStep = nextStep(nextX,nextY);
                break;
            //Norte
            case 3:
                nextY++;
                nextStep = nextStep(nextX,nextY);
                break;
            //Sul
            case 4:
                nextY--;
                nextStep = nextStep(nextX,nextY);
                break;
            //Nordeste
            case 5:
                nextY++;
                nextX++;
                nextStep = nextStep(nextX,nextY);
                break;
            //Sudeste
            case 6:
                nextY--;
                nextX++;
                nextStep = nextStep(nextX,nextY);
                break;
            //Sudoeste
            case 7:
                nextY--;
                nextX--;
                nextStep = nextStep(nextX,nextY);
                break;
            //Noroeste
            case 8:
                nextY++;
                nextX--;
                nextStep = nextStep(nextX,nextY);
                break;        
        }

        if(nextStep == -1){
            return;
        }
        
        if(nextStep == 1){
            collision(matrix[nextX][nextY]);
            return;
        }
        //apaga o rastro
        eraseTrail();
        //altera as coordenadas de acordo com a direção
        antX = nextX;
        antY = nextY;
        //faz a movimentação real(lógica e visual)
        realMove(); 


    }

}
