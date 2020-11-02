package threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import mapper.Mapper;
import models.Item;

public class WorkingThreadOtherCategories implements Runnable {

	private String firstPage;
	private String secondPages;
	private String nameFile;
	
	

	public WorkingThreadOtherCategories(String firstPage, String secondPages, String nameFile) {
		this.firstPage = firstPage;
		this.secondPages = secondPages;
		this.nameFile = nameFile;
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}

	public String getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(String firstPage) {
		this.firstPage = firstPage;
	}

	public String getSecondPages() {
		return secondPages;
	}

	public void setSecondPages(String secondPage) {
		this.secondPages = secondPage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstPage == null) ? 0 : firstPage.hashCode());
		result = prime * result + ((nameFile == null) ? 0 : nameFile.hashCode());
		result = prime * result + ((secondPages == null) ? 0 : secondPages.hashCode());
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
		WorkingThreadOtherCategories other = (WorkingThreadOtherCategories) obj;
		if (firstPage == null) {
			if (other.firstPage != null)
				return false;
		} else if (!firstPage.equals(other.firstPage))
			return false;
		if (nameFile == null) {
			if (other.nameFile != null)
				return false;
		} else if (!nameFile.equals(other.nameFile))
			return false;
		if (secondPages == null) {
			if (other.secondPages != null)
				return false;
		} else if (!secondPages.equals(other.secondPages))
			return false;
		return true;
	}

	public WorkingThreadOtherCategories() {
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
		List<Item> items = new ArrayList<Item>();
		boolean spanExists = false;

		for (Element div : doc.select("div.opbox-listing")) {
			for (Element article : div.select("article")) {
				spanExists = false;
				for (Element div1 : article.select("div.mp0t_ji")) {
					for (Element span1 : div1.select("span._9c44d_KrRuv")) {
						spanExists = true;

					}
				}
				if (spanExists) {
					Item item = new Item();
					for (Element div1 : article.select("div")) {

						for (Element span1 : div1.select("span._9c44d_KrRuv")) {
							item.setOldPrice("" + span1.html().substring(0, span1.html().indexOf(",")) + "."
									+ span1.html().substring(span1.html().indexOf(",") + 1));
						}

						for (Element span1 : div1.select("span")) {
							String line = "";
							String linePrice = "";
							linePrice = span1.html();
							if (!linePrice.contains("zł")) {
								break;
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
					boolean existsItem = false;
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

					for (Item item1 : items) {
						if (item1.getName().equals(item.getName())) {
							existsItem = true;

						}
					}
					if (!existsItem) {
						items.add(item);
					} else {
						continue;
					}

				}

			}

		}

		int currentNumberPage = 1;
		while (items.size() < 100) {
			currentNumberPage++;
			try {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				

				doc = Jsoup.connect(secondPages + currentNumberPage).timeout(10*1000).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			spanExists = false;


			for (Element div : doc.select("div.opbox-listing")) {

				for (Element article : div.select("article")) {

					if (items.size() < 100) {
						spanExists = false;
						for (Element div1 : article.select("div.mp0t_ji")) {
							for (Element span1 : div1.select("span._9c44d_KrRuv")) {
								spanExists = true;

							}
						}
						if (spanExists) {
							Item item = new Item();
							for (Element div1 : article.select("div")) {

								for (Element span1 : div1.select("span._9c44d_KrRuv")) {
									item.setOldPrice("" + span1.html().substring(0, span1.html().indexOf(",")) + "."
											+ span1.html().substring(span1.html().indexOf(",") + 1));
								}

								for (Element span1 : div1.select("span")) {
									String line = "";
									String linePrice = "";
									linePrice = span1.html();
									if (!linePrice.contains("zł")) {
										break;
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
							boolean existsItem = false;
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

							for (Item item1 : items) {
								if (item1.getName().equals(item.getName())) {
									existsItem = true;
								}
							}
							if (!existsItem) {
								items.add(item);
							} else {
								continue;
							}
						}

					} else {
						break;
					}
				}
			}
		}
		for (Item item : items) {
			Document docs = null;
			try {
				try {
					Thread.sleep(100);
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
			for (Element div1 : docs.select("div")) {
				for (Element div2 : div1
						.select("div._9a071_N-e4S._9a071_W4kYH._9a071_25AVD._9a071_1HfJE._9a071_3GCVz")) {

					rating = div2.html().split(
							"<div itemprop=\"aggregateRating\" itemscope itemtype=\"http://schema.org/AggregateRating\">")[0];
					for (Element c : div2.select("a")) {
						rating += "(" + c.html() + ")";
					}
					rating = rating.replace("\n", "").replace("\r", "");
					rating = rating.substring(0, rating.indexOf(",")) + "." + rating.substring(rating.indexOf(",") + 1);
					break;
				}

				for (Element ul : div1.select("ul._3a4zn._1sql3._1rj80._1qltd")) {

					String parameters = "";
					for (Element ul2 : ul.select("ul._1rj80._1sql3._1qltd._f8818_1uDuE")) {

						for (Element li : ul2.select("li")) {

							for (Element div3 : li.select("div")) {

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

							break;
						}

					}
					item.setParameters(parameters.replaceAll(",", ""));
					break;

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
			System.out.println(nameFile+": "+item);
		}

		Mapper.createOutputFile(items, nameFile);
	}

}
