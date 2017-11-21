package cashbox;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Buyer extends Thread {
	List<Integer>[] list;//список очередей к кассам
	Integer k;// номер покупателя
	int nk;// номер очереди покупателя
	ReentrantLock lock = new ReentrantLock();

	List<Boolean> cash;// список касс
	Buyer(List<Integer>[] list, List<Boolean> cash, Integer i) {
		this.list = list;
		this.k = i;
		this.nk = get_min_queue();
		list[nk].add(k);
		this.cash = cash;
	}

	

	int get_min_queue() {//получить кассу с минимальной очередью
		int mq = -1;
		int min = 1000000;
		for (int i = 0; i < list.length; i++) {
			if (list[i].size() < min) {
				mq = i;
				min = list[i].size();
			}
		}
		return mq;
	}

	boolean remove_in_queue() {//удалить из очереди
	return list[nk].remove(k);
	
	}

	void write_cash(){//вывести загруженность касс
		System.out.print("Загруженность касс " );
		for (int i=0; i<list.length; i++)
			System.out.print(list[i].size() + " ");
		System.out.println();
		
	}
	@Override
	public void run() {
		Random random = new Random();
		
		try {
			System.out.println("Пришел " + k + " покупатель в очередь к кассе "
					+ nk);
			System.out.println("В очереди перед " + k + " покупателем  "
					+ list[nk].indexOf(k) + " человек (" + nk + " очередь)");
			write_cash();
			while (list[nk].indexOf(k) != 0) {// если
																		// касса
																		// нашей
																		// очереди
																		// занята
																		// и
																		// порядок
																		// в
																		// очереди
																		// не
																		// первый
				Thread.sleep(100);
				
				int minq = get_min_queue();//получить кассу с минимальной очередью
				if (minq != nk && list[minq].size()<list[nk].size()-2) {// перейти в другую если разница больше 2
					System.out.println("Покупатель " + k + " покинул " + nk
							+ " очередь. Покупатель был "+ list[nk].indexOf(k) );
					lock.lock();
					try {
						remove_in_queue();//удалить из старой очереди
						list[minq].add(k);//добавить в новую
						nk = minq;
						System.out.println("Покупатель " + k + " перешел в " + nk
								+ " очередь. Покупатель стал "+ list[nk].indexOf(k));

						write_cash();
					} finally {
						lock.unlock();
					}
				}

			}
			lock.lock();
			try {
				cash.set(nk, true);
			} finally {
				lock.unlock();
			}
			System.out.println("Покупатель " + k + " на кассе " + nk);
			int rand = random.nextInt(10000);// определяем время которое касса
												// будет работать
			Thread.sleep(rand);// ждем
			lock.lock();
			try {
				list[nk].remove(k);// удаляем покупателя
				cash.set(nk, false);// освобождаем кассу
			} finally {
				lock.unlock();
			}
			System.out.println(k + " покупатель ушел");
			write_cash();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
