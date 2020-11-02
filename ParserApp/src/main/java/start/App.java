package start;

import threads.WorkingThreadFashionCategory;
import threads.WorkingThreadOtherCategories;

public class App {
	public static void main(String[] args) {
		
		WorkingThreadFashionCategory workingThread1 = new WorkingThreadFashionCategory(
				"https://allegro.pl/kategoria/odziez-meska-1455?bmatch=baseline-product-eyesa2-engag-dict45-fas-1-3-1023&tylko-przecenione=1",
				"https://allegro.pl/kategoria/odziez-meska-1455?bmatch=baseline-product-eyesa2-engag-dict45-fas-1-3-1023&tylko-przecenione=1&p=2");
		WorkingThreadOtherCategories workingThread2 = new WorkingThreadOtherCategories(
				"https://allegro.pl/kategoria/komputery-stacjonarne-486?bmatch=baseline-product-cl-eyesa2-engag-dict45-ele-1-3-1023",
				"https://allegro.pl/kategoria/komputery-stacjonarne-486?bmatch=baseline-product-cl-eyesa2-engag-dict45-ele-1-3-1023&p=", "electric.csv");
		WorkingThreadOtherCategories workingThread3 = new WorkingThreadOtherCategories(
				"https://allegro.pl/kategoria/suplementy-diety-122564?bmatch=baseline-product-eyesa2-engag-dict45-hea-1-3-1023",
				"https://allegro.pl/kategoria/suplementy-diety-122564?bmatch=baseline-product-eyesa2-engag-dict45-hea-1-3-1023&p=","vitamins.csv");
		Thread thread1 = new Thread(workingThread1);
		Thread thread2 = new Thread(workingThread2);
		Thread thread3 = new Thread(workingThread3);
		thread1.start();
		thread2.start();
		thread3.start();
	}
}
