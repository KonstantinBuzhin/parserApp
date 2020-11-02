package threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import mapper.Mapper;
import models.Item;

public class WorkingThreadFashionCategory implements Runnable {

	private String firstPage;
	private String secondPage;

	public String getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(String firstPage) {
		this.firstPage = firstPage;
	}

	public String getSecondPage() {
		return secondPage;
	}

	public void setSecondPage(String secondPage) {
		this.secondPage = secondPage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstPage == null) ? 0 : firstPage.hashCode());
		result = prime * result + ((secondPage == null) ? 0 : secondPage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkingThreadFashionCategory other = (WorkingThreadFashionCategory) obj;
		if (firstPage == null) {
			if (other.firstPage != null)
				return false;
		} else if (!firstPage.equals(other.firstPage))
			return false;
		if (secondPage == null) {
			if (other.secondPage != null)
				return false;
		} else if (!secondPage.equals(other.secondPage))
			return false;
		return true;
	}

	public WorkingThreadFashionCategory() {
	}

	public WorkingThreadFashionCategory(String firstPage, String secondPage) {
		this.firstPage = firstPage;
		this.secondPage = secondPage;
	}

	@Override
	public void run() {
		Document doc = null;
		try {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			doc = Jsoup.connect(firstPage).timeout(10*1000).get();

		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean spanExists = false;
		List<Item> items = new ArrayList<Item>();
		for (Element article : doc.select("article.mjyo_6x")) {
			spanExists = false;
			for (Element div1 : article.select("div.mp0t_ji")) {
				for (Element span1 : div1.select("span._9c44d_KrRuv")) {
					spanExists = true;

				}
			}
			if (spanExists) {
				Item item = new Item();

				for (Element h2 : article.select("h2.mgn2_14")) {
					for (Element a : h2.select("a._w7z6o")) {

						item.setName(a.html().replaceAll(",", ""));

						String line = a.outerHtml().split("href=")[1];
						line = line.split(" ")[0];
						line = line.substring(1, line.length() - 1);
						item.setLinkLine(line);
						break;
					}

				}

				for (Element div1 : article.select("div.mh36_8")) {

					for (Element span1 : div1.select("span")) {
						String line = "";
						String linePrice = "";
						linePrice = span1.html().split("<!-- -->")[0];

						if (linePrice.contains("zł")) {
							item.setOldPrice("" + linePrice.substring(0, linePrice.indexOf(",")) + "."
									+ linePrice.substring(linePrice.indexOf(",") + 1));
						}

						if (span1.html().contains("<!-- -->")) {

							for (Element span2 : span1.select("span")) {

								if (span2.html().contains("<span class=\"_qnmdr\">")) {

									for (Element span3 : span2.select("span")) {
										line = "";
										line = span3.html().split("<!-- -->")[1];
										linePrice = span1.html().split("\n")[0] + "." + line.replaceAll("[^0-9]", "");

										linePrice = linePrice.replace("\n", "").replace("\r", "");
										item.setPrice(linePrice + " zł");
										break;
									}
								}
							}
						}
					}

				}
				items.add(item);
			}

		}

		List<Item> copiedItems = new ArrayList<Item>(items);
		for (Item item : copiedItems) {
			if (item.getName() == null) {
				items.remove(item);

			}
		}

		try {
			doc = Jsoup.connect(secondPage).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Element article : doc.select("article.mjyo_6x")) {
			if (items.size() != 100) {
				spanExists = false;
				for (Element div1 : article.select("div.mp0t_ji")) {
					for (Element span1 : div1.select("span._9c44d_KrRuv")) {
						spanExists = true;

					}
				}
				if (spanExists) {
					Item item = new Item();

					for (Element h2 : article.select("h2.mgn2_14")) {
						for (Element a : h2.select("a._w7z6o")) {

							item.setName(a.html().replaceAll(",", ""));

							String line = a.outerHtml().split("href=")[1];
							line = line.split(" ")[0];
							line = line.substring(1, line.length() - 1);
							item.setLinkLine(line);
							break;
						}

					}

					for (Element div1 : article.select("div.mh36_8")) {

						for (Element span1 : div1.select("span")) {
							String line = "";
							String linePrice = "";
							linePrice = span1.html().split("<!-- -->")[0];

							if (linePrice.contains("zł")) {
								item.setOldPrice("" + linePrice.substring(0, linePrice.indexOf(",")) + "."
										+ linePrice.substring(linePrice.indexOf(",") + 1));
							}

							if (span1.html().contains("<!-- -->")) {

								for (Element span2 : span1.select("span")) {

									if (span2.html().contains("<span class=\"_qnmdr\">")) {

										for (Element span3 : span2.select("span")) {
											line = "";
											line = span3.html().split("<!-- -->")[1];
											linePrice = span1.html().split("\n")[0] + "."
													+ line.replaceAll("[^0-9]", "");

											linePrice = linePrice.replace("\n", "").replace("\r", "");
											item.setPrice(linePrice + " zł");
											break;
										}
									}
								}
							}
						}

					}
					items.add(item);
				}
			} else {
				break;
			}
		}
		for (Item item : items) {
			Document docs = null;
			try {
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String url = item.getLinkLine();
				if (url.contains("https://allegro.pl/events/clicks?emission")) {
					url = url.substring(url.indexOf("https%"), url.length());
					url = url.substring(0, url.indexOf("%3Fbi"));
					url = "https://allegro.pl/oferta/"
							+ url.substring(url.lastIndexOf("https%3A%2F%2Fallegro.pl%2Foferta%2F") + 36);
					item.setLinkLine(url);
				}
				docs = Jsoup.connect(url).timeout(5000).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String line = "";
			String rating = "";
			for (Element div1 : docs.select("div.opbox-sheet")) {
				for (Element div2 : div1.select("div._9a071_W4kYH")) {

					rating = div2.html().split(
							"<div itemprop=\"aggregateRating\" itemscope itemtype=\"http://schema.org/AggregateRating\">")[0];
					for (Element c : div2.select("a")) {
						rating += "(" + c.html() + ")";
					}
					rating = rating.replace("\n", "").replace("\r", "");
					rating = rating.substring(0, rating.indexOf(",")) + "." + rating.substring(rating.indexOf(",") + 1);
					break;
				}
				for (Element ul : div1.select("ul")) {
					if (ul.outerHtml().contains("class=\"_3a4zn _1sql3 _1rj80 _1qltd\"")
							&& ul.html().contains("class=\"_f8818_2jDsV\"")) {
						for (Element li : ul.select("li")) {
							String parameters = "";
							for (Element div3 : li.select("div")) {
								if (div3.outerHtml().contains("class=\"_1qltd _f8818_3-1jj\"")) {
									if (div3.html().contains("class=\"_17qy1 _1vryf _f8818_1X1F-\"")
											&& div3.html().contains("class=\"_17qy1 _f8818_DQKcc\"")) {
										boolean finalParameter = false;
										for (Element div4 : div3.select("div")) {
											if (!div4.html().contains("div")) {
												String parameter = div4.html();
												if (parameter.contains("<!-- -->")) {
													parameter = parameter.split("<!-- -->")[0];
													parameter = parameter.replace("\n", "").replace("\r", "");
												} else if (parameter.contains("<a ")) {
													for (Element a : div4.select("a")) {
														parameter = a.html();
													}
												}
												if (!finalParameter) {
													parameters += parameter + ":";
													finalParameter = true;
												} else {
													parameters += parameter + "; ";
													finalParameter = false;
												}
											}
										}
										break;
									}

								}
							}
							item.setParameters(parameters.replaceAll(",", ""));
							break;
						}

					}

				}

			}
			String maxQuantity = "";
			for (Element div1 : docs.select("div._9a071_1LGgN")) {

				for (Element div2 : div1.select("div._9a071_2CiHR")) {
					for (Element div3 : div2.select("div._9a071_1C4d7")) {
						maxQuantity = div3.html().replaceAll("[^0-9]", "");
						break;
					}
					break;
				}

				line = "";

				for (Element a : div1.select("a._9a071_2aZw0")) {
					for (Element span1 : a.select("span._9hx3e")) {
						if (span1.outerHtml().contains("class=\"_1h7wt _9hx3e _9a071_hxX2b\"")) {
							line += span1.html() + ";";
						}
					}
				}
				if (!rating.equals("")) {
					item.setRating(rating);
				} else {
					item.setRating("no");
				}
				if (!maxQuantity.equals("")) {
					item.setMaxQuantity(maxQuantity);
				} else {
					item.setMaxQuantity("without");
				}
				if (!line.equals("")) {
					line = line.substring(0, line.length() - 1);
					item.setSizes(line);

					break;
				} else {
					item.setSizes("without");

				}
			}
			System.out.println("fashion.csv: "+item);
		}
		Mapper.createOutputFile(items, "fashion.csv");

	}

}
