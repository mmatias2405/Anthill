import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {


	private static final long serialVersionUID = 1L;

	//largura do formigueiro
	public final int DIM_X = 50;
	//altura do formigueiro
	public final int DIM_Y = 50;

	//cor de background do formigueiro
    public final Color BACKGROUND_COLOR = Color.lightGray;

	//criação do formigueiro visual
	private JPanel[][] anthill = new JPanel[DIM_X][DIM_Y];
	//criação do formugueiro lógico
	private Ant[][] matrix = new Ant[DIM_X][DIM_Y];

	private JPanel mainPanel = new JPanel(new GridLayout(DIM_X, DIM_Y));
	
	public Main() {
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      this.init();
      
      this.pack();
      this.setVisible(true);

	}

	private void init() {
		this.setTitle("Anthill");
		Container c = getContentPane();

		for (int y = 0; y < DIM_Y; y++) {
			for (int x = 0; x < DIM_X; x++) {
				anthill[x][y] = new JPanel();
				anthill[x][y].setPreferredSize(new Dimension(12,12));
				anthill[x][y].setBackground(BACKGROUND_COLOR);
				anthill[x][y].setBorder(BorderFactory.createLineBorder(Color.white));
				mainPanel.add(anthill[x][y]);
			}
		}
		c.add(mainPanel);
	}

    public static void main(String[] args){
		//instancia da classe Main para ter acesso ao formigueiro visual e lógico
		Main main = new Main();
		//arraylist das formigas que estão no formigueiro
		ArrayList<Ant> ants = new ArrayList<Ant>();
		//numero de formigas no formigueiro, entre 20 e 50
		int numberAnts = new Random().nextInt(30) + 20;

		for(int i = 1; i <= numberAnts; i++){
			//cria a nova formiga, passando como parâmetro o formigueiro visual e adiciona ela no arraylist
			ants.add(new Ant(main.anthill));
		}

		for (Iterator<Ant> iterator = ants.iterator(); iterator.hasNext(); ) 
		{ 
			Ant a = iterator.next();
			//seta a novo formigueiro lógico para cada formiga no arraylist
			a.setMatrix(main.matrix);
			//inicia o movimento de todas as formigas do arraylist 
			a.start();
		}

	}
}