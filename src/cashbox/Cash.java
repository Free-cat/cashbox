package cashbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cashbox.Buyer;

public class Cash {

	List<Integer> [] list;//список покупателей по очередям
	List<Boolean> cash;//массив касс
	int n;//количество касс

	Cash(int n){
		this.n = n;
		this.list = new ArrayList[n];
		cash = new ArrayList<Boolean>();
		for (int i=0; i<n; i++){
			this.list[i] = new ArrayList<Integer>();
			this.cash.add(false);
		}
	}
	
	public static void main(String[] args) {
		Cash mag = new Cash(3);//касса из 3х мест
		int count_buyer = 15;//количество покупателей
		Random random = new Random();
		List<Buyer> cars = new ArrayList<Buyer>();//список потоков для ожидания
		
		for (int i=0; i<count_buyer; i++){//пока не прошло count_buyer
			int rand = random.nextInt(100);//задежка до появления очередного покупателя
			try {
				Thread.sleep(rand);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Integer k = new Integer(i);
			Buyer b = new Buyer(mag.list, mag.cash,  k);//создаем поток с новым покупателем
			cars.add(b);
			b.start();//и запускаем
			
		}
		//ожидаем завершения всех покупателей
		for(int i=0; i<cars.size(); i++)
			try {
				cars.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		System.out.println("все покупатели обслужены");
	}

}
