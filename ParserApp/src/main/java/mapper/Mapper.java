package mapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.opencsv.CSVWriter;

import models.Item;

public class Mapper {

	public static void createOutputFile(List<Item> items, String nameFile) {
		CSVWriter writer;
		try {
			writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(nameFile), StandardCharsets.UTF_8),
					';', CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
					CSVWriter.DEFAULT_LINE_END);

			String[] entries = "Name of Item,price,old Price,link of Item,sizes,max Quantity,rating, parameters"
					.split(",");
			writer.writeNext(entries);

			for (Item item : items) {

				String result = item.getName() + "," + item.getPrice() + "," + item.getOldPrice()
						+ "," + item.getLinkLine() + "," + item.getSizes() + "," + item.getMaxQuantity() + ","
						+ item.getRating() + "," + item.getParameters();
				entries = new String[]{item.getName(),item.getPrice(),item.getOldPrice(),item.getLinkLine(),item.getSizes(),item.getMaxQuantity(),item.getRating(),item.getParameters()};
				entries = result.split(",");
				writer.writeNext(entries);

			}

			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
