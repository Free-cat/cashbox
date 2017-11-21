package cashbox;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Buyer extends Thread {
	List<Integer>[] list;//������ �������� � ������
	Integer k;// ����� ����������
	int nk;// ����� ������� ����������
	ReentrantLock lock = new ReentrantLock();

	List<Boolean> cash;// ������ ����
	Buyer(List<Integer>[] list, List<Boolean> cash, Integer i) {
		this.list = list;
		this.k = i;
		this.nk = get_min_queue();
		list[nk].add(k);
		this.cash = cash;
	}

	

	int get_min_queue() {//�������� ����� � ����������� ��������
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

	boolean remove_in_queue() {//������� �� �������
	return list[nk].remove(k);
	
	}

	void write_cash(){//������� ������������� ����
		System.out.print("������������� ���� " );
		for (int i=0; i<list.length; i++)
			System.out.print(list[i].size() + " ");
		System.out.println();
		
	}
	@Override
	public void run() {
		Random random = new Random();
		
		try {
			System.out.println("������ " + k + " ���������� � ������� � ����� "
					+ nk);
			System.out.println("� ������� ����� " + k + " �����������  "
					+ list[nk].indexOf(k) + " ������� (" + nk + " �������)");
			write_cash();
			while (list[nk].indexOf(k) != 0) {// ����
																		// �����
																		// �����
																		// �������
																		// ������
																		// �
																		// �������
																		// �
																		// �������
																		// ��
																		// ������
				Thread.sleep(100);
				
				int minq = get_min_queue();//�������� ����� � ����������� ��������
				if (minq != nk && list[minq].size()<list[nk].size()-2) {// ������� � ������ ���� ������� ������ 2
					System.out.println("���������� " + k + " ������� " + nk
							+ " �������. ���������� ��� "+ list[nk].indexOf(k) );
					lock.lock();
					try {
						remove_in_queue();//������� �� ������ �������
						list[minq].add(k);//�������� � �����
						nk = minq;
						System.out.println("���������� " + k + " ������� � " + nk
								+ " �������. ���������� ���� "+ list[nk].indexOf(k));

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
			System.out.println("���������� " + k + " �� ����� " + nk);
			int rand = random.nextInt(10000);// ���������� ����� ������� �����
												// ����� ��������
			Thread.sleep(rand);// ����
			lock.lock();
			try {
				list[nk].remove(k);// ������� ����������
				cash.set(nk, false);// ����������� �����
			} finally {
				lock.unlock();
			}
			System.out.println(k + " ���������� ����");
			write_cash();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
