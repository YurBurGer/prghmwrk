package task14;

public class Pair implements Comparable<Pair>{
	private String title, link;
	public Pair(String title, String link) {
		super();
		this.title = title;
		this.link = link;
	}
	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	@Override
	public int compareTo(Pair o) {
		if(!this.title.equals(o.title))
			return this.title.compareTo(o.title);
		else
			return this.link.compareTo(o.link);
	}
	
}
