package co.tiagoaguiar.codelab.myapplication;

public class Register {
    public String type;
    public double response;
    public String createdDate;

    @Override
    public String toString() {
        return "Register{" +
                "type='" + type + '\'' +
                ", response=" + response +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getResponse() {
        return response;
    }

    public void setResponse(double response) {
        this.response = response;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
