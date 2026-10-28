package project;
import java.util.Date;

	
public abstract class Shape implements Drawable {
	    private Date dateCreated;
	    private String color;

	    public abstract String getname();

	    public Date getDateCreated() {
	        return dateCreated;
	    };

	    public String getColor() {
	        return color;
	    }

	    public abstract double getArea();
	    public abstract double getPerimeter();

	    public Shape(){
	        this.dateCreated = new Date();
	        this.color = "black";
	    }

	    public Shape(String color){
	        this.dateCreated = new Date();
	        this.color = color;
	    }
}

	
