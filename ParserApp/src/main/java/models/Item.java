package models;

public class Item {

	private String name;
	private String price;
	private String oldPrice;
	private String linkLine;
	private String sizes;
	private String maxQuantity;
	private String rating;
	private String parameters;

	public Item(String name, String price, String oldPrice, String linkLine, String sizes, String maxQuantity,
			String rating, String parameters) {
		this.name = name;
		this.price = price;
		this.oldPrice = oldPrice;
		this.linkLine = linkLine;
		this.sizes = sizes;
		this.maxQuantity = maxQuantity;
		this.rating = rating;
		this.parameters = parameters;
	}

	public String getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(String maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getSizes() {
		return sizes;
	}

	public void setSizes(String sizes) {
		this.sizes = sizes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((linkLine == null) ? 0 : linkLine.hashCode());
		result = prime * result + ((maxQuantity == null) ? 0 : maxQuantity.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((oldPrice == null) ? 0 : oldPrice.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((sizes == null) ? 0 : sizes.hashCode());
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
		Item other = (Item) obj;
		if (linkLine == null) {
			if (other.linkLine != null)
				return false;
		} else if (!linkLine.equals(other.linkLine))
			return false;
		if (maxQuantity == null) {
			if (other.maxQuantity != null)
				return false;
		} else if (!maxQuantity.equals(other.maxQuantity))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (oldPrice == null) {
			if (other.oldPrice != null)
				return false;
		} else if (!oldPrice.equals(other.oldPrice))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (sizes == null) {
			if (other.sizes != null)
				return false;
		} else if (!sizes.equals(other.sizes))
			return false;
		return true;
	}

	public String getLinkLine() {
		return linkLine;
	}

	public void setLinkLine(String linkLine) {
		this.linkLine = linkLine;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}

	public Item() {
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ", price=" + price + ", oldPrice=" + oldPrice + ", linkLine=" + linkLine
				+ ", sizes=" + sizes + ", maxQuantity=" + maxQuantity + ", rating=" + rating + ", parameters="
				+ parameters + "]";
	}
}
