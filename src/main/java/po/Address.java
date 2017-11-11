package po;

public class Address {
    private String city;
    private Integer post;

    public Address() {
    }

    public Address(String city, Integer post) {
        this.city = city;
        this.post = post;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPost() {
        return post;
    }

    public void setPost(Integer post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", post=" + post +
                '}';
    }
}
