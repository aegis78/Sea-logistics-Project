package in.saram.address.vo;

public class GreetingVO {
	
	private final long id;
    private final String content;

    public GreetingVO(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}
