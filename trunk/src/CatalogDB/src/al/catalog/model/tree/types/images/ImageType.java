package al.catalog.model.tree.types.images;

public class ImageType {
	
	private Integer key;
	private String value;
	private String text;
	
	public ImageType(int key, String value) {
		this.key = new Integer(key);
		this.value = value;
	}
	
	public ImageType(Integer key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public void setKey(Integer key) {
		this.key = key;
	}
	
	public Integer getKey() {
		return key;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public String toString() {
		return getText();
	}
}
