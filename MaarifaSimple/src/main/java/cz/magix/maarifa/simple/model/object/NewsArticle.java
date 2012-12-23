package cz.magix.maarifa.simple.model.object;

import org.joda.time.DateTime;

public class NewsArticle extends AbstractObject {
	private String title;
	private DateTime publishingDateTime;
	private String text;

	/*
	 * Getters & Setters
	 */
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public DateTime getPublishingDateTime() {
		return publishingDateTime;
	}
	public void setPublishingDateTime(DateTime publishingDateTime) {
		this.publishingDateTime = publishingDateTime;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
