package cashbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cashbox.Buyer;

public class Cash {

	List<Integer> [] list;//������ ����������� �� ��������
	List<Boolean> cash;//������ ����
	int n;//���������� ����

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
		Cash mag = new Cash(3);//����� �� 3� ����
		int count_buyer = 15;//���������� �����������
		Random random = new Random();
		List<Buyer> cars = new ArrayList<Buyer>();//������ ������� ��� ��������
		
		for (int i=0; i<count_buyer; i++){//���� �� ������ count_buyer
			int rand = random.nextInt(100);//������� �� ��������� ���������� ����������
			try {
				Thread.sleep(rand);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Integer k = new Integer(i);
			Buyer b = new Buyer(mag.list, mag.cash,  k);//������� ����� � ����� �����������
			cars.add(b);
			b.start();//� ���������
			
		}
		//������� ���������� ���� �����������
		for(int i=0; i<cars.size(); i++)
			try {
				cars.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		System.out.println("��� ���������� ���������");
	}

}
